package com.splitwise;

import com.splitwise.exceptions.EmailAlreadyUsedException;
import com.splitwise.exceptions.IllegalSplitException;
import com.splitwise.exceptions.InvalidExpenseTypeException;
import com.splitwise.exceptions.NoSuchUserException;
import com.splitwise.models.User;
import com.splitwise.models.expenses.Expense;
import com.splitwise.models.expenses.ExpenseFactory;
import com.splitwise.models.expenses.ExpenseType;
import com.splitwise.models.splits.Split;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookKeeper {
    private Map<Long, Expense> expenses;
    private Map<Long, User> users;
    private Map<String, User> userByEmail;
    private Map<User, Map<User, Double>> balances;

    private static BookKeeper INSTANCE;

    private BookKeeper() {
        expenses = new HashMap<>();
        users = new HashMap<>();
        userByEmail = new HashMap<>();
        balances = new HashMap<>();
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
        balances.put(user, new HashMap<>());
    }

    public void addExpense(String name,
                           ExpenseType type,
                           User createdBy,
                           User paidBy,
                           List<Split> splits,
                           double totalAmount) throws IllegalSplitException, InvalidExpenseTypeException, NoSuchUserException {
        Expense e = ExpenseFactory.createExpense(type, name, createdBy, totalAmount);
        expenses.put(e.getUid(), e);
        createdBy.getExpenses().add(e);
        e.setPaidBy(paidBy);
        e.setSplits(splits);

        if(!balances.containsKey(paidBy))
            throw new NoSuchUserException("Please add the user before adding their expenses");

        for(Split s : splits) {
            User paidTo = s.getUser();
            if(paidBy.equals(paidTo))
                continue;

            Map<User, Double> userBalances = balances.get(paidBy);
            if(!userBalances.containsKey(paidTo))
                userBalances.put(paidTo, 0.0d);
            userBalances.put(paidTo, s.getAmount() + userBalances.get(paidTo));

            if(!balances.containsKey(paidTo))
                throw new NoSuchUserException("Please add the user before adding their expenses");
            userBalances = balances.get(paidTo);
            if(!userBalances.containsKey(paidBy))
                userBalances.put(paidBy, 0.0d);
            userBalances.put(paidBy, s.getAmount() + userBalances.get(paidBy));
        }
    }

    // show all balances of all users
    public void showAllBalances() throws NoSuchUserException {
        for(User user1 : balances.keySet()) {
            showBalance(user1);
        }
    }

    public void showAllBalances(boolean simplify) throws NoSuchUserException {
        // todo : implement this
        for(User user1 : balances.keySet()) {
            showBalance(user1);
        }
    }

    public void showBalance(User user) {
        Map<User, Double> userBalances = balances.get(user);
        for(User user1 : userBalances.keySet()) {
            double amount = userBalances.get(user1);
            // print user1 owes amount to user2
            if(amount > 0)
                System.out.println(user.getName() + " owes " + amount + " to " + user1.getName());
            else if(amount < 0)
                System.out.println(user.getName() + " takes " + (-amount) + " from " + user1.getName());
        }
    }

    public void showBalance(Long userId) throws NoSuchUserException {
        showBalance(getUser(userId));
    }

    public List<Expense> getUserExpenses(User user) {
        return user.getExpenses();
    }

    public List<Expense> getUserExpenses(Long userId) throws NoSuchUserException {
        return getUserExpenses(getUser(userId));
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
