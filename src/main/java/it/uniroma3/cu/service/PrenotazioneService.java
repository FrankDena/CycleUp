package it.uniroma3.cu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.cu.model.Prenotazione;
import it.uniroma3.cu.repository.PrenotazioneRepository;


@Service
public class PrenotazioneService {

	@Autowired
	private PrenotazioneRepository prenotazioneRepository;
	
	public Prenotazione savePrenotazione(Prenotazione prenotazione) {
		return this.prenotazioneRepository.save(prenotazione);
	}
	
	public Prenotazione findById(Long id) {
		return this.prenotazioneRepository.findById(id).get();
	}
	
	public Iterable<Prenotazione> findAll(){
		return this.prenotazioneRepository.findAll();
	}
	
	public void deleteById(Long id) {
		this.prenotazioneRepository.deleteById(id);
	}
	
	
	
}
