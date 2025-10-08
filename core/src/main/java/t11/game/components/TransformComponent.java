package t11.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public class TransformComponent implements Component {
	public final Vector3 position = new Vector3();
	public double scale = 0.0;
	public double rotation = 0.0;
}
