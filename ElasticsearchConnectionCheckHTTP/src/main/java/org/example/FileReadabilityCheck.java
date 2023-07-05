package org.example;

import java.io.File;

public class FileReadabilityCheck {

    public static void main(String[] args) {
          String filePath = "C:\\Users\\user\\Downloads\\http_ca\\http_ca.crt";
//        String filePath = "C:\\Users\\user\\Downloads\\SSH_KEY";

        File file = new File(filePath);

        if (file.exists() && file.canRead()) {
            System.out.println("File is readable");
        } else {
            System.out.println("File is not readable");
        }
    }

}
