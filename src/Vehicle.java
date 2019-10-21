import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a simple vehicle that exists on a given tile based world.
 * Through a set of basic methods the vehicle can be navigated through the world, only making movements onto valid
 * road tiles.
 *
 * Extends the JIG Entity
 *
 * @author Jake Palmer
 */
public class Vehicle extends Entity {

	Tile[][] world;
	Vector velocity;
	int direction;

	static final int NORTH = 0;
	static final int EAST = 1;
	static final int SOUTH = 2;
	static final int WEST = 3;

	/**
	 * Creates a vehicle entity a given direction and orientation
	 *
	 * @param x the starting x-coordinate on the tile-grid
	 * @param y the starting y-coordiante on the tile-grid
	 * @param d the starting direction to be oriented in
	 * @param neutral if true, uses a default resource
	 * @param w the tile based world the vehicle exists in
	 */
	Vehicle(int x, int y, int d, boolean neutral, Tile[][] w) {
		if (neutral) {
			switch (d) {
				case NORTH:
					addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.VEHICLE_NORTH_RSC));
					break;
				case EAST:
					addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.VEHICLE_EAST_RSC));
					break;
				case SOUTH:
					addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.VEHICLE_SOUTH_RSC));
					break;
				case WEST:
					addImageWithBoundingBox(ResourceManager.getImage(CopsAndRobbers.VEHICLE_WEST_RSC));
			}
		}
		world = w;
		velocity = new Vector(0, 0);
		direction = d;
		setTilePosition(x, y);
	}

	/**
	 * Get the vehicle's x position in tile-grid based coordinates.
	 *
	 * @return x-coordinate on the tile-grid
	 */
	int getTileX() {
		return (int)((getX() - 25) / 50);
	}

	/**
	 * Get the vehicle's y position in tile-gird based coordinates.
	 *
	 * @return y-coordinate on the tile-grid
	 */
	int getTileY() {
		return (int)((getY() - 25) / 50);
	}

	/**
	 * Get the current tile the vehicle is on from the world
	 *
	 * @return The tile object the vehicle is on
	 */
	Tile getTile() {
		return world[getTileX()][getTileY()];
	}

	/**
	 * Converts the tile-grid based coordinates to screen (pixel) based coordinates and sets the entities position
	 *
	 * @param x x-coordinate on the tile-grid
	 * @param y y-coordinate on the tile-grid
	 */
	void setTilePosition(int x, int y) {
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
	void setVelocity(Vector v, boolean swapImage) {
		if (swapImage) {
			// remove old image
			switch (getCardinalDirection(velocity)) {
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
			switch (direction = getCardinalDirection(v)) {
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
		}
		velocity = v;	// update velocity
	}

	/**
	 * Give the vehicle a positive velocity in its current direction.
	 *
	 * @param speed The desired magnitude of the velocity
	 */
	void accelerate(float speed) {
		if (getTile().getNeighbor(direction).getType() != Tile.LAND_TYPE) {
			switch (direction) {
				case NORTH:
					setVelocity(Vector.getUnit(270).scale(speed), true);
					break;
				case EAST:
					setVelocity(Vector.getUnit(0).scale(speed), true);
					break;
				case SOUTH:
					setVelocity(Vector.getUnit(90).scale(speed), true);
					break;
				case WEST:
					setVelocity(Vector.getUnit(180).scale(speed), true);
			}
		}
		else {
			stop();
		}
	}

	/**
	 * Turn the vehicle to the right if the right neighbor is a road tile.
	 * Adjust the vehicle's velocity to be 90 degrees from it's current velocity.
	 */
	void turnRight() {
		if (getTile().getNeighbor((direction + 1) % 4).getType() != Tile.LAND_TYPE) {
			setVelocity(velocity.rotate(90d), true);
		}
		else {
			stop();
		}
	}

	/**
	 * Turn the vehicle to the left if the left neighbor is a road tile.
	 * Adjust the vehicle's velocity to be 270 , or -90, degrees from it's current velocity.
	 */
	void turnLeft() {
		if (direction == NORTH) {
			if (getTile().getNeighbor(WEST).getType() != Tile.LAND_TYPE) {
				setVelocity(velocity.rotate(270d), true);
			}
			else {
				stop();
			}
		}
		else if (getTile().getNeighbor(direction - 1).getType() != Tile.LAND_TYPE) {
			setVelocity(velocity.rotate(270d), true);
		}
		else {
			stop();
		}
	}

	/**
	 * Give the vehicle a velocity opposite its current direction.
	 *
	 * @param speed The desired magnitude of the velocity
	 */
	void reverse(float speed) {
		if (getTile().getNeighbor((direction + 2) % 4).getType() != Tile.LAND_TYPE) {
			switch (direction) {
				case NORTH:
					setVelocity(Vector.getUnit(270).scale(-speed), false);
					break;
				case EAST:
					setVelocity(Vector.getUnit(0).scale(-speed), false);
					break;
				case SOUTH:
					setVelocity(Vector.getUnit(90).scale(-speed), false);
					break;
				case WEST:
					setVelocity(Vector.getUnit(180).scale(-speed), false);
			}
		}
		else {
			stop();
		}
	}

	void stop() {
		setVelocity(new Vector(0f, 0f), false);
	}

	/**
	 * Check to see if the Vehicle is in the center of a tile.
	 * Allows wiggle room to account for instances where the car doesn't render in the exact center.
	 *
	 * @return true if the car is centered
	 */
	boolean isCentered() {
		float wiggle = 0f;
		return (getX() <= getTile().getX() + wiggle && getX() >= getTile().getX() - wiggle &&
			 getY() <= getTile().getY() + wiggle && getY() >= getTile().getY() - wiggle);
	}

	/**
	 * Check to see if the vehicle is in motion, i.e. has a non zero velocity.
	 *
	 * @return true if the car is in motion
	 */
	boolean inMotion() {
		return velocity.length() != 0f;
	}

	/**
	 * Reset the vehicle to the center of the tile it is in.
	 */
	void reset() {
		setTilePosition(getTileX(), getTileY());
	}

	/**
	 * Translate the vehicle according to its current velocity.
	 *
	 * @param delta the time in milliseconds since the last call to update()
	 */

	void drive(int delta) {
		// TEMPORARY STATE
		// With out considering delta, this function currently depends on the refresh rate of the game.
		// If the game runs faster, it will play faster.
		// If the frames aren't consistent, movement will be inconsistent.
		// Not good! But good enough for the moment...

		// translate(velocity.scale(delta));
		translate(velocity);
	}


	// DIJKSTRA'S

	Map<Tile, Integer> pathfind(Tile goal, float speed) {
		ArrayList<Tile> queue;
		Tile u;
		Map<Tile, Integer> d, pi;

		d = new HashMap<>();
		pi = new HashMap<>();
		queue = new ArrayList<>();
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 24; x++) {
				// initialize single source
				d.putIfAbsent(world[x][y], 1000);
				pi.putIfAbsent(world[x][y], -1);

				// initialize queue with all tiles in graph
				queue.add(world[x][y]);
			}
		}
		// initialize single source, destination goal has a path cost of 0
		d.put(goal, 0);

		while(!queue.isEmpty()) {
			u = extractMin(queue, d);
			queue.remove(u);
			// check the four neighbors
			for (int i = 0; i < 4; i++) {
				// relax
				Tile v = u.getNeighbor(i);
				if (v != null && v.getType() != Tile.LAND_TYPE) {
					if (d.get(v) > (d.get(u) + u.getEdgeWeight(i))) {
						d.put(v, d.get(u) + u.getEdgeWeight(i));
						pi.put(v, (i + 2) % 4);
					}
				}
			}
		}
		return pi;
	}

	/**
	 * Gets the tile with the lowest path cost from an existing ArrayList queue of Tiles.
	 *
	 * Used in Dijkstra's to pick the next vertex to visit.
	 *
	 * @param queue An ArrayList of Tiles to search over
	 * @param d A Map of Tiles to path costs
	 * @return The Tile object with the lowest weight attribute
	 */
	private Tile extractMin(ArrayList<Tile> queue, Map<Tile, Integer> d) {
		Tile min = queue.get(0);
		for (Tile t : queue) {
			if (d.get(t) < d.get(min)) {
				min = t;
			}
		}
		return min;
	}
}
