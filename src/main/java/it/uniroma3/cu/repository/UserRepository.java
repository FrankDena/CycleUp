package it.uniroma3.cu.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.cu.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	//public User findByUsername(String username);

}
