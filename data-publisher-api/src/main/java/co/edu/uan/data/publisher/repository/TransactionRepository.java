package co.edu.uan.data.publisher.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.uan.data.publisher.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

}
