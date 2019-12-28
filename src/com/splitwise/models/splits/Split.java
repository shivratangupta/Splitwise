package com.splitwise.models.splits;

import com.splitwise.models.User;

public abstract class Split {
    private long uid;
    private User user;
    private double amount;
    private String notes;

    private static long NEW_UID = 0;

    public Split(User user) {
        setUid(NEW_UID++);
        setUser(user);
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
