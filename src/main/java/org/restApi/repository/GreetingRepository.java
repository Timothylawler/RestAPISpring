package org.restApi.repository;

import org.restApi.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long>{
    
    
    
}
