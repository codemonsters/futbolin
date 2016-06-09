package es.codemonsters.futbolin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import es.codemonsters.futbolin.Utiles;


import es.codemonsters.futbolin.MyGdxGame;

public class GameScreen implements Screen {
	//---- Camara y visualizacion
	private OrthographicCamera camera;
	private FillViewport viewport;
	
	//---- Mundo y elementos
	private MyGdxGame game;
	private World world;
	private Fixture fixture;
	
	//---- Sprites y texturas
	private Texture textureFondo;
	private Body bodyBola;
	private Texture textureBola;
	private Sprite spriteBola;
	private Array<Body> marcosArray;
	private Texture texturaPixel;
	private Body bodyMarco;
	private Texture texturaMarcoHor, texturaMarcoVert;
	
	
	
	public GameScreen(final MyGdxGame game) {
        this.game = game; 
        
        //---- Camara y visualizacion
        camera = new OrthographicCamera(); // Camara ortogonal
        viewport = new FillViewport(1024, 576, camera);  // Vision rectangular de un area de la pantalla, en este caso ocupa toda ella
        viewport.apply();  // Aplica el viewport a la camara
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);  // Colocamos la camara en el medio del viewport (centro de la pantalla)
        
        //---- Mundo y elementos
        world = new World(new Vector2(0, -98f), true);  // --> Vector2(fuerza del viento, fuerza gravedad)
        
        //-Bola
        textureFondo = new Texture(Gdx.files.internal("fondo_160x90.jpg"));
        textureBola = new Texture(Gdx.files.internal("ball_50x50.png"));
        spriteBola = new Sprite(textureBola);
        
        BodyDef bodyBolaDef = new BodyDef();  // Guarda informacion del cuerpo (tipo, densidad, grados de rotacion...)
        bodyBolaDef.type = BodyDef.BodyType.DynamicBody;  // Tipo de cuerpo, el dinamico reacciona ante fuerzas, colisiones y otros eventos del mundo 
        bodyBolaDef.position.set(game.WORLD_WIDTH/2, game.WORLD_HEIGHT/2);  // En los cuerpos el origen de coordenadas es el centro, por lo que no hay que restar
        bodyBola = world.createBody(bodyBolaDef);  // Creacion del cuerpo en el mundo
        
        CircleShape shapeBola = new CircleShape();  // Forma del cuerpo, en este caso un circulo
        shapeBola.setRadius(textureBola.getWidth()/2); 
        
        FixtureDef fixtureDefBola = new FixtureDef();  // Forma fisica del cuerpo que se adjunta al cuerpo para darle propiedades como masa, densidad...
        fixtureDefBola.shape = shapeBola;  
        fixtureDefBola.density = 1f;
        fixture = bodyBola.createFixture(fixtureDefBola);  // Creacion de la forma fisica en el cuerpo
        
        shapeBola.dispose();
        
        //-Marco
        texturaMarcoHor = Utiles.crearTextura((int)(10*game.METROS_TO_PIXEL), (int)(30*game.METROS_TO_PIXEL), Color.BLACK);
        texturaMarcoVert = Utiles.crearTextura((int)(160*game.METROS_TO_PIXEL), (int)(10*game.METROS_TO_PIXEL), Color.BLACK);
        
        BodyDef bodyMarcoDef = new BodyDef();  // Guarda informacion del cuerpo (tipo, densidad, grados de rotacion...)
    	bodyMarcoDef.type = BodyDef.BodyType.StaticBody;  // Tipo de cuerpo, el estatico no reacciona ante fuerzas, colisiones u otros eventos del mundo 
    	bodyMarcoDef.position.set(0, 0);
    	bodyMarco = world.createBody(bodyMarcoDef);  // Creacion del cuerpo en el mundo
    	
    	PolygonShape shapeMarco = new PolygonShape();  // Forma del cuerpo
    	shapeMarco.setAsBox(texturaMarcoVert.getWidth(), texturaMarcoVert.getHeight());
        
