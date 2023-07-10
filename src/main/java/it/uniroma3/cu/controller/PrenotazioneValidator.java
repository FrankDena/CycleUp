package it.uniroma3.cu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.cu.model.Prenotazione;
import it.uniroma3.cu.service.PrenotazioneService;
import it.uniroma3.cu.repository.PrenotazioneRepository;


@Component
public class PrenotazioneValidator implements Validator{
	
	@Autowired PrenotazioneService prenotazioneService;
	@Autowired PrenotazioneRepository prenotazioneRepository;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Prenotazione.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Prenotazione prenotazione = (Prenotazione)o;
		if(prenotazione.getId() != null && this.prenotazioneRepository.existsById(prenotazione.getId()))
			errors.reject("prenotazione.duplicate");
	}
}
