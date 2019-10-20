import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The Game Over state of the game. It displays a message telling the player why they lost, and automatically
 * transitions back to the title screen after 10 seconds.
 *
 * @author Jake Palmer
 */
public class GameOverState extends BasicGameState {

	private int timer;

	@Override
	public int getID() {
		return CopsAndRobbers.GAMEOVERSTATE;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		timer = 4000;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;
		if (cap.robberGame) {
			g.drawString("Crash! You've been caught!", 490, 300);
		}
		else {
			g.drawString("The robber escaped!", 500, 300);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		timer -= delta;
		if (timer <= 0)
			game.enterState(CopsAndRobbers.TITLESTATE);

	}
}
