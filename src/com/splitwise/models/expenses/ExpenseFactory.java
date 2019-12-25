package com.splitwise.models.expenses;

import com.splitwise.exceptions.IllegalSplitException;
import com.splitwise.exceptions.InvalidExpenseTypeException;
import com.splitwise.models.User;

public class ExpenseFactory {
    public static Expense createExpense(ExpenseType type,
                                        String name,
                                        long createdById,
                                        double totalAmount) throws IllegalSplitException, InvalidExpenseTypeException {
        switch(type) {
            case EXACT:
                return new ExactAmountExpense(name, totalAmount, createdById);
            case PERCENT:
                return new PercentExpense(name, totalAmount, createdById);
            case EQUAL:
                return new EqualExpense(name, totalAmount, createdById);
            default:
                throw new InvalidExpenseTypeException("No such type Expense exist");
        }
    }
}
