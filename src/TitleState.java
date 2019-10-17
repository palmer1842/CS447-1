import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TitleState extends BasicGameState {

	private Robber robber;
	private Cop cop1, cop2, cop3;

	@Override
	public int getID() {
		return CopsAndRobbers.TITLESTATE;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		robber = new Robber(15, 10, Vehicle.WEST);
		cop1 = new Cop(8, 8, Vehicle.WEST);
		cop2 = new Cop(10, 10, Vehicle.WEST);
		cop3 = new Cop(8, 12, Vehicle.WEST);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(new Color(Color.darkGray));
		g.drawString("Cops and Robbers", 520, 300);
		g.drawString("Press Space to start", 500, 320);
		robber.render(g);
		cop1.render(g);
		cop2.render(g);
		cop3.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;
		Input input = container.getInput();

		if (input.isKeyDown(Input.KEY_SPACE)) {
			cap.enterState(CopsAndRobbers.LAUNCHSTATE);
		}
	}
}
