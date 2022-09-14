package com.ethnicthv.util;

import java.io.IOException;
import java.util.Scanner;

public class Run {
    public static String PATH = "";
    public static String OUTPUT = "";

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.print("Input Path: ");
        PATH = scanner.nextLine();
        System.out.print("Output Path: ");
        OUTPUT = scanner.nextLine();
        System.out.print("Output File Name: ");
        App app = new App(scanner.nextLine());
        if(app.checkValid(PATH,OUTPUT)) app.run();
    }
}
