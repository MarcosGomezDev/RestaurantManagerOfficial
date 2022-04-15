package com.example.appbar;

import com.example.appbar.DataBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Items implements Serializable{
    private DataBase dataBase = new DataBase();
    private DatabaseReference myRef;
    public static boolean correctInto = false;
    private String PK;
    private String description;
    private double price;

    public Items() {
    }

    public Items(String description, double price) {
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

    public void setPrice(double price) {
        this.price = price;
    }

    public void addItem(String PK, String description, double price){
        myRef = dataBase.getInstance().getReference();
        Items addSampleItems = new Items(description, price);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userUID = currentUser.getUid();
        myRef.child(userUID).child(dataBase.PARENT_ITEMS()).child(PK).setValue(addSampleItems);
    }

    public void listItems(){
        myRef = dataBase.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userUID = currentUser.getUid();
        myRef.child(userUID).child(dataBase.PARENT_ITEMS()).getDatabase();
    }

    public void addAllSampleItems() {
        addItem("coffee", "Café", 1.4);
        addItem("coffeeMilk", "Café con leche", 1.4);
        addItem("coffeAlone", "Café solo", 1.4);
        addItem("beer", "Cerveza", 1.4);
        addItem("cola", "Coca Cola", 1.4);
        addItem("water", "Agua", 1.4);
        addItem("wine", "Vino", 1.4);
        addItem("cesar_salad", "Ensalada César", 1.4);
        addItem("mixed_salad", "Ensalada Mixta", 1.4);
        addItem("iberic_salad", "Ensalada Ibérica", 1.4);
        addItem("starter_iberian_ham", "Jamón Ibérico", 1.4);
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

