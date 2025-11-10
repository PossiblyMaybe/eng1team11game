package t11.game;


import com.badlogic.gdx.Screen;

import java.util.ArrayList;



public class ScreenDispatch {
	private ArrayList<Screen> screens;
	private int currentIndex = 0;
    private int size = 0;

	public ScreenDispatch(Screen screen){
        screens = new ArrayList<Screen>();
        addScreen(screen);
        screens.get(0).show();
	}

	public void addScreen(Screen screen){
        /*Adds a screen to the list of screens contained */
		screens.add(screen);
        size += 1;
	}

	public boolean toNextScreen(){
        /*Goes to the next screen and returns true if successful*/
		if (currentIndex + 1 >= screens.size()){
			return false;
		}
		return gotoScreen(currentIndex + 1);
	}


    public void goToLast(){
        /*Goes to the last screen */
        gotoScreen(screens.size() -1);
    }

    public boolean gotoScreen(int index){
        /*Goes to a specified screen, and returns true if it was successful */
        if (index < 0 || index >= size)
            return false;
        screens.get(currentIndex).dispose();
        currentIndex = index;
        screens.get(index).show();
        return true;
    }

    public Screen getScreen()
    {
        /*Returns the current screen */
        return screens.get(currentIndex);
    }

    public boolean isLast(){
        /*Checks if the current screen is the last one, it is used to see if the player has made it to the end screen */
        return currentIndex == screens.size() - 1;
    }

}
