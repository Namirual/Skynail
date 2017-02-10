package skynail.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.List;
import javax.swing.JPanel;
import skynail.domain.City;
import skynail.domain.Point;
import skynail.game.MapController;

/**
 * Generic interface for user interface manager.
 * @author lmantyla
 */
public interface UIManager {


    public MapPainter getMapPainter();
    
    public void setMapPainter(MapPainter mapPainter);

    public void updateMap(List<Point> legalMoveList);

    public void startCityScene(City city);

    public void endCityScene(City city);

    public MapController getMapController();

    public void setMapController(MapController mapController);
}