import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Vehicle extends Entity {

	Vector velocity;
	int xLocation;
	int yLocation;

	Vehicle(int x, int y) {
		addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.VEHICLE_RSC));

		velocity = new Vector(0, 0);
		setLocation(x, y);
	}

	int getxLocation() {
		return xLocation;
	}

	int getyLocation() {
		return yLocation;
	}

	/**
	 * Converts the tile-grid based coordinates to screen (pixel) based coordinates and sets the entities position
	 *
	 * @param x x-coordinate on the tile-grid
	 * @param y y-coordinate on the tile-grid
	 */
	void setLocation(int x, int y) {
		xLocation = x;
		yLocation = y;
		setPosition((x - 1) * 50 + 25, (y - 1) * 50 + 25);
	}

	/**
	 * Set the velocity of the Vehicle entity
	 *
	 * @param v a JIG Vector object
	 */
	void setVelocity(Vector v) {
		velocity = v;
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
