package t11.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class LogoScreen extends ScreenAdapter{
	private OrthographicCamera cam;

	private SpriteCache cache;
	private ArrayList<Integer> cacheIDs;
	private int currentCacheIndex;

	private double timeElapsed;

	public LogoScreen(SpriteCache cache, String[] texPaths){
		Texture tex;	
		this.cacheIDs = new ArrayList<Integer>();
		this.cache = cache;
		this.cache.clear();
		for (String p : texPaths){
			this.cache.beginCache();
			tex = new Texture(p);
			this.cache.add(tex,0,0);
			this.cacheIDs.add(this.cache.endCache());
		}
		
		this.cam = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		this.cam.position.set(Gdx.graphics.getWidth()/ 2.0f, Gdx.graphics.getHeight() /2.0f, 0);
	}

	@Override
	public void show(){
		super.show();
		this.currentCacheIndex = 0;
		this.timeElapsed = 0;
	}

	@Override
	public void render(float delta){
		super.render(delta);
		Gdx.gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.cam.update();
		
		this.cache.setProjectionMatrix(this.cam.combined);
		this.cache.begin();
		this.cache.draw(this.cacheIDs.get(this.currentCacheIndex));
		this.cache.end();
		
		
		this.timeElapsed += delta;
		this.logoManage();
	}

	private void logoManage(){
		if (this.timeElapsed > 5.0 && this.currentCacheIndex < this.cacheIDs.size()-1){
			this.currentCacheIndex++;
		}
		//TODO: add the fading of the logos maybe	
	}
}

