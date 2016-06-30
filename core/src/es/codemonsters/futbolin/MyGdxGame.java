package es.codemonsters.futbolin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import es.codemonsters.futbolin.game.GameScreen;
import es.codemonsters.futbolin.mainmenu.MainMenuScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	
	public static final String TITLE = "Futbolin";
	
	public final float WORLD_WIDTH = 40f;
	public final float WORLD_HEIGHT = 22.5f;
	public final float METROS_TO_PIXEL = 25.6f;// Relacion entre el numero de pixeles que representa cada metro (world units --> screen units)
	
	public void create() {
        batch = new SpriteBatch();
        this.setScreen(new GameScreen(this));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
    }
    
}
