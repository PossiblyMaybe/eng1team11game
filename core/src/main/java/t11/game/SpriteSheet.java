package t11.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A class to handle sprite sheets for objects and allows them to change between frames
 */
public class SpriteSheet {
    private Texture texture;
    private TextureRegion[][] frames;
    private int currentFrame = 0;
    private int height;
    private int width;
    private int endFrame; //the last frame, used to make sure there are no out of bound index problems
    private final int SIZE = 16;

    /**
     *
     * @param spriteIn the path of the sprite sheet to be used
     * @param endFrame the last frame in the sprite sheet to prevent out of index errors
     */
    public SpriteSheet(String spriteIn, int endFrame){
        texture = new Texture(spriteIn);
        TextureRegion spriteSheet = new TextureRegion(texture);
        height = spriteSheet.getRegionHeight() / SIZE;
        width = spriteSheet.getRegionWidth() / SIZE;
        this.endFrame = endFrame;

        frames = spriteSheet.split(SIZE, SIZE);
    }

    /**
     * Gets a specific texture from the spritesheet
     * @param frame the index of the frame that should be returned
     * @return returns the frame requested
     */
    public TextureRegion getTexture(int frame){
        /*Returns the texture from the spritesheet from a specific frame */
        int hframe = frame % width;
        int vframe = frame / width;

        if (vframe >= height){ return frames[0][0];}


        return frames[vframe][hframe];
    }

    /**Gets the current frame of the spritesheet */
    public TextureRegion getTexture()
    {
        return this.getTexture(currentFrame);
    }

    /**Goes to the next frame and returns it */
    public TextureRegion nextFrame(){
        currentFrame = (currentFrame + 1 <= endFrame) ? currentFrame + 1: 0;
        return this.getTexture(currentFrame);
    }

    /**Sets the current frame to a given index and returns it
     * @param frame the frame index that you want to go to
     * @return returns the frame it was set to
     * */
    public TextureRegion setFrame(int frame){

        currentFrame = (frame <= endFrame && frame >= 0) ? frame: 0;
        return this.getTexture(currentFrame);
    }

    /**
     *
     * @return returns the current frame index
     */
    public int getCurrentFrame(){return currentFrame;}

    /**
     * Disposes of the sprite sheet
     */
    public void dispose(){
        texture.dispose();
    }



}
