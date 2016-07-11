package org.restApi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Greeting {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String text;
    
    
    public Greeting(){}

    public String getText() {
        return text;
    }

    public Long getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
}
