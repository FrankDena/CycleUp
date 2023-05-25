package it.uniroma3.cu.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.cu.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
	public boolean existsByTitleAndTextAndValutation(String title, String text, Integer valutation);

}
