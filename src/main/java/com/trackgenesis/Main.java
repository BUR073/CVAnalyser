// SID: 2408078

package com.trackgenesis;
import com.trackgenesis.UI.Menu;

import java.io.IOException;


public class Main {


    public Main() {}


    public static void main(String[] args) throws IOException {
        System.setProperty("console.encoding", "UTF-8");
        Menu menu = new Menu();
        menu.showMenu();

    }

}