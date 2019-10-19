import jig.Entity;
import jig.ResourceManager;

/**
 * A class representing a single tile in the game world.
 *
 * Each tile is assigned a type, either Land, Road, or Safe House.
 *
 * @author Jake Palmer
 */
public class Tile extends Entity {

	static final int LAND_TYPE = 0;
	static final int ROAD_TYPE = 1;
	static final int SAFE_HOUSE_TYPE = 2;

	// successors used for collision checks and path finding
	private Tile northNeighbor;
	private Tile eastNeighbor;
	private Tile southNeighbor;
	private Tile westNeighbor;

	private String id;
	private int type;
	private int weight;

	Tile(int type, int x, int y) {
		super((x) * 50 + 25, (y) * 50 + 25);

		id = "" + x + "." + y;
		System.out.println(id);

		switch(type) {
			case LAND_TYPE:
				addImage(ResourceManager.getImage(CopsAndRobbers.LAND_RSC));
				this.type = LAND_TYPE;
				this.weight = 1000;
				break;
			case ROAD_TYPE:
				this.type = ROAD_TYPE;
				addImage(ResourceManager.getImage(CopsAndRobbers.ROAD_RSC));
				this.weight = 1;
				break;
			case SAFE_HOUSE_TYPE:
				this.type = SAFE_HOUSE_TYPE;
				this.weight = 1;
				addImage(ResourceManager.getImage(CopsAndRobbers.SAFE_HOUSE_RSC));
		}
		// initialize to null as default
		northNeighbor = null;
		eastNeighbor = null;
		southNeighbor = null;
		westNeighbor = null;
	}

	void setNorthNeighbor(Tile n) {
		northNeighbor = n;
	}

	void setEastNeighbor(Tile n) {
		eastNeighbor = n;
	}

	void setSouthNeighbor(Tile n) {
		southNeighbor = n;
	}

	void setWestNeighbor(Tile n) {
		westNeighbor = n;
	}

	Tile getNeighbor(int direction) {
		switch (direction) {
			case Vehicle.NORTH: return northNeighbor;
			case Vehicle.EAST: return eastNeighbor;
			case Vehicle.SOUTH: return southNeighbor;
			case Vehicle.WEST: return westNeighbor;
			default: return null;
		}
	}

	String getID() {
		return id;
	}

	int getType() {
		return type;
	}

	int getWeight() {
		return weight;
	}

	int getEdgeWeight(int direction) {
		switch (direction) {
			case Vehicle.NORTH: return (weight + northNeighbor.getWeight()) / 2;
			case Vehicle.EAST: return (weight + eastNeighbor.getWeight()) / 2;
			case Vehicle.SOUTH: return (weight + southNeighbor.getWeight()) / 2;
			case Vehicle.WEST: return (weight + westNeighbor.getWeight()) / 2;
			default: return -1;
		}
	}
}
