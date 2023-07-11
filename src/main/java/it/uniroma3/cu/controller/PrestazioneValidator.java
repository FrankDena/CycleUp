package it.uniroma3.cu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.cu.model.Prestazione;
import it.uniroma3.cu.repository.PrestazioneRepository;
import it.uniroma3.cu.service.PrestazioneService;

@Component
public class PrestazioneValidator implements Validator{
	
	@Autowired PrestazioneService prestazioneService;
	@Autowired PrestazioneRepository prestazioneRepository;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Prestazione.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Prestazione prestazione = (Prestazione)o;
		if(prestazione.getId() != null && this.prestazioneRepository.existsById(prestazione.getId()))
			errors.reject("prestazione.duplicate");
	}
}
