package it.uniroma3.cu.controller;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import it.uniroma3.cu.model.Servizio;
import it.uniroma3.cu.service.ServizioService;

@Component
public class ServizioValidator implements Validator{
	
	@Autowired ServizioService servizioService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Servizio.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Servizio servizio = (Servizio)o;
		if (servizio.getTitle()!=null && servizio.getDescription()!=null && servizio.getPrice()!=null &&
				servizioService.existsByTitleAndDescriptionAndPrice(servizio.getTitle(), servizio.getDescription(), servizio.getPrice())) {
			errors.reject("servizio.duplicate");
		}
			
		
	}
}
