package com.example.appbar.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@SuppressWarnings({"FieldCanBeLocal"})
public class DataBase {

    private final FirebaseDatabase instance = FirebaseDatabase.getInstance(
            "https://restaurantmanagerofficial-default-rtdb.europe-west1.firebasedatabase.app/");
    private final DatabaseReference databaseReference = instance.getReference();
    private final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private final String PARENT_ITEMS = "items";
    private final String PARENT_STAFF = "staff";
    private final String PARENT_BOOKING = "booking";
    private final String PARENT_TABLES = "tables";

    public DataBase() {
    }

    public FirebaseDatabase getInstance() {
        return instance;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public String PARENT_ITEMS() {
        return PARENT_ITEMS;
    }

    public String PARENT_STAFF() {
        return PARENT_STAFF;
    }

    public String PARENT_BOOKING() {
        return PARENT_BOOKING;
    }

    public String PARENT_TABLES() {
        return PARENT_TABLES;
    }
}
