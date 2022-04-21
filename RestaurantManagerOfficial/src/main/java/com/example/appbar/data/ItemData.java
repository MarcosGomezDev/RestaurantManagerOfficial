package com.example.appbar.data;

public class ItemData {

    private DataBase dataBase = new DataBase();
    private String PK;
    private String description;
    private double price;
    public boolean noItems = true;

    public ItemData() {
    }

    public ItemData(String description, double price) {
        this.description = description;
        this.price = price;
    }

    public ItemData(String PK, String description, double price) {
        this.PK = PK;
        this.description = description;
        this.price = price;
    }

    public String getPK() {
        return PK;
    }

    public void setPK(String PK) {
        this.PK = PK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceString() { return String.valueOf(price); }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addItem(String PK, String description, double price){
        ItemData addItem = new ItemData(PK, description, price);
        String userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID)
                .child(dataBase.PARENT_ITEMS())
                .child(PK)
                .setValue(addItem);
    }

    public void listItems(){
        String userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID)
                .child(dataBase.PARENT_ITEMS())
                .getDatabase();
    }

    public void addAllSampleItems() {
//        addItem(dataBase.generatePK(), "Café", 1.4);
//        addItem(dataBase.generatePK(), "Café con leche", 1.4);
//        addItem(dataBase.generatePK(), "Café solo", 1.4);
//        addItem(dataBase.generatePK(), "Cerveza", 1.4);
//        addItem(dataBase.generatePK(), "Coca Cola", 1.4);
//        addItem(dataBase.generatePK(), "Agua", 1.4);
//        addItem(dataBase.generatePK(), "Vino", 1.4);
//        addItem(dataBase.generatePK(), "Ensalada César", 1.4);
//        addItem(dataBase.generatePK(), "Ensalada Mixta", 1.4);
//        addItem(dataBase.generatePK(), "Ensalada Ibérica", 1.4);
//        addItem(dataBase.generatePK(), "Jamón Ibérico", 1.4);
        addItem("starter_bread_tomato", "Pan con tomate", 1.4);
        addItem("starter_cheese", "Queso", 1.4);
        addItem("starter_chips", "Patatas fritas", 1.4);
        addItem("starter_spicy_potatoes", "Patatas bravas", 1.4);
        addItem("meat_sirloin", "Solomillo", 1.4);
        addItem("meat_entrecote", "Entrecot", 1.4);
        addItem("meat_ribeye", "Chuletón", 1.4);
        addItem("rice_paella", "Paella", 1.4);
        addItem("rice_black", "Arroz negro", 1.4);
        addItem("rice_shellfish", "Arroz con mariscos", 1.4);
        addItem("fish_cod", "Bacalao", 1.4);
        addItem("fish_tuna", "Atún", 1.4);
        addItem("fish_hake", "Merluza", 1.4);
        addItem("kids_chicken_breast", "Pollo a la plancha", 1.4);
        addItem("kids_spaghetti_bolognese", "Espagueti a la boloñesa", 1.4);
        addItem("kids_nuggets", "Nuggets", 1.4);
        addItem("dissert_cheesecake", "Tarta de queso", 1.4);
        addItem("dissert_brownie", "Brownie", 1.4);
        addItem("dissert_ice_cream", "Helado", 1.4);

    }


}
