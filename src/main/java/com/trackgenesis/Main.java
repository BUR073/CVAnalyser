package com.trackgenesis;
import com.trackgenesis.UI.Menu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {


    public Main() {}


    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
        menu.showMenu();

    }

}