package com.cse203.adressbook.datamodel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PersonData {

    /*
    use singleton to always work with same random access file
    */
    private static PersonData instance = new PersonData();
    private static String filename = "AddressBook.txt";
    private static RandomAccessFile randomAccessFile;

    static {
        try {
            randomAccessFile = new RandomAccessFile(filename, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //hide PersonData() to avoid creating new instances so it will use same instance always
    private PersonData() {

    }

    public static PersonData getInstance() {
        return instance;
    }

    //add person to file
    public void addPerson(Person person) throws IOException {
        RandomAccessFile randomAccessFile = getRandomAccessFile();
        if (write(person, getLastId() + 1)) {
            randomAccessFile.seek(randomAccessFile.length());
        } else {
            System.out.println("Person can not be added");
        }
    }

    //update Person
    public Person updatePerson(Person person, int id) throws IOException {
        if (write(person, id)) {
            return getPerson(id - 1);
        }
        return null;
    }

    //write new or existing person to file
    private boolean write(Person person, int id) throws IOException {
        RandomAccessFile randomAccessFile = getRandomAccessFile();
        //set pointer
        randomAccessFile.seek((id - 1) * SizeOf.person.getValue());

        try {
            randomAccessFile.write(Helper.fixedLength(String.valueOf(id).getBytes(), SizeOf.id));
            randomAccessFile.write(Helper.fixedLength(person.getName().getBytes(), SizeOf.name));
            randomAccessFile.write(Helper.fixedLength(person.getStreet().getBytes(), SizeOf.street));
            randomAccessFile.write(Helper.fixedLength(person.getCity().getBytes(), SizeOf.city));
            randomAccessFile.write(Helper.fixedLength(String.valueOf(person.getGender()).getBytes(), SizeOf.gender));
            randomAccessFile.write(Helper.fixedLength(person.getZip().getBytes(), SizeOf.zip));
            System.out.println("Person has been written.");//for debugging
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //Get first Person
    public Person firstPerson() throws IOException {
        RandomAccessFile randomAccessFile = getRandomAccessFile();
        if (randomAccessFile.length() > 0) {
            Person person = getPerson(0);
            if (person != null) {
                System.out.println("Pointer is here: " + randomAccessFile.getFilePointer()); //for debug
                return person;
            }
        }
        return null;
    }

    //get last Person
    public Person lastPerson() throws IOException {
        RandomAccessFile randomAccessFile = getRandomAccessFile();
        if (randomAccessFile.length() > 0) {
            Person person = getPerson((int) randomAccessFile.length() / SizeOf.person.getValue() - 1);
            if (person != null) {
                return person;
            }
        }
        return null;
    }

    //get next person according to personcount
    public Person nextPerson() throws IOException {
        Person person = getPerson((int) getRandomAccessFile().getFilePointer() / SizeOf.person.getValue());
        if (person != null) {
            return person;
        }


        return null;
    }

    //get previous person according to personcount
    public Person previousPerson() throws IOException {
        int seekPoint = ((int) getRandomAccessFile().getFilePointer() / SizeOf.person.getValue()) - 2;
        if (seekPoint>=0) {
            return  getPerson(seekPoint);
        }
        return null;
    }

    public Person searchPerson(int id) throws IOException {
        Person person = getPerson(id - 1);
        if (person != null) {
            return person;
        }
        return null;
    }

    //retrieve Person info from file accourding to personPointer
    private Person getPerson(int personPointer) throws IOException {
        RandomAccessFile randomAccessFile = getRandomAccessFile();
        randomAccessFile.seek(personPointer * SizeOf.person.getValue());
        int readBytes = 0;
        try {
            byte[] sizeOfId = new byte[SizeOf.id.getValue()];
            readBytes += randomAccessFile.read(sizeOfId);
            int id = Integer.valueOf(new String(sizeOfId).trim());

            byte[] sizeOfName = new byte[SizeOf.name.getValue()];
            readBytes += randomAccessFile.read(sizeOfName);
            String name = new String(sizeOfName).trim();


            byte[] sizeOfStreet = new byte[SizeOf.street.getValue()];
            readBytes += randomAccessFile.read(sizeOfStreet);
            String street = new String(sizeOfStreet).trim();

            byte[] sizeOfCity = new byte[SizeOf.city.getValue()];
            readBytes += randomAccessFile.read(sizeOfCity);
            String city = new String(sizeOfCity).trim();

            byte[] sizeOfGender = new byte[SizeOf.gender.getValue()];
            readBytes += randomAccessFile.read(sizeOfGender);
            char gender = (new String(sizeOfGender).trim()).charAt(0);

            byte[] sizeOfZip = new byte[SizeOf.zip.getValue()];
            readBytes += randomAccessFile.read(sizeOfZip);
            String zip = new String(sizeOfZip).trim();

            System.out.println("getPerson().readBytes = " + readBytes); //for debug

            return new Person(id, name, street, city, gender, zip);

        } catch (NumberFormatException e) {
            return null;
        }


    }

    private int getLastId() throws IOException {
        RandomAccessFile randomAccessFile = getRandomAccessFile();
        if (randomAccessFile.length() == 0) {
            return 0;
        }
        randomAccessFile.seek(randomAccessFile.length() - SizeOf.person.getValue());
        byte[] sizeOfId = new byte[SizeOf.id.getValue()];
        int readBytes = randomAccessFile.read(sizeOfId);
        String lastId = new String(sizeOfId);

        System.out.println("getLastId().readBytes = " + readBytes); //for debug
        System.out.println("Last id is " + lastId);//for debugging

        return Integer.valueOf(lastId.trim());
    }

    private RandomAccessFile getRandomAccessFile() {
        if (randomAccessFile == null) {
            try {
                return new RandomAccessFile(filename, "rw");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return randomAccessFile;
    }

    public int personCount() {
        try {
            return (int) getRandomAccessFile().length() / SizeOf.person.getValue();
        } catch (IOException e) {
            System.out.println("Could not find the file!" + e.getMessage());
        }
        return 0;
    }
}
