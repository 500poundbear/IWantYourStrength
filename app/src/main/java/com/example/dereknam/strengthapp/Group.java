package com.example.dereknam.strengthapp;

/**
 * Created by Derek Nam on 04-07-2015.
 */
public class Group {
    private long id;
    private String name;
    private String description;

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String toString(){
        return name;
    }
}
