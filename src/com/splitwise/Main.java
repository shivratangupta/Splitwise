package com.splitwise;

import com.splitwise.exceptions.InvalidExpenseTypeException;
import com.splitwise.exceptions.NoSuchUserException;
import com.splitwise.models.User;
import com.splitwise.models.expenses.Expense;
import com.splitwise.models.expenses.ExpenseType;

import java.util.Scanner;

public class Main {
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
                    try {
                        ExpenseType expType = ExpenseType.fromString(cmd[1]);
                    } catch (InvalidExpenseTypeException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    String name = cmd[2];
                    Double amount = Double.parseDouble(cmd[3]);


                    // created by
                    // paid by
                    // splits
                    break;
                case "modify_expense":
                    // expense id
                    break;
                case "show":
                    if(cmd.length > 1) {
                        // show for user
                        User user;
                        try {
                            try {
                                Long userId = Long.parseLong(cmd[1]);
                                user = bk.getUser(userId);
                            } catch (NumberFormatException e) {
                                String userEmail = cmd[1].trim();
                                user = bk.getUser(userEmail);
                            }
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
// add_expense expense_type --
// show show all balances
// show userID

// 0 shiv@gmail.com
// show 0
// show shiv@gmail.com
