package com.trackgenesis;
import com.trackgenesis.UI.Login;
import java.io.IOException;
import com.trackgenesis.util.KeyboardReader;

public class Main {

    private boolean loggedIn = false;
    private KeyboardReader kbr = new KeyboardReader();

    public void start() throws IOException {
        this.kbr = new KeyboardReader();
        String filePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/users.txt"; // Path to your users file
//        String filePath = "../users.txt";
        Login login = new Login(filePath); // Create the object


        while (!loggedIn) {
            loggedIn = login.login();
            kbr.newLine();
            // Call the login method
        }
    }

    public static void main(String[] args) throws IOException {
        new Main().start();

    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}