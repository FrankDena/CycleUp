package it.uniroma3.cu.repository;


import org.springframework.data.repository.CrudRepository;

import it.uniroma3.cu.model.Servizio;

public interface ServizioRepository extends CrudRepository<Servizio, Long> {

	public boolean existsByTitleAndDescriptionAndPrice(String title, String description, Float price);
	
}
