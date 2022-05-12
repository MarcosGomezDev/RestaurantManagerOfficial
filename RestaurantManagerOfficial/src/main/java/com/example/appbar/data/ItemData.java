package com.example.appbar.data;

public class ItemData {

    private final DataBase dataBase = new DataBase();
    private String description;
    private String price;
    private long units;

    public ItemData() {
    }

    public ItemData(String description, String price) {
        this.description = description;
        this.price = price;
    }

    public ItemData(String description, String price, long units) {
        this.description = description;
        this.price = price;
        this.units = units;
    }

    public long getUnits() { return units; }

    public void setUnits(long units) { this.units = units; }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public void addItem(String description, String price) {
        ItemData addItem = new ItemData(description, price, 1);
        String userUID = dataBase.getCurrentUser().getUid();
        String PK = description.replace(" ", "_");
        dataBase.getDatabaseReference().child(userUID)
                .child(dataBase.PARENT_ITEMS())
                .child(PK)
                .setValue(addItem);
    }

    public void addAllSampleItems() {
        addItem("Café", "1.4");
        addItem("Café con leche", "1.6");
        addItem("Café solo", "1.1");
        addItem("Cerveza", "2.4");
        addItem("Coca Cola", "2.4");
        addItem("Agua", "2.4");
        addItem("Vino", "2.4");
        addItem("Ensalada César", "5.4");
        addItem("Ensalada Mixta", "4.4");
        addItem("Ensalada Ibérica", "7.4");
        addItem("Jamón Ibérico", "8.4");
        addItem("Pan con tomate", "3.5");
        addItem("Queso", "3.2");
        addItem("Patatas fritas", "2.8");
        addItem("Patatas bravas", "3.7");
        addItem("Solomillo", "10.4");
        addItem("Entrecot", "12.4");
        addItem("Chuletón", "11.4");
        addItem("Paella", "15.4");
        addItem("Arroz negro", "13.4");
        addItem("Arroz con mariscos", "21");
        addItem("Bacalao", "14");
        addItem("Atún", "14");
        addItem("Merluza", "12");
        addItem("Pollo a la plancha", "10.5");
        addItem("Espagueti a la boloñesa", "8.4");
        addItem("Nuggets", "5.4");
        addItem("Tarta de queso", "3.5");
        addItem("Brownie", "3");
        addItem("Helado", "2.5");
    }
}
