/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinerpg;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vadimkaz58
 */
public class OnlineServer extends Online{
    private ArrayList<ClientThread> clientThreadArrayList = new ArrayList<>();
    Game game;    
    int index;
    OnlineServer(Game aThis) throws IOException {
        this.game = aThis;
                
        ListThread listThread = new ListThread();
        listThread.start();
    }
    
    public class ListThread extends Thread {
        public void run() {
            ServerSocket srvSocket = null;
            try {
                srvSocket = new ServerSocket(8080);
            } catch (IOException ex) {
                Logger.getLogger(OnlineServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (true) {
            Socket sock = null;
                try {
                    sock = srvSocket.accept();
                } catch (IOException ex) {
                    Logger.getLogger(OnlineServer.class.getName()).log(Level.SEVERE, null, ex);
                }

            ClientThread t = new ClientThread(sock);

            clientThreadArrayList.add(t);
            t.start();
        }
        }
    }
    
    public class ClientThread extends Thread {
        private final Socket sock;
        public InputStream is;
        public OutputStream os;

        public ClientThread(Socket sock) {
            this.sock = sock;
        }
        public void run() {
            int ch;
            int x = 0;
            int y = 0;
            int stage = 0;
            String name = "";
            String score = "";
            try {
                is = sock.getInputStream();
                os = sock.getOutputStream();
            } catch (IOException ex) {
                Logger.getLogger(OnlineServer.class.getName()).log(Level.SEVERE, null, ex);
            }           
            try {
                while ((ch = is.read()) != -1) {
                    switch (stage) {
                        case 0: 
                            if (ch == '\n') {
                                stage++;
                            } else {
                                name += (char) ch;
                            }
                            break;
                        case 1:
                            if (ch == '\n') {
                                stage++;
                                getsPlayer(name, Integer.parseInt(score));
                            } else {
                                score += (char) ch;
                            }
                            break;
                        case 2:
                            x = ((char) ch) - '0';
                            stage++;
                            break;
                        case 3:
                            y = ((char) ch) - '0';
                            stage = 2;
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
        String mess = Integer.toString(x) + Integer.toString(y);
        try {
            clientThreadArrayList.get(index).os.write(mess.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(OnlineServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getsPlayer(String name, int score) {
        game.addPlayer(name, score);
    }

    public void sendmes(String mess) throws IOException {
        for (int i = 0; i < clientThreadArrayList.size(); i++) {
            clientThreadArrayList.get(i).os.write(mess.getBytes());
        }
    }
    
    public void sendPlayer(String name, int score) throws IOException {
        String mess = name + "\n" + Integer.toString(score) + "\n"; 
        clientThreadArrayList.get(index).os.write(mess.getBytes());
    }
    
    @Override
    public void selectPlayer(int index) throws IOException {
        String mess = "e";
        this.index = index;
        for (int i = 0; i < clientThreadArrayList.size(); i++) {
            if (i != index) {
                clientThreadArrayList.get(i).os.write(mess.getBytes());
                clientThreadArrayList.get(i).interrupt();
            }
        }
        mess = "b";
        clientThreadArrayList.get(index).os.write(mess.getBytes());
        sendPlayer(game.player1, game.scoreP1);
    }
}
