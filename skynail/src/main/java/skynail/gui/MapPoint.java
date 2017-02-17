/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

/**
 * Class used by the graphical UI to store the positions of map points.
 *
 * @author lmantyla
 */
public class MapPoint {

    private float x;
    private float y;

    public MapPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public MapPoint(MapPoint mapPoint) {
        this.x = mapPoint.x;
        this.y = mapPoint.y;
    }

    public int getX() {
        return Math.round(x);
    }

    public int getY() {
        return Math.round(y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXf(float x) {
        this.x = x;
    }

    public void setYf(float y) {
        this.y = y;
    }

    public float getXf() {
        return x;
    }

    public float getYf() {
        return y;
    }

    public boolean checkIfInside(int x, int y) {
        int size = 10;
        if (x < this.x + size && x > this.x - size
                && y < this.y + size && y > this.y - size) {
            return true;
        }
        return false;
    }
}
