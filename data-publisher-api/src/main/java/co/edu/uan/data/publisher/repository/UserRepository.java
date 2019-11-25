package co.edu.uan.data.publisher.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.uan.data.publisher.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{

}
