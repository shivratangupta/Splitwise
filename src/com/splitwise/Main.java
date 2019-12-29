package com.splitwise;

import com.splitwise.exceptions.IllegalSplitException;
import com.splitwise.exceptions.InvalidExpenseTypeException;
import com.splitwise.exceptions.NoSuchUserException;
import com.splitwise.models.User;
import com.splitwise.models.expenses.Expense;
import com.splitwise.models.expenses.ExpenseFactory;
import com.splitwise.models.expenses.ExpenseType;
import com.splitwise.models.splits.Split;
import com.splitwise.models.splits.SplitFactory;

import java.util.Scanner;

public class Main {
    public static User getUser(String userIdentification) throws NoSuchUserException {
        BookKeeper bk = BookKeeper.getInstance();
        User user;
        try {
            Long userId = Long.parseLong(userIdentification);
            user = bk.getUser(userId);
        } catch (NumberFormatException e) {
            String userEmail = userIdentification.trim();
            user = bk.getUser(userEmail);
        }

        return user;
    }

    public static void main(String[] args) {
        BookKeeper bk = BookKeeper.getInstance();

        bk.addUser(new User("reyaan", "reyaan@gmail.com", "reyaan"));
        bk.addUser(new User("shriyan", "shriyan@gmail.com", "shriyan"));
        bk.addUser(new User("areana", "areana@gamil.com", "areana"));

        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("> ");
            String[] cmd = sc.nextLine().split(" ");
            switch(cmd[0]) {
                case "add_user":
                    User u = new User(cmd[1], cmd[2], cmd[3]);
                    bk.addUser(new User(cmd[1], cmd[2], cmd[3]));
                    break;
                case "add_expense":
                    ExpenseType expType;
                    try {
                        expType = ExpenseType.fromString(cmd[1]);
                    } catch (InvalidExpenseTypeException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    String name = cmd[2];
                    Double totalAmount = Double.parseDouble(cmd[3]);
                    User createdBy;
                    try {
                        createdBy = getUser(cmd[4]);
                    } catch (NoSuchUserException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    Expense expense;
                    try {
                        expense = ExpenseFactory.createExpense(expType, name, createdBy, totalAmount);
                    } catch (InvalidExpenseTypeException | IllegalSplitException ignore) {
                        continue;
                    }

                    if(cmd.length > 5) {
                        User paidBy;
                        try {
                            paidBy = getUser(cmd[5]);
                        } catch (NoSuchUserException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        expense.setPaidBy(paidBy);

                        int numberOfSplits = cmd.length - 5;
                        if(numberOfSplits % 2 != 0) {
                            System.out.println("Invalid format!\nExpected: > " +
                                    "add_expense expense_type name createdBy [paidBy] [user val user val user val]");
                        }

                    }

                    // splits
                    break;
                case "add_split":
                    // expense
                    // split details
                    break;
                case "modify_expense":
                    // expense id
                    break;
                case "show":
                    if(cmd.length > 1) {
                        // show for user
                        User user;
                        try {
                            user = getUser(cmd[1]);
                            bk.showBalance(user);
                        } catch (NoSuchUserException e) {
                            System.out.println(e.getMessage());
                        }
                    } else{
                        // show for all
                        bk.showAllBalances();
                    }
                    break;
                default:
                    System.out.println("Invalid Command!");
            }
        }
    }
}

// -------------input format------------------
// add_user name email passwordhash
// add_expense expense_type name createdBy [paidBy] [user val user val]
// show show all balances
// show userID

// 0 shiv@gmail.com
// show 0
// show shiv@gmail.com
