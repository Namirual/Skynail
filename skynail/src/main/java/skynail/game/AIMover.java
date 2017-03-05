package skynail.game;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import skynail.domain.Dungeon;
import skynail.domain.City;

import skynail.domain.Player;
import skynail.domain.Point;
import skynail.service.DiceRoller;
import skynail.service.PathService;

/**
 * Class that controls the actions of an AI opponent.
 *
 * @author lmantyla
 */
public class AIMover {

    private Player aiPlayer;
    private AIState aiState;
    private int health;
    private MapLogic mapLogic;
    private PathService pathService;
    private DiceRoller diceRoller;
    private Map<Point, Integer> legalMoves;
    private Point target;

    /**
     * Creates an AI Mover.
     *
     * @param aiPlayer The player class used by AI.
     * @param mapLogic Logic class used in the game containing game state info.
     * @param diceRoller DiceRoller used in the game.
     */
    public AIMover(Player aiPlayer, MapLogic mapLogic, DiceRoller diceRoller) {
        this.aiPlayer = aiPlayer;
        this.mapLogic = mapLogic;
        this.pathService = new PathService(aiPlayer);
        this.diceRoller = diceRoller;
        this.health = 1;
        this.target = aiPlayer.getLocation();
        this.aiState = AIState.goToDungeon;
    }

    /**
     * Examines and modifies AI state and moves the AI player towards the
     * target.
     *
     * @return List of points the AI player will move through.
     */
    public List<Point> moveAiPlayer() {
        if (aiState == AIState.dead) {
            return Arrays.asList(aiPlayer.getLocation());
        }

        if (mapLogic.getPlayerWithSkynail() == aiPlayer) {
            aiState = AIState.goToGoal;
        } else if (mapLogic.getPlayerWithSkynail() == mapLogic.getPlayer() && health > 0) {
            aiState = AIState.chasePlayer;
        }
        target = decideTarget();

        if (target == null) {
            aiState = AIState.chasePlayer;
            target = mapLogic.getPlayer().getLocation();
        }

        int moves = diceRoller.diceThrow(3);

        List<Point> pathPoints = pathService.getPartialMovementPath(target, moves);
        if (pathPoints.isEmpty()) {
            pathPoints.add(aiPlayer.getLocation());
        }
        
        aiPlayer.setLocation(pathPoints.get(0));

        return pathPoints;
    }

    /**
     * Uses pathService and AI State to determine target to move towards.
     *
     * @return Point the AI wants to reach.
     */
    public Point decideTarget() {
        legalMoves = pathService.calculateLegalMoves(100);

        Class targetType = Point.class;

        if (aiState == AIState.goToCity) {
            targetType = City.class;
        } else if (aiState == AIState.goToDungeon) {
            targetType = Dungeon.class;
        } else if (aiState == AIState.goToGoal) {
            return mapLogic.getGoal();
        } else if (aiState == AIState.chasePlayer) {
            return mapLogic.getPlayer().getLocation();
        }

        Point target = null;

        for (Point point : legalMoves.keySet()) {
            if (point.getClass().equals(targetType)) {
                if (targetType == Dungeon.class) {
                    Dungeon dungeon = (Dungeon) point;
                    if (!dungeon.isCleared()) {
                        target = point;
                        break;
                    }
                } else {
                    target = point;
                    break;
                }
            }
        }
        return target;
    }

    /**
     * Handles AI player reaching its target.
     */
    public void handleReachingTarget() {
        if (target.getClass().equals(Dungeon.class)) {
            handleReachingDungeon((Dungeon) target);
        }

        if (target.getClass().equals(City.class)) {
            handleReachingCity((City) target);
        }
    }

    /**
     * Simplified resolution method for AI player entering a dungeon.
     *
     * @param dungeon Dungeon being entered.
     */
    public void handleReachingDungeon(Dungeon dungeon) {
        if (dungeon.isCleared()) {
            return;
        }
        health -= dungeon.getMonsters().size() + 1;
        for (int num = 0; num < aiPlayer.getCompanions().size(); num++) {
            if (!dungeon.getMonsters().isEmpty()) {
                dungeon.getMonsters().remove(dungeon.getMonsters().size() - 1);
            }
        }
        if (dungeon.getMonsters().isEmpty()) {
            dungeon.setCleared(true);
            aiPlayer.addTrophyContents(dungeon.getTrophy());
            if (dungeon.getTrophy().isSkynail()) {
                mapLogic.skynailFound(aiPlayer);
            }
        }
        if (health < 1) {
            aiState = AIState.goToCity;
        }
    }

    /**
     * Simplified resolution method for AI player entering a city.
     *
     * @param city City being entered.
     */
    public void handleReachingCity(City city) {
        health = aiPlayer.getCompanions().size();
        aiState = AIState.goToDungeon;
    }

    public Player getAiPlayer() {
        return aiPlayer;
    }

    public Point getTarget() {
        return target;
    }

    public void setTarget(Point target) {
        this.target = target;
    }

    public AIState getAiState() {
        return aiState;
    }

    public void setAiState(AIState aiState) {
        this.aiState = aiState;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}
