package com.splitwise.models.splits;

import com.splitwise.models.User;

public class PercentSplit extends Split {
    private double percent;

    public PercentSplit(long userId, double percent) {
        super(userId);
        setPercent(percent);
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent){
        this.percent = percent;
    }
}
