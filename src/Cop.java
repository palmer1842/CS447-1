import jig.ResourceManager;
import jig.Vector;

public class Cop extends Vehicle {

	Cop(int x, int y, int d) {
		super(x, y, d, CopsAndRobbers.COP_WEST_RSC);
	}

	@Override
	void setVelocity(Vector v, boolean swapImage) {
		if (swapImage) {
			// remove old image
			switch (getCardinalDirection(velocity)) {
				case NORTH:
					removeImage(ResourceManager.getImage(CopsAndRobbers.COP_NORTH_RSC));
					break;
				case EAST:
					removeImage(ResourceManager.getImage(CopsAndRobbers.COP_EAST_RSC));
					break;
				case SOUTH:
					removeImage(ResourceManager.getImage(CopsAndRobbers.COP_SOUTH_RSC));
					break;
				case WEST:
					removeImage(ResourceManager.getImage(CopsAndRobbers.COP_WEST_RSC));
			}
			// add new image to reflect new velocity
			switch (direction = getCardinalDirection(v)) {
				case NORTH:
					addImage(ResourceManager.getImage(CopsAndRobbers.COP_NORTH_RSC));
					break;
				case EAST:
					addImage(ResourceManager.getImage(CopsAndRobbers.COP_EAST_RSC));
					break;
				case SOUTH:
					addImage(ResourceManager.getImage(CopsAndRobbers.COP_SOUTH_RSC));
					break;
				case WEST:
					addImage(ResourceManager.getImage(CopsAndRobbers.COP_WEST_RSC));
			}
		}
		velocity = v;	// update velocity
	}
}
