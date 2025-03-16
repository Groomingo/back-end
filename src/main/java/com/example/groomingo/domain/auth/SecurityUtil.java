package com.example.groomingo.domain.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityUtil {

	static String getEmailFromToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails)principal).getUsername();
		} else {
			throw new SecurityException("Invalid token");
		}
	}
}
