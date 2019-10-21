import jig.Collision;
import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Map;

/**
 * The primary state of the game where game play takes place.
 *
 * Each time the state is entered, a Tile array is loaded based on the current level of the game, and other variables
 * are initialized.
 * During game play, the state makes collision checks and checks to see if the vehicles are centered on a tile.
 * If they are centered, input is taken from the player and/or path finding and movement is performed for the computer.
 *
 * The player's score starts at a max of 100, and can be decreased depending on how well the player performs.
 * The score decreases as follows:
 * 		-.001% for each frame. This causes the score to gradually decrease as time goes on.
 * 		-10% for colliding with the neutral vehicle.
 * 		-50% for losing the game, either by getting caught or failing to catch the robber.
 *
 * @author Jake Palmer
 */
public class PlayState extends BasicGameState {

	private int[][] worldMap1 = {
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

	private int[][] worldMap2ALT = {
		{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
		{ 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
		{ 0,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0 },
		{ 0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0 },
		{ 0,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0 },
		{ 0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0 },
		{ 0,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0 },
		{ 0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0 },
		{ 0,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0 },
		{ 0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0 },
		{ 0,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0 },
		{ 0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0 },
		{ 0,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,0,0,0 },
		{ 0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,2,0,0 },
		{ 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
		{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 }
	};

	private int[][] worldMap2 = {
		{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
		{ 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
		{ 0,1,1,0,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
		{ 0,1,1,0,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
		{ 0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,0 },
		{ 0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,1,0 },
		{ 0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0 },
		{ 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
		{ 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0 },
		{ 0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,0 },
		{ 0,1,1,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0 },
		{ 0,1,1,0,0,2,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0 },
		{ 0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,0 },
		{ 0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0 },
		{ 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0 },
		{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 }
	};

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
		CopsAndRobbers cap = (CopsAndRobbers) game;

		cap.level = 1;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		CopsAndRobbers cap = (CopsAndRobbers) game;

		int[][] worldMap = worldMap1;
		if (cap.level == 2) {
			worldMap = worldMap2;
		}
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

		cap.score = 100;
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
			if (cap.level == 1) {
				cap.level = 2;
				cap.enterState(CopsAndRobbers.LAUNCHSTATE);
			}
			else {
				cap.enterState(CopsAndRobbers.WINSTATE);
			}
		}

		// Decrease score by 10% if player collides with neutral driver
		collisionTest = player.collides(neutral);
		if (collisionTest != null) {
			cap.score = cap.score * 0.9d;
		}

		// Safe house 'collision' check
		// If robber, player wins
		if (cap.robberGame && player.getTile().getType() == Tile.SAFE_HOUSE_TYPE) {
			winTimer -= delta;	// wait a moment before transitioning to allow player to see car enter safe house
			if (winTimer <= 0) {
				if (cap.level == 1) {
					cap.level = 2;
					cap.enterState(CopsAndRobbers.LAUNCHSTATE);
				}
				else {
					cap.enterState(CopsAndRobbers.WINSTATE);
				}
			}
		}
		// If cop, player loses
		if (!cap.robberGame && computer.getTile().getType() == Tile.SAFE_HOUSE_TYPE) {
			winTimer -= delta;	// wait a moment before transitioning to allow player to see car enter safe house
			if (winTimer <= 0) {
				cap.enterState(CopsAndRobbers.GAMEOVERSTATE);
			}
		}

		// get input for player
		if (player.isCentered()) {
			Input input = container.getInput();

			// check for level skip cheat code
			if (input.isKeyPressed(Input.KEY_L)) {
				cap.level = 2;
				cap.enterState(CopsAndRobbers.LAUNCHSTATE);
			}

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

		// decrease score slowly as time goes on
		cap.score = cap.score * .999d;
	}

	/**
	 * Render an overlay on the map that displays the results of path finding via Dijkstra's.
	 * @param pi
	 * @param g
	 */
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
