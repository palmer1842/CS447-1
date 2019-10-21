import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The start up state of the game. It displays the title of the game and allows the user to select a game mode.
 *
 * A mock chase seen is rendered, and a moveable rectangle is drawn around one vehicle. The player can use the 'A'
 * and 'D' keys to select a game mode, and then press space to begin the game.
 *
 * @author Jake Palmer
 */
public class TitleState extends BasicGameState {

	private int selectBoxX;
	private boolean restart;
	private int timer;

	@Override
	public int getID() {
		return CopsAndRobbers.TITLESTATE;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		selectBoxX = 395;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		restart = true;
		timer = 500;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(new Color(Color.darkGray));
		g.drawString("Cops and Robbers", 520, 300);
		g.drawString("Press Space to start", 500, 320);

		g.setLineWidth(5f);
		g.setColor(new Color(Color.orange));
		g.drawRect(selectBoxX, 495, 60, 60);
		g.setColor(new Color(Color.white));

		g.drawImage(ResourceManager.getImage(CopsAndRobbers.COP_EAST_RSC), 300, 450);
		g.drawImage(ResourceManager.getImage(CopsAndRobbers.COP_EAST_RSC), 400, 500);
		g.drawImage(ResourceManager.getImage(CopsAndRobbers.COP_EAST_RSC), 300, 550);
		g.drawImage(ResourceManager.getImage(CopsAndRobbers.ROBBER_EAST_RSC), 750, 500);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;
		Input input = container.getInput();

		// delay for player to release space bar after the game restarts
		if (restart) {
			timer -= delta;
			if (timer <= 0) {
				restart = false;
			}
		}

		// switch between game mode selector
		if (input.isKeyDown(Input.KEY_A)) {
			selectBoxX = 395;
			cap.robberGame = false;
		}

		if (input.isKeyDown(Input.KEY_D)) {
			selectBoxX = 745;
			cap.robberGame = true;
		}

		// start game
		if (input.isKeyDown(Input.KEY_SPACE) && !restart) {
			cap.enterState(CopsAndRobbers.LAUNCHSTATE);
		}
	}
}
