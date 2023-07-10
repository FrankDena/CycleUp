package it.uniroma3.cu.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.cu.model.Prenotazione;
import it.uniroma3.cu.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

	public List<Prenotazione> findAllByCliente(User cliente);
	public boolean existsById(Long id);

	
}
