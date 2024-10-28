package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Il client è partito");

        try {
            Scanner scanner = new Scanner(System.in);

            Socket mySocket = new Socket("localhost", 3000);
            System.out.println("Il client si è collegato");

            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream())); 
            DataOutputStream out = new DataOutputStream(mySocket.getOutputStream()); 
            
            
            System.out.println("scrivi Esci per uscire\n Lista per visualizzare la lista\n Elimina per togliere un elemento \nInserisci una elemento da aggiungere alle note :");
            String messaggio;
            String rispostaRicevuta;
            do {
                rispostaRicevuta="";
                messaggio = scanner.nextLine();
                switch (messaggio) {
                    case "Lista":
                        out.writeBytes("?"+"\n");
                        rispostaRicevuta= in.readLine();
                        while (!rispostaRicevuta.equals("@")) {
                            System.out.println(rispostaRicevuta);
                            rispostaRicevuta=in.readLine();
                        };
                        System.out.println("Lista finita");
                        break;
                    case "Elimina":
                        out.writeBytes(messaggio+"\n");
                        System.out.println("inserisci il numero dell'elemento da togliere dalla lista");
                        messaggio=scanner.nextLine();
                        out.writeBytes(messaggio+"\n");
                        rispostaRicevuta=in.readLine();
                        if(rispostaRicevuta.equals("OK")){
                            System.out.println("Nota cancellata");
                        }else if(rispostaRicevuta.equals("errore")){
                            System.out.println("Nota non cancellata, riprova");
                        }
                        break;
                    case "Esci":
                        System.out.println("\nConnessione terminata");
                        out.writeBytes("!"+"\n");
                        break;
                
                    default:
                        out.writeBytes(messaggio+"\n");
                        rispostaRicevuta=in.readLine();
                        if(rispostaRicevuta.equals("OK")){
                            System.out.println("Nota Salvata");
                        }else{
                            System.out.println("Nota non salvata, riprova");
                        }
                        break;
                }
     
                 
             } while (!messaggio.equals("Esci"));
             mySocket.close();

        } catch (Exception e) {
            System.out.println("errore");
        }
    }
}

// 1. Si connette al server sulla porta 3000.
// 2. Invia una delle seguenti stringhe al server:
//    - Una nota da memorizzare (qualsiasi testo).
//    - Il carattere "?" per ricevere la lista delle note salvate.
//    - Il carattere "!" per terminare la connessione.
// 3. Se non ha inviato "!" aspetta una risposta dal server:
//    - Se ha inviato una nota, riceve il messaggio di conferma "OK".
//    - Se ha inviato "?", riceve la lista delle note (ogni nota su una linea separata) e, 
//al termine, una linea contenente solo il carattere "@".