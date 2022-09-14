package com.ethnicthv.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;

public class App {
    public String OUTPUT_NAME = "/output.txt";
    public File file;
    public File output;

    private String input_path;

    public App(){}

    public App(String output_name) {
        this.OUTPUT_NAME = "/" + output_name + ".txt";
    }

    public boolean checkValid(String path, String output) throws IOException {
        System.out.println("[Logging]: Checking path valid...");
        file = new File(path);
        this.output = new File(output + OUTPUT_NAME);
        if (!this.output.exists()) {
            if (!this.output.createNewFile()) {
                System.out.printf("[Error]: Cannot make output file at: [%s] \n", output);
                return false;
            } else {
                System.out.printf("[Logging]: Output file has been created at: [%s] \n", output);
            }
        } else {
            System.out.printf("[Logging]: Output file has already been created at: [%s] \n", output);
        }
        System.out.println("[Logging]: Output file path is vaid!");

        if (this.file.exists()) {
            if (this.file.isDirectory()) {
                if (this.file.canRead()) System.out.println("[Logging]: Input file path is valid");
                else {
                    System.out.println("[Error]: Input file path is unaccessible!");
                    return false;
                }
            } else {
                System.out.println("[Error]: Input file path is not a Direction!");
                return false;
            }
        } else {
            System.out.println("[Error]: Input file path doesn't exists!");
            return false;
        }
        input_path = path;

        return true;
    }

    public void run() throws IOException {
        HashMap<String, String> output_storage = new HashMap<>();
        Files.walk(file.toPath()).forEach(path -> {
            System.out.println(path);
            File file = path.toFile();
            if (file.getName().contains(".java")) {
                output_storage.put(file.getName(), file.getPath());
            }
        });

        StringBuilder builder = new StringBuilder();
        output_storage.forEach((key, value) -> builder.append(String.format("%-30s%s",classNameFormat(key),packageNameFormat(value))).append('\n'));
        try {
            Files.writeString(output.toPath(), builder.toString(), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String classNameFormat(String class_name) {
        return class_name.substring(0, class_name.length() - 5);
    }

    public String packageNameFormat(String package_name) {
        StringBuilder builder = new StringBuilder(package_name);
        builder.delete(0, input_path.length() + 1);
        builder.delete(builder.length() - 5, builder.length() - 1);
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '\\') builder.setCharAt(i, '.');
        }
        return builder.substring(0,builder.length()-1);
    }
}
