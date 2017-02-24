/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.game;

import java.util.List;
import skynail.domain.Monster;
import skynail.domain.Player;
import skynail.gui.UIManager;
import skynail.service.DiceRoller;

import skynail.domain.City;
import skynail.domain.Item;

/**
 * Controller for visiting cities.
 * @author lmantyla
 */
public class CityController {

    private UIManager uiManager;
    private Player player;
    private City city;

    /**
     * Creates new city controller.
     * @param uiManager uiManager in use.
     * @param player Current player.
     * @param city City that is being visited.
     */
    public CityController(UIManager uiManager, Player player, City city) {
        this.uiManager = uiManager;
        this.player = player;
        this.city = city;
    }

    /**
     * Used to make purchases in cities.
     *
     * @param item Item being bought.
     * @return true if transaction is successful
     */
    public boolean handleItemBuy(Item item) {
        if (city.getItems().containsKey(item)) {
            return player.buyItemWithGold(item, city.getItems().get(item));
        }
        return false;
    }

    public City getCity() {
        return city;
    }

    public Player getPlayer() {
        return player;
    }
}
