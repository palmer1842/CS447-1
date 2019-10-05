import jig.Entity;
import jig.ResourceManager;

public class Vehicle extends Entity {

	int xLocation;
	int yLocation;

	Vehicle(int x, int y) {
		addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.VEHICLE_RSC));

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


	void drive(int delta) {
		// translate(velocity.scale(delta)); // fluid, smooth motion. Why would you ever want that?

		// abrupt skips along the grid, that's a real mans video game
	}

}
