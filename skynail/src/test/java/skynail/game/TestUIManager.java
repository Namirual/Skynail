/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.game;

import java.util.List;
import skynail.domain.City;
import skynail.domain.Player;
import skynail.domain.Point;
import skynail.gui.MapPainter;
import skynail.gui.UIManager;

/**
 *
 * @author lmantyla
 */
public class TestUIManager implements UIManager {

    List<Point> worldMap;
    List<Point> legalMoves;

    boolean battle;
    boolean victory;

    @Override
    public void displayMapMovement(Player player, List<Point> points) {
    }

    @Override
    public void startCityScene(CityController cityController) {
    }

    @Override
    public void endCityScene() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showMapMessage(String text) {
    }

    public void showEndScreen(String text) {
        victory = true;
    }

    @Override
    public void startBattleScene(BattleController battleController) {
        battle = true;
    }

    @Override
    public void endBattleScene() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMapController(MapController mapController) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MapPainter getMapPainter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMapPainter(MapPainter mapPainter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MapController getMapController() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
