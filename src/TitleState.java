import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TitleState extends BasicGameState {

	private int selectBoxX;

	@Override
	public int getID() {
		return CopsAndRobbers.TITLESTATE;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
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

		g.drawImage(ResourceManager.getImage(CopsAndRobbers.COP_EAST_RSC), 300, 450);
		g.drawImage(ResourceManager.getImage(CopsAndRobbers.COP_EAST_RSC), 400, 500);
		g.drawImage(ResourceManager.getImage(CopsAndRobbers.COP_EAST_RSC), 300, 550);
		g.drawImage(ResourceManager.getImage(CopsAndRobbers.ROBBER_EAST_RSC), 750, 500);
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
