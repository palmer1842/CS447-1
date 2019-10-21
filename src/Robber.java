import jig.ResourceManager;
import jig.Vector;

/**
 * A class that defines a specific type of Vehicle entity - a Robber
 *
 * This class uses the robber's resources and implements its unique path finding algorithm.
 *
 * @author Jake Palmer
 */
public class Robber extends Vehicle {

	Robber(int x, int y, int d, Tile[][] w) {
		super(x, y, d, false, w);
		switch (d) {
			case NORTH:
				addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.ROBBER_NORTH_RSC));
				break;
			case EAST:
				addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.ROBBER_EAST_RSC));
				break;
			case SOUTH:
				addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.ROBBER_SOUTH_RSC));
				break;
			case WEST:
				addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.ROBBER_WEST_RSC));
		}
	}

	/**
	 * This override method uses the Robber's resources
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
					removeImage(ResourceManager.getImage(CopsAndRobbers.ROBBER_NORTH_RSC));
					break;
				case EAST:
					removeImage(ResourceManager.getImage(CopsAndRobbers.ROBBER_EAST_RSC));
					break;
				case SOUTH:
					removeImage(ResourceManager.getImage(CopsAndRobbers.ROBBER_SOUTH_RSC));
					break;
				case WEST:
					removeImage(ResourceManager.getImage(CopsAndRobbers.ROBBER_WEST_RSC));
			}
			// add new image to reflect new velocity
			switch (direction = getCardinalDirection(v)) {
				case NORTH:
					addImage(ResourceManager.getImage(CopsAndRobbers.ROBBER_NORTH_RSC));
					break;
				case EAST:
					addImage(ResourceManager.getImage(CopsAndRobbers.ROBBER_EAST_RSC));
					break;
				case SOUTH:
					addImage(ResourceManager.getImage(CopsAndRobbers.ROBBER_SOUTH_RSC));
					break;
				case WEST:
					addImage(ResourceManager.getImage(CopsAndRobbers.ROBBER_WEST_RSC));
			}
		}
		velocity = v;	// update velocity
	}
}
