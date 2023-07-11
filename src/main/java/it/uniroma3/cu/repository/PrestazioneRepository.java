package it.uniroma3.cu.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.cu.model.Prestazione;
import it.uniroma3.cu.model.User;

public interface PrestazioneRepository extends CrudRepository<Prestazione, Long> {

	public List<Prestazione> findAllByCliente(User cliente);
	public boolean existsById(Long id);
	
}
