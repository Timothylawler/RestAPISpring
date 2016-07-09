package org.restApi.model;

import java.math.BigInteger;


public class Greeting {
    
    
    private String text;
    private BigInteger id;
    
    public Greeting(){}

    public String getText() {
        return text;
    }

    public BigInteger getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
    
    
}
