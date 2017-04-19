/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorgraphicmayradb;

/**
 *
 * @author abraham
 */
public class OperacionesDB {
    private static OperacionesDB instance=new OperacionesDB();
    
    protected OperacionesDB(){
        
    }
    
    public static OperacionesDB getInstance(){
        return instance;
    }
    
    
    protected static void Ejemplo(String msj){
        System.out.println("Soy el objeto OperacionesDB:"+instance.hashCode()+ "recibi:"+msj);
        
    }
    
    
}
