package org.restApi.api;

import org.restApi.model.Greeting;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
    
    private static BigInteger nextId;
    private static Map<BigInteger, Greeting> greetingMap;
    
    private static Greeting save(Greeting greeting){
        if(greetingMap == null){
            greetingMap = new HashMap<BigInteger, Greeting>();
            nextId = BigInteger.ONE;
        }
        //  if update..
        if(greeting.getId() != null){
            Greeting oldGreeting = greetingMap.get(greeting.getId());
            if(oldGreeting == null)
                return null;
            
            greetingMap.remove(greeting.getId());
            greetingMap.put(greeting.getId(), greeting);
            return greeting;
        }
        
        //  If Create..
        greeting.setId(nextId);
        nextId = nextId.add(BigInteger.ONE);
        greetingMap.put(greeting.getId(), greeting);
        return greeting;
    }
    
    private static boolean delete(BigInteger id){
        Greeting deleteGreeting = greetingMap.remove(id);
        //  If the greeting was nog found in the map, return false
        if(deleteGreeting == null)
            return false;
        return true;
    }
    
    static {
        Greeting g1 = new Greeting();
        g1.setText("Hello World!");
        save(g1);
        
        Greeting g2 = new Greeting();
        g2.setText("Hola Mundo!");
        save(g2);
    }
    
    /**
     * @return  Returns all greetings available
     */
    @RequestMapping(
            value="/api/greetings"
            , method=RequestMethod.GET
            , produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings(){
        
        Collection<Greeting> greetings = greetingMap.values();
        
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
    public ResponseEntity<Greeting> getGreeting(@PathVariable("id") BigInteger id){
        
        Greeting greeting = greetingMap.get(id);
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
        Greeting savedGreeting = save(greeting);
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
        
        Greeting updatedGreeting = save(greeting);
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
            @PathVariable("id") BigInteger id,
            @RequestBody Greeting greeting){
        
        boolean deleted = delete(id);
        if(!deleted)
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
    }
}
