package it.uniroma3.cu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.cu.model.Review;
import it.uniroma3.cu.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	public Review saveReview(Review review) {
		return this.reviewRepository.save(review);
	}
	
	public Review findById(Long id) {
		return this.reviewRepository.findById(id).get();
	}
	
	public Iterable<Review> findAll(){
		return this.reviewRepository.findAll();
	}
	
	public void deleteById(Long id) {
		this.reviewRepository.deleteById(id);
	}
	
	public boolean existsByTitleAndTextAndValutation(String title, String text, Integer valutation) {
		return this.reviewRepository.existsByTitleAndTextAndValutation(title,text,valutation);
	}
	
}
