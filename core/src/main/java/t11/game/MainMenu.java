package t11.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;


import com.badlogic.gdx.graphics.Texture;



public class MainMenu extends ScreenAdapter{
	private Stage uiStage;
	private TextureRegionDrawable drawableTex;
	private ImageButton buttonTest;



	public MainMenu(){
		this.drawableTex = new TextureRegionDrawable(new TextureRegion(new Texture("libgdx.png")));
		this.buttonTest = new ImageButton(this.drawableTex);

		this.uiStage = new Stage();
		this.uiStage.addActor(this.buttonTest);
		Gdx.input.setInputProcessor(uiStage);
	}

	@Override
	public void render(float delta){
		super.render(delta);
		Gdx.gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		uiStage.act(Gdx.graphics.getDeltaTime());
		uiStage.draw();
	
	}
}
