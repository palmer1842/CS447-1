import jig.Entity;
import jig.ResourceManager;
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

	static final String LAND_RSC = "resource/LandTile.png";
	static final String ROAD_RSC = "resource/RoadTile.png";

	Tile[][] tile = new Tile[24][16];

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
		ResourceManager.loadImage(LAND_RSC);
		ResourceManager.loadImage(ROAD_RSC);
	}

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new CopsAndRobbers("Cops and Robbers"));
			app.setDisplayMode(1200, 800, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
}
