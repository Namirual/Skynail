/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.List;
import javax.swing.JPanel;
import skynail.domain.City;
import skynail.domain.Point;
import skynail.game.MapController;

/**
 * Graphical user interface used to handle changes between different views.
 *
 * @author lmantyla
 */
public class GUIManager extends JPanel implements UIManager {

    MapPainter mapPainter;
    MapListener mapListener;
    MapController mapController;
    CityScene scene;

    public GUIManager(List<Point> worldMap) {
        setSize(600, 600);
        setLayout(new BorderLayout());

        MapPainter mapPainter = new MapPainter(worldMap, this);
        this.setMapPainter(mapPainter);
    }

    public MapPainter getMapPainter() {
        return mapPainter;
    }

    public void setMapPainter(MapPainter mapPainter) {
        this.mapPainter = mapPainter;
        this.add(mapPainter);
    }

    public void updateMap(List<Point> legalMoveList) {
        mapPainter.setLegalMoves(legalMoveList);
        mapPainter.update();

    }

    public void startCityScene(City city) {
        scene = new CityScene(this, city);
        this.add(scene);
        mapPainter.setVisible(false);
        scene.setVisible(true);
    }

    public void endCityScene(City city) {
        mapPainter.setVisible(true);
        scene.setVisible(false);
    }

    public MapController getMapController() {
        return mapController;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }
}
