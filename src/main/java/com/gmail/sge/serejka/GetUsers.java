package com.gmail.sge.serejka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetUsers {

    private Gson gson;

    public GetUsers() {
        gson = new GsonBuilder().create();
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

    public void getList() throws IOException{
        try {
            URL url = new URL(Utils.getUrl() + "/user?type=active");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream inputStream = http.getInputStream();
            try {
                byte[] buf = requestBodyToArray(inputStream);
                String list = new String(buf,StandardCharsets.UTF_8);
                System.out.println(list);
            } finally {
                inputStream.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public User getUserFromList(String name) throws IOException {
        User user = null;
        try {
            URL url = new URL(Utils.getUrl() + "/user?type=current&name=" + name);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream is = http.getInputStream();
            try {
                byte[] buf = requestBodyToArray(is);
                String strBuf = new String(buf, StandardCharsets.UTF_8);
                user = gson.fromJson(strBuf, User.class);
            } finally {
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void exitForUser(User user) throws IOException {
        try {
            URL url = new URL(Utils.getUrl() + "/user?type=exit&name=" + user.getName());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream inputStream = http.getInputStream();
            try {
                byte[] buf = requestBodyToArray(inputStream);
                System.out.println("Exit ready for: " + user.getName());
            } finally {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}