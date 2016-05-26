package es.codemonsters.futbolin.mainmenu;

import com.badlogic.gdx.Screen;

import es.codemonsters.futbolin.MyGdxGame;
import es.codemonsters.futbolin.gamesetup.GameSetupScreen;

public class MainMenuScreen implements Screen {
	
	private MyGdxGame game;
	
	public MainMenuScreen(final MyGdxGame game) {
        this.game = game;
        this.game.setScreen(new GameSetupScreen(this.game));
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
