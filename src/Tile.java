import jig.Entity;
import jig.ResourceManager;

public class Tile extends Entity {

	static final int LAND_TYPE = 0;
	static final int ROAD_TYPE = 1;

	// successors used for collision checks and path finding
	private Tile northNeighbor;
	private Tile eastNeighbor;
	private Tile southNeighbor;
	private Tile westNeighbor;

	private int type;

	Tile(int type, float x, float y) {
		super(x, y);
		switch(type) {
			case LAND_TYPE:
				addImage(ResourceManager.getImage(CopsAndRobbers.LAND_RSC));
				this.type = LAND_TYPE;
				break;
			case ROAD_TYPE:
				this.type = ROAD_TYPE;
				addImage(ResourceManager.getImage(CopsAndRobbers.ROAD_RSC));
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

	int getType() {
		return type;
	}
}
