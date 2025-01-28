package com.example.processamento;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class EnvLoader {
    private static final Map<String, String> fields = new LinkedHashMap<>();

    public static void loadEnv(String filePath) throws IOException {
        Files.lines(Paths.get(filePath))
             .filter(line -> line.contains("=") && !line.startsWith("#"))
             .forEach(line -> {
                 String[] parts = line.split("=", 2);
                 fields.put(parts[0].trim(), parts[1].trim());
             });
    }

    public static Map<String, String> getFields() {
        return fields;
    }
}
