package org.cangtu.cas9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by CangTu on 16-10-25.
 * BackgroundDatabase类
 */
public class BackgroundDatabase {
    public String s;
    public int cnt;

    public BackgroundDatabase(String s, int cnt) {
        this.s = s.toUpperCase();
        this.cnt = cnt;
    }

    public static int count(String str, String subStr) {
        return str.length() - str.replace(subStr, "").length();
    }

    public static BackgroundDatabase fromCsvLine(String line) {
        String[] parts = line.split(",");
        return new BackgroundDatabase(parts[0], count(parts[1], ";")+1);
    }

    public static List<BackgroundDatabase> readCsv(String csvFileUrl) {
        List<BackgroundDatabase> bdList = new ArrayList<>(100000);
        try (Stream<String> lines = Files.lines(Paths.get(csvFileUrl))) {
            lines.forEach(line -> bdList.add(fromCsvLine(line)));
        } catch (IOException e) {
            System.out.printf("File not found: %s\n", csvFileUrl);
            System.exit(1);
        }
        return bdList;
    }
}
