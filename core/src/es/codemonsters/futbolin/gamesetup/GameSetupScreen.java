package es.codemonsters.futbolin.gamesetup;

import com.badlogic.gdx.Screen;

import es.codemonsters.futbolin.MyGdxGame;
import es.codemonsters.futbolin.game.GameScreen;


public class GameSetupScreen implements Screen {
	
	private MyGdxGame game;
	
	public GameSetupScreen(final MyGdxGame game) {
        this.game = game;
        this.game.setScreen(new GameScreen(this.game));
        this.dispose();
    }
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

}
