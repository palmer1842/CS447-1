import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

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
		g.drawString("You are the robber!", 515, 300);
		g.drawString("Reach the safe house with out getting caught", 390, 320);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		timer -= delta;
		if (timer <= 0)
			game.enterState(CopsAndRobbers.PLAYSTATE);
	}
}
