package com.neves6.piazzapanic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public final class Utility {

    public static String settingsFilepath = "./settings.txt";

    private Utility() {
    } // not intended to be instantiated

    public static ArrayList<String> getSettings() {
        settingsFileExistenceHandler();
        ArrayList<String> settings = new ArrayList<String>();
        try (FileReader f = new FileReader(settingsFilepath)) {
            StringBuffer sb = new StringBuffer();
            while (f.ready()) {
                char c = (char) f.read();
                if (c == '\n') {
                    settings.add(sb.toString());
                    sb = new StringBuffer();
                } else {
                    sb.append(c);
                }
            }
            if (sb.length() > 0) {
                settings.add(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return settings;
    }

    public static void setSettings(ArrayList<String> settings) {
        settingsFileExistenceHandler();
        try {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(settingsFilepath));
        for (int i = 0; i < settings.size(); i++) {
            outputWriter.write(settings.get(i)+"");
            //outputWriter.newLine();
            outputWriter.write("\n");
        }
        outputWriter.flush();
        outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void settingsFileExistenceHandler() {
        File f = new File(settingsFilepath);
        if (f.isFile()) {
        } else {
            try {
                f.createNewFile();
                ArrayList<String> defaults = new ArrayList<String>();
                defaults.add("windowed");
                defaults.add("full");
                setSettings(defaults);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
