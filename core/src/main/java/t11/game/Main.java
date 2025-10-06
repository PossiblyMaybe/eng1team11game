package t11.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

import t11.game.ScreenDispatch;

public class Main extends Game {
	private SpriteBatch batch;
	private Texture image;
	private Screen test;
	private Screen test3;

	private ScreenDispatch test2;
	private static long timeStarted;
	private static long timeElapsed;
	@Override
	public void create() {
        	batch = new SpriteBatch();
		test = new LibGDXScreen(batch);
		test3 = new Team11LogoScreen(batch);
    		test2 = new ScreenDispatch();
		test2.addScreen(test);
		System.out.println("test2");
		this.setScreen(test);
		this.timeStarted = System.currentTimeMillis();
	}

    	@Override
    	public void render() {
		super.render();
		this.timeElapsed = System.currentTimeMillis() - this.timeStarted;
		this.nextScreen();
	}

    	@Override
    	public void dispose() {
        	batch.dispose();
    	}

	private void nextScreen(){
		if (this.timeElapsed > 10000){
			return;
		} else if (this.timeElapsed > 4000){
			this.setScreen(test3);
		}
	}
}
