package com.splitwise.exceptions;

public class InvalidExpenseTypeException extends Exception {
    public InvalidExpenseTypeException(String message){
        super(message);
    }
}
