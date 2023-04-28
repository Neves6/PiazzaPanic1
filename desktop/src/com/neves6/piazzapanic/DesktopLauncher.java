package com.neves6.piazzapanic;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM
// argument
public class DesktopLauncher {
  public static void main(String[] arg) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setForegroundFPS(60);
    config.setTitle("Piazza Panic!");
    config.useVsync(true);
    config.setWindowedMode(1280, 720); // 720p, 16:9
    config.setWindowSizeLimits(1280, 720, 7680, 4320); // min 720p, max 8k
    new Lwjgl3Application(new PiazzaPanicGame(), config);
  }
}
