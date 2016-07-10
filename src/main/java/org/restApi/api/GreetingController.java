package org.restApi.api;

import org.restApi.model.Greeting;
import java.util.Collection;
import org.restApi.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    
    @Autowired
    private GreetingService greetingService;
    
   
    /**
     * @return  Returns all greetings available
     */
    @RequestMapping(
            value="/api/greetings"
            , method=RequestMethod.GET
            , produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings(){
        
        Collection<Greeting> greetings = greetingService.findAll();
        
        return new ResponseEntity<Collection<Greeting>>(greetings,
            HttpStatus.OK);
    }
    
    /**
     * Get a greeting with a specific id
     *  eg path url "localhost:8080/api/greetings/1"
     * @param id    entity id
     */
    @RequestMapping(
            value="/api/greetings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(@PathVariable("id") Long id){
        
        Greeting greeting = greetingService.findOne(id);
        if(greeting == null)
            return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }
    
    
    /**
     * Used for creating greeting entities
     */
    @RequestMapping(
            value="/api/greetings",
            method=RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> createGreeting(
            @RequestBody Greeting greeting){
        
        //  Save the greeting passed and return it with status.CREATED
        Greeting savedGreeting = greetingService.create(greeting);
        return new ResponseEntity<Greeting>(savedGreeting, HttpStatus.CREATED);
    }
    
    
    /**
     * Used for updating an existing entity
     * @param greeting  Entity to be updated
     * @return  The updated entity
     */
    @RequestMapping(
            value="/api/greetings/{id}",
            method=RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> updateGreeting(
            @RequestBody Greeting greeting){
        
        //  could extract and check the id from the url but that is juset
        //      there to how that this is possible. as for now just pass
        //      the id as json param along with the text
        
        Greeting updatedGreeting = greetingService.update(greeting);
        //  If the greeting was not found in out list, return internal server error
        if(updatedGreeting == null)
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        
        return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
    }
    
    /**
     * Used for deleteing an entity corresponding to the passed id
     * @param id        key for the entity to delete
     * @param greeting  Not supported, could be used if wanting to pass a model
     * @return          Server error(error) or no content(succesful delete)
     */
    @RequestMapping(
            value="/api/greetings/{id}",
            method=RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> deleteGreeting(
            @PathVariable("id") Long id,
            @RequestBody Greeting greeting){
        
        greetingService.delete(id);
        return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
    }
}
