import jig.ResourceManager;
import jig.Vector;

/**
 * A class that defines a specific type of Vehicle entity - a Cop
 *
 * This class uses the cop's resources and implements its unique path finding algorithm.
 *
 * @author Jake Palmer
 */
public class Cop extends Vehicle {

	Cop(int x, int y, int d, Tile[][] w) {
		super(x, y, d, false, w);
		switch (d) {
			case NORTH:
				addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.COP_NORTH_RSC));
				break;
			case EAST:
				addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.COP_EAST_RSC));
				break;
			case SOUTH:
				addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.COP_SOUTH_RSC));
				break;
			case WEST:
				addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.COP_WEST_RSC));
		}
	}

	/**
	 * This override method uses the cop's resources
	 *
	 * Set the velocity of the Vehicle entity.
	 * Update the entity image to match its direction of travel.
	 *
	 * @param v a JIG Vector object
	 */
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
