package t11.game.components;

import com.badlogic.ashley.core.Component;

public class EntityTypeComponent implements Component {
	public static final int PLAYER = 0;
	public static final int SCENERY = 1;
	public static final int OTHER = -1;

	public int type = OTHER;
}
