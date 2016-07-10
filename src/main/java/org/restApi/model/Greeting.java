package org.restApi.model;

import java.math.BigInteger;


public class Greeting {
    
    
    private String text;
    private Long id;
    
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
