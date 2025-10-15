package t11.game;


import com.badlogic.gdx.Screen;

import java.util.ArrayList;



public class ScreenDispatch {
	private ArrayList<IScreen> screens;
	private int currentIndex = 0;
    private int size = 0;

	public ScreenDispatch(IScreen screen){
        screens = new ArrayList<IScreen>();
        addScreen(screen);
        screens.get(0).show();
	}

	public void addScreen(IScreen screen){
		screens.add(screen);
        size += 1;
	}

	public IScreen toNextScreen(){
		if (currentIndex + 1 >= screens.size()){
			return screens.get(currentIndex); //TODO: make this throw an exception
		}
		currentIndex++;
		return screens.get(currentIndex);
	}

    public boolean gotoScreen(int index){
        if (index < 0 || index >= size)
            return false;
        screens.get(currentIndex).dispose();
        currentIndex = index;
        screens.get(index).show();
        return true;
    }

    public void drawScreen(){
        //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        //Make an interface so this doesn't shit itself
        screens.get(currentIndex).draw();
    }
}
