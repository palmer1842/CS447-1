import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The Win state of the game. It displays a message congratulating the user as well as their final score.
 * When the user presses the Space Bar, it transitions back to the title screen.
 *
 * @author Jake Palmer
 */
public class WinState extends BasicGameState {
	@Override
	public int getID() {
		return CopsAndRobbers.WINSTATE;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawString("You win!", 555, 300);
		g.drawString("Press Space to restart", 490, 320);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;
		Input input = container.getInput();

		if (input.isKeyDown(Input.KEY_SPACE)) {
			cap.enterState(CopsAndRobbers.TITLESTATE);
		}
	}
}
