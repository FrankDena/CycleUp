package it.uniroma3.cu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.uniroma3.cu.model.Servizio;

import it.uniroma3.cu.repository.ServizioRepository;

@Service
public class ServizioService {

	@Autowired
	private ServizioRepository servizioRepository;
	
	public Servizio saveServizio(Servizio servizio) {
		return this.servizioRepository.save(servizio);
	}
	
	public Servizio findById(Long id) {
		return this.servizioRepository.findById(id).get();
	}
	
	public Iterable<Servizio> findAll(){
		return this.servizioRepository.findAll();
	}
	
	public void deleteById(Long id) {
		this.servizioRepository.deleteById(id);
	}
	
	public boolean existsByTitleAndDescriptionAndPrice(String title, String description, Float price) {
		return this.servizioRepository.existsByTitleAndDescriptionAndPrice(title,description,price);
	}
	
}
