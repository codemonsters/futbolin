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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import es.codemonsters.futbolin.Utiles;


import es.codemonsters.futbolin.MyGdxGame;

public class GameScreen implements Screen {
	//---- Camara y visualizacion
	private OrthographicCamera camera;
	private ScreenViewport viewport;
	
	//---- Mundo y elementos
	private MyGdxGame game;
	private World world;
	
	//---- Sprites y texturas
	private Texture texturaFondo;
	
	private Body bodyBola;
	private Texture texturaBola;
	private Sprite spriteBola;	
	
	private Texture texturaMarcoHor, texturaMarcoVert;
	private Body bodyMarcoSup;
	private Body bodyMarcoInf;
	private Array<Body> marcosArray = new Array<Body>();
	
	
	// Devuelve la posicion de la textura segun la posicion del cuerpo
	public Vector2 posicionCuerpoToSprite(Body cuerpo) {
		Vector2 posicionTextura = new Vector2();
		
		if(cuerpo.getFixtureList().get(0).getShape() instanceof CircleShape){
			posicionTextura.x = (cuerpo.getPosition().x - cuerpo.getFixtureList().get(0).getShape().getRadius())*game.METROS_TO_PIXEL;
			posicionTextura.y = (cuerpo.getPosition().y - cuerpo.getFixtureList().get(0).getShape().getRadius())*game.METROS_TO_PIXEL;
			
		}
		
		else if(cuerpo.getFixtureList().get(0).getShape() instanceof PolygonShape){
			PolygonShape poligono = (PolygonShape) cuerpo.getFixtureList().get(0).getShape();
			
			Vector2 tmp = new Vector2();
			for(int i=0 ; i<poligono.getVertexCount() ; i++){
				poligono.getVertex(i, tmp);
				
				if(posicionTextura.x > tmp.x)
					posicionTextura.x = tmp.x + cuerpo.getPosition().x;
				if(posicionTextura.y > tmp.y)
					posicionTextura.y = tmp.y + cuerpo.getPosition().y;
			}
		}
		
		else{
			// Añadir excepcion
		}
			
		return posicionTextura;
	}
	
	public Vector2 posicionSpriteToCuerpo(Sprite sprite) {
		Vector2 posicionCuerpo = new Vector2();
		posicionCuerpo.x = sprite.getX() + sprite.getWidth()/2;
		posicionCuerpo.y = sprite.getY() + sprite.getHeight()/2;
		return posicionCuerpo;
		
	}
	
	public GameScreen(final MyGdxGame game) {
        this.game = game; 
        
        //---- Camara y visualizacion
        camera = new OrthographicCamera(); // Camara ortogonal
        //viewport = new FillViewport(1024, 576, camera);  // Vision rectangular de un area de la pantalla, en este caso ocupa toda ella
        viewport = new ScreenViewport(camera);
        viewport.apply();  // Aplica el viewport a la camara
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);  // Colocamos la camara en el medio del viewport (centro de la pantalla)
        
        //---- Mundo y elementos
        world = new World(new Vector2(0, -98f), true);  // --> Vector2(fuerza del viento, fuerza gravedad)
        
        //- Fondo
        texturaFondo = new Texture(Gdx.files.internal("fondo_160x90.jpg"));
        
        //- Bola
        texturaBola = new Texture(Gdx.files.internal("ball_50x50.png"));  // radio 25
        spriteBola = new Sprite(texturaBola);
        bodyBola = Utiles.crearCuerpoCircular(world, BodyDef.BodyType.DynamicBody, game.WORLD_WIDTH/2, game.WORLD_HEIGHT/2, 0.50f, 0.5f, 1f);
        
        //- Tablero
        texturaMarcoHor = Utiles.crearTextura((int)(1024*game.METROS_TO_PIXEL), (int)(50*game.METROS_TO_PIXEL), Color.GREEN);
        
        bodyMarcoInf = Utiles.crearCuerpoRectangular(world, BodyDef.BodyType.StaticBody, game.WORLD_WIDTH/2, texturaMarcoHor.getHeight()/2, game.WORLD_WIDTH, texturaMarcoHor.getHeight(), 1000f, 100f);  
        marcosArray.add(bodyMarcoInf);
        bodyMarcoSup = Utiles.crearCuerpoRectangular(world, BodyDef.BodyType.StaticBody, game.WORLD_WIDTH/2, game.WORLD_WIDTH-texturaMarcoHor.getHeight()/2, game.WORLD_WIDTH, texturaMarcoHor.getHeight(), 1000f, 100f);  
        marcosArray.add(bodyMarcoSup);
        

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
        //spriteBola.setPosition(bodyBola.getPosition().x*game.METROS_TO_PIXEL - spriteBola.getWidth()/2, bodyBola.getPosition().y*game.METROS_TO_PIXEL - spriteBola.getHeight()/2);
		spriteBola.setPosition(posicionCuerpoToSprite(bodyBola).x, posicionCuerpoToSprite(bodyBola).y);
		System.out.println(posicionCuerpoToSprite(bodyBola).x + " ; " +  posicionCuerpoToSprite(bodyBola).y);
        
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
        	game.batch.draw(texturaMarcoHor, posicionCuerpoToSprite(cuadradoMarco).x, posicionCuerpoToSprite(cuadradoMarco).y);
        }
        game.batch.draw(texturaBola, 0, 0);
        game.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width,height);
	    camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		
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
		
		texturaBola.dispose();
		
		texturaMarcoHor.dispose();
		texturaMarcoVert.dispose();
		
		world.dispose();
		
	}

}
