package com.example.dereknam.strengthapp;

import java.sql.SQLException;

/**
 * Created by Derek Nam on 18-07-2015.
 */
public class Status {
    private long id;
    private long individual_id;
    private long status_label_id;
    private String timestamp;
    private boolean accounted;
    private Individual individual;
    private Label label;

    public boolean getAccounted(){
        return accounted;
    }
    public void setAccounted(boolean accounted){
        this.accounted = accounted;
    }
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getIndividual_id(){
        return individual_id;
    }
    public void setIndividual_id(long individual_id){
        this.individual_id = individual_id;
    }
    public long getStatus_label_id(){
        return status_label_id;
    }
    public void setStatus_label_id(long status_label_id){
        this.status_label_id = status_label_id;
    }
    public String getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }
    public Individual getIndividual(){
        return this.individual;
    }
    public void setIndividual(Individual individual){
        this.individual = individual;
    }
    public Label getLabel(){
        return this.label;
    }
    public void setLabel(Label label){
        this.label = label;
    }
}
