package com.neves6.piazzapanic.tests;

import com.neves6.piazzapanic.gamemechanisms.Utility;
import java.io.File;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestUtility {

  @Test
  public void testSettingsFileCreateDefault() {
    // Delete any existing file
    File settingsFile = new File("./settings.txt");
    if (settingsFile.exists()) {
      settingsFile.delete();
    }
    // Creates new file with default values if one doesn't exist
    ArrayList<String> settings = Utility.getSettings();
    // Test if file exists
    Assert.assertTrue("New settings file cannot be created", settingsFile.exists());
  }

   @Test public void testSettingsFileReadDefaultValues() {
    // Delete any existing file
     File settingsFile = new File("./settings.txt");
   if (settingsFile.exists()) {
     settingsFile.delete();
   } // ArrayList containing expected values
     ArrayList<String> defaultSettings = new ArrayList<>();
   defaultSettings.add("windowed");
   defaultSettings.add("full");
   // Creates new file with default values, reads values to ArrayList
     ArrayList<String> settings = Utility.getSettings();
     // Test if values read from file match expected values
   Assert.assertTrue( "Default settings file contents don't match expected values",
   settings.equals(defaultSettings)); }

  @Test
  public void testSettingsFileReadWriteNewValues() {
    // ArrayList containing non-default values
    ArrayList<String> newSettings = new ArrayList<>();
    newSettings.add("fullscreen");
    newSettings.add("none");
    // Creates new file with non-default values, reads values to ArrayList
    Utility.setSettings(newSettings);
    ArrayList<String> settings = Utility.getSettings();
    // Test if values read from file match expected values
    Assert.assertTrue(
        "Edited settings file contents don't match expected changes", settings.equals(newSettings));
  }
}
