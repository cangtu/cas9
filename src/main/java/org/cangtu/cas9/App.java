package org.cangtu.cas9;

import org.apache.commons.cli.*;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App implements Runnable
{
    private Align align = new Align();

    private String name;
    private List<Cas9> cas9List;
    private String bdFileUrl;
    private String resFileUrl;
    private List<Double> alignResList;

    public App(String name, List<Cas9> cas9List, String bdFileUrl, String resFileUrl) {
        this.name = name;
        this.cas9List = cas9List;
        this.bdFileUrl = bdFileUrl;
        this.resFileUrl = resFileUrl;
        this.alignResList = new ArrayList<>(100000);
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resFileUrl))) {
            for (int i = 0; i < cas9List.size(); i++) {
                Cas9 cas9 = cas9List.get(i);
                writer.write(String.format("%s,%s,%.3f\n", cas9.id, cas9.seq, alignResList.get(i)));
            }
        } catch (IOException e) {
            System.out.printf("Can't write to file: %s\n", resFileUrl);
        }
    }

    public void run() {
        System.out.printf("Thread: %s, reading background database file: %s...\n", name, bdFileUrl);
        List<BackgroundDatabase> bdList = BackgroundDatabase.readCsv(bdFileUrl);
        System.out.printf("Thread: %s, aligning...\n", name);
        long startTime = System.currentTimeMillis();
        cas9List.forEach(cas9 -> alignResList.add(align.batchAlign(cas9, bdList)));
        long endTime = System.currentTimeMillis();
        System.out.printf("Thread: %s, aligning %d candidates takes %d milliseconds.\n", name, cas9List.size(), endTime-startTime);
        System.out.printf("Thread: %s, saving...\n", name);
        save();
    }

    public static Map<String, String> config(String configFileUrl) {
        Map<String, String> configMap = new HashMap<>(50);
        try (BufferedReader br = new BufferedReader(new FileReader(configFileUrl))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");
                configMap.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.printf("File not found: %s\n", configFileUrl);
        }
        return configMap;
    }

    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("h", false, "Lists this help");
        options.addOption("i", "input", true, "Candidates file");
        options.addOption("c", "config", true, "Configurations file--a csv file, first column is bd file, second column is outfile");
        options.addOption("t", "threads", true, "How many threads you want to start");
        Option.builder("t").longOpt("threads").hasArg().desc("How many threads you want to start").build();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("h")) {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("Options", options);
        } else {
            String cas9FileUrl = cmd.getOptionValue("i");
            String configFileUrl = cmd.getOptionValue("c");
            String threadCntString = cmd.getOptionValue("t");
            if ((cas9FileUrl == null) || (configFileUrl == null) || (threadCntString == null)) {
                throw new ParseException("Invalid commandline arguments.");
            } else {
                int threadCnt = Integer.parseInt(threadCntString);
                Map<String, String> configMap = config(configFileUrl);
                if (threadCnt == configMap.size()) {
                    List<Cas9> cas9s = Cas9.readCsv(cas9FileUrl);
                    ExecutorService threadPool = Executors.newFixedThreadPool(threadCnt);
                    for (String bdFile : configMap.keySet()) {
                        String resFile = configMap.get(bdFile);
                        threadPool.execute(new App(String.format("Thread-%s", bdFile), cas9s, bdFile, resFile));
                    }
                    threadPool.shutdown();
                } else {
                    throw new Exception("Thread count should be equal to line count of config file");
                }
            }
        }
    }
}
