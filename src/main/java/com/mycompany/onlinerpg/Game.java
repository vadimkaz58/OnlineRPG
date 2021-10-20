/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinerpg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;

/**
 *
 * @author vadimkaz58
 */
public class Game extends javax.swing.JPanel {
    
    boolean online;
    String name;
    Menu menu;
    int startPage = 0;
    String player2 = "player2";
    String ipBDServer;
    Square[][] field = new Square[3][3];
    Player[] players = new Player[2];
    Player activePlayer;
    JButton[][] jBF = new JButton[3][3];
    int scoreP1 = 0;
    /**
     * Creates new form Game
     */
    public Game(Menu menu, String name, boolean online, String ipBDServer, int scoreP1) {
        this.online = online;
        this.name = name;
        this.menu = menu;
        this.ipBDServer = ipBDServer;
        this.scoreP1 = scoreP1;
        if (online) {
            startPage = 0;  
        } else {
            startPage = 1;
        }
        initComponents();
        jLabelPlayer1.setText(name);
        jTabbedPane1.setSelectedIndex(startPage);
        players[0] = new Player(name, new ImageIcon("src/image/shield.png"));
        players[1] = new Player(player2, new ImageIcon("src/image/sword.png"));
        activePlayer = players[0];
        jLabelPlayer1.setForeground(Color.blue);
        jLabelPlayer1.setFont(new Font("Segou UI", Font.PLAIN, 13));
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Square();
            }
        }
        jBF[0][0] = jBF1x1;
        jBF[0][1] = jBF1x2;
        jBF[0][2] = jBF1x3;
        jBF[1][0] = jBF2x1;
        jBF[1][1] = jBF2x2;
        jBF[1][2] = jBF2x3;
        jBF[2][0] = jBF3x1;
        jBF[2][1] = jBF3x2;
        jBF[2][2] = jBF3x3;
    }
    
    private void changeActivePlayer() {
        if (activePlayer == players[1]) {
            activePlayer = players[0];
            jLabelPlayer2.setForeground(Color.BLACK);
            jLabelPlayer2.setFont(new Font("Segou UI", Font.PLAIN, 12));
            jLabelPlayer1.setForeground(Color.blue);
            jLabelPlayer1.setFont(new Font("Segou UI", Font.PLAIN, 13));
        } else {
            activePlayer = players[1];
            jLabelPlayer1.setForeground(Color.BLACK);
            jLabelPlayer1.setFont(new Font("Segou UI", Font.PLAIN, 12));
            jLabelPlayer2.setForeground(Color.blue);
            jLabelPlayer2.setFont(new Font("Segou UI", Font.PLAIN, 13));
        }
    }
    
    private Player checkWinner() {
        Player currPlayer;
        Player lastPlayer = null;
        int successCounter = 1;
        for (int i = 0, len = field.length; i < len; i++) {
            currPlayer = field[i][i].player;
            if (currPlayer != null) {
                if (lastPlayer == currPlayer) {
                    successCounter++;
                    if (successCounter == len) {
                        return currPlayer;
                    }
                }
            }
            lastPlayer = currPlayer;
        }
        successCounter = 1;
        for (int i = 0, len = field.length; i < len; i++) {
            currPlayer = field[i][len - (i + 1)].player;
            if (currPlayer != null) {
                if (lastPlayer == currPlayer) {
                    successCounter++;
                    if (successCounter == len) {
                        return currPlayer;
                    }
                }
            }
            lastPlayer = currPlayer;
        }
        for (int i = 0, len = field.length; i < len; i++) {
            lastPlayer = null;
            successCounter = 1;
            for (int j = 0, len2 = field[i].length; j < len2; j++) {
                currPlayer = field[i][j].player;
                if (currPlayer == lastPlayer && (currPlayer != null && lastPlayer != null)) {
                    successCounter++;
                    if (successCounter == len2) {
                        return currPlayer;
                    }
                }
                lastPlayer = currPlayer;
            }
        }
        for (int i = 0, len = field.length; i < len; i++) {
            lastPlayer = null;
            successCounter = 1;
            for (int j = 0, len2 = field[i].length; j < len2; j++) {
                currPlayer = field[j][i].player;
                if (currPlayer == lastPlayer && (currPlayer != null && lastPlayer != null)) {
                    successCounter++;
                    if (successCounter == len2) {
                        return currPlayer;
                    }
                }
                lastPlayer = currPlayer;
            }
        }
        return null;
    }
    
    private void win() {
        Player winner = checkWinner();
        if (winner != null) {
            if (winner == players[0]) {
                jLabelScore1.setText(Integer.toString(++winner.score));  
            } else {
                jLabelScore2.setText(Integer.toString(++winner.score));
            }
            refresh();
        }
    }
    
    private void refresh() {
        for (int i = 0; i < jBF.length; i++) {
            for (int j = 0; j < jBF[i].length;j++) {
                jBF[i][j].setIcon(null);
                field[i][j].player = null;
            }
        }
        activePlayer = players[0];
    }
    
    private void field(int x, int y) {
        if (field[x][y].player == null) {
            field[x][y].player = activePlayer;
            jBF[x][y].setIcon(activePlayer.img);
        } else if (field[x][y].player == activePlayer) {
            return;
        } else {
            field[x][y].player = null;
            jBF[x][y].setIcon(null);
        }
        changeActivePlayer();
        win();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0,0,0,0)); UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true); jTabbedPane1.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() { private final java.awt.Insets borderInsets = new java.awt.Insets(0,0,0,0); @Override protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) { } @Override protected Insets getContentBorderInsets(int tabPlacement) {     return borderInsets; } });
        jTabbedPane1.setUI(new javax.swing.plaf.metal.MetalTabbedPaneUI(){
            protected void paintTabArea(Graphics g,int tabPlacement,int selectedIndex){} });
    jPanel1 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jPanel2 = new javax.swing.JPanel();
    jPanel4 = new javax.swing.JPanel();
    jButton3 = new javax.swing.JButton();
    jPanel3 = new javax.swing.JPanel();
    jBF1x1 = new javax.swing.JButton();
    jBF1x2 = new javax.swing.JButton();
    jBF1x3 = new javax.swing.JButton();
    jBF2x1 = new javax.swing.JButton();
    jBF2x2 = new javax.swing.JButton();
    jBF2x3 = new javax.swing.JButton();
    jBF3x1 = new javax.swing.JButton();
    jBF3x2 = new javax.swing.JButton();
    jBF3x3 = new javax.swing.JButton();
    jLabelPlayer1 = new javax.swing.JLabel();
    jLabelScore2 = new javax.swing.JLabel();
    jLabelPlayer2 = new javax.swing.JLabel();
    jLabelScore1 = new javax.swing.JLabel();
    jLabelDD = new javax.swing.JLabel();

    setBackground(new java.awt.Color(255, 204, 204));
    setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

    jTabbedPane1.setBackground(new java.awt.Color(255, 204, 204));
    jTabbedPane1.setMinimumSize(new java.awt.Dimension(500, 500));
    jTabbedPane1.setPreferredSize(new java.awt.Dimension(500, 500));

    jPanel1.setBackground(new java.awt.Color(255, 204, 204));
    java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
    jPanel1Layout.columnWidths = new int[] {0, 13, 0, 13, 0, 13, 0};
    jPanel1Layout.rowHeights = new int[] {0, 13, 0, 13, 0};
    jPanel1.setLayout(jPanel1Layout);

    jTable1.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Имя", "Очки"
        }
    ));
    jScrollPane1.setViewportView(jTable1);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 7;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 250;
    gridBagConstraints.ipady = 250;
    jPanel1.add(jScrollPane1, gridBagConstraints);

    jButton1.setText("Отмена");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    jPanel1.add(jButton1, gridBagConstraints);

    jButton2.setText("Принять");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    jPanel1.add(jButton2, gridBagConstraints);

    jLabel1.setBackground(new java.awt.Color(0, 0, 0));
    jLabel1.setForeground(new java.awt.Color(0, 0, 0));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 7;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    jPanel1.add(jLabel1, gridBagConstraints);

    jTabbedPane1.addTab("tab1", jPanel1);

    jPanel2.setBackground(new java.awt.Color(255, 204, 204));
    jPanel2.setLayout(new java.awt.BorderLayout());

    jPanel4.setBackground(new java.awt.Color(255, 204, 204));
    jPanel4.setLayout(new java.awt.BorderLayout());

    jButton3.setBackground(new java.awt.Color(60, 63, 65));
    jButton3.setText("Выход");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });
    jPanel4.add(jButton3, java.awt.BorderLayout.LINE_START);

    jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

    jPanel3.setBackground(new java.awt.Color(255, 204, 204));
    java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
    jPanel3Layout.columnWidths = new int[] {0, 13, 0, 13, 0};
    jPanel3Layout.rowHeights = new int[] {0, 13, 0, 13, 0, 13, 0};
    jPanel3.setLayout(jPanel3Layout);

    jBF1x1.setMaximumSize(new java.awt.Dimension(50, 50));
    jBF1x1.setMinimumSize(new java.awt.Dimension(50, 50));
    jBF1x1.setPreferredSize(new java.awt.Dimension(50, 50));
    jBF1x1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jBF1x1ActionPerformed(evt);
            jFieldAction(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    jPanel3.add(jBF1x1, gridBagConstraints);

    jBF1x2.setMaximumSize(new java.awt.Dimension(50, 50));
    jBF1x2.setMinimumSize(new java.awt.Dimension(50, 50));
    jBF1x2.setPreferredSize(new java.awt.Dimension(50, 50));
    jBF1x2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jBF1x2ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 2;
    jPanel3.add(jBF1x2, gridBagConstraints);

    jBF1x3.setMaximumSize(new java.awt.Dimension(50, 50));
    jBF1x3.setMinimumSize(new java.awt.Dimension(50, 50));
    jBF1x3.setPreferredSize(new java.awt.Dimension(50, 50));
    jBF1x3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jBF1x3ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 2;
    jPanel3.add(jBF1x3, gridBagConstraints);

    jBF2x1.setMaximumSize(new java.awt.Dimension(50, 50));
    jBF2x1.setMinimumSize(new java.awt.Dimension(50, 50));
    jBF2x1.setPreferredSize(new java.awt.Dimension(50, 50));
    jBF2x1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jBF2x1ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    jPanel3.add(jBF2x1, gridBagConstraints);

    jBF2x2.setMaximumSize(new java.awt.Dimension(50, 50));
    jBF2x2.setMinimumSize(new java.awt.Dimension(50, 50));
    jBF2x2.setPreferredSize(new java.awt.Dimension(50, 50));
    jBF2x2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jBF2x2ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 4;
    jPanel3.add(jBF2x2, gridBagConstraints);

    jBF2x3.setMaximumSize(new java.awt.Dimension(50, 50));
    jBF2x3.setMinimumSize(new java.awt.Dimension(50, 50));
    jBF2x3.setPreferredSize(new java.awt.Dimension(50, 50));
    jBF2x3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jBF2x3ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 4;
    jPanel3.add(jBF2x3, gridBagConstraints);

    jBF3x1.setMaximumSize(new java.awt.Dimension(50, 50));
    jBF3x1.setMinimumSize(new java.awt.Dimension(50, 50));
    jBF3x1.setPreferredSize(new java.awt.Dimension(50, 50));
    jBF3x1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jBF3x1ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 6;
    jPanel3.add(jBF3x1, gridBagConstraints);

    jBF3x2.setMaximumSize(new java.awt.Dimension(50, 50));
    jBF3x2.setMinimumSize(new java.awt.Dimension(50, 50));
    jBF3x2.setPreferredSize(new java.awt.Dimension(50, 50));
    jBF3x2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jBF3x2ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 6;
    jPanel3.add(jBF3x2, gridBagConstraints);

    jBF3x3.setMaximumSize(new java.awt.Dimension(50, 50));
    jBF3x3.setMinimumSize(new java.awt.Dimension(50, 50));
    jBF3x3.setPreferredSize(new java.awt.Dimension(50, 50));
    jBF3x3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jBF3x3ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 6;
    jPanel3.add(jBF3x3, gridBagConstraints);

    jLabelPlayer1.setForeground(new java.awt.Color(0, 0, 0));
    jLabelPlayer1.setText("Player1");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    jPanel3.add(jLabelPlayer1, gridBagConstraints);

    jLabelScore2.setForeground(new java.awt.Color(0, 0, 0));
    jLabelScore2.setText("0");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    jPanel3.add(jLabelScore2, gridBagConstraints);

    jLabelPlayer2.setForeground(new java.awt.Color(0, 0, 0));
    jLabelPlayer2.setText("Player2");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    jPanel3.add(jLabelPlayer2, gridBagConstraints);

    jLabelScore1.setForeground(new java.awt.Color(0, 0, 0));
    jLabelScore1.setText("0");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    jPanel3.add(jLabelScore1, gridBagConstraints);

    jLabelDD.setForeground(new java.awt.Color(0, 0, 0));
    jLabelDD.setText(":");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    jPanel3.add(jLabelDD, gridBagConstraints);

    jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

    jTabbedPane1.addTab("tab2", jPanel2);

    add(jTabbedPane1);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        menu.setContentPane(menu.jPanel1);
        menu.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jBF2x1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBF2x1ActionPerformed
        field(1,0);
    }//GEN-LAST:event_jBF2x1ActionPerformed

    private void jBF2x2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBF2x2ActionPerformed
        field(1,1);
    }//GEN-LAST:event_jBF2x2ActionPerformed

    private void jBF2x3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBF2x3ActionPerformed
        field(1,02);
    }//GEN-LAST:event_jBF2x3ActionPerformed

    private void jBF3x1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBF3x1ActionPerformed
        field(2,0);
    }//GEN-LAST:event_jBF3x1ActionPerformed

    private void jBF3x2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBF3x2ActionPerformed
        field(2,1);
    }//GEN-LAST:event_jBF3x2ActionPerformed

    private void jBF3x3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBF3x3ActionPerformed
        field(2,2);
    }//GEN-LAST:event_jBF3x3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int score = Integer.parseInt(jLabelScore1.getText()) - Integer.parseInt(jLabelScore2.getText());
        DatabaseProcedures databaseProcedures = new DatabaseProcedures(ipBDServer);
        try {
            databaseProcedures.addScoreBD(score, name);
        } catch (SQLException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        databaseProcedures.close();
        menu.setContentPane(menu.jPanel1);
        scoreP1 += score; 
        menu.labelScore.setText("Очки: " + Integer.toString(scoreP1));
        menu.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jBF1x1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBF1x1ActionPerformed
        field(0,0);
        
    }//GEN-LAST:event_jBF1x1ActionPerformed

    private void jFieldAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFieldAction
        
    }//GEN-LAST:event_jFieldAction

    private void jBF1x2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBF1x2ActionPerformed
        field(0,1);
    }//GEN-LAST:event_jBF1x2ActionPerformed

    private void jBF1x3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBF1x3ActionPerformed
        field(0,2);
    }//GEN-LAST:event_jBF1x3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBF1x1;
    private javax.swing.JButton jBF1x2;
    private javax.swing.JButton jBF1x3;
    private javax.swing.JButton jBF2x1;
    private javax.swing.JButton jBF2x2;
    private javax.swing.JButton jBF2x3;
    private javax.swing.JButton jBF3x1;
    private javax.swing.JButton jBF3x2;
    private javax.swing.JButton jBF3x3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelDD;
    private javax.swing.JLabel jLabelPlayer1;
    private javax.swing.JLabel jLabelPlayer2;
    private javax.swing.JLabel jLabelScore1;
    private javax.swing.JLabel jLabelScore2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
