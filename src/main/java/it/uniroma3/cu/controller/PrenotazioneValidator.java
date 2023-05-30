package it.uniroma3.cu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.cu.model.Prenotazione;
import it.uniroma3.cu.service.PrenotazioneService;

@Component
public class PrenotazioneValidator implements Validator{
	
	@Autowired PrenotazioneService prenotazioneService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Prenotazione.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
			
		
	}
}
