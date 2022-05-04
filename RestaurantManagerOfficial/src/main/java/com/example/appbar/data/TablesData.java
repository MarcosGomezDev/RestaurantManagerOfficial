package com.example.appbar.data;

public class TablesData {

    private int numTable;
    private int numPersons;
    private boolean reserved;

    public TablesData(int numTable, int numPersons) {
        this.numTable = numTable;
        this.numPersons = numPersons;
    }

    public int getNumTable() {
        return numTable;
    }

    public void setNumTable(int numTable) {
        this.numTable = numTable;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public void setNumPersons(int numPersons) {
        this.numPersons = numPersons;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
