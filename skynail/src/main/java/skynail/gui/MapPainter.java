/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

import java.util.List;
import skynail.domain.Point;
import skynail.game.MapController;

/**
 *
 * @author lmantyla
 */
public interface MapPainter {

    public void setLegalMoves(List<Point> legalMoveList);

    public void setController(MapController controller);
    
    public void update();

}
