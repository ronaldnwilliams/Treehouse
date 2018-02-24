package com.rnw;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        BufferedReader br = null;
        String line = "";
        int count = 0;
        String abc = "abcdefghijklmnopqrstuvwxyz";
        String letters = abc + abc.toUpperCase();
        try {
            br = new BufferedReader(new FileReader("/Users/ronaldwilliams/Programming/Treehouse/Java/RestAPI/FileReader/src/com/rnw/file/ltcos_op.csv"));
            while ((line = br.readLine()) != null) {
                String[] stylist = line.toString().split(", ");
                String date = stylist[4];
                String location = stylist[1];
                try {
                    int newDate = Integer.parseInt(date.substring(9, 11));
                    if ( newDate >= 18 && (location.contains("TRAVIS") || location.contains("WILLIAMSON") || location.contains("BASTROP") || location.contains("HAYS"))) {
                        count++;
                        System.out.println(location + " " + date.substring(9, 11));
                    }
                } catch (NumberFormatException e) {

                } catch (StringIndexOutOfBoundsException e) {

                }
            }
            System.out.println("Total = " + count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
