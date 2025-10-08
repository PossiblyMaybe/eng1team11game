package t11.game;


import com.badlogic.gdx.Screen;

import java.util.ArrayList;



public class ScreenDispatch {
	private ArrayList<Screen> screens;
	private int currentIndex;

	public ScreenDispatch(){
		this.screens = new ArrayList<Screen>();
	}

	public void addScreen(Screen screen){
		screens.add(screen);
	}

	public Screen toNextScreen(){
		if (currentIndex + 1 >= screens.size()){
			return this.screens.get(currentIndex); //TODO: make this throw an exception
		}
		currentIndex++;
		return this.screens.get(currentIndex);
	}
}
