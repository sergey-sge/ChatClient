package com.gmail.sge.serejka;

public class Utils {
    private static final String URL = "http://localhost";
    private static final int PORT = 8080;

    public static String getUrl(){
        return URL + ":" + PORT;
    }
}