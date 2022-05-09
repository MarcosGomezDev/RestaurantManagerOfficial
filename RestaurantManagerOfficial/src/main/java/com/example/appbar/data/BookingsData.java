package com.example.appbar.data;

public class BookingsData {
    private DataBase dataBase = new DataBase();
    private String nombre;
    private String email;
    private String telefono;
    private String fecha;
    private String personas;

    public BookingsData(String nombre, String email, String telefono, String fecha,String personas) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fecha = fecha;
        this.personas = personas;
    }

    public BookingsData() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPersonas() {
        return personas;
    }

    public void setPersonas(String personas) {
        this.personas = personas;
    }

    public void addBookings(String nombre, String email, String telefono, String fecha, String personas){
        BookingsData add = new BookingsData(nombre,email, telefono,fecha,personas );
        String userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID)
                .child(dataBase.PARENT_BOOKING())
                .child(nombre)
                .setValue(add);
    }
}


