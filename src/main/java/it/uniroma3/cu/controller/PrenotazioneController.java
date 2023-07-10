package it.uniroma3.cu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import it.uniroma3.cu.model.Credentials;
import it.uniroma3.cu.model.Prenotazione;
import it.uniroma3.cu.model.Servizio;
import it.uniroma3.cu.model.User;
import it.uniroma3.cu.service.CredentialsService;
import it.uniroma3.cu.service.PrenotazioneService;
import it.uniroma3.cu.service.ServizioService;
import it.uniroma3.cu.service.UserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class PrenotazioneController {
	
	@Autowired PrenotazioneValidator prenotazioneValidator;
	@Autowired PrenotazioneService prenotazioneService;
	@Autowired UserService userService;
	@Autowired CredentialsService credentialsService;
	@Autowired ServizioService servizioService;
	
	@GetMapping("/formNewPrenotazione")
	public String formNewPrenotazione(Model model) {
		model.addAttribute("prenotazione", new Prenotazione());
		return "formNewPrenotazione.html";
	}
	
	@PostMapping("/prenotazioni")
	public ModelAndView newPrenotazione(@Valid @ModelAttribute("prenotazione") Prenotazione prenotazione,
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
			return new ModelAndView("redirect:/addServiziToPrenotazione/"+prenotazione.getId());
		} else {
			return new ModelAndView("formNewPrenotazione.html");
		}
	}
	
	@GetMapping("/addServiziToPrenotazione/{id}")
	public String addServizi(@PathVariable("id")Long id, Model model) {
		List<Servizio> serviziNonScelti = this.servizioService.findAllByPrenotazioniNotContaining(this.prenotazioneService.findById(id));
		List<Servizio> serviziScelti = this.prenotazioneService.findById(id).getServizi();
		model.addAttribute("serviziNonScelti",serviziNonScelti);
		model.addAttribute("serviziScelti",serviziScelti);
		model.addAttribute("prenotazione",this.prenotazioneService.findById(id));
		return "addServizi.html";
	}
	
	@Transactional
	@GetMapping("/setServizioToPrenotazione/{idServizio}/{idPrenotazione}")
	public String setServizioToPrenotazione(Model model, @PathVariable("idServizio")Long idServizio,@PathVariable("idPrenotazione")Long idPrenotazione) {
		Servizio servizio = this.servizioService.findById(idServizio);
		Prenotazione prenotazione = this.prenotazioneService.findById(idPrenotazione);
		prenotazione.addServizio(servizio);
		servizio.addPrenotazione(prenotazione);
		
		this.prenotazioneService.savePrenotazione(prenotazione);
		this.servizioService.saveServizio(servizio);
		
		List<Servizio> serviziNonScelti = this.servizioService.findAllByPrenotazioniNotContaining(prenotazione);
		List<Servizio> serviziScelti = prenotazione.getServizi();
		model.addAttribute("serviziNonScelti",serviziNonScelti);
		model.addAttribute("serviziScelti",serviziScelti);
		model.addAttribute("prenotazione",prenotazione);
		return "addServizi.html";
	}
	
	@Transactional
	@GetMapping("/removeServizioFromPrenotazione/{idServizio}/{idPrenotazione}")
	public String removeServizioFromPrenotazione(Model model, @PathVariable("idServizio")Long idServizio,@PathVariable("idPrenotazione")Long idPrenotazione) {
		Servizio servizio = this.servizioService.findById(idServizio);
		Prenotazione prenotazione = this.prenotazioneService.findById(idPrenotazione);
		prenotazione.getServizi().remove(servizio);
		servizio.getPrenotazioni().remove(prenotazione);
		
		this.prenotazioneService.savePrenotazione(prenotazione);
		this.servizioService.saveServizio(servizio);
		
		List<Servizio> serviziNonScelti = this.servizioService.findAllByPrenotazioniNotContaining(prenotazione);
		List<Servizio> serviziScelti = prenotazione.getServizi();
		model.addAttribute("serviziNonScelti",serviziNonScelti);
		model.addAttribute("serviziScelti",serviziScelti);
		model.addAttribute("prenotazione",prenotazione);
		return "addServizi.html";
	}
	
	@GetMapping("/prenotazioni/{id}")
	public String getPrenotazione(@PathVariable("id")Long id, Model model) {
		Prenotazione prenotazione = this.prenotazioneService.findById(id);
		model.addAttribute("prenotazione",prenotazione);
		return "prenotazione.html";
	}
	
	@GetMapping("/prenotazioni")
	public String showPrenotazioni(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User cliente = credentials.getUser();
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			model.addAttribute("prenotazioni",this.prenotazioneService.findAll());
		}
		else {
			model.addAttribute("prenotazioni",this.prenotazioneService.findAllByCliente(cliente));
		}
		return "prenotazioni.html";
	}
}
