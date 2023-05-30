package it.uniroma3.cu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.cu.model.Credentials;
import it.uniroma3.cu.model.Prenotazione;
import it.uniroma3.cu.model.User;
import it.uniroma3.cu.service.CredentialsService;
import it.uniroma3.cu.service.PrenotazioneService;
import it.uniroma3.cu.service.UserService;
import jakarta.validation.Valid;

@Controller
public class PrenotazioneController {
	
	@Autowired PrenotazioneValidator prenotazioneValidator;
	@Autowired PrenotazioneService prenotazioneService;
	@Autowired UserService userService;
	@Autowired CredentialsService credentialsService;
	
	@GetMapping("/formNewPrenotazione")
	public String formNewPrenotazione(Model model) {
		model.addAttribute("prenotazione", new Prenotazione());
		return "formNewPrenotazione.html";
	}
	
	@PostMapping("/prenotazioni")
	public String newPrenotazione(@Valid @ModelAttribute("prenotazione") Prenotazione prenotazione,
			BindingResult bindingResult ,Model model) {
		this.prenotazioneValidator.validate(prenotazione, bindingResult);
		if (!bindingResult.hasErrors()) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = credentialsService.getCredentials(username).getUser();
			prenotazione.setCliente(user);
			this.prenotazioneService.savePrenotazione(prenotazione);
			user.getPrenotazioni().add(prenotazione);
			this.userService.saveUser(user);
			model.addAttribute("prenotazione",prenotazione);
			return "prenotazione.html";
		} else {
			return "formNewPrenotazione.html";
		}
	}
	
	@GetMapping("/prenotazioni/{id}")
	public String getPrenotazione(@PathVariable("id")Long id, Model model) {
		Prenotazione prenotazione = this.prenotazioneService.findById(id);
		model.addAttribute("prenotazione",prenotazione);
		return "prenotazione.html";
	}
	
	@GetMapping("/prenotazioni")
	public String showPrenotazioni(Model model) {
		model.addAttribute("prenotazioni",this.prenotazioneService.findAll());
		return "prenotazioni.html";
	}
	
}
