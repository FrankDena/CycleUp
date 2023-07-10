package it.uniroma3.cu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.cu.model.Servizio;
import it.uniroma3.cu.service.ServizioService;
import jakarta.validation.Valid;

@Controller
public class ServizioController {
	
	@Autowired ServizioValidator servizioValidator;
	@Autowired ServizioService servizioService;
	
	@GetMapping("/admin/formNewServizio")
	public String formNewServizio(Model model) {
		model.addAttribute("servizio", new Servizio());
		return "admin/formNewServizio.html";
	}
	
	@PostMapping("/admin/servizi")
	public String newServizio(@Valid @ModelAttribute("servizio") Servizio servizio,
			BindingResult bindingResult ,Model model) {
		this.servizioValidator.validate(servizio, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.servizioService.saveServizio(servizio);
			model.addAttribute("servizio",servizio);
			return "servizio.html";
		} else {
			return "admin/formNewServizio.html";
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
	
	@GetMapping("/admin/manageServizi")
	public String manageServizi(Model model) {
		model.addAttribute("servizi", this.servizioService.findAll());
		return "admin/manageServizi.html";
	}
	
	@GetMapping("/admin/formUpdateServizio/{id}")
	public String formUpdateServizio (@PathVariable("id")Long id, Model model) {
		model.addAttribute("servizio",this.servizioService.findById(id));
		return "admin/formUpdateServizio.html";
	}
	
	@PostMapping("/editTitle/{id}")
	public String editTitle(@PathVariable("id") Long id, Model model,@RequestParam String title) {
		Servizio servizio = this.servizioService.findById(id);
		servizio.setTitle(title);
		this.servizioService.saveServizio(servizio);
		model.addAttribute("servizio",this.servizioService.findById(id));
		return "admin/formUpdateServizio.html";
	}
	
	@PostMapping("/editDescription/{id}")
	public String editDescription(@PathVariable("id") Long id, Model model,@RequestParam String description) {
		Servizio servizio = this.servizioService.findById(id);
		servizio.setDescription(description);
		this.servizioService.saveServizio(servizio);
		model.addAttribute("servizio",this.servizioService.findById(id));
		return "admin/formUpdateServizio.html";
	}
	
	@PostMapping("/editPrice/{id}")
	public String editPrice(@PathVariable("id") Long id, Model model,@RequestParam Float price) {
		Servizio servizio = this.servizioService.findById(id);
		servizio.setPrice(price);
		this.servizioService.saveServizio(servizio);
		model.addAttribute("servizio",this.servizioService.findById(id));
		return "admin/formUpdateServizio.html";
	}
	
}
