package com.ojt.studentmanagmentsb.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ojt.studentmanagmentsb.dto.UserDto;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.entity.UserView;
import com.ojt.studentmanagmentsb.other.LoadJspTemplate;
import com.ojt.studentmanagmentsb.other.TokenGenerator;
import com.ojt.studentmanagmentsb.repository.UserRepository;
import com.ojt.studentmanagmentsb.service.EmailService;
import com.ojt.studentmanagmentsb.service.UserService;
import com.ojt.studentmanagmentsb.service.UserViewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Controller
public class UserController {

	@Autowired
	UserService userservice;
	@Autowired
	TokenGenerator tokengenerator;
	@Autowired
	EmailService emailservice;
	@Autowired
	UserRepository userrepo;
	@Autowired
	UserViewService userviewservice;
	@Autowired
	LoadJspTemplate loadJspReport;

	@GetMapping("/register-user")
	public ModelAndView registerUser(Model model) {
		model.getAttribute("userRegisterSuccess");
		model.getAttribute("userRegisterError");
		return new ModelAndView("USR001", "userdto", new UserDto());
	}

	@PostMapping("/register-user")
	public String registerUserPost(@ModelAttribute("userdto") UserDto userdto, Model model,
			RedirectAttributes redirectAttribute, BindingResult bindingResult, @RequestParam("email") String email) {

		User user = new User();
		user.setUsername(userdto.getUsername());
		user.setEmail(userdto.getEmail());
		user.setRole("USER");
		user.setStatus("INACTIVE");

		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		String registerDate = currentDate.format(formatter);
		user.setRegisterDate(registerDate);

		String hashedPassword = BCrypt.hashpw(userdto.getPassword(), BCrypt.gensalt());
		user.setPassword(hashedPassword);

		// for email
		user.setEmailverified(false);

		String email_verify_token = tokengenerator.generateToken();
		user.setEmailtoken(email_verify_token);

		String email_expiration_date = LocalDateTime.now().plusHours(24).toString();
		user.setEmail_expiration_date(email_expiration_date);

		boolean createUser = userservice.createUser(user);

		String toEmail = user.getEmail();
		String subject = "Email Verification";
		String body = "Dear " + user.getUsername() + "\n\nPlease click the lick to verify your email "
				+ "<a href='http://localhost:8080/verify-email/" + user.getEmailtoken() + "'>Verify Email</a>";
		emailservice.sendEmail(toEmail, subject, body);

		if (createUser) {
			redirectAttribute.addFlashAttribute("userRegisterSuccess",
					"You have successfully registered your account. Please check your email to verify.");
		} else {
			redirectAttribute.addFlashAttribute("userRegisterError",
					"Something went wrong when registering your account. Please try again.");
		}
		return "redirect:/register-user";

	}

	@GetMapping("/verify-email/{email_verify_token}")
	public String verifyEmail(@PathVariable("email_verify_token") String email_verify_token, Model model,
			RedirectAttributes redirectAttribute) {
		// User user=userservice.getUserByEmailToken(email_verify_token);
		System.out.println(email_verify_token);
		User user = userservice.getUserByEmailToken(email_verify_token);
		if (user != null) {
			System.out.println("4");
			// LocalDateTime
			// expire_date=LocalDateTime.parse(user.getEmail_expiration_date());
			if (!user.isEmailverified()) {
				user.setEmailverified(true);
				user.setStatus("ACTIVE");
				userservice.createUser(user);
			} else if (user.isEmailverified()) {
				model.addAttribute("emailAlreadyVerifyed", "You have already verified your email.");

			}
//			else if(!expire_date.isBefore(LocalDateTime.now())){
//				model.addAttribute("verifyExpireError", "Your email verification has expired");
//				System.out.println(LocalDateTime.now());
//			}
		} else {
			redirectAttribute.addFlashAttribute("verifyError",
					"Something went wrong when verifying your email. Please try again.");
		}
		return "redirect:/login";
	}

	@GetMapping("/register-admin")
	public ModelAndView registerAdmin(Model model) {
		model.getAttribute("userRegisterSuccess");
		model.getAttribute("userRegisterError");
		return new ModelAndView("ADM001", "userdto", new UserDto());
	}

	@PostMapping("/register-admin")
	public String registerAdmin(@ModelAttribute("userdto") @Validated UserDto userdto,
			@RequestParam("code") String code, Model model, RedirectAttributes redirectAttribute,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors() || code.equals("")) {
			model.addAttribute("codeEmptyError", "Code can't blank.");
			model.addAttribute("userdto", userdto);
			return "USR001";
		}

