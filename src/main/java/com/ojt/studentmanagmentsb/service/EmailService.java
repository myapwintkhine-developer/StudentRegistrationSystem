package com.ojt.studentmanagmentsb.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	private JavaMailSender javaMailSender = null;
	
	public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
	
	public void sendEmail(String toEmail,String subject, String body) {
	MimeMessage message = javaMailSender.createMimeMessage();
	MimeMessageHelper helper;
	try {
		helper = new MimeMessageHelper(message, true);
		helper.setFrom("myapwintkhine99@gmail.com");
		helper.setTo(toEmail);
		helper.setSubject(subject);
		helper.setText(body, true); // Set the content type to HTML
	} catch (MessagingException e) {
		
		e.printStackTrace();
	}
	

	javaMailSender.send(message);
	}
}
