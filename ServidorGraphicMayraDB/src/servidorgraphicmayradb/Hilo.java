/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorgraphicmayradb;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
/**
 *
 * @author abraham
 */
public class Hilo implements Runnable{
    private Thread t;
    private Socket cl;
    public Hilo(Socket cl) {
        t= new Thread(this, String.valueOf(this.hashCode()));
        this.cl=cl;
    }
    
    public void start(){
        this.t.start();
    }
    
   

    @Override
    public void run() {
        OperacionesDB BaseDatos=OperacionesDB.getInstance();
        
         try {
            PrintWriter pw=new PrintWriter(new  OutputStreamWriter(cl.getOutputStream()));
            BufferedReader br= new BufferedReader(new InputStreamReader(cl.getInputStream()));
            for(;;){
                      String msj=br.readLine();
                       if (msj.compareToIgnoreCase("salir")==0) { //si el cliente pone la palabra salir (ya sea con mayusc o minusc)
                           System.out.println("Cliente termina aplicacion");
                           br.close();
                           pw.close();
                           cl.close();
                           break;
                       }
                       else{
                           //System.out.println(msj);
                           BaseDatos.Ejemplo(msj);
                       }
                       
                 }
            
        } catch (Exception e) {
             System.out.println("no pude");
             e.printStackTrace();
        }
        
    }
    
}
