package t11.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteSheet {
    private Texture texture;
    private TextureRegion[][] frames;
    private int currentFrame = 0;
    private int height;
    private int width;
    private int endFrame; //the last frame, used to make sure there are no out of bound index problems
    private final int SIZE = 16;

    public SpriteSheet(String spriteIn, int endFrame){
        texture = new Texture(spriteIn);
        TextureRegion spriteSheet = new TextureRegion(texture);
        height = spriteSheet.getRegionHeight() / SIZE;
        width = spriteSheet.getRegionWidth() / SIZE;
        this.endFrame = endFrame;

        frames = spriteSheet.split(SIZE, SIZE);
    }

    public TextureRegion getTexture(int frame){
        /*Returns the texture from the spritesheet from a specific frame */
        int hframe = frame % width;
        int vframe = frame / width;

        if (vframe >= height){ return frames[0][0];}


        return frames[vframe][hframe];
    }

    public TextureRegion getTexture()
    {
        /*Gets the current frame of the spritesheet */
        return this.getTexture(currentFrame);
    }

    public TextureRegion nextFrame(){
        /*Goes to the next frame and returns it */
        currentFrame = (currentFrame + 1 <= endFrame) ? currentFrame + 1: 0;
        return this.getTexture(currentFrame);
    }

    public TextureRegion setFrame(int frame){
        /*Sets the current frame to a given index and returns it */
        currentFrame = (frame <= endFrame && frame >= 0) ? frame: 0;
        return this.getTexture(currentFrame);
    }

    public int getCurrentFrame(){return currentFrame;}

    public void dispose(){
        texture.dispose();
    }



}
