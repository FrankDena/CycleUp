package it.uniroma3.cu.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.cu.model.Prenotazione;
import it.uniroma3.cu.model.Servizio;

public interface ServizioRepository extends CrudRepository<Servizio, Long> {

	public boolean existsByTitleAndDescriptionAndPrice(String title, String description, Float price);

	public List<Servizio> findAllByPrenotazioniNotContaining(Prenotazione prenotazione);
	
}
