import jig.Collision;
import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.Map;

public class PlayState extends BasicGameState {

	private int[][] worldMap = {
		{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
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
		{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 }
	};

//	private int[][] worldMap = {
//		{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
//		{ 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
//		{ 0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
//		{ 0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
//		{ 0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,0 },
//		{ 0,1,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,1,0 },
//		{ 0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,2,0,0,1,1,0 },
//		{ 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
//		{ 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0 },
//		{ 0,1,1,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
//		{ 0,1,1,0,0,0,0,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0 },
//		{ 0,1,1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0 },
//		{ 0,1,1,1,1,1,1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
//		{ 0,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0 },
//		{ 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0 },
//		{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 }
//	};

	private Vehicle player;
	private Vehicle computer;
	private Vehicle neutral;
	private Map<Tile, Integer> computerPI;
	private int winTimer;
	private boolean dijkstraToggle;
	private Tile safeHouse;

	@Override
	public int getID() {
		return CopsAndRobbers.PLAYSTATE;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;

		// build tile array based on 'worldMap'
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 24; x++) {
				cap.world[x][y] = new Tile(worldMap[y][x], x, y);
				if (cap.world[x][y].getType() == Tile.SAFE_HOUSE_TYPE) {
					safeHouse = cap.world[x][y];
				}
			}
		}

		// assign neighbors to each tile in the grid
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 24; x++) {
				if (y > 0) { cap.world[x][y].setNorthNeighbor(cap.world[x][y - 1]); }
				if (x < 23) { cap.world[x][y].setEastNeighbor(cap.world[x + 1][y]); }
				if (y < 15) { cap.world[x][y].setSouthNeighbor(cap.world[x][y + 1]); }
				if (x > 0) { cap.world[x][y].setWestNeighbor(cap.world[x - 1][y]); }
			}
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		CopsAndRobbers cap = (CopsAndRobbers) game;

		winTimer = 200;	// 200 millisecond delay after winning to allow animations to finish
		dijkstraToggle = false;

		// depending on which game mode the player selected, assign them to the robber or a cop
		if (cap.robberGame) {
			player = new Robber(1, 1, Vehicle.EAST, cap.world);
			computer = new Cop(21, 14, Vehicle.WEST, cap.world);
		}
		else {
			player = new Cop(21, 14, Vehicle.WEST, cap.world);
			computer = new Robber(2, 1, Vehicle.EAST, cap.world);
		}

		neutral = new Vehicle(1, 14, Vehicle.EAST, true, cap.world);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;

		// draw map
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 24; j++) {
				cap.world[j][i].render(g);
			}
		}

		if (dijkstraToggle) {
			renderPI(computerPI, g);
		}

		player.render(g);
		computer.render(g);

		neutral.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;
		float speed = 5f;

		// Vehicle Collision test
		Collision collisionTest = player.collides(computer);
		if (collisionTest != null && cap.robberGame) {
			cap.enterState(CopsAndRobbers.GAMEOVERSTATE);
		}
		else if (collisionTest != null) {
			cap.enterState(CopsAndRobbers.WINSTATE);
		}

		// Safe house 'collision' check
		if (cap.robberGame && player.getTile().getType() == Tile.SAFE_HOUSE_TYPE) {
			winTimer -= delta;	// wait a moment before transitioning to allow player to see car enter safe house
			if (winTimer <= 0) {
				cap.enterState(CopsAndRobbers.WINSTATE);
			}
		}
		if (!cap.robberGame && computer.getTile().getType() == Tile.SAFE_HOUSE_TYPE) {
			winTimer -= delta;	// wait a moment before transitioning to allow player to see car enter safe house
			if (winTimer <= 0) {
				cap.enterState(CopsAndRobbers.GAMEOVERSTATE);
			}
		}

		// get input for player
		if (player.isCentered()) {
			Input input = container.getInput();

			// toggle for path finding overlay
			if (input.isKeyPressed(Input.KEY_T)) {
				dijkstraToggle = !dijkstraToggle;
			}

			if (input.isKeyDown(Input.KEY_W) && player.inMotion()) {	// only allow turning while in motion
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
		}

		// computer AI plans movement
		if (computer.isCentered()) {
			if (cap.robberGame) {
				computerPI = computer.pathfind(player.getTile(), speed);
			}
			else {
				computerPI = computer.pathfind(safeHouse, speed);
			}
			int pathDir = computerPI.get(computer.getTile());
			//System.out.println("PathDir: " + pathDir + " ComputerDir: " + computer.direction);
			if (computer.inMotion()) {	// only allow turning while in motion
				if (computer.direction == Vehicle.NORTH && pathDir == 3) {
					computer.turnLeft();
				}
				else if (pathDir == computer.direction - 1) {
					computer.turnLeft();
				}
				else if (pathDir == (computer.direction + 1) % 4) {
					computer.turnRight();
				}
				else {
					computer.accelerate(speed);
				}
			}
			else {
				computer.accelerate(speed);
			}
		}

		if (neutral.isCentered()) {
			if (neutral.getTile().getNeighbor(neutral.direction).getType() == Tile.ROAD_TYPE) {
				neutral.accelerate(speed);
			}
			else {
				neutral.turnLeft();
			}
		}

		player.drive(delta);
		computer.drive(delta);
		neutral.drive(delta);
	}

	private void renderPI(Map<Tile, Integer> pi, Graphics g) {
		for (Map.Entry<Tile, Integer> entry : pi.entrySet()) {
			switch(entry.getValue()) {
				case Vehicle.NORTH:
					g.drawImage(ResourceManager.getImage(CopsAndRobbers.ARROW_NORTH_RSC), entry.getKey().getX() - 25, entry.getKey().getY() - 25);
					break;
				case Vehicle.EAST:
					g.drawImage(ResourceManager.getImage(CopsAndRobbers.ARROW_EAST_RSC), entry.getKey().getX() - 25, entry.getKey().getY() - 25);
					break;
				case Vehicle.SOUTH:
					g.drawImage(ResourceManager.getImage(CopsAndRobbers.ARROW_SOUTH_RSC), entry.getKey().getX() - 25, entry.getKey().getY() - 25);
					break;
				case Vehicle.WEST:
					g.drawImage(ResourceManager.getImage(CopsAndRobbers.ARROW_WEST_RSC), entry.getKey().getX() - 25, entry.getKey().getY() - 25);
			}
		}
	}
}
