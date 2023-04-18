package com.gmail.sge.serejka;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static String to = null;
    static String login = "";
    static String password = "";
    static User user;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            doLogin();
            Thread th = new Thread(new GetThread(login));
            th.setDaemon(true);
            th.start();
            System.out.println("Enter your message");
            while (true) {
                String text = scanner.nextLine();
                to = null;
                if (text.isEmpty()){
                    new GetUsers().exitForUser(user);
                    break;
                }
                checkText(text);
                Message m = new Message(login,text,to);
                int res = m.send(Utils.getUrl() + "/add");
                if (res != 200){
                    System.out.println("HTTP error occured: " + res);
                    return;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void checkText(String text) throws IOException {
        if (text.equals("/users")){
            to = user.getName();
            new GetUsers().getList();
        }

        if (text.contains("/check")) {
            String[] array = text.split(" ");
            String name = array[1];
            String activity = "";
            to = user.getName();
            if (new GetUsers().getUserFromList(name) != null) {
                if (new GetUsers().getUserFromList(name).isActive()) {
                    activity = " is online";
                } else {
                    activity = " is offline";
                }
                System.out.println("User " + name + activity);
            } else {
                System.out.println("User not found");
            }
        }
        if (text.contains("@")){
            String [] temp = text.split(" ");
            for (int i = 0; i < temp.length; i++){
                if (temp[i].contains("@")){
                    to = temp[i].substring(1);
                    if (to.equals("room")){
                        to += " " + temp[i+1];
                    }
                }
            }
        }

        if (text.contains("/join")){
            String[] array = text.split(" ");
            String name ="";
            for (int i = 0; i < array.length; i++){
                if (array[i].contains("/join")){
                    to = user.getName();
                    name = array[i + 1];
                }
            }
            System.out.println(new GetRoom().joinRoom(name,user.getName()));
        }

        if (text.contains("/create")){
            String[] array = text.split(" ");
            String name = "";
            for (int i = 0; i < array.length; i++){
                if (array[i].contains("/create")){
                    to = user.getName();
                    name = array[i + 1];
                }
            }
            System.out.println(new GetRoom().createRoom(name, user.getName()));
        }
    }

    public static void doLogin() throws IOException {
        do {
            System.out.println("Enter your login");
            login = scanner.nextLine();
            System.out.println("Enter your password");
            password = scanner.nextLine();
            user = new User(login,password,false);
            user.check(Utils.getUrl() + "/user");
            user = new GetUsers().getUserFromList(login);
            if (!user.isActive()){
                System.out.println("Incorrect password");
            }
        } while (!user.isActive());
    }
}