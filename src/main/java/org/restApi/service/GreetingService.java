package org.restApi.service;

import java.util.Collection;
import org.restApi.model.Greeting;

/**
 *
 * @author Timothy
 */
public interface GreetingService {
    
    Collection<Greeting> findAll();
    
    Greeting findOne(Long id);
    
    Greeting create(Greeting greeting);
    
    Greeting update(Greeting greeting);
    
    void delete(Long id);
    
    void evictCache();
    
}
