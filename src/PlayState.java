import jig.Collision;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState {

	int[][] world_map = {{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
						 { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
					     { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
					     { 0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
					     { 0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,0 },
					     { 0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,0 },
					     { 0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,2,0,0,1,1,0 },
					     { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
						 { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
						 { 0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
					  	 { 0,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0 },
						 { 0,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0 },
						 { 0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
						 { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
						 { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
						 { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 }};

	Vehicle player;
	Vehicle computer;
	Collision collisionTest;
	int winTimer;

	@Override
	public int getID() {
		return CopsAndRobbers.PLAYSTATE;
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
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		CopsAndRobbers cap = (CopsAndRobbers) game;

		winTimer = 200;

		if (cap.robbergame) {
			player = new Robber(1, 1, Vehicle.EAST, cap.tile);
			computer = new Cop(21, 14, Vehicle.WEST, cap.tile);
		}
		else {
			player = new Cop(21, 14, Vehicle.WEST, cap.tile);
			computer = new Robber(2, 1, Vehicle.EAST, cap.tile);
		}
		computer.accelerate(5f);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 24; j++) {
				cap.tile[j][i].render(g);
			}
		}

		player.render(g);
		computer.render(g);
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

		// Vehicle Collision test
		collisionTest = player.collides(computer);
		if (collisionTest != null) {
			System.out.println("Collision");
			cap.enterState(CopsAndRobbers.GAMEOVERSTATE);
		}

		// Safe house 'collision' check
		if (player.getTile().getType() == Tile.SAFE_HOUSE_TYPE) {
			winTimer -= delta;	// wait a moment before transitioning to allow player to see car enter safe house
			if (winTimer <= 0) {
				cap.enterState(CopsAndRobbers.WINSTATE);
			}
		}

		if (player.isCentered()) {
			Input input = container.getInput();

			if (input.isKeyDown(Input.KEY_W) && player.inMotion()) {
				if (input.isKeyDown(Input.KEY_A)) {
					player.turnLeft();
				}
				else if (input.isKeyDown(Input.KEY_D)) {
					player.turnRight();
				}
				else {
					player.accelerate(speed);
				}
			}
			else if (input.isKeyDown(Input.KEY_W)) {
				player.accelerate(speed);
			}
			else if (input.isKeyDown(Input.KEY_S)) {
				player.reverse(speed);
			}
			else {
				player.stop();
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
		if (computer.isCentered()) {
			computer.turnRight();
		}
		player.drive(delta);
		computer.drive(delta);
	}
}
