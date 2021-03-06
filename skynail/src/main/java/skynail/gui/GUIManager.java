/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.List;
import javax.swing.JPanel;
import skynail.domain.City;
import skynail.domain.Dungeon;
import skynail.domain.Player;
import skynail.domain.Point;
import skynail.game.BattleController;
import skynail.game.CityController;
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
    BattleScene bScene;

    public GUIManager() {
        setSize(600, 600);
        setLayout(new BorderLayout());

        MapPainter mapPainter = new MapPainter(this);
        this.setMapPainter(mapPainter);
    }

    public MapPainter getMapPainter() {
        return mapPainter;
    }

    public void setMapPainter(MapPainter mapPainter) {
        this.mapPainter = mapPainter;
        this.add(mapPainter);
    }

    public void displayMapMovement(Player player, List<Point> pathPoints) {
        mapPainter.movePath(player, pathPoints);
        //mapPainter.update();
    }

    public void showMapMessage(String text) {
        mapPainter.showMessageWindow(text);
    }

    public void showEndScreen(String text) {
        mapPainter.showMessageWindow(text, true);
    }

    public void startCityScene(CityController cityController) {
        scene = new CityScene(this, cityController);
        this.add(scene);
        mapPainter.setVisible(false);
        scene.setVisible(true);
    }

    public void endCityScene() {
        mapPainter.setVisible(true);
        scene.setVisible(false);
        scene = null;
    }

    public void startBattleScene(BattleController battleController) {
        bScene = new BattleScene(this, battleController);
        this.add(bScene);
        mapPainter.setVisible(false);
        bScene.setVisible(true);
    }

    public void endBattleScene() {
        mapPainter.setVisible(true);
        bScene.setVisible(false);
        bScene = null;
    }

    public MapController getMapController() {
        return mapController;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }
}
