package com.example.appbar.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StaffData {
    private DataBase dataBase = new DataBase();
    private String fecha;
    private String horaini;
    private String horafin;
    private String dni;
    private String dia= "dd";
    private String mes ="MM";
    private String anno = "yyyy";



    public StaffData() {
    }

    public StaffData(String dni, String horaini, String horafin, String fecha) {
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
                .child(dni+" "+fechadia()+fechames()+fechaanno())
                .setValue(add);
    }
    public String fechadia(){
        String dia1 = new SimpleDateFormat(dia).format(new Date());
        return dia1;
    }
    public String fechames(){
        String mes1 = new SimpleDateFormat(mes).format(new Date());
        return mes1;
    }
    public String fechaanno(){
        String anno1 = new SimpleDateFormat(anno).format(new Date());
        return anno1;
    }
}
