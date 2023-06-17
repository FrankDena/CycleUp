package it.uniroma3.cu.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Prestazione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne 
	private Prenotazione prenotazione;
	
	@OneToOne 
	private Review recensione;
	
	@ManyToOne
	private User cliente;
	
	private Float prezzoFinale;
	
	private Float prezzoRicambi;
	
	private Integer oreManodopera;
	
	
	private LocalDate dataCompletamento;
	
	@Column (nullable = true)
	private String note;

	
	
	
	
	


	public User getCliente() {
		return cliente;
	}

	public void setCliente(User cliente) {
		this.cliente = cliente;
	}

	public LocalDate getDataCompletamento() {
		return dataCompletamento;
	}

	public void setDataCompletamento(LocalDate dataCompletamento) {
		this.dataCompletamento = dataCompletamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Prenotazione getPrenotazione() {
		return prenotazione;
	}

	public void setPrenotazione(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
	}

	public Review getRecensione() {
		return recensione;
	}

	public void setRecensione(Review recensione) {
		this.recensione = recensione;
	}

	public Float getPrezzoFinale() {
		return prezzoFinale;
	}

	public void setPrezzoFinale(Float prezzoFinale) {
		this.prezzoFinale = prezzoFinale;
	}

	public Float getPrezzoRicambi() {
		return prezzoRicambi;
	}

	public void setPrezzoRicambi(Float prezzoRicambi) {
		this.prezzoRicambi = prezzoRicambi;
	}

	public Integer getOreManodopera() {
		return oreManodopera;
	}

	public void setOreManodopera(Integer oreManodopera) {
		this.oreManodopera = oreManodopera;
	}
	

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		return Objects.hash(oreManodopera, prenotazione, prezzoFinale, prezzoRicambi);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prestazione other = (Prestazione) obj;
		return Objects.equals(oreManodopera, other.oreManodopera) && Objects.equals(prenotazione, other.prenotazione)
				&& Objects.equals(prezzoFinale, other.prezzoFinale)
				&& Objects.equals(prezzoRicambi, other.prezzoRicambi);
	}
	
	
	
}
