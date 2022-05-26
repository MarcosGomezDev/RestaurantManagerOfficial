package com.example.appbar.data;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
Clase para el majero de las Reservas
 */

@SuppressWarnings({"FieldCanBeLocal", "SpellCheckingInspection", "UnnecessaryLocalVariable", "SimpleDateFormat"})
public class StaffData {
    private final DataBase dataBase = new DataBase();
    private String fecha;
    private String horaini;
    private String horafin;
    private String dni;
    private final String dia= "dd";
    private final String mes ="MM";
    private final String anno = "yyyy";
    //Constructores
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

    public String getHoraini() {
        return horaini;
    }

    public String getHorafin() {
        return horafin;
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
