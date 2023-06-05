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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.uniroma3.cu.model.Prenotazione;
import it.uniroma3.cu.model.Prestazione;
import it.uniroma3.cu.model.Review;
import it.uniroma3.cu.service.PrestazioneService;
import it.uniroma3.cu.service.ReviewService;
import jakarta.validation.Valid;

@Controller
public class ReviewController {
	//@Autowired ReviewRepository reviewRepository;
	@Autowired ReviewValidator reviewValidator;
	@Autowired ReviewService reviewService;
	@Autowired PrestazioneService prestazioneService;
	
	@GetMapping("/formNewReview")
	public String formNewReviewDefault(Model model) {
		Review review = new Review();
		model.addAttribute("review", review);
		return "formNewReview.html";
	}
	
	@PostMapping("/reviews")
	public ModelAndView newReviewDefault(@Valid @ModelAttribute("review") Review review,
			BindingResult bindingResult ,Model model) {
		this.reviewValidator.validate(review, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.reviewService.saveReview(review);
			model.addAttribute("review",review);
			return new ModelAndView("redirect:/selectPrestazione/"+review.getId());
		} else {
			//model.addAttribute("messaggioErrore","Questa review esiste gia'");
			return new ModelAndView("formNewReview.html");
		}
	}
	
	@GetMapping("/selectPrestazione/{id}")
	public String selectPrestazione(@PathVariable("id")Long id, Model model) {
		model.addAttribute("prestazioni",this.prestazioneService.findAll());
		model.addAttribute("recensione",this.reviewService.findById(id));
		return "selectPrestazione.html";
	}
	
	@GetMapping("/setPrestazioneToRecensione/{idPres}/{idRec}")
	public String setPrestazioneToRecensione(@PathVariable("idPres")Long idPres, @PathVariable("idRec")Long idRec, Model model) {
		Prestazione prestazione = this.prestazioneService.findById(idPres);
		Review recensione = this.reviewService.findById(idRec);
		
		prestazione.setRecensione(recensione);
		recensione.setPrestazione(prestazione);
		
		this.reviewService.saveReview(recensione);
		this.prestazioneService.savePrestazione(prestazione);
		
		model.addAttribute("review",recensione);
		return "review.html";
	}
	
	@GetMapping("/reviews/{id}")
	public String getReview(@PathVariable("id")Long id, Model model) {
		Review review = this.reviewService.findById(id);
		model.addAttribute("review",review);
		return "review.html";
	}
	
	@GetMapping("/reviews")
	public String showReviews(Model model) {
		model.addAttribute("reviews",this.reviewService.findAll());
		return "reviews.html";
	}
	
	@GetMapping("/index")
	public String indexReview() {
		return "indexReview.html";
	}
	
	@GetMapping("/admin/indexReview")
	public String indexReviewAdmin() {
		return "admin/indexReview.html";
	}
		
	@GetMapping("/admin/formDeleteReview")
	public String formDeleteReview(Model model) {
		model.addAttribute("reviews",this.reviewService.findAll());
		return "admin/formDeleteReview.html";
	}
	
	@GetMapping("/admin/deleteReview/{id}")
	public String deleteReview(@PathVariable("id")Long id, Model model) {
		this.reviewService.deleteById(id);
		model.addAttribute("reviews",this.reviewService.findAll());
		return "reviews.html";
	}
	
}
