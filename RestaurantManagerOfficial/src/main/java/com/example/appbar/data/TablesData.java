package com.example.appbar.data;

public class TablesData {

    private int numTable;
    private int numPeople;
    private boolean reserved;

    public TablesData(int numTable, int numPeople) {
        this.numTable = numTable;
        this.numPeople = numPeople;
    }

    public int getNumTable() {
        return numTable;
    }

    public void setNumTable(int numTable) {
        this.numTable = numTable;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
