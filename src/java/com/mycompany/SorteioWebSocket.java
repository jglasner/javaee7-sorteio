/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@ServerEndpoint("/sorteioEndpoint")
public class SorteioWebSocket {

    private static List<String> nomes;

    public SorteioWebSocket() {
        this.nomes = new ArrayList<>();
    }

    @OnOpen
    public void onOpen(Session client) {
        System.out.println("Nomes size " + nomes.size());
        for (String s : nomes) {
            client.getAsyncRemote().sendText("nome " + s);
        }
    }

    @OnMessage
    public void onMessage(String data, Session client) {
        if (data.startsWith("nome ")) {
            String nome = data.substring(5);
            nomes.add(nome);
            System.out.println("Nome incluido: " + nome);
        } else if (data.equals("sortear")) {
            Random random = new Random();
            int sorteado_index = random.nextInt(nomes.size());
            String nome = nomes.get(sorteado_index);
            System.out.println("Sorteado: " + nome);
            try {
                client.getBasicRemote().sendText(nome);
            } catch (IOException ex) {
                Logger.getLogger(SorteioWebSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
