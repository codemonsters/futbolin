package es.codemonsters.futbolin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import es.codemonsters.futbolin.MyGdxGame;

public class GameScreen implements Screen {
	
	private MyGdxGame game;
	private World world;
	private Body body;
	private Texture texture;
	private Sprite sprite;
	private Fixture fixture;
	
	private static final float WORLD_WIDTH = 1920;
	private static final float WORLD_HEIGHT = 1080;
	
	public GameScreen(final MyGdxGame game) {
        this.game = game;
        
        world = new World(new Vector2(0, -98f), true);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;   
        
        texture = new Texture(Gdx.files.internal("ball_50x50.png"));
        sprite = new Sprite(texture);
        
        bodyDef.position.set((WORLD_WIDTH+texture.getWidth())/2, (WORLD_HEIGHT+texture.getHeight())/2);
        
        body = world.createBody(bodyDef);
        
        CircleShape shape = new CircleShape();
        shape.setRadius(texture.getWidth()/2);
        
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

        sprite.setPosition(body.getPosition().x, body.getPosition().y);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(sprite, sprite.getX(), sprite.getY());
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
		texture.dispose();
		world.dispose();
		
	}

}
