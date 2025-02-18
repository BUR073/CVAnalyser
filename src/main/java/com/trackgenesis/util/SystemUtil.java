package com.trackgenesis.util;

import java.io.FileWriter;
import java.io.IOException;

public class SystemUtil {

    public static void exit() {
        System.exit(1);
    }


    public static void emptyFile(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(""); // Overwrite with an empty string
        }
    }
}

