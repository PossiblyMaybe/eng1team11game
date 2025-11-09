package t11.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class endScreen extends ScreenAdapter {


    private SpriteBatch batch;
    private BitmapFont font;
    private int score;

    private Texture outcome;

    public endScreen(SpriteBatch batch, BitmapFont font){
        this.batch = batch;
        this.font = font;
    }

    @Override
    public void render(float delta){
        batch.begin();
        font.draw(batch, "Score = ".concat(Integer.toString(score)), 265, 200);
        batch.draw(outcome, 240, 200, 160, 160);
        batch.end();
    }

    @Override
    public void show(){
        score = (int) (Main.timeRemaining * 100 + Main.coinCount * 10);
        if (Main.timeRemaining < 0) {
            outcome = new Texture("LOSE.png");
        } else {
            outcome = new Texture("WIN.png");
        }
    }

    @Override
    public void dispose(){
        outcome.dispose();
    }

    public void restart(){

    }
}
