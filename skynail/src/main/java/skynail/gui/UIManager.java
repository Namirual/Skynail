package skynail.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.List;
import javax.swing.JPanel;
import skynail.domain.City;
import skynail.domain.Point;
import skynail.game.BattleController;
import skynail.game.CityController;
import skynail.game.MapController;

/**
 * Generic interface for user interface manager.
 * @author lmantyla
 */
public interface UIManager {


    public MapPainter getMapPainter();
    
    public void setMapPainter(MapPainter mapPainter);

    public void displayMapMovement(List<Point> pathPoints);

    public void showMapMessage(String text);
    
    public void startCityScene(CityController cityController);

    public void endCityScene();

    public void startBattleScene(BattleController battleController);

    public void endBattleScene();
        
    public MapController getMapController();

    public void setMapController(MapController mapController);
}