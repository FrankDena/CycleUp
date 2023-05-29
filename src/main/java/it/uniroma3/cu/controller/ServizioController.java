package it.uniroma3.cu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.cu.model.Review;
import it.uniroma3.cu.model.Servizio;
import it.uniroma3.cu.service.ServizioService;
import jakarta.validation.Valid;

@Controller
public class ServizioController {
	
	@Autowired ServizioValidator servizioValidator;
	@Autowired ServizioService servizioService;
	
	@GetMapping("/formNewServizio")
	public String formNewServizio(Model model) {
		model.addAttribute("servizio", new Servizio());
		return "formNewServizio.html";
	}
	
	@PostMapping("/servizi")
	public String newServizio(@Valid @ModelAttribute("servizio") Servizio servizio,
			BindingResult bindingResult ,Model model) {
		this.servizioValidator.validate(servizio, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.servizioService.saveServizio(servizio);
			model.addAttribute("servizio",servizio);
			return "servizio.html";
		} else {
			//model.addAttribute("messaggioErrore","Questa review esiste gia'");
			return "formNewServizio.html";
		}
	}
	
	@GetMapping("/servizi/{id}")
	public String getServizio(@PathVariable("id")Long id, Model model) {
		Servizio servizio = this.servizioService.findById(id);
		model.addAttribute("servizio",servizio);
		return "servizio.html";
	}
	
	@GetMapping("/servizi")
	public String showServizi(Model model) {
		model.addAttribute("servizi",this.servizioService.findAll());
		return "servizi.html";
	}
	
}
