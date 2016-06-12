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
	
	//---- Sprites y texturas
	private Texture texturaFondo;
	
	private Body bodyBola;
	private Texture textureBola;
	private Sprite spriteBola;
	
	private Texture texturaMarcoHor, texturaMarcoVert;
	private Body bodyMarcoSup;
	private Body bodyMarcoInf;
	private Array<Body> marcosArray = new Array<Body>();
	
	
	public GameScreen(final MyGdxGame game) {
        this.game = game; 
        
        //---- Camara y visualizacion
        camera = new OrthographicCamera(); // Camara ortogonal
        viewport = new FillViewport(1024, 576, camera);  // Vision rectangular de un area de la pantalla, en este caso ocupa toda ella
        viewport.apply();  // Aplica el viewport a la camara
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);  // Colocamos la camara en el medio del viewport (centro de la pantalla)
        
        //---- Mundo y elementos
        world = new World(new Vector2(0, -98f), true);  // --> Vector2(fuerza del viento, fuerza gravedad)
        
        //- Fondo
        texturaFondo = new Texture(Gdx.files.internal("fondo_160x90.jpg"));
        
        //- Bola
        textureBola = new Texture(Gdx.files.internal("ball_50x50.png"));
        spriteBola = new Sprite(textureBola);
        
        bodyBola = Utiles.crearCuerpo(world, BodyDef.BodyType.DynamicBody, game.WORLD_WIDTH/2, game.WORLD_HEIGHT/2);
        
        CircleShape shapeBola = new CircleShape();  // Forma del cuerpo, en este caso un circulo
        shapeBola.setRadius(textureBola.getWidth()/2); 
        
        FixtureDef fixtureBola = Utiles.crearFixture(shapeBola, 1f, 0.5f);  
        bodyBola.createFixture(fixtureBola);
        
        shapeBola.dispose();
        
        //- Tablero
        texturaMarcoHor = Utiles.crearTextura((int)(160*game.METROS_TO_PIXEL), 25 , Color.GREEN);
        texturaMarcoVert = Utiles.crearTextura(25, (int)(90*game.METROS_TO_PIXEL), Color.RED);
        
        PolygonShape shapeMarcoHor = new PolygonShape();
        shapeMarcoHor.setAsBox(texturaMarcoHor.getWidth(), texturaMarcoHor.getHeight());
        
        PolygonShape shapeMarcoVert = new PolygonShape();
        shapeMarcoVert.setAsBox(texturaMarcoVert.getWidth(), texturaMarcoVert.getHeight());
        
        FixtureDef fixtureMarcoHor = Utiles.crearFixture(shapeMarcoHor, 1000f, 0);
        shapeMarcoHor.dispose();
        
        FixtureDef fixtureMarcoVert = Utiles.crearFixture(shapeMarcoVert, 1000f, 0);
        shapeMarcoVert.dispose();
        
        bodyMarcoSup = Utiles.crearCuerpo(world, BodyDef.BodyType.StaticBody, 0, Gdx.graphics.getHeight()-texturaMarcoHor.getHeight());
        //bodyMarcoSup.createFixture(fixtureMarcoHor);
        bodyMarcoInf = Utiles.crearCuerpo(world, BodyDef.BodyType.StaticBody, 0, 0);
        bodyMarcoInf.createFixture(fixtureMarcoHor);
        
        marcosArray.add(bodyMarcoSup);
        marcosArray.add(bodyMarcoInf);
        
        //- Jugadores
        
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
        
        //---- Actualizar los jugadores
	}
	
	@Override
	public void render(float delta) {
		//---- Actualizar
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);  // Actualizar los cuerpos --> step(tiempo que transcurre en el mundo, iteraciones de velocidad, iteraciones de posicion)	
		camera.update();  // Actualizar la camara
		update();  // Actualizar los elementos del mundo

		//---- Dibujar
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.setProjectionMatrix(camera.combined);  // Combina el view con la proyeccion de la camara
        
        game.batch.begin();
        
        game.batch.draw(texturaFondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(spriteBola, spriteBola.getX(), spriteBola.getY());
        
        for(Body cuadradoMarco : marcosArray){
        	game.batch.draw(texturaMarcoHor, cuadradoMarco.getPosition().x, cuadradoMarco.getPosition().y);
        }
        
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
		texturaFondo.dispose();
		
		textureBola.dispose();
		
		texturaMarcoHor.dispose();
		texturaMarcoVert.dispose();
		
		world.dispose();
		
	}

}
