package com.example.DeliveryFeeApplication.deliveryFee;

public enum City {
    TALLINN(3),
    TARTU(2.5),
    PÄRNU(2);

    final double rbf; // regional base fee not considering vehicle type

    City(double rbf) {
        this.rbf = rbf;
    }

    public double getRbf() {
        return rbf;
    }
}
