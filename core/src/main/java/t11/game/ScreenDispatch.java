package t11.game;


import com.badlogic.gdx.Screen;

import java.util.ArrayList;


/**
 * Class used to manage screens
 */
public class ScreenDispatch {
	private ArrayList<Screen> screens;
	private int currentIndex = 0;
    private int size = 0;

    /**
     *
     * @param screen takes a screen as input so the list is never empty
     */
	public ScreenDispatch(Screen screen){
        screens = new ArrayList<Screen>();
        addScreen(screen);
        screens.get(0).show();
	}

    /**
     * Adds a screen to the list of screens
     * @param screen input screen
     */
	public void addScreen(Screen screen){
		screens.add(screen);
        size += 1;
	}

    /**
     * Goes to the next screen
     * @return returns true if it was successful, and false if not
     */
	public boolean toNextScreen(){
		if (currentIndex + 1 >= screens.size()){
			return false;
		}
		return gotoScreen(currentIndex + 1);
	}

    /**
     * Goes to the last screen (which should be endScreen)
     */
    public void goToLast(){
        gotoScreen(screens.size() -1);
    }

    /**
     * Goes to a specific screen
     * @param index the index of screen it wants to go to from the list of screens
     * @return returns true if successful and false if not
     */
    public boolean gotoScreen(int index){
        if (index < 0 || index >= size)
            return false;
        currentIndex = index;
        screens.get(index).show();
        return true;
    }

    /**Returns the current screen */
    public Screen getScreen()
    {

        return screens.get(currentIndex);
    }
    /**Checks if the current screen is the last one, it is used to see if the player has made it to the end screen */
    public boolean isLast(){
        return currentIndex == screens.size() - 1;
    }

    /**
     * Disposes of all the screens when the game ends
     */
    public void dispose(){
        for (Screen screen: screens){
            screen.dispose();
        }
    }

}
