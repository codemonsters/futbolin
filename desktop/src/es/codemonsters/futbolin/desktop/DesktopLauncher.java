package es.codemonsters.futbolin.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import es.codemonsters.futbolin.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = MyGdxGame.TITLE;
		config.width = 1024;
		config.height = 576;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
