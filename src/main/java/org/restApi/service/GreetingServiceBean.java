package org.restApi.service;

import java.util.Collection;
import org.restApi.model.Greeting;
import org.restApi.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
//  Tell the program that every method in the greeting service interface 
        //  supports transaction
        //  Read only tells the program that the methods will NOT modify or add data
@Transactional(
        propagation = Propagation.SUPPORTS,
        readOnly = true)
public class GreetingServiceBean implements GreetingService {

    
    @Autowired
    private GreetingRepository greetingRepository;
    
    @Override
    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = greetingRepository.findAll();
        return greetings;
    }

    @Override
    @Cacheable(
            value = "greetings",
            key = "#id")
    public Greeting findOne(Long id) {
        Greeting greeting = greetingRepository.findOne(id);
        
        return greeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(
            value = "greetings",
            key = "#result.id")
    public Greeting create(Greeting greeting) {
        if(greeting.getId() != null){
            //  Greeting already has an id. cannot create Greeting with 
            //      Specified ID value
            return null;
        }
        //  Save the greeting passed and return it
        Greeting savedGreeting = greetingRepository.save(greeting);
        
        //  Illustrate Tx rollback
        //      Throws an Exception if a new entity is created with id 4
        //      After the Exception is thrown a new entity can be created with id 5
        /*if(savedGreeting.getId() == 4L){
            throw new RuntimeException("Roll me back!");
        }*/
            
        return savedGreeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(
            value="greetings",
            key = "#greeting.id")
    public Greeting update(Greeting greeting) {
        Greeting greetingPersisted = findOne(greeting.getId());
        if(greetingPersisted == null){
            //  Cannot update a greeting that does not exist
            return null;
        }
        
        Greeting updatedGreeting = greetingRepository.save(greeting);
        return updatedGreeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CacheEvict(
            value = "greeting",
            key = "#id")
    public void delete(Long id) {
        greetingRepository.delete(id);
    }
    
    /**
     * CLEAR THE CACHE
     */
    @Override
    @CacheEvict(
            value="greetings",
            allEntries = true)
    public void evictCache(){
        
    }

}
