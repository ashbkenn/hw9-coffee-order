package edu.iu.habahram.coffeeorder.repository;

import edu.iu.habahram.coffeeorder.model.Customer;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Strings> {
    Customer findByUsername(String username);

}