/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import skynail.domain.Companion;
import skynail.domain.Monster;

import skynail.domain.Item;

import skynail.game.BattleController;
import skynail.game.BattleState;

/**
 *
 * @author lmantyla
 */
public class BattleScene extends JPanel {

    private GUIManager manager;

    private JLabel label1;
    private JLabel label2;
    private JButton button1;
    private JButton button2;
    private BattleController controller;

    private JPanel graphicPanel;
    private JPanel bottomPanels;
    private JPanel companionPanel;
    private JPanel monsterPanel;
    private JPanel buttonPanel;

    private Item currentItem;

    BattleScene(GUIManager manager, BattleController controller) {

        this.controller = controller;
        this.manager = manager;

        this.setLayout(new BorderLayout());

        graphicPanel = new JPanel();
        graphicPanel.setLayout(null);
        graphicPanel.setSize(200, 200);
        graphicPanel.setSize(new Dimension(300, 300));
        this.add(graphicPanel);

        bottomPanels = new JPanel();
        bottomPanels.setLayout(new BoxLayout(bottomPanels, BoxLayout.PAGE_AXIS));
        this.add(bottomPanels, BorderLayout.SOUTH);

        label1 = new JLabel("No item selected.");
        label2 = new JLabel("");

        monsterPanel = new JPanel();
        companionPanel = new JPanel();

        JPanel messagePanel = new JPanel();

        messagePanel.add(label1);
        messagePanel.add(label2);

        JPanel statPanel = new JPanel();

        statPanel.setLayout(new GridLayout(1, 2));
        statPanel.setPreferredSize(new Dimension(450, 100));

        statPanel.add(monsterPanel);
        statPanel.add(companionPanel);

        update();

        button1 = new JButton("Use item!");
        button2 = new JButton("Escape!");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemSelection();
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
        bottomPanels.add(messagePanel);
        bottomPanels.add(statPanel);
        bottomPanels.add(buttonPanel);
    }

    public void checkState(BattleState state) {
        if (state == BattleState.playerTurn) {
            label2.setText("You hit the monster!");
        }

        if (state == BattleState.enemyTurn) {
            label2.setText("You hit the monster, and monsters strikes back!");
        }

        if (state == BattleState.victory) {
            victoryScene();
        }
        if (state == BattleState.death) {
            deathScene();
        }
    }

    public void update() {
        graphicPanel.removeAll();

        monsterPanel.removeAll();

        monsterPanel.setLayout(new GridLayout(controller.getMonsters().size(), 1));
        int monsterHeight = 50;

        for (int num = 0; num < controller.getMonsters().size(); num++) {
            Monster monster = controller.getMonsters().get(num);
            final int finalNum = num;
            JButton character = new JButton(monster.getName());
            character.setBounds(50, 50, 100, 50);

            character.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BattleState state = controller.handlePlayerAttack(controller.getMonsters().get(finalNum));
                    checkState(state);
                    update();
                }
            });

            character.setLocation(100, monsterHeight);
            monsterHeight += 50;
            graphicPanel.add(character);

            JLabel monsterLabel = new JLabel("<html><b>" + "<br>" + monster.getName() + " HP: " + monster.getHP() + "</b>");
            monsterPanel.add(monsterLabel);
        }

        companionPanel.removeAll();
        companionPanel.setLayout(new GridLayout(controller.getPlayer().getCompanions().size(), 1));

        int charHeight = 50;

        for (int num = 0; num < controller.getPlayer().getCompanions().size(); num++) {
            Companion companion = controller.getPlayer().getCompanions().get(num);

            int finalNum = num;

            JButton character = new JButton(companion.getName());
            character.setBounds(50, 50, 100, 50);

            character.setLocation(300, charHeight);
            charHeight += 50;
            graphicPanel.add(character);

            character.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentItem == null) {
                        return;
                    }
                    BattleState state = controller.handleItemUse(controller.getPlayer().getCompanions().get(finalNum), currentItem);
                    currentItem = null;
                    label1.setText("No item selected.");
                    update();
                    if (state == BattleState.playerTurn) {
                        label2.setText("You heal " + controller.getPlayer().getCompanions().get(finalNum).getName());
                    }
                }
            });

            JPanel charPanel = new JPanel();
            JLabel charLabel;
            if (controller.getCharacterTurn() == num) {
                charLabel = new JLabel("<html><b>" + "<br>" + companion.getName() + " HP: " + companion.getHP() + "</b>");
            } else {
                charLabel = new JLabel("<html>" + "<br>" + companion.getName() + " HP: " + companion.getHP());
            }

            companionPanel.add(charLabel);

            companionPanel.repaint();
            companionPanel.revalidate();

            monsterPanel.repaint();
            monsterPanel.revalidate();

            graphicPanel.repaint();
            graphicPanel.revalidate();
        }
    }

    public void itemSelection() {
        JDialog itemSelect = new JDialog();

        itemSelect.setPreferredSize(new Dimension(240, 240));
        itemSelect.setSize(240, 240);
        itemSelect.setLocationRelativeTo(this);
        itemSelect.setModal(true);
        itemSelect.setUndecorated(true);

        JButton leaveButton = new JButton("Return");
        JPanel itemPanel = new JPanel();
        itemSelect.getContentPane().add(itemPanel);
        itemPanel.add(leaveButton);

        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemSelect.setVisible(false);
            }
        });

        for (Item item : controller.getPlayer().getItems().keySet()) {
            int itemNumber = controller.getPlayer().getItems().get(item);

            JButton itemButton = new JButton(item.getName() + " : " + itemNumber);

            itemPanel.add(itemButton);
            itemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentItem = item;
                    label1.setText("Current item: " + item.getName() + " : " + itemNumber);
                    itemSelect.setVisible(false);
                }
            });
        }
        itemSelect.setVisible(true);
    }

    public void victoryScene() {

        JDialog victoryMessage = new JDialog();

        victoryMessage.setPreferredSize(new Dimension(240, 240));
        victoryMessage.setSize(240, 240);
        victoryMessage.setLocationRelativeTo(this);
        victoryMessage.setModal(true);
        victoryMessage.setUndecorated(true);

        JPanel victoryTexts = new JPanel();
        JButton endButton = new JButton("Leave victorious!");
        JLabel endText = new JLabel();

        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                victoryMessage.setVisible(false);
                manager.endBattleScene();
                manager.getMapController().getMapLogic().processBattleResult(BattleState.victory);
                victoryMessage.dispose();
            }
        });
        buttonPanel.remove(button1);

        endText.setText("You won the battle!");

        victoryTexts.add(endText);
        victoryTexts.add(endButton);
        victoryMessage.getContentPane().add(victoryTexts);

        victoryMessage.setVisible(true);
    }

    public void deathScene() {
        label2.setText("You died...");
        buttonPanel.remove(button1);
        buttonPanel.remove(button2);
    }
}
