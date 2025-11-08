package t11.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen extends ScreenAdapter implements Screen {
    SpriteBatch batch = new SpriteBatch();
    static Boolean menuDone = false;
    OrthographicCamera camera;
    Viewport viewport;

    public MenuScreen(OrthographicCamera camera, Viewport viewport, BitmapFont font) {
        this.camera = camera;
        this.viewport = viewport;
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        Main.font.draw(batch, "ESCAPE FROM UNI", 250, 350);
        Main.font.draw(batch, "Tap anywhere to begin!", 245, 300);

        batch.end();

        if (Gdx.input.isTouched()) {
            dispose();
            menuDone = true;
        }
    }

    @Override
    public void resize(int width, int height) { viewport.update(width, height, true);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}
}
