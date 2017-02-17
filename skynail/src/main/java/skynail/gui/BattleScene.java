/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import skynail.domain.City;
import skynail.domain.Companion;
import skynail.game.BattleController;
import skynail.game.BattleState;

/**
 *
 * @author lmantyla
 */
public class BattleScene extends JPanel {

    private GUIManager manager;

    private JLabel label;
    private JLabel label2;
    private JButton button1;
    private JButton button2;
    private BattleController controller;

    private JPanel companionPanel;
    private JPanel monsterPanel;
    private JPanel buttonPanel;

    BattleScene(GUIManager manager, BattleController controller) {

        this.controller = controller;

        label = new JLabel("Monster HP: " + controller.getMonster().getHP());
        label2 = new JLabel("Fight it out!");

        monsterPanel = new JPanel();
        monsterPanel.setLayout(new GridLayout(1, 0));

        monsterPanel.add(label);
        companionPanel = new JPanel();

        JPanel messagePanel = new JPanel();
        messagePanel.add(label2);

        JPanel statPanel = new JPanel();
        statPanel.setLayout(new GridLayout(1, 2));
        statPanel.setPreferredSize(new Dimension(450, 50));
        statPanel.add(monsterPanel);
        statPanel.add(companionPanel);

        writeCompanions();

        this.add(statPanel);
        this.add(messagePanel);

        button1 = new JButton("Attack!");
        button2 = new JButton("Escape!");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BattleState state = controller.handlePlayerAttack(controller.getMonster());
                if (state == BattleState.playerTurn) {
                    writeCompanions();
                    label.setText("Monster HP " + controller.getMonster().getHP());
                    label2.setText("You hit the monster!");
                }

                if (state == BattleState.enemyTurn) {
                    writeCompanions();
                    label.setText("Monster HP " + controller.getMonster().getHP());
                    label2.setText("You hit the monster, and the monster strikes back!");
                }

                if (state == BattleState.victory) {
                    label.setText("Monster HP " + controller.getMonster().getHP());
                    victoryScene();
                }
                if (state == BattleState.death) {
                    deathScene();
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.endBattleScene();
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        this.add(buttonPanel);
    }

    public void writeCompanions() {
        companionPanel.removeAll();
        companionPanel.setLayout(new GridLayout(controller.getPlayer().getCompanions().size(), 1));

        for (int luku = 0; luku < controller.getPlayer().getCompanions().size(); luku++) {
            Companion companion = controller.getPlayer().getCompanions().get(luku);
            JPanel charPanel = new JPanel();
            JLabel charLabel;
            if (controller.getCharacterTurn() == luku) {
                charLabel = new JLabel("<html><b>" + "<br>" + companion.getName() + " HP: " + companion.getHP() + "</b>");
            } else {
                charLabel = new JLabel("<html>" + "<br>" + companion.getName() + " HP: " + companion.getHP());
            }

            companionPanel.add(charLabel);
        }
    }

    public void victoryScene() {
        label2.setText("You won!");
        buttonPanel.remove(button1);
        button2.setText("Leave victorious!");
    }

    public void deathScene() {
        label.setText("You died...");
        buttonPanel.remove(button1);
        buttonPanel.remove(button2);
    }
}
