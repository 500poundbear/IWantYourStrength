package com.example.dereknam.strengthapp;

/**
 * Created by Derek Nam on 21-07-2015.
 */
public class Strength {
    private long id;
    private Group group;
    private int current;
    private int total;
    private String timestamp;

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public Group getGroup(){
        return group;
    }
    public void setGroup(Group group){
        this.group = group;
    }
    public int getCurrent(){
        return current;
    }
    public void setCurrent(int current){
        this.current = current;
    }
    public int getTotal(){
        return total;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public String getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }
}
