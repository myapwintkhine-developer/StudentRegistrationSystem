package com.ojt.studentmanagmentsb.other;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.service.UserService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Component
public class LoadJspTemplate {
	
	@Autowired UserService userservice;
	
	public JasperReport loadTemplate() throws JRException, IOException {
	ClassPathResource resource=new ClassPathResource("report.jrxml");
	return JasperCompileManager.compileReport(resource.getInputStream());
	}
	
	public void generateReport() throws JRException, IOException {
		List<User> data = userservice.getAllUsers();
	    
	    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

		JasperReport jasperReport = loadTemplate();
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
	    String outputFile = System.getProperty("user.home") + "/Downloads/userreport.pdf";

	    JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
	    
	    JRXlsxExporter exporter = new JRXlsxExporter();
	    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(System.getProperty("user.home") +"/Downloads/userreport.xlsx"));
	    
	    SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
	    configuration.setDetectCellType(true);
	    configuration.setRemoveEmptySpaceBetweenColumns(true);
	    configuration.setRemoveEmptySpaceBetweenRows(true);
	    exporter.setConfiguration(configuration);
	    configuration.setWhitePageBackground(false);
	    configuration.setCollapseRowSpan(false);
	    configuration.setIgnoreGraphics(false);
	    exporter.exportReport();


	}

}
