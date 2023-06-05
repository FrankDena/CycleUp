package it.uniroma3.cu.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Prenotazione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToMany
	private List<Servizio> servizi;
	
	@ManyToOne
	private User cliente;
	
	private String modelloBici;
	
	@OneToOne (mappedBy = "prenotazione")
	private Prestazione prestazione;
	
	
	private LocalDate dataInserimento;

	
	
	public LocalDate getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(LocalDate dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public Prestazione getPrestazione() {
		return prestazione;
	}

	public void setPrestazione(Prestazione prestazione) {
		this.prestazione = prestazione;
	}

	public List<Servizio> getServizi() {
		return servizi;
	}

	public void setServizi(List<Servizio> servizi) {
		this.servizi = servizi;
	}
	
	public void addServizio(Servizio servizio) {
		this.servizi.add(servizio);
	}

	public User getCliente() {
		return cliente;
	}

	public void setCliente(User cliente) {
		this.cliente = cliente;
	}

	public String getModelloBici() {
		return modelloBici;
	}

	public void setModelloBici(String modelloBici) {
		this.modelloBici = modelloBici;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, modelloBici, servizi);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prenotazione other = (Prenotazione) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(modelloBici, other.modelloBici)
				&& Objects.equals(servizi, other.servizi);
	}
	
	
	
}
