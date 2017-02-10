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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import skynail.domain.City;

/**
 * Generates the city view and buttons.
 *
 * @author lmantyla
 */
public class CityScene extends JPanel {

    CityScene(UIManager manager, City city) {

        //setSize(450, 450);
        JLabel label = new JLabel(city.getIntroText());
        JLabel label2 = new JLabel("");
        this.add(label);
        this.add(label2);

        JButton button1 = new JButton("Buy things");
        JButton button2 = new JButton("Leave");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manager.getMapController().handleCityBuy()) {
                    label2.setText("You bought an item!");
                } else {
                    label2.setText("You don't have enough gold!");
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.endCityScene(city);
            }
        });

        this.add(button1);
        this.add(button2);
    }
}
