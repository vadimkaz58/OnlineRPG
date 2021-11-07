/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinerpg;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vadimkaz58
 */
public class OnlineClient extends Online{
    private static InputStream is;
    private static OutputStream os;
    InputThread inputThread;
    Game game;
    int stage;
    
    public void sendPlayer(String name, int score) throws IOException {
        String mess = name + "\n" + Integer.toString(score) + "\n"; 
        os.write(mess.getBytes());
    }
    
    public class InputThread extends Thread {
        @Override
        public void run() {
            int ch;
            int x = 0;
            int y = 0;
            int stage = 0;
            String name = "";
            String score = "";
            try {
                while ((ch = is.read()) != -1) {
                    switch (stage) {
                        case 0:
                            if (ch == 'e') {
                                game.exit();
                            } else if (ch == 'b') {
                                game.begin();
                            }
                            stage++;
                            break;
                        case 1: 
                            if (ch == '\n') {
                                stage++;
                            } else {
                                name += (char) ch;
                            }
                            break;
                        case 2:
                            if (ch == '\n') {
                                stage++;
                                getsPlayer(name, Integer.parseInt(score));
                            } else {
                                score += (char) ch;
                            }
                            break;
                        case 3:
                            x = ((char) ch) - '0';
                            stage++;
                            break;
                        case 4:
                            y = ((char) ch) - '0';
                            stage = 3;
                            getXY(x, y);
                            break;

                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }
        }
    }
    public void getXY(int x, int y) {
          game.field(x, y);
    }
    
    @Override
    public void sendXY(int x, int y) {
        String message = Integer.toString(x) + Integer.toString(y);
        try {
            os.write(message.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(OnlineClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getsPlayer(String name, int score) {
        game.setPlayer(name, score, true);
    }
    
    public void closeStream() {
        try {
            os.close();
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(OnlineClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    OnlineClient(String ip, int port, Game game) throws Exception {
        Socket sock = new Socket(ip, port);
        this.game = game;
        is = sock.getInputStream();
        os = sock.getOutputStream();
        
        inputThread = new InputThread();
        inputThread.start();
        
        sendPlayer(game.player2, game.scoreP2);
    }
}
