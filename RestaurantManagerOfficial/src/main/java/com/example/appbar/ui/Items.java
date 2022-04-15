package com.example.appbar.ui;

import com.example.appbar.DataBase;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class Items {
    private DataBase dataBase = new DataBase();
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

    HashMap<String, String> item = new HashMap<String, String>();

    public void createItems() {
        item.put(coffee,"Café");



        // Tengo que pensar un poco mas como voy a introducir los datos.









        DatabaseReference myRef = dataBase.getInstance().getReference(dataBase.ROOT_USER())
                .child(dataBase.PARENT_ITEMS())
                .child(dataBase.CHILD_FIRST_NAME());
        myRef.setValue(coffee);
    }





}
