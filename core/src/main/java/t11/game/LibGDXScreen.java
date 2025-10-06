package t11.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;


public class LibGDXScreen extends ScreenAdapter{

	private OrthographicCamera cam;
	private SpriteBatch batch;
	private Texture tex;

	public LibGDXScreen(SpriteBatch batch){
		this.batch = batch;
		this.tex = new Texture("libgdx.png");
		this.cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.cam.position.set(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, 0);
	}

	@Override
	public void render(float delta){
		super.render(delta);	
		Gdx.gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.cam.update();
		this.batch.setProjectionMatrix(this.cam.combined);
		this.batch.enableBlending();
		this.batch.begin();

		this.batch.draw(this.tex,Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f);
//TODO: Get the logo to stay centered when the screen resizes, below is debug stuff, screen width, screen width/2 and texture width respectively
//		I think stuff like the FitViewport is right for the task, will have a look
//
//		System.out.format("%d, %d", Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
//		System.out.format(" : %d, %d", Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
//		System.out.format(" : %d, %d\n", this.tex.getWidth(),this.tex.getHeight());
		this.batch.end();
	}
}
