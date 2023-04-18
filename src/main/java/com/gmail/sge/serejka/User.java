package com.gmail.sge.serejka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class User {
    private String name;
    private String password;
    private boolean active;

    public User(String name, String password, boolean active) {
        this.name = name;
        this.password = password;
        this.active = active;
    }

    public User() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String toJson(){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static User fromJson(String json){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, User.class);
    }

    public int check(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        try {
            String json = toJson();
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            return connection.getResponseCode();
        } finally {
            outputStream.close();
        }
    }


    @Override
    public String toString() {
        return "User{" +
                " name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                '}';
    }
}