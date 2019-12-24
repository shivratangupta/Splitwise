package com.splitwise.models.splits;

import com.splitwise.models.User;

public class ExactAmountSplit extends Split {

    public ExactAmountSplit(User user, double amount) {
        super(user);
        setAmount(amount);
    }
}
