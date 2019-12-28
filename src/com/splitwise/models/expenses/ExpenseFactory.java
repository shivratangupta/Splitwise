package com.splitwise.models.expenses;

import com.splitwise.exceptions.IllegalSplitException;
import com.splitwise.exceptions.InvalidExpenseTypeException;
import com.splitwise.models.User;

public class ExpenseFactory {
    public static Expense createExpense(ExpenseType type,
                                        String name,
                                        User createdBy,
                                        double totalAmount) throws IllegalSplitException, InvalidExpenseTypeException {
        switch(type) {
            case EXACT:
                return new ExactAmountExpense(name, totalAmount, createdBy);
            case PERCENT:
                return new PercentExpense(name, totalAmount, createdBy);
            case EQUAL:
                return new EqualExpense(name, totalAmount, createdBy);
            default:
                throw new InvalidExpenseTypeException("No such type Expense exist");
        }
    }
}
