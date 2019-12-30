package com.splitwise;

import com.splitwise.commands.CommandFactory;
import com.splitwise.exceptions.BadCommandFormatException;
import com.splitwise.models.User;

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
            try {
                CommandFactory.getInstance().execute(cmd);
            } catch (BadCommandFormatException e) {
                System.out.println(e.getMessage());
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
