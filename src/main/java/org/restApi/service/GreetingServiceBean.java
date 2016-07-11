package org.restApi.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.restApi.model.Greeting;
import org.restApi.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceBean implements GreetingService {

    
    @Autowired
    private GreetingRepository greetingRepository;
    
    @Override
    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = greetingRepository.findAll();
        return greetings;
    }

    @Override
    public Greeting findOne(Long id) {
        Greeting greeting = greetingRepository.findOne(id);
        
        return greeting;
    }

    @Override
    public Greeting create(Greeting greeting) {
        if(greeting.getId() != null){
            //  Greeting already has an id. cannot create Greeting with 
            //      Specified ID value
            return null;
        }
        //  Save the greeting passed and return it
        Greeting savedGreeting = greetingRepository.save(greeting);
        return savedGreeting;
    }

    @Override
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
    public void delete(Long id) {
        greetingRepository.delete(id);
    }

}
