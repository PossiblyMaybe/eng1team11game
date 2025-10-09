package t11.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteCache;

public class Main extends Game {
	private SpriteCache cache;
	private double timeElapsed;
	
	private Screen openingLogos;
	private Screen mainMenu;
	private ScreenDispatch test;
	
	@Override
	public void create() {
        	cache = new SpriteCache();
		
		String[] pngs = {"libgdx.png","teamLogo.png"};
		openingLogos = new LogoScreen(cache, pngs);
		mainMenu = new MainMenu();

		test = new ScreenDispatch();
		test.addScreen(openingLogos);
		test.addScreen(mainMenu);

		this.setScreen(openingLogos);
		
	}

    	@Override
    	public void render() {
		super.render();
		this.timeElapsed += Gdx.graphics.getDeltaTime();
		if (this.timeElapsed > 10){
			this.setScreen(this.test.toNextScreen());
		}
	}

    	@Override
    	public void dispose() {
        	cache.dispose();
    		openingLogos.dispose();
		mainMenu.dispose();
	}

}
