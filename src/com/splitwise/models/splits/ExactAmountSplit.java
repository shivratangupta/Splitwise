package com.splitwise.models.splits;

import com.splitwise.models.User;

public class ExactAmountSplit extends Split {

    public ExactAmountSplit(long userId, double amount) {
        super(userId);
        setAmount(amount);
    }
}
