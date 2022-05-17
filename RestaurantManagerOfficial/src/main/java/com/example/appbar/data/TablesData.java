package com.example.appbar.data;

public class TablesData {

    private final DataBase dataBase = new DataBase();
    private String numTable;
    private String numPeople;
    private boolean reserved;

    public TablesData(){
    }

    public TablesData(String numTable, String numPeople, boolean reserved) {
        this.numTable = numTable;
        this.numPeople = numPeople;
        this.reserved = reserved;
    }

    public void addTable(String numTable, String numPeople, boolean reserved) {
        TablesData addTable = new TablesData(numTable, numPeople, reserved);
        String userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(numTable)
                .setValue(addTable);
    }

    public void addInitialTables() {
        addTable("1","4", false);
        addTable("2","4", false);
        addTable("3","4", false);
        addTable("4","4", false);
        addTable("5","4", false);
        addTable("6","4", false);
        addTable("7","4", false);
        addTable("8","4", false);
        addTable("9","4", false);
    }

    public String getNumTable() {
        return numTable;
    }

    public void setNumTable(String numTable) {
        this.numTable = numTable;
    }

    public String getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(String numPeople) {
        this.numPeople = numPeople;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
