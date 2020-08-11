package com.cse203.adressbook.datamodel;

public enum SizeOf {

    id(4),name(32),street(32),city(20),gender(2),zip(5),person(95); //95 bytes

    private int value;

    SizeOf(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
