package org.cangtu.cas9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * Created by Cangtu on 16-10-25.
 * Cas9类
 */
public class Cas9 {
    public String id;
    public String seq;

    public Cas9(String id, String seq) {
        this.id = id;
        this.seq = seq.toUpperCase();
    }

    public static Cas9 fromCsvLine(String line) {
        String[] parts = line.trim().split(",");
        return new Cas9(parts[0], parts[1]);
    }

    public static List<Cas9> readCsv(String csvFileUrl) {
        List<Cas9> cas9s = new ArrayList<>(100000);
        try (Stream<String> lines = Files.lines(Paths.get(csvFileUrl))) {
            lines.forEach(line -> cas9s.add(fromCsvLine(line)));
        } catch (IOException e) {
            System.out.printf("File not found: %s\n", csvFileUrl);
            System.exit(1);
        }
        return cas9s;
    }
}
