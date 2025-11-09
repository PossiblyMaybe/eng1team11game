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
		screens.add(screen);
        size += 1;
	}

	public Screen toNextScreen(){
		if (currentIndex + 1 >= screens.size()){
			return screens.get(currentIndex);
		}
        screens.get(currentIndex).dispose();
		currentIndex++;
		return screens.get(currentIndex);
	}

    public void goToLast(){
        gotoScreen(screens.size() -1);
    }

    public boolean gotoScreen(int index){
        if (index < 0 || index >= size)
            return false;
        screens.get(currentIndex).dispose();
        currentIndex = index;
        screens.get(index).show();
        return true;
    }

    public Screen getScreen()
    {
        return screens.get(currentIndex);
    }

    public boolean isLast(){
        return currentIndex == screens.size() - 1;
    }

}
