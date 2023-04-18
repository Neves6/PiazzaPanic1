package com.neves6.piazzapanic.gamemechanisms;

import java.io.*;
import java.util.ArrayList;

/** Helper/convenience class. Currently only handles retrieving and saving settings. */
public final class Utility {
  public static String settingsFilepath = "./settings.txt";

  /** Utility constructor. SHOULD NOT BE INITIALIZED! */
  public Utility() {}

  /**
   * Retrieves settings from file.
   *
   * @return ArrayList of settings values.
   */
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

  /**
   * Saves settings to file.
   *
   * @param settings ArrayList of settings values to be saved.
   */
  public static void setSettings(ArrayList<String> settings) {
    settingsFileExistenceHandler();
    try {
      BufferedWriter outputWriter = null;
      outputWriter = new BufferedWriter(new FileWriter(settingsFilepath));
      for (String setting : settings) {
        outputWriter.write(setting + "");
        // outputWriter.newLine();
        outputWriter.write("\n");
      }
      outputWriter.flush();
      outputWriter.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Checks if file exists, creates it if it doesn't and saves default values. */
  private static void settingsFileExistenceHandler() {
    File f = new File(settingsFilepath);
    if (f.isFile()) {
      return;
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
