package t11.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteSheet {
    private TextureRegion[][] frames;
    private int currentFrame = 0;
    private int height;
    private int width;
    private int endFrame;
    private final int SIZE = 16;

    public SpriteSheet(String spriteIn, int endFrame){
        TextureRegion spriteSheet = new TextureRegion(new Texture(spriteIn));
        height = spriteSheet.getRegionHeight() / SIZE;
        width = spriteSheet.getRegionWidth() / SIZE;
        this.endFrame = endFrame;

        frames = spriteSheet.split(SIZE, SIZE);
    }

    public TextureRegion getTexture(int frame){
        int hframe = frame % width;
        int vframe = frame / width;

        if (vframe >= height){ return frames[0][0];}

        return frames[hframe][vframe];

    }

    public TextureRegion getTexture()
    {
        return this.getTexture(currentFrame);
    }

    public TextureRegion nextFrame(){
        currentFrame = (currentFrame + 1 <= endFrame) ? currentFrame + 1: 0;
        return this.getTexture(currentFrame);
    }

    public TextureRegion setFrame(int frame){
        currentFrame = (frame <= endFrame && frame >= 0) ? frame: 0;
        return this.getTexture(currentFrame);
    }

    public int getCurrentFrame(){return currentFrame;}



}
