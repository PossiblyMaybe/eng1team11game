package t11.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EndScreen extends ScreenAdapter{
    SpriteBatch batch = new SpriteBatch();
    public static Boolean win = true;
    OrthographicCamera camera;
    Viewport viewport;

    public EndScreen(OrthographicCamera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        if (win) {
            ScreenUtils.clear(Color.GREEN);
        } else {
            ScreenUtils.clear(Color.RED);
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
