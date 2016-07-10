/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
}
