package it.uniroma3.cu.model;

import java.util.Objects;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String title;
	

	
	@Min(0)
	@Max(5)
	private Integer valutation;
	
	private String text;
	
	
	@OneToOne (mappedBy = "recensione")
	private Prestazione prestazione;
	
	
	
	
	public Prestazione getPrestazione() {
		return prestazione;
	}
	public void setPrestazione(Prestazione prestazione) {
		this.prestazione = prestazione;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getValutation() {
		return valutation;
	}
	public void setValutation(Integer valutation) {
		this.valutation = valutation;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(text, title, valutation);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		return Objects.equals(text, other.text) && Objects.equals(title, other.title)
				&& Objects.equals(valutation, other.valutation);
	}
	
	
	
}
