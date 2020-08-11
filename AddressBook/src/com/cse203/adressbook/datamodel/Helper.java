package com.cse203.adressbook.datamodel;

public class Helper {
//
//    public static final int ID = 4;
//    public static final int NAME = 32;
//    public static final int STREET = 32;
//    public static final int CITY = 20;
//    public static final int GENDER = 2;
//    public static final int ZIP = 5;

    public static byte[] fixedLength(byte[] valueToBeFixed, SizeOf size ){

        //get byte of input value and set it to fixed length
        int lengthOfFixedSize =0;
        switch(size){
            case id:lengthOfFixedSize=SizeOf.id.getValue();break;
            case name:lengthOfFixedSize=SizeOf.name.getValue();break;
            case street:lengthOfFixedSize=SizeOf.street.getValue();break;
            case city:lengthOfFixedSize=SizeOf.city.getValue();break;
            case gender:lengthOfFixedSize=SizeOf.gender.getValue();break;
            case zip:lengthOfFixedSize=SizeOf.zip.getValue();break;
        }

        byte[] fixedByte = new byte[lengthOfFixedSize];

        for (int i = 0; i < valueToBeFixed.length; i++) {
            fixedByte[i]=valueToBeFixed[i];
        }
        return fixedByte;
    }
}
