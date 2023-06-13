package it.uniroma3.cu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import it.uniroma3.cu.model.Prenotazione;
import it.uniroma3.cu.model.Prestazione;
import it.uniroma3.cu.service.CredentialsService;
import it.uniroma3.cu.service.PrenotazioneService;
import it.uniroma3.cu.service.PrestazioneService;
import it.uniroma3.cu.service.ServizioService;
import it.uniroma3.cu.service.UserService;
import jakarta.validation.Valid;

@Controller
public class PrestazioneController {
	
	@Autowired PrestazioneValidator prestazioneValidator;
	@Autowired PrestazioneService prestazioneService;
	@Autowired PrenotazioneService prenotazioneService;
	@Autowired UserService userService;
	@Autowired CredentialsService credentialsService;
	@Autowired ServizioService servizioService;
	
	@GetMapping("/selectPrenotazione/{id}")
	public String selectPrenotazione(@PathVariable("id")Long id, Model model) {
		model.addAttribute("prenotazioni",this.prenotazioneService.findAll());
		model.addAttribute("prestazione",this.prestazioneService.findById(id));
		return "selectPrenotazione.html";
	}
	
	@GetMapping("/setPrenotazioneToPrestazione/{idPren}/{idPres}")
	public String setPrenotazioneToPrestazione(@PathVariable("idPren")Long idPren, @PathVariable("idPres")Long idPres, Model model) {
		Prenotazione prenotazione = this.prenotazioneService.findById(idPren);
		Prestazione prestazione = this.prestazioneService.findById(idPres);
		
		prestazione.setPrenotazione(prenotazione);
		prenotazione.setPrestazione(prestazione);
		
		this.prenotazioneService.savePrenotazione(prenotazione);
		this.prestazioneService.savePrestazione(prestazione);
		
		model.addAttribute("prestazione",prestazione);
		return "prestazione.html";
	}
	
	@GetMapping("/admin/formNewPrestazione")
	public String formNewPrestazione(Model model) {
		model.addAttribute("prestazione", new Prestazione());
		return "admin/formNewPrestazione.html";
	}
	
	@PostMapping("/admin/prestazioni")
	public ModelAndView newPrestazione(@Valid @ModelAttribute("prestazione") Prestazione prestazione,
			BindingResult bindingResult , Model model) {
		this.prestazioneValidator.validate(prestazione, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.prestazioneService.savePrestazione(prestazione);
			model.addAttribute("prestazione",prestazione);
			return new ModelAndView("redirect:/selectPrenotazione/"+prestazione.getId());
		} else {
			return new ModelAndView("admin/formNewPrestazione.html");
		}
	}
	
	@GetMapping("/prestazioni/{id}")
	public String getPrestazione(@PathVariable("id")Long id, Model model) {
		Prestazione prestazione = this.prestazioneService.findById(id);
		model.addAttribute("prestazione",prestazione);
		return "prestazione.html";
	}
	
	@GetMapping("/prestazioni")
	public String showPrestazioni(Model model) {
		model.addAttribute("prestazioni",this.prestazioneService.findAll());
		return "prestazioni.html";
	}
	
}
