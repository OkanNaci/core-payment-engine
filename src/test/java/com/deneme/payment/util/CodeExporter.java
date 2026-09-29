package com.deneme.payment.util;

import java.io.IOException;
import java.nio.file.*;

public class CodeExporter {

    public static void main(String[] args) {
        String outputFile = "tum_kodlar.txt";
        Path projectRoot = Paths.get(".");

        try (var writer = Files.newBufferedWriter(Paths.get(outputFile))) {
            Files.walk(projectRoot)
                    .filter(Files::isRegularFile)
                    .filter(path -> {
                        String pathStr = path.toString();
                        // target, .git, .idea gibi gereksiz klasörleri atla
                        if (pathStr.contains("target") || pathStr.contains(".git") || pathStr.contains(".idea")) {
                            return false;
                        }
                        // Sadece bu uzantılara sahip dosyaları al
                        return pathStr.endsWith(".java") ||
                                pathStr.endsWith("pom.xml") ||
                                pathStr.endsWith(".yml") ||
                                pathStr.endsWith(".properties");
                    })
                    .forEach(path -> {
                        try {
                            writer.write("=================================================\n");
                            writer.write("DOSYA: " + projectRoot.relativize(path) + "\n");
                            writer.write("=================================================\n");
                            writer.write(Files.readString(path));
                            writer.write("\n\n");
                        } catch (IOException e) {
                            System.err.println("Dosya okunamadı: " + path);
                        }
                    });

            System.out.println("✅ GÖREV TAMAMLANDI!");
            System.out.println("Bütün kodların şu dosyada toplandı: " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}