		String verifyCode = "12345";
		if (verifyCode.equals(code)) {
			User user = new User();
			user.setUsername(userdto.getUsername());
			user.setEmail(userdto.getEmail());
			user.setRole("ADMIN");
			user.setStatus("INACTIVE");

			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
			String registerDate = currentDate.format(formatter);
			user.setRegisterDate(registerDate);

			String hashedPassword = BCrypt.hashpw(userdto.getPassword(), BCrypt.gensalt());
			user.setPassword(hashedPassword);

			// for email
			user.setEmailverified(false);

			String email_verify_token = tokengenerator.generateToken();
			user.setEmailtoken(email_verify_token);

			String email_expiration_date = LocalDateTime.now().plusHours(24).toString();
			user.setEmail_expiration_date(email_expiration_date);

			boolean createUser = userservice.createUser(user);

			String toEmail = user.getEmail();
			String subject = "Email Verification";
			String body = "Dear " + user.getUsername() + "\n\nPlease click the lick to verify your email "
					+ "<a href='http://localhost:8080/verify-email/" + user.getEmailtoken() + "'>Verify Email</a>";
			emailservice.sendEmail(toEmail, subject, body);

			if (createUser) {
				redirectAttribute.addFlashAttribute("userRegisterSuccess",
						"You have successfully registered your account. Please check your email to verify.");
			} else {
				redirectAttribute.addFlashAttribute("userRegisterError",
						"Something went wrong when registering your account. Please try again.");
			}

			return "redirect:/register-admin";
		} else {
			model.addAttribute("wrongCodeError", "Verify code is incorrect!");
			model.addAttribute("userdto", userdto);
			return "ADM001";
		}
	}

	@GetMapping("/select-one-user/{id}")
	public ModelAndView selectOneUser(@PathVariable("id") int id) {
		User oneUser = userservice.getUserById(id);
		return new ModelAndView("USR002", "oneUser", oneUser);
	}

	@PostMapping("/update-user")
	public String updateUser(@ModelAttribute("oneUser") @Validated User oneUser, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("oneUser", oneUser);
			return "USR002";
		}
		User user = new User();
		user = userservice.getUserById(oneUser.getId());
		user.setUsername(oneUser.getUsername());
		user.setEmail(oneUser.getEmail());
		user.setRole(oneUser.getRole());
		if (!oneUser.getPassword().equals("") || oneUser.getPassword() != null) {
			String hashedPassword = BCrypt.hashpw(oneUser.getPassword(), BCrypt.gensalt());
			user.setPassword(hashedPassword);
		}
		boolean updateUser = userservice.updateUser(user);
		if (updateUser) {
			model.addAttribute("userUpdateSuccess", "User update success!");
			return "redirect:/view-all-users";
		} else {
			User failedUser = user;
			model.addAttribute("oneUser", failedUser);
			model.addAttribute("userUpdateError", "User update failed. Please try again.");
			return "USR002";
		}

	}

	@GetMapping("/view-all-users")
	public String selectAllUsers(Model model) {
		List<User> user_list = userservice.getUserByEmailverifiedTrue();
		model.addAttribute("user_list", user_list);
		return "USR003";
	}

	@GetMapping("/deactivate-user/{id}")
	public String inactivateUser(@PathVariable("id") int id, Model model) {
		User user = userservice.getUserById(id);
		user.setStatus("INACTIVE");
		boolean updateUser = userservice.updateUser(user);
		if (updateUser) {
			model.addAttribute("userDeactivateSuccess", "You have deactivated the user successfully.");
		} else {
			model.addAttribute("userDeactivateError", "User deactivation failed. Please try again.");
		}
		return "redirect:/view-all-users";
	}

	@GetMapping("/activate-user/{id}")
	public String activateUser(@PathVariable("id") int id, Model model) {
		User user = userservice.getUserById(id);
		user.setStatus("ACTIVE");
		boolean updateUser = userservice.updateUser(user);
		if (updateUser) {
			model.addAttribute("userActivateSuccess", "You have deactivated the user successfully.");
		} else {
			model.addAttribute("userActivateError", "User deactivation failed. Please try again.");
		}
		return "redirect:/view-all-users";
	}

	@GetMapping("/view-active-users")
	public String selectActiveUsers(Model model) {
		List<User> active_user_list = userservice.getUserByStatus("ACTIVE");
		model.addAttribute("active_user_list", active_user_list);
		return "";
	}

	@GetMapping("/view-inactive-users")
	public String selectInactiveUsers(Model model) {
		List<User> inactive_user_list = userservice.getUserByStatus("INACTIVE");
		model.addAttribute("inactive_user_list", inactive_user_list);
		return "";
	}

	@PostMapping("/user-search")
	public String userSearchPost(@RequestParam("id") String user_id, @RequestParam("username") String username,
			@RequestParam("status") String status, Model model) {
		int id = 0;
		User user = new User();
		UserView searchUser = new UserView();
		List<UserView> searchUserList = new ArrayList<UserView>();

		if (!user_id.equals("")) {
			id = Integer.parseInt(user_id);
		}

		if (!user_id.equals("") || username != null || !username.equals("") || status != null || !status.equals("")) {
			user.setId(id);
			user.setUsername(username);
			user.setStatus(status);

			if (user.getUsername() != null && !user.getUsername().equals("") && user.getStatus() != null
					&& !user.getStatus().equals("") && user.getId() > 0) {
				searchUser = userviewservice.searchUserByIdNameStatus(id, username, status);
				if (searchUser == null) {
					model.addAttribute("searchError", "User not found!");
				} else {

					model.addAttribute("searchUser", searchUser);
				}
			}

			else if (user.getUsername() != null && !user.getUsername().equals("") && user.getId() > 0) {

				searchUser = userviewservice.searchUserByIdName(user.getId(), user.getUsername());
				if (searchUser == null) {
					model.addAttribute("searchError", "User not found!");
				} else {

					model.addAttribute("searchUser", searchUser);
				}
			}

			else if (user.getId() > 0 && !user.getStatus().equals("") && user.getUsername().equals("")) {
				searchUser = userviewservice.searchUserByIdStatus(user.getId(), user.getStatus());
				if (searchUser == null) {
					model.addAttribute("searchError", "User not found!");
				} else {

					model.addAttribute("searchUser", searchUser);
				}
			}

			else if (user.getId() <= 0 && !user.getUsername().equals("") && !user.getStatus().equals("")) {
				searchUserList = userviewservice.searchUserByNameStatus(user.getUsername(), user.getStatus());
				if (searchUserList == null) {
					model.addAttribute("searchError", "User not found!");
				} else {
					model.addAttribute("searchUserList", searchUserList);
				}
			}

			else if (user.getId() <= 0 && !user.getUsername().equals("") && user.getStatus().equals("")) {
				searchUserList = (List<UserView>) userviewservice.searchUserByUsername(user.getUsername());
				if (searchUserList == null) {
					model.addAttribute("searchError", "User not found!");
				} else {
					model.addAttribute("searchUserList", searchUserList);
				}
			}

			else if (user.getId() > 0 && user.getUsername().equals("") && user.getStatus().equals("")) {
				searchUser = userviewservice.searchById(user.getId());
				if (searchUser == null) {
					model.addAttribute("searchError", "User not found!");
				} else {
					model.addAttribute("searchUser", searchUser);
				}
			}

			else if (user.getId() <= 0 && user.getUsername().equals("") && !user.getStatus().equals("")) {
				searchUserList = userviewservice.searchUserByStatus(user.getStatus());

				if (searchUserList == null) {
					model.addAttribute("searchError", "User not found!");
				} else {
					model.addAttribute("searchUserList", searchUserList);
				}
			}

			return "USR004";
		} else {
			model.addAttribute("searchError", "User not found!");
			return "redirect:/view-all-users";
		}
	}

	@GetMapping("/download-user-report/{type}")
	public void downloadReport(@PathVariable("type") String type, HttpServletResponse response,
			HttpServletRequest request) throws IOException, JRException {
		ClassPathResource resource = new ClassPathResource("userreport.jrxml");
		String path = resource.getFile().getAbsolutePath();

		// Retrieve data for the report (assuming you have a service to fetch the data)
		List<UserView> list = userviewservice.getAllUsersFromView();

		// Parameters for the report
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ReportTitle", "User List");

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

		JasperReport jasperReport = JasperCompileManager.compileReport(path);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

		if (type.equalsIgnoreCase("pdf")) {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=userreport.pdf");

			OutputStream out = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
			out.flush();
			out.close();
		} else if (type.equalsIgnoreCase("excel")) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=userreport.xlsx");

			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

			SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
			configuration.setDetectCellType(true);
			configuration.setRemoveEmptySpaceBetweenColumns(true);
			configuration.setRemoveEmptySpaceBetweenRows(true);
			exporter.setConfiguration(configuration);
			configuration.setWhitePageBackground(false);
			configuration.setCollapseRowSpan(false);
			configuration.setIgnoreGraphics(false);
			exporter.exportReport();
		} else {
			// Handle invalid type parameter
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report type");
		}
	}

}
