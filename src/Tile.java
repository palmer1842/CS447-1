import jig.Entity;
import jig.ResourceManager;

public class Tile extends Entity {

	static final int LAND_TYPE = 0;
	static final int ROAD_TYPE = 1;

	int type;

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
	}

	int getType() {
		return type;
	}
}
