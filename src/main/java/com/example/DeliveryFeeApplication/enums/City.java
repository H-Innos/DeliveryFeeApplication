package com.example.DeliveryFeeApplication.enums;

public enum City {
    TALLINN(3),
    TARTU(2.5),
    PÄRNU(2);

    double rbf;

    City(double rbf) {
        this.rbf = rbf;
    }

    public double getRbf() {
        return rbf;
    }
}
