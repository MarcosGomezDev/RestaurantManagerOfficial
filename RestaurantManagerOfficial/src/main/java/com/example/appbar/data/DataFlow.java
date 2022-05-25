package com.example.appbar.data;

/**
 * Clase con variables estaticas accesibles desde todas las clases.
 */
public class DataFlow {
    /**
     * Datos para los Items
     */
    public static String currentDescriptionItemString;
    public static double currentPriceItemDouble;
    public static long currentUnitItemLong;

    /**
     * Datos para Table Box
     */
    public static boolean comeFromTableBox;
    public static double amountItemBasketDouble;
    public static double totalItemAmountPrice;

    /**
     * Datos Para Tables
     */
    public static String currentNumTableString;
    public static String currentCapacityTableString;
    public static boolean currentReservedTableBool;

    /**
     * Datos para reservas
     */
    public static String getNombre;
    public static String getFecha;
    public static String getEmail;
    public static String getTelefono;
    public static String getPersonas;
}
