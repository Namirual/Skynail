/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

import java.util.List;
import skynail.gui.MapPoint;

/**
 * Interface for map points.
 * 
 * @author lmantyla
 */
public interface Point {

    /**
     * Gets points liked to the Point.
     * @return list of points.
     */
    List<Point> getLinkedPoints();

    /**
     * Calculates the number of moves required to move to this point.
     * 
     * @param player the player for which the move is calculated.
     * Any properties of the player may impact the calculation.
     * @return Number of moves required to move to this point.
     */
    int movesRequired(Player player);
    
    /**
     * Returns the MapPoint containing the coordinates of the Point on the map.
     * @return map point.
     */
    MapPoint getMapPoint();

    /**
     * Adds another point as a connection to current point. 
     * @param newPoints one or more points to be added.
     */
    void addPoints(Point... newPoints);

    /**
     * Adds another point as connection and adds connection to that point.
     * @param newPoints one or more points to be added.
     */
    void addPointsBothWays(Point... newPoints);

    /**
     * Gets name of the point.
     * @return name of the point.
     */
    String getName();
}
