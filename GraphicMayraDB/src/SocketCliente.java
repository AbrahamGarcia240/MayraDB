/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abraham
 */


import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SocketCliente {
    
    private InetAddress host;
    private int port;
    private PrintWriter pw;
    private byte[]b;
    private BufferedReader br1;
    private Socket cl;

    private static SocketCliente instancia= new SocketCliente();
    
    private SocketCliente() {
        
    }
    
    public static SocketCliente getInstance(){
        return instancia;
    }
    
    public void Init(InetAddress host, int port){
        this.host = host;
        this.port = port;
        try {
            Socket cl= new Socket(host, port);
             this.pw= new PrintWriter(new OutputStreamWriter(cl.getOutputStream())); //flujo de escritura para leer los mensajes
             this.br1= new BufferedReader(new InputStreamReader(cl.getInputStream()));
        } catch (IOException ex) {
            System.out.println("No he podido crear un socket");
        }
    }
    
    public void Envia(String dato){
        try {
                pw.println(dato);
                pw.flush();
        } catch (Exception e) {
            System.out.println("No pude enviar");
        }
    }
    
    public String Recibe(){
        String msj="no pude";
        try {
             msj=br1.readLine();
            
        } catch (IOException ex) {
            Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msj;
    }
    
    
    
    public void Desconectar(){
        try {
            this.pw.close();
            this.br1.close();
            this.cl.close();
            System.out.println("Socket terminado");
        } catch (IOException ex) {
            System.out.println("No he podido cerrar el socket");
        }
        catch(NullPointerException e)
        {
            System.out.println("");
        }
        
    }
    
    
    
}
