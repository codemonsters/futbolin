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
	private Texture textureBola;
	private Sprite spriteBola;
	private Fixture fixture;
	
	private static final float WORLD_WIDTH = 160;
	private static final float WORLD_HEIGHT = 90;
	
	public GameScreen(final MyGdxGame game) {
        this.game = game; 
        
        camera = new OrthographicCamera(); 
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        
        viewport = new FillViewport(1024, 576, camera);
        //viewport.setUnitsPerPixel(6.4f);
        viewport.apply();
        
        world = new World(new Vector2(0, -0.5f), true);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;   
        
        textureBola = new Texture(Gdx.files.internal("ball_50x50.png"));
        spriteBola = new Sprite(textureBola);
        
        bodyDef.position.set((WORLD_WIDTH-textureBola.getWidth())/2, (WORLD_HEIGHT-textureBola.getHeight())/2);
        
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
        spriteBola.setPosition(body.getPosition().x, body.getPosition().y);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
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
