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
public class City implements Point {

    String name;
    List<Point> points;

    public City(String name, List<Point> points) {
        this.name = name;
        this.points = points;
    }

    @Override
    public List<Point> getLinkedPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    @Override
    public int movesRequired(Team team) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addPoints(Point... newPoints) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    

    @Override
    public void addPointsBothWays(Point... newPoints) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MapPoint getMapPoint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
