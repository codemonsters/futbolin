package es.codemonsters.futbolin;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class Utiles {
	public static Texture crearTextura(int ancho, int alto, Color color) {
		Pixmap pixmap = new Pixmap(ancho, alto, Format.RGBA8888);
		pixmap.setColor(color);
    	Texture pixmapTexture = new Texture(pixmap);
    	pixmap.dispose();
		return pixmapTexture;
	}
}
