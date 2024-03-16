package com.example.DeliveryFeeApplication.enums;

public enum Vehicle {
    CAR(1, 0, 0, 0, false, 0, 0, false),
    SCOOTER(0.5, 0.5, 1, 0, false, 1, 0.5, true),
    BIKE(0, 0.5, 1, 0.5, true, 1, 0.5, true);

    // Fields and constructor

    private final double rbfIncrement;
    private final double ATEFNegative10to0;
    private final double ATEFLessThanNegative10;
    private final double WSEFBetween10and20;
    private final boolean isForbiddenWhenWindSpeedOver20;
    private final double WPEFSnowAndSleet;
    private final double WPEFRain;
    private final boolean isForbiddenWhenExtremeConditions;

    Vehicle(double rbfIncrement, double atefNegative10to0, double atefLessThanNegative10, double wsefBetween10and20, boolean isForbiddenWhenWindSpeedOver20, double wpefSnowAndSleet, double wpefRain, boolean isForbiddenWhenExtremeConditions) {
        this.rbfIncrement = rbfIncrement;
        this.ATEFNegative10to0 = atefNegative10to0;
        this.ATEFLessThanNegative10 = atefLessThanNegative10;
        this.WSEFBetween10and20 = wsefBetween10and20;
        this.isForbiddenWhenWindSpeedOver20 = isForbiddenWhenWindSpeedOver20;
        this.WPEFSnowAndSleet = wpefSnowAndSleet;
        this.WPEFRain = wpefRain;
        this.isForbiddenWhenExtremeConditions = isForbiddenWhenExtremeConditions;
    }

    public double getRbfIncrement() {
        return rbfIncrement;
    }

    public double getATEFNegative10to0() {
        return ATEFNegative10to0;
    }

    public double getATEFLessThanNegative10() {
        return ATEFLessThanNegative10;
    }

    public double getWSEFBetween10and20() {
        return WSEFBetween10and20;
    }

    public boolean isForbiddenWhenWindSpeedOver20() {
        return isForbiddenWhenWindSpeedOver20;
    }

    public double getWPEFSnowAndSleet() {
        return WPEFSnowAndSleet;
    }

    public double getWPEFRain() {
        return WPEFRain;
    }

    public boolean isForbiddenWhenExtremeConditions() {
        return isForbiddenWhenExtremeConditions;
    }
}