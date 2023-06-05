package it.uniroma3.cu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.cu.model.Credentials;
import it.uniroma3.cu.model.User;
import it.uniroma3.cu.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	
	@GetMapping(value = "/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegisterUser";
	}
	
	@GetMapping(value = "/login")
	public String showLoginForm (Model model) {
		return "formLogin";
	}
	
	
	@GetMapping(value = "/")
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return "index.html";
		}
		else {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				model.addAttribute("user",(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
				return "admin/indexAdmin.html";
			}
			if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
				model.addAttribute("user",(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
				return "index.html";
			}
		}
		return "index.html";
	}
	
	/*@GetMapping(value = "/indexMovie")
	public String indexMovie(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return "indexMovie.html";
		}
		else {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				model.addAttribute("user",(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
				return "admin/indexMovie.html";
			}
			if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
				model.addAttribute("user",(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
				return "indexMovie.html";
			}
		}
		return "indexMovie.html";
	}*/
	
	@GetMapping(value = "/success")
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			model.addAttribute("user",(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			return "admin/indexAdmin.html";
		}
		if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
			model.addAttribute("user",(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			return "index.html";
		}
		return "index.html";
	}
	
	@PostMapping(value = { "/register" })
	public String registerUser(@Valid @ModelAttribute("user")User user,
			BindingResult userBindingResult, @Valid
			@ModelAttribute("credentials")Credentials credentials,
			BindingResult credentialsBindingResult,
			Model model) {
		if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			model.addAttribute("user",user);
			return "registrationSuccessful";
		}
		return "registerUser";
	}
}