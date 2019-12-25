package com.splitwise;

import com.splitwise.exceptions.EmailAlreadyUsedException;
import com.splitwise.exceptions.IllegalSplitException;
import com.splitwise.exceptions.InvalidExpenseTypeException;
import com.splitwise.exceptions.NoSuchUserException;
import com.splitwise.models.User;
import com.splitwise.models.expenses.Expense;
import com.splitwise.models.expenses.ExpenseFactory;
import com.splitwise.models.expenses.ExpenseType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookKeeper {
    private Map<Long, Expense> expenses;
    private Map<Long, User> users;
    private Map<String, User> userByEmail;

    private static BookKeeper INSTANCE;

    private BookKeeper() {
        expenses = new HashMap<Long, Expense>();
        users = new HashMap<Long, User>();
        userByEmail = new HashMap<String, User>();
    }

    // singleton pattern
    public static BookKeeper getInstance() {
        if(INSTANCE == null)
            INSTANCE = new BookKeeper();
        return INSTANCE;
    }

    public void addUser(User user) {
        users.put(user.getUid(), user);
        if(user.getEmail() != null || user.getEmail() != "")
            userByEmail.put(user.getEmail(), user);
    }

    public void addExpense(String name,
                           ExpenseType type,
                           User createdBy,
                           double totalAmount) throws IllegalSplitException, InvalidExpenseTypeException {
        Expense e = ExpenseFactory.createExpense(type, name, createdBy.getUid(), totalAmount);
        expenses.put(e.getUid(), e);
        createdBy.getExpenseIDs().add(e.getUid());
    }

    public User getUser(Long uid) throws NoSuchUserException {
        if(!users.containsKey(uid))
            throw new NoSuchUserException("User with uid=" + uid + " doesn't exist");
        return users.get(uid);
    }

    public User getUser(String email) throws NoSuchUserException {
        if(!userByEmail.containsKey(email))
            throw new NoSuchUserException("User with email=" + email + "doesn't exist");
        return userByEmail.get(email);
    }

    public void changeEmail(User user, String email) throws EmailAlreadyUsedException {
        // check email has been mapped with other user already
        if(userByEmail.containsKey(email)) {
            if(!userByEmail.get(email).equals(user)) {
                throw new EmailAlreadyUsedException();
            }
        }
        if(userByEmail.containsKey(user.getEmail()))
            userByEmail.remove(user.getEmail());
        user.setEmail(email);
        userByEmail.put(email, user);
    }
}
