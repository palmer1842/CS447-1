import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState {

	int[][] world_map = {{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
						 { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
					     { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
					     { 0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
					     { 0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,0 },
					     { 0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,0 },
					     { 0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
					     { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
						 { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
						 { 0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
					  	 { 0,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0 },
						 { 0,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0 },
						 { 0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
						 { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
						 { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
						 { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 }};

	Vehicle car;

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;

		int x = 25;
		int y = 25;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 24; j++) {
				cap.tile[j][i] = new Tile(world_map[i][j], x, y);
				x += 50;
			}
			x = 25;
			y += 50;
		}

		// assign neighbors to each tile in the grid
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 24; j++) {
				if (i > 0) { cap.tile[j][i].setNorthNeighbor(cap.tile[j][i - 1]); }
				if (j < 23) { cap.tile[j][i].setEastNeighbor(cap.tile[j + 1][i]); }
				if (i < 15) { cap.tile[j][i].setSouthNeighbor(cap.tile[j][i + 1]); }
				if (j > 0) { cap.tile[j][i].setWestNeighbor(cap.tile[j - 1][i]); }
			}
		}

		car = new Vehicle(1, 1);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 24; j++) {
				cap.tile[j][i].render(g);
			}
		}

		car.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;
		float speed = 5f;
		// OUTILNE
		// if at center
		// 	getInput
		// 	plan
		// else
		// 	drive(plan)

		if (car.isCentered(cap.tile[car.getxLocation()][car.getyLocation()])) {
			Input input = container.getInput();

			if (input.isKeyDown(Input.KEY_W) && car.inMotion()) {
				if (input.isKeyDown(Input.KEY_A)) {
					car.turnLeft();
				}
				else if (input.isKeyDown(Input.KEY_D)) {
					car.turnRight();
				}
			}
			else if (input.isKeyDown(Input.KEY_W)) {
				car.accelerate(speed);
			}
			else if (input.isKeyDown(Input.KEY_S) && !car.inMotion() &&
				cap.tile[car.getxLocation()][car.getyLocation() + 1].getType() == Tile.ROAD_TYPE) {
				car.reverse(speed);
			}
			else {
				car.stop();
				car.reset();
			}


//			if (input.isKeyDown(Input.KEY_W) &&
//				cap.tile[car.getxLocation()][car.getyLocation() - 1].getType() == Tile.ROAD_TYPE) {
//				car.setVelocity(new Vector(0f, -speed));
//				car.reset();
//			}
//			else if (input.isKeyDown(Input.KEY_S) &&
//				cap.tile[car.getxLocation()][car.getyLocation() + 1].getType() == Tile.ROAD_TYPE) {
//				car.setVelocity(new Vector(0f, speed));
//				car.reset();
//			}
//			else if (input.isKeyDown(Input.KEY_A) &&
//				cap.tile[car.getxLocation() - 1][car.getyLocation()].getType() == Tile.ROAD_TYPE) {
//				car.setVelocity(new Vector(-speed, 0f));
//				car.reset();
//			}
//			else if (input.isKeyDown(Input.KEY_D) &&
//				cap.tile[car.getxLocation() + 1][car.getyLocation()].getType() == Tile.ROAD_TYPE) {
//				car.setVelocity(new Vector(speed, 0f));
//				car.reset();
//			}
//			else {
//				car.setVelocity(new Vector(0, 0));
//				car.reset();
//			}
		}
		car.drive(delta);
	}
}
