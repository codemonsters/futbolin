package es.codemonsters.futbolin;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

public class Utiles {
	public static Texture crearTextura(int ancho, int alto, Color color) {
		Pixmap pixmap = new Pixmap(ancho, alto, Format.RGBA8888);  // Crea un pixmap
		pixmap.setColor(color);  // Elige el color a utilizar para las siguientes operaciones
		pixmap.fill();  // Rellenga el pixamp del color escogido
		
    	Texture pixmapTexture = new Texture(pixmap);  // Creacion de la textura a partir del pixmap
    	pixmap.dispose();  // Elimina el pixmap
		return pixmapTexture;
	}
	
	public static Body crearCuerpoCircular(World mundo, BodyType tipoCuerpo, float x, float y, float radio, float densidad, float friccion){
		BodyDef bodyDef = new BodyDef();  // Guarda informacion del cuerpo (tipo, densidad, grados de rotacion, velocidad...)
        bodyDef.type = tipoCuerpo;  // Tipo de cuerpo, el dinamico reacciona ante fuerzas, colisiones y otros eventos del mundo 
        bodyDef.position.set(new Vector2(x, y));  // En los cuerpos el origen de coordenadas es el centro
        Body body = mundo.createBody(bodyDef);  // Crea el cuerpo en el mundo que se indica
        
        CircleShape shapeBody = new CircleShape();
        shapeBody.setRadius(radio); 
        
        FixtureDef bodyFixture = crearFixture(shapeBody, densidad, friccion);
        body.createFixture(bodyFixture);
        
        shapeBody.dispose();
        
        return body;     
	}
	
	/***
	 * 
	 * @param mundo
	 * @param tipoCuerpo
	 * @param x  Coordenada x del centro del cuerpo
	 * @param y  Coordenada y del centro del cuerpo
	 * @param ancho
	 * @param alto
	 * @param densidad
	 * @param friccion
	 * @return
	 */
	public static Body crearCuerpoRectangular(World mundo, BodyType tipoCuerpo, float x, float y, float ancho, float alto, float densidad, float friccion){  
		BodyDef bodyDef = new BodyDef();  // Guarda informacion del cuerpo (tipo, densidad, grados de rotacion, velocidad...)
        bodyDef.type = tipoCuerpo;  // Tipo de cuerpo, el dinamico reacciona ante fuerzas, colisiones y otros eventos del mundo 
        bodyDef.position.set(new Vector2(x, y));  // En los cuerpos el origen de coordenadas es el centro
        Body body = mundo.createBody(bodyDef);  // Crea el cuerpo en el mundo que se indica
        
        PolygonShape shapeBody = new PolygonShape();
        shapeBody.setAsBox(ancho/2, alto/2); 
        
        FixtureDef bodyFixture = crearFixture(shapeBody, densidad, friccion);
        body.createFixture(bodyFixture);
        
        shapeBody.dispose();
        
        return body;     
	}
	
	public static FixtureDef crearFixture(Shape formaCuerpo, float densidad, float friccion){ 
        FixtureDef fixtureDef = new FixtureDef();  // Creacion de la forma fisica en el cuerpo con datos como la forma, la densidad, la friccion (en este caso no est� puesta)...
        fixtureDef.shape = formaCuerpo;
        fixtureDef.density = densidad;
        fixtureDef.friction = friccion;
        return fixtureDef;
	}
}
