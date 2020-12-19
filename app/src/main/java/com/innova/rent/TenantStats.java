package com.innova.rent;

public class TenantStats {
    private String name;
    private int rent;
    private int recievedRent;
    private int remainingRent;



    private int ID;
    private int date;
    private String block;

    public TenantStats(String name, int rent, int recievedRent, int remainingRent) {
        this.name = name;
        this.rent = rent;
        this.recievedRent = recievedRent;
        this.remainingRent = remainingRent;
    }

    public TenantStats(String name, int rent, int recievedRent, int remainingRent, int ID, int date, String block) {
        this.name = name;
        this.rent = rent;
        this.recievedRent = recievedRent;
        this.remainingRent = remainingRent;
        this.ID = ID;
        this.date = date;
        this.block = block;
    }

    public TenantStats() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getRecievedRent() {
        return recievedRent;
    }

    public void setRecievedRent(int recievedRent) {
        this.recievedRent = recievedRent;
    }

    public int getRemainingRent() {
        return remainingRent;
    }

    public void setRemainingRent(int remainingRent) {
        this.remainingRent = remainingRent;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}
