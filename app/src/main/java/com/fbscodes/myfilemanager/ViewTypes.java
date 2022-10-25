package com.fbscodes.myfilemanager;

public enum ViewTypes {
    ROW(0), GRID(1);

    private int value;
    ViewTypes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
