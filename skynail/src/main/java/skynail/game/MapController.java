package skynail.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import skynail.domain.City;
import skynail.domain.Companion;
import skynail.domain.Dungeon;
import skynail.domain.Monster;

import skynail.domain.Point;
import skynail.domain.Player;
import skynail.gui.UIManager;
import skynail.service.PathService;
import skynail.service.DiceRoller;

/**
 * Controller with methods that receives and handles player input from the UI.
 *
 * @author lmantyla
 */
public class MapController {

    private UIManager uiManager;
    private DiceRoller diceRoller;
    
    private PathService pathService;
    private MapLogic mapLogic;

    private List<Point> pathPoints;
    private Map<Point, Integer> legalMoves;

    int moves;

    /**
     * Initializes the Map Controller.
     *
     * @param player current player
     * @param worldMap all the points in the current game
     * @param uiManager user interface currently in use
     * @param diceRoller currently used dice roller
     */
    public MapController(Player player, List<Point> worldMap, DiceRoller diceRoller, UIManager uiManager) {
        this.uiManager = uiManager;
        this.diceRoller = diceRoller;
        
        this.pathService = new PathService(player);
        this.mapLogic = new MapLogic(diceRoller, uiManager, worldMap, player);
        this.legalMoves = pathService.calculateLegalMoves(1);

        this.pathPoints = new ArrayList<Point>();
    }

    /**
     * The method attempts to move the player to point selected in the UI.
     * <p>
     * The method is called by the user interface. The method subsequently calls
     * update() to initiate redrawing the map view.
     *
     * @param point Map point selected by player in the interface.
     */
    public void handlePointInput(Point point) {

        if (legalMoves.containsKey(point)) {

            pathPoints = pathService.getMovementPath(point);
            getPlayer().setLocation(point);
            uiManager.displayMapMovement(getPlayer(), pathPoints);

            moves = 0;
            legalMoves.clear();

            if (mapLogic.checkPlayerFight()) {
                return;
            }
            mapLogic.processTurn();
        }
    }

    /**
     * Processes different area types the player may enter.
     */
    public void handleEnteringArea() {
        Point point = getPlayer().getLocation();
        if (point.getClass().equals(City.class)) {
            City city = (City) point;
            uiManager.startCityScene(new CityController(uiManager, getPlayer(), city));

        }

        if (point.getClass().equals(Dungeon.class)) {
            Dungeon dungeon = (Dungeon) point;
            if (!dungeon.isCleared()) {
                BattleController battleController = new BattleController(uiManager, diceRoller, getPlayer(), dungeon.getMonsters());
                uiManager.startBattleScene(battleController);
            }
        }
    }

    /**
     * Calls a remote dice roller.
     *
     * @return random number from dice roller
     */
    public int handleDiceRoll() {
        if (moves != 0) {
            mapLogic.processTurn();
        }
        moves = diceRoller.diceThrow(3);
        legalMoves = pathService.calculateLegalMoves(moves);

        return moves;
    }

    public MapLogic getMapLogic() {
        return mapLogic;
    }

    /**
     * Redirects get command to mapLogic.
     * @return Player used by human player
     */
    public Player getPlayer() {
        return mapLogic.getPlayer();
    }

    /**
     * Redirects set command to mapLogic.
     * @param player Player used by human player.
     */
    public void setPlayer(Player player) {
        mapLogic.setPlayer(player);
    }

    public List<Point> getWorldMap() {
        return mapLogic.getWorldMap();
    }

    public Map<Point, Integer> getLegalMoves() {
        return legalMoves;
    }

    public List<Point> getPathPoints() {
        return pathPoints;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public int getMoves() {
        return moves;
    }

    /**
     * Redirects set command for world map to MapLogic.
     * @param worldMap List containing all points in the game.
     */
    public void setWorldMap(List<Point> worldMap) {
        mapLogic.setWorldMap(worldMap);
    }
}
