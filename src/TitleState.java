import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TitleState extends BasicGameState {

	private Robber robber;
	private Cop cop1, cop2, cop3;
	private int selectBoxX;

	@Override
	public int getID() {
		return CopsAndRobbers.TITLESTATE;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		robber = new Robber(15, 10, Vehicle.EAST);
		cop1 = new Cop(6, 9, Vehicle.EAST);
		cop2 = new Cop(8, 10, Vehicle.EAST);
		cop3 = new Cop(6, 11, Vehicle.EAST);
		selectBoxX = 395;
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
		robber.render(g);
		cop1.render(g);
		cop2.render(g);
		cop3.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		CopsAndRobbers cap = (CopsAndRobbers)game;
		Input input = container.getInput();

		// switch between game mode selector
		if (input.isKeyDown(Input.KEY_A)) {
			selectBoxX = 395;
			cap.robbergame = false;
		}

		if (input.isKeyDown(Input.KEY_D)) {
			selectBoxX = 745;
			cap.robbergame = true;
		}

		// start game
		if (input.isKeyDown(Input.KEY_SPACE)) {
			cap.enterState(CopsAndRobbers.LAUNCHSTATE);
		}
	}
}
