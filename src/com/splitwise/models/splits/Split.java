package com.splitwise.models.splits;

import com.splitwise.models.User;

public abstract class Split {
    private long uid;
    private long userId;
    private double amount;
    private String notes;

    private static long NEW_UID = 0;

    public Split(long userId) {
        setUid(NEW_UID++);
        setUserId(userId);
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getUserId(){
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
