package t11.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicsCamera;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter{
	private Orthographics Camera cam;
	private SpriteCache cache;
	private ArrayList<Integer> cacheIDs;
	private into currentCacheIndex;

	public GameScreen(SpriteCache cache){
		this.cacheIDs = new ArrayList<Integer>();
		this.cache = cache;
		this.cache.clear();

		//TEXTURE LOADING INNIT


		this.cam = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		this.cam.position.set(Gdx.graphics.getWidth()/2.0f,Gdx.graphics.getHeight()/2.0f,0);	
	}

	@Override
	public void show(){
		super.show();
		this.currentCacheIndex = 0;
		this.timeElapsed = 0;
	}

	@Override
	public void render(float delta){
		super.render(delta)
		Gdx.gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.cam.update();
	}
}
