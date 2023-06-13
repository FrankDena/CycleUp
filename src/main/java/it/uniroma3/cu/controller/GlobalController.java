package it.uniroma3.cu.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = {PrenotazioneController.class, ServizioController.class, PrestazioneController.class, ReviewController.class})
public class GlobalController {
	
	@ModelAttribute("user")
	public UserDetails getUser() {
		UserDetails user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return user;
	}
}
