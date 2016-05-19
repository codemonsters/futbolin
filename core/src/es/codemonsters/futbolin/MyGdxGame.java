package es.codemonsters.futbolin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import es.codemonsters.futbolin.mainmenu.MainMenuScreen;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	
	public void create() {
        batch = new SpriteBatch();
        
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
    }
    
}
