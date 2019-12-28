package com.splitwise.models;

import com.splitwise.models.expenses.Expense;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String hashedPass;
    private long uid; // unique ID not user ID

    private static long NEW_UID = 0;
    private List<Expense> expenses;

    public User(String name, String email, String hashedPass){
        this.setUid(NEW_UID++);
        this.setName(name);
        this.setEmail(email);
        this.setHashedPass(hashedPass);
    }

    // getters and setters
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setHashedPass(String hashedPass){
        this.hashedPass = hashedPass;
    }

    public String getHashedPass(){
        return hashedPass;
    }

    public void setUid(long uid){
        this.uid = uid;
    }

    public long getUid(){
        return uid;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    @Override
    public boolean equals(Object ob) {
        if(!(ob instanceof User))
            return false;
        return getUid() == ((User) ob).getUid();
    }

    @Override
    public String toString() {
        return "User(uid=" + getUid() + ", name=" + getName() + ", email=" + getEmail() + ")";
    }
}
