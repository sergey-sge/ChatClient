package com.gmail.sge.serejka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Message {
    private Date date = new Date();
    private String from;
    private String to;
    private String text;

    public Message(String from, String text, String to) {
        this.from = from;
        this.text = text;
        this.to = to;
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static Message fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, Message.class);
    }

    public int send(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        try {
            String json = toJSON();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            return connection.getResponseCode();
        }  finally {
            os.close();
        }
    }



    @Override
    public String toString() {
        return "Message{" +
                "date=" + date +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
