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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import es.codemonsters.futbolin.MyGdxGame;

public class GameScreen implements Screen {
	
	private FillViewport viewport;
	private OrthographicCamera camera;
	private MyGdxGame game;
	private World world;
	private Body body;
	private Texture textureFondo;
	private Texture textureBola;
	private Sprite spriteBola;
	private Fixture fixture;

	
	public GameScreen(final MyGdxGame game) {
        this.game = game; 
        
        camera = new OrthographicCamera(); 
        
        viewport = new FillViewport(1024, 576, camera);
        //viewport.setUnitsPerPixel(6.4f);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        
        world = new World(new Vector2(0, -0f), true);
        
        textureFondo = new Texture(Gdx.files.internal("fondo_160x90.jpg"));
        
        textureBola = new Texture(Gdx.files.internal("ball_50x50.png"));
        spriteBola = new Sprite(textureBola);
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;   
        //bodyDef.position.set((game.WORLD_WIDTH-textureBola.getWidth())/2, (game.WORLD_HEIGHT-textureBola.getHeight())/2);
        bodyDef.position.set(0, 0);
        body = world.createBody(bodyDef);
        
        CircleShape shape = new CircleShape();
        shape.setRadius(textureBola.getWidth()/2);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        fixture = body.createFixture(fixtureDef);
        
        shape.dispose();
        
        Gdx.gl.glClearColor(1, 1, 1, 1);
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		camera.update();
        spriteBola.setPosition(body.getPosition().x*game.PIXEL_TO_METROS - spriteBola.getWidth()/2, body.getPosition().y*game.PIXEL_TO_METROS - spriteBola.getHeight()/2);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.setProjectionMatrix(camera.combined);
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
		world.dispose();
		
	}

}
