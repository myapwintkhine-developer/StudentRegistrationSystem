package com.ojt.studentmanagmentsb;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ojt.studentmanagmentsb.service.EmailService;
import jakarta.mail.internet.MimeMessage;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestEmailService {

	@Mock
	private JavaMailSender javaMailSender;
	
//	@Before
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//	}

	
	@Test
	public void testEmailService() {
		
//		EmailService emailservice=new EmailService(javaMailSender);
//		String toEmail = "recipient@example.com";
//        String subject = "Test Subject";
//        String body = "Test Body";
//        
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
//        when(javaMailSender.createMimeMessageHelper()).thenReturn(messageHelper);
//
//        emailservice.sendEmail(toEmail, subject, body);
//
//        // Verify that the send() method is called once
//        verify(javaMailSender).send(mimeMessage);






	}
}
