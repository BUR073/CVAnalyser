//package com.example;
//
//import com.example.utils.Encrypt;
//
//public class App {
//    public static void main(String[] args) {
//
//        Encrypt.createKey();
//        Menu.showMenu();
//
//
//    }
//}
package com.example;

import com.example.utils.Encrypt;

public class App {
    public static void main(String[] args) {

        Encrypt.createKey();        // Call createKey() on the instance

        Menu.showMenu();
    }
}