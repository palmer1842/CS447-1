import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The transition state from the Title screen to the main game play state.
 *
 * A message is displayed indicating the players objective and current level.
 *
 * @author Jake Palmer
 */
public class LaunchState extends BasicGameState {

	private int timer;
	@Override
	public int getID() {
		return CopsAndRobbers.LAUNCHSTATE;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		timer = 2000;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers) game;

		if (cap.robberGame) {
			g.drawString("You are the robber!", 515, 300);
			g.drawString("Reach the safe house with out getting caught", 390, 320);
		}
		else {
			g.drawString("You are a cop!", 530, 300);
			g.drawString("Catch the robber before they reach the safe house", 385, 320);
		}

		g.drawString("Level " + cap.level, 555, 360);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		timer -= delta;
		if (timer <= 0)
			game.enterState(CopsAndRobbers.PLAYSTATE);
	}
}
