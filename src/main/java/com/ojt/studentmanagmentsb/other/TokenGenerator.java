package com.ojt.studentmanagmentsb.other;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {
	public String generateToken() {
		return UUID.randomUUID().toString();
	}
}
