package com.example.appbar.data;

public class ItemData {

    private DataBase dataBase = new DataBase();
    private String PK;
    private String description;
    private String price;

    public ItemData() {
    }

    public ItemData(String description, String price) {
        this.description = description;
        this.price = price;
    }

    //public String PK() { return PK; }

    public void setPK(String PK) {
        this.PK = PK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void addItem(String description, String price){

        ItemData addItem = new ItemData(description, price);

        String userUID = dataBase.getCurrentUser().getUid();

        String PK = description.replace(" ", "_");

        dataBase.getDatabaseReference().child(userUID)
                .child(dataBase.PARENT_ITEMS())
                .child(PK)
                .setValue(addItem);
    }

    public void addAllSampleItems() {
        addItem("Café", "1.4");
        addItem("Café con leche", "1.4");
        addItem("Café solo", "1.4");
        addItem("Cerveza", "1.4");
        addItem("Coca Cola", "1.4");
        addItem("Agua", "1.4");
        addItem("Vino", "1.4");
        addItem("Ensalada César", "1.4");
        addItem("Ensalada Mixta", "1.4");
        addItem("Ensalada Ibérica", "1.4");
        addItem("Jamón Ibérico", "1.4");
        addItem("Pan con tomate", "1.4");
        addItem("Queso", "1.4");
        addItem("Patatas fritas", "1.4");
        addItem("Patatas bravas", "1.4");
        addItem("Solomillo", "1.4");
        addItem("Entrecot", "1.4");
        addItem("Chuletón", "1.4");
        addItem("Paella", "1.4");
        addItem("Arroz negro", "1.4");
        addItem("Arroz con mariscos", "1.4");
        addItem("Bacalao", "1.4");
        addItem("Atún", "1.4");
        addItem("Merluza", "1.4");
        addItem("Pollo a la plancha", "1.4");
        addItem("Espagueti a la boloñesa", "1.4");
        addItem("Nuggets", "1.4");
        addItem("Tarta de queso", "1.4");
        addItem("Brownie", "1.4");
        addItem("Helado", "1.4");
    }
}
