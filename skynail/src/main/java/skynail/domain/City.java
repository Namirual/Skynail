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
 * Map point for cities, presently unfinished.
 *
 * @author lmantyla
 */
public class City extends Road implements Point {

    String introText;

    public City(String name) {
        super(name);
    }

    public City(String name, String introText) {
        super(name);
        this.introText = introText;
    }

    public City(String name, String introText, MapPoint mapPoint) {
        super(name, mapPoint);
        this.introText = introText;
    }

    public String getIntroText() {
        return introText;
    }
}
