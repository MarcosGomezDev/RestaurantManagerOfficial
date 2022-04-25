package com.example.appbar.data;

public class StaffData {
    private DataBase dataBase = new DataBase();
    private String fecha;
    private String horaini;
    private String horafin;
    private String dni;

    public StaffData() {
    }

    public StaffData(String fecha, String horaini, String horafin, String dni) {
        this.fecha = fecha;
        this.horaini = horaini;
        this.horafin = horafin;
        this.dni = dni;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraini() {
        return horaini;
    }

    public void setHoraini(String horaini) {
        this.horaini = horaini;
    }

    public String getHorafin() {
        return horafin;
    }

    public void setHorafin(String horafin) {
        this.horafin = horafin;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    public void addBookings(String dni, String horaini, String horafin, String fecha ){
        StaffData add = new StaffData(dni,horaini,horafin,fecha );
        String userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID)
                .child(dataBase.PARENT_STAFF())
                .child(dni)
                .setValue(add);
    }
}
