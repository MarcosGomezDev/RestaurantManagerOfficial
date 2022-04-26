package com.example.appbar.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"FieldCanBeLocal", "UnnecessaryLocalVariable"})
public class DataBase {

    private final FirebaseDatabase instance = FirebaseDatabase.getInstance(
            "https://restaurantmanagerofficial-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference databaseReference = instance.getReference();
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private int key = 10000;
    private final String PARENT_ITEMS = "Productos";
    private final String PARENT_STAFF = "staff";
    private final String PARENT_STAFF_SIGN_IN = "staff_signin";
    private final String PARENT_BOOKING = "booking";
    private final String PARENT_CASH_HISTORY = "cash_history";
    private final String PARENT_TABLES = "tables";
    private final String CHILD_FIRST_NAME = "firt_name";
    private final String CHILD_LAST_NAME = "last_name";
    private final String CHILD_PHONE = "phone";
    private final String CHILD_EMAIL = "email";
    private final String CHILD_NO_BILL = "no_bill"; //Hay que implantar un metodo que cree automaticamente numeros de factura.
    private final String CHILD_PRICE = "price";
    private final String CHILD_PEOPLES = "no_peoples";
    private final String CHILD_DATE = "date";
    private final String CHILD_ID_ITEM = "id_item";

    public DataBase() {}

    public FirebaseDatabase getInstance() { return instance; }

    public DatabaseReference getDatabaseReference() { return databaseReference; }

    public FirebaseUser getCurrentUser() { return currentUser; }

    public int getKey() { return key; }

    public void setKey(int key) { this.key = key; }

    public String PARENT_ITEMS() {
        return PARENT_ITEMS;
    }

    public String PARENT_STAFF() {
        return PARENT_STAFF;
    }

    public String PARENT_STAFF_SIGN_IN() {
        return PARENT_STAFF_SIGN_IN;
    }

    public String PARENT_BOOKING() {
        return PARENT_BOOKING;
    }

    public String PARENT_CASH_HISTORY() {
        return PARENT_CASH_HISTORY;
    }

    public String PARENT_TABLES() {
        return PARENT_TABLES;
    }

    public String CHILD_FIRST_NAME() { return CHILD_FIRST_NAME; }

    public String CHILD_LAST_NAME() { return CHILD_LAST_NAME; }

    public String CHILD_PHONE() { return CHILD_PHONE; }

    public String CHILD_EMAIL() { return CHILD_EMAIL; }

    public String CHILD_NO_BILL() { return CHILD_NO_BILL; }

    public String CHILD_PRICE() { return CHILD_PRICE; }

    public String CHILD_PEOPLES() { return CHILD_PEOPLES; }

    public String CHILD_DATE() { return CHILD_DATE; }

    public String CHILD_ID_ITEM() { return CHILD_ID_ITEM; }

}
