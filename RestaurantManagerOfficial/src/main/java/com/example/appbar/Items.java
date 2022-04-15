package com.example.appbar;

import com.example.appbar.DataBase;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Items {
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

        myRef.child(dataBase.PARENT_ITEMS()).child(PK).setValue(addSampleItems);
    }

    public void addAllSampleItems() {
        addItem("coffee", "Café", 1.4);
        addItem("coffeeMilk", "Café con leche", 1.4);
        addItem("coffeAlone", "Café solo", 1.4);
        addItem("beer", "Cerveza", 1.4);
        addItem("cola", "Coca Cola", 1.4);
        addItem("water", "Agua", 1.4);
        addItem("wine", "Vino", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);
        addItem("coffee", "Café", 1.4);

    }

}

/*

    private final String coffee = "Café";
    private final String coffeeMilk = "Café con leche";
    private final String coffeAlone= "Café solo";
    private final String beer = "Cerveza";
    private final String cola= "Coca Cola";
    private final String water = "Agua";
    private final String wine = "Vino";
    private final String cesar_salad = "Ensalada César";
    private final String mixed_salad = "Ensalada Mixta";
    private final String iberic_salad = "Ensalada Ibérica";
    private final String starter_iberian_ham= "Jamón Ibérico";
    private final String starter_bread_tomato = "Pan con tomate";
    private final String starter_cheese = "Queso";
    private final String starter_chips = "Patatas fritas";
    private final String starter_spicy_potatoes = "Patatas bravas";
    private final String meat_sirloin = "Solomillo";
    private final String meat_entrecote = "Entrecot";
    private final String meat_ribeye = "Chuletón";
    private final String rice_paella = "Paella";
    private final String rice_black = "Arroz negro";
    private final String rice_shellfish = "Arroz con mariscos";
    private final String fish_cod = "Bacalao";
    private final String fish_tuna = "Atún";
    private final String fish_hake = "Merluza";
    private final String kids_chicken_breast = "Pollo a la plancha";
    private final String kids_spaghetti_bolognese= "Espagueti a la boloñesa";
    private final String kids_nuggets = "Nuggets";
    private final String dissert_cheesecake = "Tarta de queso";
    private final String dissert_brownie = "Brownie";
    private final String dissert_ice_cream = "Helado";






    private void items() {

        itemsMap = new HashMap<String, String>(){{
            put("coffee","Café");
            put("coffeeMilk","Café con leche");
            put("coffeAlone","");
            put("beer","Cerveza");
            put("cola","Coca Cola");
            put("water","Agua");
            put("wine","Vino");
            put("cesar_salad","Ensalada César");
            put("mixed_salad","Ensalada Mixta");
            put("iberic_salad","Jamón Ibérico");
            put("starter_iberian_ham","Ensalada Ibérica");
            put("starter_bread_tomato","Pan con tomate");
            put("starter_cheese","Queso");
            put("starter_chips","Patatas fritas");
            put("starter_spicy_potatoes","Patatas bravas");
            put("meat_sirloin","Solomillo");
            put("meat_entrecote","Entrecot");
            put("","");
            put("","");
            put("","");
            put("","");
            put("","");
            put("","");
            put("","");
            put("","");
            put("","");
            put("","");
            put("","");
            put("","");
        }};
    }

 */

