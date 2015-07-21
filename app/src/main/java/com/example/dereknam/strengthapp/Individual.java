package com.example.dereknam.strengthapp;

/**
 * Created by Derek Nam on 08-07-2015.
 */
public class Individual {
    private long id;
    private String name;
    private String description;
    private String short_form;
    private long group_id;

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
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
    public String getShort_form(){
        return short_form;
    }
    public void setShort_form(String short_form){
        this.short_form = short_form;
    }
    public long getGroup_id(){
        return group_id;
    }
    public void setGroup_id(long group_id){
        this.group_id = group_id;
    }
}
