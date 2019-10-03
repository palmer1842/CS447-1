import jig.Entity;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Basic runnable game.
 *
 * @author Jake Palmer
 */
public class CopsAndRobbers extends StateBasedGame {

	public CopsAndRobbers(String name) {
		super(name);

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		/* Potential States:
			Title
			Launch
			Play as Robber
			Play as Cop
			Win/Lose
		 */
	}

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new CopsAndRobbers("Cops and Robbers"));
			app.setDisplayMode(800, 600, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
}
