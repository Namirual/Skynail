/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import skynail.domain.Point;
import skynail.domain.Player;
import skynail.service.PathService;

/**
 * Early text control UI, does not interact with MapController.
 * 
 * @author lmantyla
 */

public class TextControl {

    Player player;
    Scanner scanner;
    Random random;

    /**
     * Creates text control interface.
     * @param player Current player.
     * @param scanner Scanner for reading input.
     * @param random Randomiser.
     */
    public TextControl(Player player, Scanner scanner, Random random) {
        this.player = player;
        this.scanner = scanner;
        this.random = random;
    }

    /**
     * Starts text game.
     */
    public void startTextGame() {
        System.out.println("Welcome, " + player.getName());
        System.out.println("You have up to 3 moves each turn. Use numbers to move, 0 to quit.");

        boolean running = true;
        while (running) {
            running = handlePlayerMove(new PathService(player));
        }
    }

    /**
     * Handles player moves.
     * @param pathService PathService for producing paths.
     * @return Returns true when legal move is selected.
     */
    public boolean handlePlayerMove(PathService pathService) {
        int moves = random.nextInt(3) + 1;
        System.out.println("You have " + moves + " moves. You are now in " + player.getLocation().getName());

        Map<Point, Integer> legalMoves = pathService.calculateLegalMoves(moves);

        List<Point> legalMoveList = new ArrayList<Point>();
        legalMoveList.addAll(legalMoves.keySet());

        int value = 1;

        for (Point point : legalMoveList) {
            System.out.println(value + ": " + point.getName());
            value++;
        }
        
        System.out.print("Move where? ");
        int target = Integer.parseInt(scanner.nextLine());

        if (target == 0) {
            return false;
        } else if (target >= legalMoveList.size() + 1) {
            System.out.println("Please select a legal move.");
        } else {
            writeRoute(pathService.getMovementPath(legalMoveList.get(target - 1)));

            player.setLocation(legalMoveList.get(target - 1));
        }
        return true;
    }

    /**
     * Writes each point visited by the player during a move.
     * @param route List of points from the PathService.
     */
    public void writeRoute(List<Point> route) {
        System.out.print("Traveling from " + route.get(route.size() - 1).getName());
        for (int routeLength = route.size() - 2; routeLength > 0; routeLength--) {
            System.out.print(" through " + route.get(routeLength).getName());
        }
        System.out.println(" to " + route.get(0).getName());
    }
}
