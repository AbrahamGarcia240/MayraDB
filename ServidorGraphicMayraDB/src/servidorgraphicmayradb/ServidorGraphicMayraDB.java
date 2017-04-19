/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package servidorgraphicmayradb;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;    
/**
 *
 * @author abraham
 */
public class ServidorGraphicMayraDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //aqui guardo los n hilos a usar
        HashMap <String, Hilo> Lista_Hilos= new  HashMap<String,Hilo>();
        // TODO code application logic here
        System.out.println("<=============== Servidor Mayra DB levantado===============> ");
        //creo un objeto singleton para que todos los hilos hagan operaciones sobre este objeto
        
         OperacionesDB BaseDatos=OperacionesDB.getInstance();
         //BaseDatos.Ejemplo();
         int pto=5000;
         try {
             ServerSocket s= new ServerSocket(pto);
             System.out.println("He iniciado el servicio, espero clientes...");
             while(true){
                Socket cl= s.accept();
                Lista_Hilos.put(String.valueOf(cl.getInetAddress())+String.valueOf(cl.getPort()), new Hilo(cl));
                Lista_Hilos.get(String.valueOf(cl.getInetAddress())+String.valueOf(cl.getPort())).start(); 
                System.out.println("Cliente conectado desde: "+cl.getInetAddress()+" : "+cl.getPort());
                
             }
               //System.exit(0);
        } catch (Exception e) {
             System.out.println("No se pudo crear el socket");
        }
        
    }
    
}
