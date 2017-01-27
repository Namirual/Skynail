/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lmantyla
 */
// Simplest type of map point.
public class Road implements Point {

    String name;
    List<Point> points;

    public Road(String name) {
        this.name = name;
        this.points = new ArrayList<>();
    }

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
    public String getName() {
        return name;
    }

    @Override
    public List<Point> getLinkedPoints() {
        return points;
    }

    @Override
    public int movesRequired(Team team) {
        return 1;
    }

}
