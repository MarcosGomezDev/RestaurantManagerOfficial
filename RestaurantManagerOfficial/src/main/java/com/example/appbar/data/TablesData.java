package com.example.appbar.data;

/**
 * Clase para el manejo de las mesas.
 */
public class TablesData {

    private final DataBase dataBase = new DataBase();
    private String numTable;
    private String numPeople;
    private boolean reserved;

    public TablesData(){
    }

    /**
     * Constructor de la clase TablesData
     * @param numTable Identificador de la mesa, puede ser un número o un carácter.
     * @param numPeople Numero de comensales que puede tener una mesa.
     * @param reserved Boleano que nos indica si la mesa esta reservada o no.
     */
    public TablesData(String numTable, String numPeople, boolean reserved) {
        this.numTable = numTable;
        this.numPeople = numPeople;
        this.reserved = reserved;
    }

    /**
     * Metodo para añadir una mesa.
     * @param numTable Identificador de la mesa, puede ser un número o un carácter.
     * @param numPeople Numero de comensales que puede tener una mesa.
     * @param reserved Boleano que nos indica si la mesa esta reservada o no.
     */
    public void addTable(String numTable, String numPeople, boolean reserved) {
        TablesData addTable = new TablesData(numTable, numPeople, reserved);
        String userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(numTable)
                .setValue(addTable);
    }

    /**
     * Método para añadir las mesas iniciales de ejemplo.
     */
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

    public String getNumPeople() {
        return numPeople;
    }

    public boolean isReserved() {
        return reserved;
    }
}
