/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

import java.util.ArrayList;
import java.util.List;
import skynail.gui.MapPoint;

/**
 * Simple map point.
 *
 * @author lmantyla
 */
public class Road implements Point {

    String name;
    List<Point> points;
    MapPoint mapPoint;

    /**
     * Creates new Road for the text interface without a MapPoint.
     *
     * @param name name of the Road.
     */
    public Road(String name) {
        this.name = name;
        this.points = new ArrayList<>();
    }

    /**
     * Creates new road for the graphical user interface, MapPoint is included.
     *
     * @param name name of the Road.
     * @param mapPoint mapPoint containing the coordinates of the road.
     */
    public Road(String name, MapPoint mapPoint) {
        this.name = name;
        this.points = new ArrayList<>();
        this.mapPoint = mapPoint;
    }

    @Override
    public void addPoints(Point... newPoints) {
        for (Point point : newPoints) {
            points.add(point);
        }
    }

    @Override
    public void addPointsBothWays(Point... newPoints) {
        for (Point point : newPoints) {
            points.add(point);
            point.addPoints(this);
        }
    }

    @Override
    public MapPoint getMapPoint() {
        return mapPoint;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Point> getLinkedPoints() {
        return points;
    }

    @Override
    public int movesRequired(Player team) {
        return 1;
    }

}
