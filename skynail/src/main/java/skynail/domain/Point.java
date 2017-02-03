/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

import java.util.List;
import skynail.gui.MapPoint;

/**
 *
 * @author lmantyla
 */
public interface Point {

    List<Point> getLinkedPoints();

    int movesRequired(Team team);
    
    MapPoint getMapPoint();

    void addPoints(Point... newPoints);

    void addPointsBothWays(Point... newPoints);

    String getName();
}
