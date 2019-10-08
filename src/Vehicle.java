import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Vehicle extends Entity {

	Vector velocity;
	int direction;

	static final int NORTH = 0;
	static final int EAST = 1;
	static final int SOUTH = 2;
	static final int WEST = 3;

	Vehicle(int x, int y) {
		addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.VEHICLE_EAST_RSC));

		velocity = new Vector(0, 0);
		direction = EAST;
		setLocation(x, y);
	}

	int getxLocation() {
		return (int)((getX() - 25) / 50);
	}

	int getyLocation() {
		return (int)((getY() - 25) / 50);
	}

	/**
	 * Converts the tile-grid based coordinates to screen (pixel) based coordinates and sets the entities position
	 *
	 * @param x x-coordinate on the tile-grid
	 * @param y y-coordinate on the tile-grid
	 */
	void setLocation(int x, int y) {
		setPosition((x) * 50 + 25, (y) * 50 + 25);
	}

	/**
	 * Gets the cardinal, or compass, direction from a vector.
	 * It assumes that the vector is pointed in a cardinal direction.
	 *
	 * @param v the vector to check
	 * @return The direction the vector is pointed encoded as an int.
	 */
	int getCardinalDirection(Vector v) {
		if (v.getX() == 0) {
			if (v.getY() > 0) {
				return SOUTH;
			}
			else if (v.getY() < 0) {
				return NORTH;
			}
			else {
				return direction;
			}
		}
		if (v.getX() > 0) {
			return EAST;
		}
		else {
			return WEST;
		}
	}

	/**
	 * Set the velocity of the Vehicle entity.
	 * Update the entity image to match its direction of travel.
	 *
	 * @param v a JIG Vector object
	 */
	void setVelocity(Vector v) {
		// remove old image
		switch(getCardinalDirection(velocity)) {
			case NORTH:
				removeImage(ResourceManager.getImage(CopsAndRobbers.VEHICLE_NORTH_RSC));
				break;
			case EAST:
				removeImage(ResourceManager.getImage(CopsAndRobbers.VEHICLE_EAST_RSC));
				break;
			case SOUTH:
				removeImage(ResourceManager.getImage(CopsAndRobbers.VEHICLE_SOUTH_RSC));
				break;
			case WEST:
				removeImage(ResourceManager.getImage(CopsAndRobbers.VEHICLE_WEST_RSC));
		}
		// add new image to reflect new velocity
		switch(direction = getCardinalDirection(v)) {
			case NORTH:
				addImage(ResourceManager.getImage(CopsAndRobbers.VEHICLE_NORTH_RSC));
				break;
			case EAST:
				addImage(ResourceManager.getImage(CopsAndRobbers.VEHICLE_EAST_RSC));
				break;
			case SOUTH:
				addImage(ResourceManager.getImage(CopsAndRobbers.VEHICLE_SOUTH_RSC));
				break;
			case WEST:
				addImage(ResourceManager.getImage(CopsAndRobbers.VEHICLE_WEST_RSC));
		}
		velocity = v;	// update velocity
	}

	/**
	 * Check to see if the Vehicle is in the center of a tile.
	 * Allows 2 pixels of wiggle room to account for instances where the car doesn't render in the exact center.
	 *
	 * @return true if the car is centered
	 */
	boolean isCentered() {
		return ((getX() % 25) <= 2 && (getX() % 25) >= -2 &&
				(getY() % 25) <= 2 && (getY() % 25) >= -2);
	}

	/**
	 * Translate the vehicle according to its current velocity.
	 *
	 * @param delta the time in milliseconds since the last call to update()
	 */
	void drive(int delta) {
		translate(velocity.scale(delta));
	}

}
