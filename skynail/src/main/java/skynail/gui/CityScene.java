/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import skynail.domain.City;
import skynail.domain.Item;
import skynail.domain.Player;
import skynail.game.CityController;

/**
 * Generates the city view and buttons.
 *
 * @author lmantyla
 */
public class CityScene extends JPanel {

    GUIManager manager;
    CityController controller;
    City city;

    JLabel shopLabel;

    CityScene(GUIManager manager, CityController controller) {
        this.controller = controller;
        this.manager = manager;
        this.city = controller.getCity();

        //setSize(450, 450);
        JLabel label = new JLabel(city.getIntroText());
        shopLabel = new JLabel("You can buy items here.");
        this.add(label);
        this.add(shopLabel);

        JButton button1 = new JButton("Shop");
        JButton button2 = new JButton("Leave");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemSelection();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.endCityScene();
            }
        });

        this.add(button1);
        this.add(button2);
    }

    public void itemSelection() {
        JDialog itemSelect = new JDialog();

        itemSelect.setPreferredSize(new Dimension(240, 240));
        itemSelect.setSize(240, 240);
        itemSelect.setLocationRelativeTo(this);
        itemSelect.setModal(true);
        itemSelect.setUndecorated(true);

        JButton leaveButton = new JButton("Return");
        JLabel goldLabel = new JLabel("You have " + controller.getPlayer().getGold() + " gold");

        JPanel itemPanel = new JPanel();
        itemSelect.getContentPane().add(itemPanel);
        
        itemPanel.add(goldLabel);
        itemPanel.add(leaveButton);

        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemSelect.setVisible(false);
            }
        });

        for (Item item : city.getItems().keySet()) {
            int itemPrice = city.getItems().get(item);

            JButton itemButton = new JButton(item.getName() + " : " + itemPrice + " gold");

            itemPanel.add(itemButton);
            itemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (controller.handleItemBuy(item)) {
                        shopLabel.setText("You bought the " + item.getName());
                    } else {
                        shopLabel.setText("You don't have enough gold!");
                    }
                    itemSelect.setVisible(false);
                }
            });
        }
        itemSelect.setVisible(true);
    }
}
