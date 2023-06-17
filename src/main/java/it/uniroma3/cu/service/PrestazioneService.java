package it.uniroma3.cu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.cu.model.Prestazione;
import it.uniroma3.cu.model.User;
import it.uniroma3.cu.repository.PrestazioneRepository;


@Service
public class PrestazioneService {

	@Autowired
	private PrestazioneRepository prestazioneRepository;
	
	public Prestazione savePrestazione(Prestazione prestazione) {
		return this.prestazioneRepository.save(prestazione);
	}
	
	public Prestazione findById(Long id) {
		return this.prestazioneRepository.findById(id).get();
	}
	
	public Iterable<Prestazione> findAll(){
		return this.prestazioneRepository.findAll();
	}
	
	public void deleteById(Long id) {
		this.prestazioneRepository.deleteById(id);
	}
	
	public List<Prestazione> findAllByCliente(User cliente) {
		return this.prestazioneRepository.findAllByCliente(cliente);
	}
	
}
