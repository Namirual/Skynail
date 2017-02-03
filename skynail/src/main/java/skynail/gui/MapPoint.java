/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

/**
 *
 * @author lmantyla
 */
public class MapPoint {

    private int x;
    private int y;

    public MapPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
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
