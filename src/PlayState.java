import jig.Collision;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

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

	private Vehicle player;
	private Vehicle computer;
	private int winTimer;

	@Override
	public int getID() {
		return CopsAndRobbers.PLAYSTATE;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;

		// build tile array based on 'worldMap'
		int x = 25;
		int y = 25;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 24; j++) {
				cap.world[j][i] = new Tile(worldMap[i][j], x, y);
				x += 50;
			}
			x = 25;
			y += 50;
		}

		// assign neighbors to each tile in the grid
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 24; j++) {
				if (i > 0) { cap.world[j][i].setNorthNeighbor(cap.world[j][i - 1]); }
				if (j < 23) { cap.world[j][i].setEastNeighbor(cap.world[j + 1][i]); }
				if (i < 15) { cap.world[j][i].setSouthNeighbor(cap.world[j][i + 1]); }
				if (j > 0) { cap.world[j][i].setWestNeighbor(cap.world[j - 1][i]); }
			}
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		CopsAndRobbers cap = (CopsAndRobbers) game;

		winTimer = 200;	// 200 millisecond delay after winning to allow animations to finish

		// depending on which game mode the player selected, assign them to the robber or a cop
		if (cap.robberGame) {
			player = new Robber(1, 1, Vehicle.EAST, cap.world);
			computer = new Cop(21, 14, Vehicle.WEST, cap.world);
		}
		else {
			player = new Cop(21, 14, Vehicle.WEST, cap.world);
			computer = new Robber(2, 1, Vehicle.EAST, cap.world);
		}
		computer.accelerate(5f);
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

		player.render(g);
		computer.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;
		float speed = 5f;

		// Vehicle Collision test
		Collision collisionTest = player.collides(computer);
		if (collisionTest != null) {
			cap.enterState(CopsAndRobbers.GAMEOVERSTATE);
		}

		// Safe house 'collision' check
		if (player.getTile().getType() == Tile.SAFE_HOUSE_TYPE) {
			winTimer -= delta;	// wait a moment before transitioning to allow player to see car enter safe house
			if (winTimer <= 0) {
				cap.enterState(CopsAndRobbers.WINSTATE);
			}
		}

		// get input for player
		if (player.isCentered()) {
			Input input = container.getInput();

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
			computer.turnRight();
		}

		player.drive(delta);
		computer.drive(delta);
	}
}
