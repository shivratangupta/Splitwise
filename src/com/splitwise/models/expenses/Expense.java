package com.splitwise.models.expenses;

import com.splitwise.Utils;
import com.splitwise.exceptions.IllegalSplitException;
import com.splitwise.imports.Geolocation;
import com.splitwise.imports.Image;
import com.splitwise.models.User;
import com.splitwise.models.splits.Split;

import java.util.Date;
import java.util.List;

public abstract class Expense {
    private long uid;  // not user id. unique id
    private String name;
    private double totalAmount;
    private User paidBy;
    private User createdBy;
    private static ExpenseType type = null;
    private List<Split> splits;

    private String notes;
    private Geolocation location;
    private Date dateCreated;
    private List<Image> images;

    private static long NEW_UID = 0;

    public Expense(String name, double totalAmount, User createdBy) throws IllegalSplitException {
        setUid(NEW_UID++);
        setName(name);
        setTotalAmount(totalAmount);
        setCreatedBy(createdBy);
    }

    // setters
    public void setUid(long uid){
        this.uid = uid;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }

    public void setLocation(Geolocation location){
        this.location = location;
    }

    public void setImages(List<Image> images){
        this.images = images;
    }

    public void setPaidBy(User paidBy){
        this.paidBy = paidBy;
    }

    public void setCreatedBy(User createdBy){
        this.createdBy = createdBy;
    }

    // getters
    public long getUid(){
        return uid;
    }

    public String getName(){
        return name;
    }

    public String getNotes(){
        return notes;
    }

    public Date getDateCreated(){
        return dateCreated;
    }

    public Geolocation getLocation(){
        return location;
    }

    public List<Image> getImages(){
        return images;
    }

    public double getTotalAmount(){
        return totalAmount;
    }

    public User getPaidBy(){
        return paidBy;
    }

    public User getCreatedById(){
        return createdBy;
    }

    public List<Split> getSplits(){
        return splits;
    }

    public ExpenseType getType(){
        return type;
    }

    abstract void validateSplits() throws IllegalSplitException;

    // validate amount
    boolean validate() {
        // common logic
        double sum = 0;
        for(Split s : splits)
            sum += s.getAmount();

        return Utils.isApproxEqual(sum, getTotalAmount());
    }

    // recalculation
    abstract void recalculate();

    // set totalAmount
    public void setTotalAmount(double totalAmount){
        this.totalAmount = totalAmount;
        recalculate();
    }

    // set splits
    public void setSplits(List<Split> splits) throws IllegalSplitException {
        this.splits = splits;
        validateSplits();
        recalculate();
    }

    // add split
    public void addSplits(Split s) throws IllegalSplitException {
        splits.add(s);
        validateSplits();
        recalculate();
    }

    // remove split
    public void removeSplit(Split s) throws IllegalSplitException {
        splits.remove(s);
        recalculate();
    }
}
