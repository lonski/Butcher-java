package org.lonski.butcher.desktop;

import org.lonski.butcher.Butcher;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 768;
		config.title = "Butcher";
		new LwjglApplication(new Butcher(), config);
	}
}
