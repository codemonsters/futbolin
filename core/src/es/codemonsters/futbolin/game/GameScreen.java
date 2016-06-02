package es.codemonsters.futbolin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;


import es.codemonsters.futbolin.MyGdxGame;

public class GameScreen implements Screen {
	//---- Camara y visualizacion
	private OrthographicCamera camera;
	private FillViewport viewport;
	
	//---- Mundo y elementos
	private MyGdxGame game;
	private World world;
	private Body body;
	private Fixture fixture;
	
	//---- Sprites y texturas
	private Texture textureFondo;
	private Texture textureBola;
	private Sprite spriteBola;
	
	public GameScreen(final MyGdxGame game) {
        this.game = game; 
        
        //---- Camara y visualizacion
        camera = new OrthographicCamera(); // Camara ortogonal
        viewport = new FillViewport(1024, 576, camera);  // Vision rectangular de un area de la pantalla, en este caso ocupa toda ella
        viewport.apply();  // Aplica el viewport a la camara
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);  // Colocamos la camara en el medio del viewport (centro de la pantalla)
        
        //---- Mundo y elementos
        world = new World(new Vector2(0, -98f), true);  // --> Vector2(fuerza del viento, fuerza gravedad)
        
        textureFondo = new Texture(Gdx.files.internal("fondo_160x90.jpg"));
        textureBola = new Texture(Gdx.files.internal("ball_50x50.png"));
        spriteBola = new Sprite(textureBola);
        
        BodyDef bodyBolaDef = new BodyDef();  // Guarda informacion del cuerpo (tipo, densidad, grados de rotacion...)
        bodyBolaDef.type = BodyDef.BodyType.DynamicBody;  // Tipo de cuerpo, el dinamico reacciona ante fuerzas, colisiones y otros eventos del mundo 
        bodyBolaDef.position.set(game.WORLD_WIDTH/2, game.WORLD_HEIGHT/2);  // En los cuerpos el origen de coordenadas es el centro, por lo que no hay que restar
        body = world.createBody(bodyBolaDef);  // Creacion del cuerpo en el mundo
        
        CircleShape shape = new CircleShape();  // Forma del cuerpo, en este caso un circulo
        shape.setRadius(textureBola.getWidth()/2); 
        
        FixtureDef fixtureDef = new FixtureDef();  // Forma fisica del cuerpo que se adjunta al cuerpo para darle propiedades como masa, densidad...
        fixtureDef.shape = shape;  
        fixtureDef.density = 1f;
        fixture = body.createFixture(fixtureDef);  // Creacion de la forma fisica en el cuerpo
        
        shape.dispose();
        
        //---- Color de relleno render
        Gdx.gl.glClearColor(1, 1, 1, 1);
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	public void update(){
		//---- Actualizar bola
        spriteBola.setPosition(body.getPosition().x*game.METROS_TO_PIXEL - spriteBola.getWidth()/2, body.getPosition().y*game.METROS_TO_PIXEL - spriteBola.getHeight()/2);
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

		world.dispose();
		
	}

}
