package net.davidnorton.flappybear.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.davidnorton.flappybear.FBGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Flappy Bear";
        config.width = 272;
        config.height = 408;
		new LwjglApplication(new FBGame(), config);
	}
}
