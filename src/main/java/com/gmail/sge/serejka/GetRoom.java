package com.gmail.sge.serejka;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetRoom {

    public GetRoom() {
        super();
    }

    private byte[] requestBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) {
                bos.write(buf, 0, r);
            }
        } while (r != -1);

        return bos.toByteArray();
    }

    public String joinRoom(String roomName, String userName) {
        String result = "";

        try {
            URL url = new URL(Utils.getUrl() + "/room?type=join&name=" + roomName + "&user=" + userName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            try{
                byte[] buf = requestBodyToArray(inputStream);
                result = new String(buf, StandardCharsets.UTF_8);
            } finally {
                inputStream.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public String createRoom(String roomName, String userName) {
        String result = "";
        try {
            URL url = new URL(Utils.getUrl() + "/room?type=create&name=" + roomName + "&user=" + userName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            try{
                byte[] buf = requestBodyToArray(inputStream);
                result = new String(buf, StandardCharsets.UTF_8);
            } finally {
                inputStream.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}