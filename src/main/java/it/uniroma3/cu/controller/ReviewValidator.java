package it.uniroma3.cu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import it.uniroma3.cu.model.Review;
import it.uniroma3.cu.service.ReviewService;

@Component
public class ReviewValidator implements Validator{
	
	@Autowired
	private ReviewService reviewService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Review.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Review review = (Review) o;
		if (review.getTitle()!=null && review.getText()!=null && review.getValutation()!=null 
				&& reviewService.existsByTitleAndTextAndValutation(review.getTitle(),
						review.getText(), review.getValutation())) {
			errors.reject("review.duplicate");
		}
		
		
		
	}
	
}
