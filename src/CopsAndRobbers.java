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

	public static final int TITLESTATE = 0;
	public static final int LAUNCHSTATE = 1;
	public static final int PLAYSTATE = 2;
	public static final int GAMEOVERSTATE = 3;
	public static final int WINSTATE = 4;

	static final String LAND_RSC = "resource/tile/LandTile.png";
	static final String ROAD_RSC = "resource/tile/RoadTile.png";
	static final String SAFE_HOUSE_RSC = "resource/tile/SafeHouseTile.png";
	static final String VEHICLE_NORTH_RSC = "resource/vehicle/VehicleN.png";
	static final String VEHICLE_EAST_RSC = "resource/vehicle/VehicleE.png";
	static final String VEHICLE_SOUTH_RSC = "resource/vehicle/VehicleS.png";
	static final String VEHICLE_WEST_RSC = "resource/vehicle/VehicleW.png";
	static final String COP_NORTH_RSC = "resource/cop/CopVehicleN.png";
	static final String COP_EAST_RSC = "resource/cop/CopVehicleE.png";
	static final String COP_SOUTH_RSC = "resource/cop/CopVehicleS.png";
	static final String COP_WEST_RSC = "resource/cop/CopVehicleW.png";
	static final String ROBBER_NORTH_RSC = "resource/robber/RobberVehicleN.png";
	static final String ROBBER_EAST_RSC = "resource/robber/RobberVehicleE.png";
	static final String ROBBER_SOUTH_RSC = "resource/robber/RobberVehicleS.png";
	static final String ROBBER_WEST_RSC = "resource/robber/RobberVehicleW.png";

	Tile[][] tile = new Tile[24][16];
	boolean robbergame;

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
		addState(new TitleState());
		addState(new LaunchState());
		addState(new PlayState());
		addState(new GameOverState());
		addState(new WinState());

		ResourceManager.loadImage(LAND_RSC);
		ResourceManager.loadImage(ROAD_RSC);
		ResourceManager.loadImage(SAFE_HOUSE_RSC);
		ResourceManager.loadImage(VEHICLE_NORTH_RSC);
		ResourceManager.loadImage(VEHICLE_EAST_RSC);
		ResourceManager.loadImage(VEHICLE_SOUTH_RSC);
		ResourceManager.loadImage(VEHICLE_WEST_RSC);
		ResourceManager.loadImage(COP_NORTH_RSC);
		ResourceManager.loadImage(COP_EAST_RSC);
		ResourceManager.loadImage(COP_SOUTH_RSC);
		ResourceManager.loadImage(COP_WEST_RSC);
		ResourceManager.loadImage(ROBBER_NORTH_RSC);
		ResourceManager.loadImage(ROBBER_EAST_RSC);
		ResourceManager.loadImage(ROBBER_SOUTH_RSC);
		ResourceManager.loadImage(ROBBER_WEST_RSC);
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