        FixtureDef fixtureDefMarco = new FixtureDef();  // Forma fisica del cuerpo que se adjunta al cuerpo para darle propiedades como masa, densidad...
        fixtureDefMarco.shape = shapeMarco;  
        fixtureDefMarco.density = 1f;
        fixture = bodyMarco.createFixture(fixtureDefMarco);  // Creacion de la forma fisica en el cuerpo
        
        shapeMarco.dispose();
    	
        
    	/*
    	for(int i=1 ; i<7 ; i++){  // Marcos horizontales
    		BodyDef bodyMarcoDef = new BodyDef();  // Guarda informacion del cuerpo (tipo, densidad, grados de rotacion...)
        	bodyMarcoDef.type = BodyDef.BodyType.StaticBody;  // Tipo de cuerpo, el estatico no reacciona ante fuerzas, colisiones u otros eventos del mundo 
        	
        	PolygonShape shapeMarco = new PolygonShape();  // Forma del cuerpo
        	shapeMarco.setAsBox(texturaMarcoHor.getWidth(), texturaMarcoHor.getHeight());
        
        	marcosArray.add(body);
            body = world.createBody(bodyMarcoDef);  // Creacion del cuerpo en el mundo
            
            FixtureDef fixtureDefMarco = new FixtureDef();  // Forma fisica del cuerpo que se adjunta al cuerpo para darle propiedades como masa, densidad...
            fixtureDefMarco.shape = shapeMarco;  
            fixtureDefMarco.density = 1f;
            fixture = body.createFixture(fixtureDefMarco);  // Creacion de la forma fisica en el cuerpo
            
            shapeMarco.dispose();
        }
    	bodyBolaDef.position.set(, );
        
    	for(int i=1 ; i<3 ; i++){  // Marcos horizontales
    		BodyDef bodyMarcoDef = new BodyDef();  // Guarda informacion del cuerpo (tipo, densidad, grados de rotacion...)
        	bodyMarcoDef.type = BodyDef.BodyType.StaticBody;  // Tipo de cuerpo, el estatico no reacciona ante fuerzas, colisiones u otros eventos del mundo 
        	bodyBolaDef.position.set(, );
        	
        	PolygonShape shapeMarco = new PolygonShape();  // Forma del cuerpo
        	shapeMarco.setAsBox(texturaMarcoHor.getWidth(), texturaMarcoHor.getHeight());
        
        	marcosArray.add(body);
            body = world.createBody(bodyMarcoDef);  // Creacion del cuerpo en el mundo
            
            FixtureDef fixtureDefMarco = new FixtureDef();  // Forma fisica del cuerpo que se adjunta al cuerpo para darle propiedades como masa, densidad...
            fixtureDefMarco.shape = shapeMarco;  
            fixtureDefMarco.density = 1f;
            fixture = body.createFixture(fixtureDefMarco);  // Creacion de la forma fisica en el cuerpo
            
            shapeMarco.dispose();
        }
    	*/
        	
    	
    	
        //-Jugador
        
        //---- Color de relleno render
        Gdx.gl.glClearColor(1, 1, 1, 1);
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	public void update(){
		//---- Actualizar bola
        spriteBola.setPosition(bodyBola.getPosition().x*game.METROS_TO_PIXEL - spriteBola.getWidth()/2, bodyBola.getPosition().y*game.METROS_TO_PIXEL - spriteBola.getHeight()/2);
	}
	
	@Override
	public void render(float delta) {
		//---- Actualizar
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);  // --> step(tiempo que transcurre en el mundo, iteraciones de velocidad, iteraciones de posicion)	
		camera.update();  // Actualizar la camara
		update();  // Actualizar los elementos del mundo

		//---- Dibujar
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.setProjectionMatrix(camera.combined);  // Combina el view con la proyeccion de la camara
        game.batch.begin();
        
        game.batch.draw(textureFondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(spriteBola, spriteBola.getX(), spriteBola.getY());
        game.batch.draw(texturaMarcoVert, 0, 0);
        
        game.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		textureBola.dispose();
		textureFondo.dispose();
		texturaPixel.dispose();
		texturaMarcoHor.dispose();
		texturaMarcoVert.dispose();
		
		world.dispose();
		
	}

}
