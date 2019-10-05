import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Vehicle extends Entity {

	Vector velocity;

	Vehicle(float x, float y) {
		super(x, y);

		addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.VEHICLE_RSC));

		velocity = new Vector(0, 0);
	}

}
