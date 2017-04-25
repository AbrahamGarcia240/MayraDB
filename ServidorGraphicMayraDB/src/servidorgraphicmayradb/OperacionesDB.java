/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorgraphicmayradb;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import org.apache.commons.collections.map.MultiValueMap;

 
/**
 *
 * @author abraham
 */
public class OperacionesDB {
    static String enviar="";
    static Socket cl;
    static PrintWriter pw;
    static BufferedReader br;
    static String BaseActiva;
    static int pasoporinsert=0;
     static HashMap<String, HashMap<String, LinkedList<Object>>> MayraDB= new HashMap<>();
     static HashMap<String, Creador> x= new HashMap<>();
     static HashMap<String, Integer> enteros= new HashMap<>();
     static HashMap<String, Double> dobles= new HashMap<>();
     static HashMap<String, String> cadenas= new HashMap<>();
     static HashMap<String, MultiValueMap> metadatos= new HashMap<>();
    
    private static OperacionesDB instance=new OperacionesDB();
    
    protected OperacionesDB(){
        
    }
    
    public static OperacionesDB getInstance(){
        return instance;
    }
    
    
    public static String creaDB(String nombreDB){
        try {
            MayraDB.put(nombreDB, new HashMap<String, LinkedList<Object>>());
            enviar="base de datos creada :)";
            
            //pw.println(enviar);
            //pw.flush();
        } catch (Exception e) {
            System.out.println("No pude crear la base de datos. :(");
            enviar="No pude crear la base de datos";
            pw.println(enviar);
            pw.flush();
            
        }
        return enviar;
    }
    
       public static String seleccionaTuplasTabla(String nombreBD, String nombreTabla,String[] tuplas){
       Creador misc= new Creador("");
       String vector[] = null;
       if(MayraDB.containsKey(nombreBD)){
           if(MayraDB.get(nombreBD).containsKey(nombreTabla)){
               String tupla="";
               MayraDB.get(nombreBD).get(nombreTabla).stream()
                       .forEach(a->{
                           if(metadatos.get(nombreTabla).containsKey("entero")){
                           Collection<String> it= metadatos.get(nombreTabla).getCollection("entero");
                               for(int i=0;i<tuplas.length;i++){
                                   if(it.contains(tuplas[i])){
                                                              /*System.out.println("encontre doble "+ columnas.get(y));
                       dobles.put(columnas.get(y), Double.parseDouble(valores.get(y)));*/
                                           
                                            System.out.print("| "+tuplas[i]+" : "+misc.getterInteger(a, tuplas[i])+" |");
                                            enviar=enviar+"| "+tuplas[i]+" : "+misc.getterInteger(a, tuplas[i])+" |";
                                   }
                               }
                            // System.out.println("HAY ENTEROS");
                            }
                          
                           if(metadatos.get(nombreTabla).containsKey("dobles")){
                            // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("dobles");
                               for(int i=0;i<tuplas.length;i++){
                                   if(it.contains(tuplas[i])){
                                       System.out.print("| "+tuplas[i]+" : "+misc.getterDouble(a, tuplas[i])+" |");
                                       enviar=enviar+"| "+tuplas[i]+" : "+misc.getterDouble(a, tuplas[i])+" |";
                                   }
                               }

                                }
                           if(metadatos.get(nombreTabla).containsKey("cadena")){
                                // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("cadena");
                               for(int i=0;i<tuplas.length;i++){
                                   if(it.contains(tuplas[i])){
                                       System.out.print("| "+tuplas[i]+" : "+misc.getterVarchar(a, tuplas[i])+" |");
                                       enviar=enviar+"| "+tuplas[i]+" : "+misc.getterVarchar(a, tuplas[i])+" |";
                                   }
                               }
                            }
                           System.out.println(" ");
                           enviar=enviar+"_";
                       });
            //pw.println(enviar);
            //pw.flush();
            return enviar;
            //enviar="";
               
           }
           else{
               System.out.println("La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe");
            enviar="La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe";
            //pw.println(enviar);
            //pw.flush();
            return enviar;
           }
       }
       else{
            System.out.println("La base de datos "+nombreBD+" no existe");
                        enviar="La base de datos "+nombreBD+" no existe";
            //pw.println(enviar);
            //pw.flush();
            return enviar;
       }
   }
    public static String muestraDBs(){
        enviar="";
        if(MayraDB.size()>0){
            Set <String> DBS= MayraDB.keySet();
            DBS.stream()
            .forEach(a->enviar=enviar+a+"_");
                
                //pw.println(enviar);
                //pw.flush();
            return enviar;
        }
        else{
            System.out.println("Empty set");
            enviar="Empty Set ";
            //pw.println(enviar);
            //pw.flush();
            return enviar;
        }
    }
    
    public static String eliminaDB(String nombreDB){
        boolean loLogre=false;
        try {
            if(MayraDB.containsKey(nombreDB)){
                MayraDB.remove(nombreDB);
                enviar="base de datos removida correctamente ";
                //pw.println(enviar);
                //pw.flush();
                return enviar;
            }
            
        } catch (Exception e) {
            System.out.println("No puede eliminar la base de datos");
            enviar="No puede eliminar la base de datos";
            //pw.println(enviar);
            //pw.flush();
            //loLogre=false;
            return enviar;
        }
        //return loLogre;
        return enviar;
    }
    
    public static String agregaTabla(String nombreDB, String nombreTabla){
        boolean loLogre=false;
        try {
             x.put(nombreTabla, new Creador(nombreTabla));
             if(metadatos.get(nombreTabla).containsKey("entero")){
                // System.out.println("HAY ENTEROS");
                 Collection<String> it= metadatos.get(nombreTabla).getCollection("entero");
                 it.stream()
                         .forEach(a->x.get(nombreTabla).setInteger(a, false));
             }
             if(metadatos.get(nombreTabla).containsKey("dobles")){
                // System.out.println("HAY DOBLES");
                 Collection<String> it= metadatos.get(nombreTabla).getCollection("dobles");
                 it.stream()
                         .forEach(a->x.get(nombreTabla).setDouble(a, false));
             }
             if(metadatos.get(nombreTabla).containsKey("cadena")){
                 //System.out.println("HAY CADENAS");
                 Collection<String> it= metadatos.get(nombreTabla).getCollection("cadena");
                 it.stream()
                         .forEach(a->x.get(nombreTabla).setVarchar(a, false));
             }
             
            MayraDB.get(nombreDB).put(nombreTabla, new LinkedList<Object>());
             loLogre=true;
             enviar="Tabla creada correctamente :)";
             return enviar;
            //pw.println(enviar);
            //pw.flush();
        } catch (Exception e) {
            System.out.println("No pude crear la tabla");
            enviar="No pude crear la tabla :(";
            //pw.println(enviar);
            //pw.flush();
            return enviar;
           // loLogre=false;
        }
        //return loLogre;
    }
    
    public static String muestraTablas(String nombreDB){
        enviar="";
        if(MayraDB.get(nombreDB).size()>0){
            Set <String> Tablas= MayraDB.get(nombreDB).keySet();
            Tablas.stream()
                    .forEach(a->enviar=enviar+a+"_");
            //pw.println(enviar);
            //pw.flush();  
            return enviar;
        }
        else{
            System.out.println("La base de datos "+nombreDB+" aun no tiene tablas");
            enviar="La base de datos "+nombreDB+" aun no tiene tablas";
            //pw.println(enviar);
            //pw.flush();
            return enviar;
        }
    }
    
   public static String insertaEnTabla(String nombreDB, String nombreTabla, int elemento){
       boolean loLogre=false;
       pasoporinsert=1;
       try {
           if(MayraDB.containsKey(nombreDB)){
               if(MayraDB.get(nombreDB).containsKey(nombreTabla)){
                   //creo una nueva tupla
                    MayraDB.get(nombreDB).get(nombreTabla).add(x.get(nombreTabla).Finaliza());
                    Creador misc= new Creador("");
                    
                    if(enteros.size()>0){
                        enteros.forEach((k,v)->misc.setterInteger(MayraDB.get(nombreDB).get(nombreTabla).get(elemento), k, v));
                    }
                    
                    if(dobles.size()>0){
                        dobles.forEach((k,v)->misc.setterDouble(MayraDB.get(nombreDB).get(nombreTabla).get(elemento), k, v));
                    }
                    
                    if(cadenas.size()>0){
                        cadenas.forEach((k,v)->misc.setterVarchar(MayraDB.get(nombreDB).get(nombreTabla).get(elemento), k, v));
                    }
                   
                    loLogre=true;
                enviar="tupla insertada";
                return enviar;
                //pw.println(enviar);
                //pw.flush();
                   
               }
               else{
                   System.out.println("La tabla "+nombreTabla+ " @ "+ nombreDB+" no existe");
                   loLogre=false;
                enviar="La tabla "+nombreTabla+ " @ "+ nombreDB+" no existe";
                //pw.println(enviar);
                //pw.flush();
                return enviar;
               }
           }else{
               System.out.println("La base de datos "+nombreDB+" no existe");
               loLogre=false;
                enviar="La base de datos "+nombreDB+" no existe";
                //pw.println(enviar);
                //pw.flush();
                return enviar;
           }
       } catch (Exception e) {
           
           return "No pude insertar al elemento "+elemento+" en "+nombreTabla+" @ "+nombreDB;
       }
       
   }
   
   public static String seleccionaTodoTabla(String nombreBD, String nombreTabla){
       Creador misc= new Creador("");
              enviar="";
       if(MayraDB.containsKey(nombreBD)){
           if(MayraDB.get(nombreBD).containsKey(nombreTabla)){
               String tupla="";
               MayraDB.get(nombreBD).get(nombreTabla).stream()
                       .forEach(a->{
                           if(metadatos.get(nombreTabla).containsKey("entero")){
                            // System.out.println("HAY ENTEROS");
                            Collection<String> it= metadatos.get(nombreTabla).getCollection("entero");
                            /*it.stream()
                                .forEach(p->System.out.print("| "+p+" : "+misc.getterInteger(a, p)+" |"));*/
                             it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterInteger(a, p)+" |");
                            }
                          
                           if(metadatos.get(nombreTabla).containsKey("dobles")){
                            // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("dobles");
                                /*it.stream()
                                .forEach(p->System.out.print("| "+p+" : "+misc.getterDouble(a, p)+" |"));*/
                                 it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterDouble(a, p)+" |");
                                }
                           if(metadatos.get(nombreTabla).containsKey("cadena")){
                                // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("cadena");
                                /*it.stream()
                                    .forEach(p->System.out.print("| "+p+" : "+misc.getterVarchar(a, p)+" |"));*/
                                 it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterVarchar(a, p)+" |");
                            }
                           enviar=enviar+"_";
                           System.out.println(" ");
                       });
                //pw.println(enviar);
               //pw.flush();
               return enviar;
               //enviar="";
               
           }
           else{
               System.out.println("La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe");
               enviar="La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe";
               //pw.println(enviar);
               //pw.flush();
               return enviar;
           }
       }
       else{
            System.out.println("La base de datos "+nombreBD+" no existe");
            enviar="La base de datos "+nombreBD+" no existe";
            //pw.println(enviar);
            //pw.flush();
            return enviar;
       }
   }
   
   public static String seleccionaConCondicionIgualA(String nombreBD, String nombreTabla ,String tipoDatoComparacion, String NombreCampoCondicion, Object condicion){
       Creador misc= new Creador("");
       enviar="";
       if(MayraDB.containsKey(nombreBD)){
           if(MayraDB.get(nombreBD).containsKey(nombreTabla)){
               String tupla="";
               if(tipoDatoComparacion.compareToIgnoreCase("entero")==0){
                   MayraDB.get(nombreBD).get(nombreTabla).stream()
                        .filter(a->misc.getterInteger(a, NombreCampoCondicion)==(Integer)condicion)
                       .forEach(a->{
                           if(metadatos.get(nombreTabla).containsKey("entero")){
                            // System.out.println("HAY ENTEROS");
                            Collection<String> it= metadatos.get(nombreTabla).getCollection("entero");
                            /*it.stream()
                                .forEach(p->System.out.print("| "+p+" : "+misc.getterInteger(a, p)+" |"));
                            }*/
                            it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterInteger(a, p)+" |");
                            }
                            //enviar="La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe";
                           if(metadatos.get(nombreTabla).containsKey("dobles")){
                            // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("dobles");
                                /*it.stream()
                                .forEach(p->System.out.print("| "+p+" : "+misc.getterDouble(a, p)+" |"));
                                }*/
                            it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterDouble(a, p)+" |");
                            }                           
                           if(metadatos.get(nombreTabla).containsKey("cadena")){
                                // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("cadena");
                                /*it.stream()
                                    .forEach(p->System.out.print("| "+p+" : "+misc.getterVarchar(a, p)+" |"));*/
                                it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterVarchar(a, p)+" |");                          
                            }
                           enviar=enviar+"_";
                           System.out.println(" ");
                       });
               }
               else if(tipoDatoComparacion.compareToIgnoreCase("dobles")==0){
                   MayraDB.get(nombreBD).get(nombreTabla).stream()
                        .filter(a->misc.getterDouble(a, NombreCampoCondicion).equals((Double)condicion))
                       .forEach(a->{
                           if(metadatos.get(nombreTabla).containsKey("entero")){
                            // System.out.println("HAY ENTEROS");
                            Collection<String> it= metadatos.get(nombreTabla).getCollection("entero");
                            /*it.stream()
                                .forEach(p->System.out.print("| "+p+" : "+misc.getterInteger(a, p)+" |"));
                            }*/
                            it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterInteger(a, p)+" |");
                            }
                            //enviar="La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe";
                           if(metadatos.get(nombreTabla).containsKey("dobles")){
                            // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("dobles");
                                /*it.stream()
                                .forEach(p->System.out.print("| "+p+" : "+misc.getterDouble(a, p)+" |"));
                                }*/
                            it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterDouble(a, p)+" |");
                            }                           
                           if(metadatos.get(nombreTabla).containsKey("cadena")){
                                // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("cadena");
                                /*it.stream()
                                    .forEach(p->System.out.print("| "+p+" : "+misc.getterVarchar(a, p)+" |"));*/
                                it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterVarchar(a, p)+" |");                          
                            }
                           enviar=enviar+"_";
                           System.out.println(" ");
                       });
               }
               else if(tipoDatoComparacion.compareToIgnoreCase("cadena")==0){
                   MayraDB.get(nombreBD).get(nombreTabla).stream()
                        .filter(a->misc.getterVarchar(a, NombreCampoCondicion).equals((String)condicion))
                       .forEach(a->{
                           if(metadatos.get(nombreTabla).containsKey("entero")){
                            // System.out.println("HAY ENTEROS");
                            Collection<String> it= metadatos.get(nombreTabla).getCollection("entero");
                            it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterInteger(a, p)+" |");
                            }
                          
                           if(metadatos.get(nombreTabla).containsKey("dobles")){
                            // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("dobles");
                                it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterDouble(a, p)+" |");
                                }
                           if(metadatos.get(nombreTabla).containsKey("cadena")){
                                // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("cadena");
                                it.stream()
                                    .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterVarchar(a, p)+" |");
                            }
                           enviar=enviar+"_";
                           System.out.println(" ");
                       });
               }
               System.out.println("enviar tiene "+enviar);
               //pw.println(enviar);
               //pw.flush();
               return enviar;
               
           }
           else{
               System.out.println("La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe");
               enviar="La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe";
               //pw.println(enviar);
               //pw.flush();
               return enviar;
           }
       }
       else{
            System.out.println("La base de datos "+nombreBD+" no existe");
            enviar="La base de datos "+nombreBD+" no existe";
            //pw.println(enviar);
            //pw.flush();
            return enviar;
       }
   }
   
    public static String seleccionaConCondicionDistintaA(String nombreBD, String nombreTabla ,String tipoDatoComparacion, String NombreCampoCondicion, Object condicion){
       Creador misc= new Creador("");
       enviar="";
       if(MayraDB.containsKey(nombreBD)){
           if(MayraDB.get(nombreBD).containsKey(nombreTabla)){
               String tupla="";
               if(tipoDatoComparacion.compareToIgnoreCase("entero")==0){
                   MayraDB.get(nombreBD).get(nombreTabla).stream()
                        .filter(a->misc.getterInteger(a, NombreCampoCondicion)!=(Integer)condicion)
                       .forEach(a->{
                           if(metadatos.get(nombreTabla).containsKey("entero")){
                            // System.out.println("HAY ENTEROS");
                            Collection<String> it= metadatos.get(nombreTabla).getCollection("entero");
                            it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterInteger(a, p)+" |");
                            }
                          
                           if(metadatos.get(nombreTabla).containsKey("dobles")){
                            // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("dobles");
                                it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterDouble(a, p)+" |");
                                }
                           if(metadatos.get(nombreTabla).containsKey("cadena")){
                                // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("cadena");
                                it.stream()
                                    .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterVarchar(a, p)+" |");
                            }
                           System.out.println(" ");
                           enviar=enviar+" ";
                       });
               }
               else if(tipoDatoComparacion.compareToIgnoreCase("dobles")==0){
                   MayraDB.get(nombreBD).get(nombreTabla).stream()
                        .filter(a->!(misc.getterDouble(a, NombreCampoCondicion).equals((Double)condicion)))
                       .forEach(a->{
                           if(metadatos.get(nombreTabla).containsKey("entero")){
                            // System.out.println("HAY ENTEROS");
                            Collection<String> it= metadatos.get(nombreTabla).getCollection("entero");
                            it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterInteger(a, p)+" |");
                            }
                          
                           if(metadatos.get(nombreTabla).containsKey("dobles")){
                            // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("dobles");
                                it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterDouble(a, p)+" |");
                                }
                           if(metadatos.get(nombreTabla).containsKey("cadena")){
                                // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("cadena");
                                it.stream()
                                    .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterVarchar(a, p)+" |");
                            }
                           System.out.println(" ");
                           enviar=enviar+" ";
                       });
               }
               else if(tipoDatoComparacion.compareToIgnoreCase("cadena")==0){
                   MayraDB.get(nombreBD).get(nombreTabla).stream()
                        .filter(a->!(misc.getterVarchar(a, NombreCampoCondicion).equals((String)condicion)))
                       .forEach(a->{
                           if(metadatos.get(nombreTabla).containsKey("entero")){
                            // System.out.println("HAY ENTEROS");
                            Collection<String> it= metadatos.get(nombreTabla).getCollection("entero");
                            it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterInteger(a, p)+" |");
                            }
                          
                           if(metadatos.get(nombreTabla).containsKey("dobles")){
                            // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("dobles");
                                it.stream()
                                .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterDouble(a, p)+" |");
                                }
                           if(metadatos.get(nombreTabla).containsKey("cadena")){
                                // System.out.println("HAY ENTEROS");
                                Collection<String> it= metadatos.get(nombreTabla).getCollection("cadena");
                                it.stream()
                                    .forEach(p->enviar=enviar+"| "+p+" : "+misc.getterVarchar(a, p)+" |");
                            }
                           System.out.println(" ");
                       });
               }
               
               return enviar;
               
           }
           else{
               System.out.println("La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe");
               return "La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe";
           }
       }
       else{
            System.out.println("La base de datos "+nombreBD+" no existe");
            return "La base de datos "+nombreBD+" no existe";
       }
   }
   public static String describeTabla(String nombreBD, String nombreTabla){
       Creador misc= new Creador("");
       if(pasoporinsert==0){
               x.get(nombreTabla).Imprime();
               return "";
       }
       else{
        if(MayraDB.containsKey(nombreBD)){
            if(MayraDB.get(nombreBD).containsKey(nombreTabla)){
                System.out.println(misc.getterVarchar(MayraDB.get(nombreBD).get(nombreTabla).get(0), "Constraint"));
                enviar=misc.getterVarchar(MayraDB.get(nombreBD).get(nombreTabla).get(0), "Constraint");
                //pw.println(enviar);
                //pw.flush();
                return enviar;
            }
            else{
                System.out.println("La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe");
                enviar="La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe";
                //pw.println(enviar);
                //pw.flush();
                return enviar;
            }
        }
        else{
            System.out.println("La base de datos "+nombreBD+" no existe");
            enviar="La base de datos "+nombreBD+" no existe";
                //pw.println(enviar);
               //pw.flush();
            return enviar;
        }
       }
   }
   
   public static String actualizaTablaConIgual(String nombreBD, String nombreTabla, String tipoDatoComparacion, String NombreCampoCondicion, Object condicion, String tipoDatoDestino, String nombreDatoDestino, Object destino){
       boolean loLogre= false;
        Creador misc= new Creador("");
       if(MayraDB.containsKey(nombreBD)){
           if(MayraDB.get(nombreBD).containsKey(nombreTabla)){
               if(tipoDatoComparacion.compareToIgnoreCase("entero")==0){
                    MayraDB.get(nombreBD).get(nombreTabla).stream()
                            .filter(a->misc.getterInteger(a,NombreCampoCondicion )==(Integer)condicion)
                            .forEach(a->{
                                if(tipoDatoDestino.compareToIgnoreCase("entero")==0){
                                    misc.setterInteger(a, nombreDatoDestino,(Integer)destino);
                                 } 
                                if(tipoDatoDestino.compareToIgnoreCase("dobles")==0){
                                    misc.setterDouble(a, nombreDatoDestino,(Double)destino);
                                 }
                                if(tipoDatoDestino.compareToIgnoreCase("cadena")==0){
                                    misc.setterVarchar(a, nombreDatoDestino,(String)destino);
                                 }
                            });
                    
               }
               if(tipoDatoComparacion.compareToIgnoreCase("dobles")==0){
                    MayraDB.get(nombreBD).get(nombreTabla).stream()
                            .filter(a->misc.getterDouble(a,NombreCampoCondicion )==(Double)condicion)
                            .forEach(a->{
                                if(tipoDatoDestino.compareToIgnoreCase("entero")==0){
                                    misc.setterInteger(a, nombreDatoDestino,(Integer)destino);
                                 } 
                                if(tipoDatoDestino.compareToIgnoreCase("dobles")==0){
                                    misc.setterDouble(a, nombreDatoDestino,(Double)destino);
                                 }
                                if(tipoDatoDestino.compareToIgnoreCase("cadena")==0){
                                    misc.setterVarchar(a, nombreDatoDestino,(String)destino);
                                 }
                            });
               
               }
               if(tipoDatoComparacion.compareToIgnoreCase("caracter")==0){
                    MayraDB.get(nombreBD).get(nombreTabla).stream()
                            .filter(a->misc.getterVarchar(a,NombreCampoCondicion )==(String)condicion)
                            .forEach(a->{
                                if(tipoDatoDestino.compareToIgnoreCase("entero")==0){
                                    misc.setterInteger(a, nombreDatoDestino,(Integer)destino);
                                 } 
                                if(tipoDatoDestino.compareToIgnoreCase("dobles")==0){
                                    misc.setterDouble(a, nombreDatoDestino,(Double)destino);
                                 }
                                if(tipoDatoDestino.compareToIgnoreCase("cadena")==0){
                                    misc.setterVarchar(a, nombreDatoDestino,(String)destino);
                                 }
                            });
               }
               loLogre=true;
                enviar="Modificación exitosa";
                //pw.println(enviar);
                //pw.flush();
                return enviar;
           }
           else{
               System.out.println("La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe");
               loLogre=false;
               return "La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe";
               //pw.println(enviar);
               //pw.flush();
           }
       }
       else{
           System.out.println("La base de datos "+nombreBD+" no existe");
           loLogre=false;
           return"La base de datos "+nombreBD+" no existe";
           //pw.println(enviar);
           //pw.flush();
       }
      
    
   }
   
   public static String eliminaDeTabla(String nombreBD, String nombreTabla, String tipoDatoComparacion, String NombreCampoCondicion, Object condicion){
       boolean loLogre=false;
        Creador misc= new Creador("");
       if(MayraDB.containsKey(nombreBD)){
           if(MayraDB.get(nombreBD).containsKey(nombreTabla)){
               if(tipoDatoComparacion.compareToIgnoreCase("entero")==0){
                    MayraDB.get(nombreBD).replace(nombreTabla, MayraDB.get(nombreBD).get(nombreTabla),(LinkedList)MayraDB.get(nombreBD).get(nombreTabla).stream()
                            .filter(a->misc.getterInteger(a,NombreCampoCondicion )!=(Integer)condicion)
                            .collect(Collectors.toCollection(LinkedList::new))
                    );
               }
               if(tipoDatoComparacion.compareToIgnoreCase("dobles")==0){
                    MayraDB.get(nombreBD).replace(nombreTabla, MayraDB.get(nombreBD).get(nombreTabla),(LinkedList)MayraDB.get(nombreBD).get(nombreTabla).stream()
                            .filter(a->misc.getterDouble(a,NombreCampoCondicion )!=(Double)condicion)
                            .collect(Collectors.toCollection(LinkedList::new))
                    );
                 }
               
             
               if(tipoDatoComparacion.compareToIgnoreCase("caracter")==0){
                     MayraDB.get(nombreBD).replace(nombreTabla, MayraDB.get(nombreBD).get(nombreTabla),(LinkedList)MayraDB.get(nombreBD).get(nombreTabla).stream()
                            .filter(a->misc.getterVarchar(a,NombreCampoCondicion )!=(String)condicion)
                            .collect(Collectors.toCollection(LinkedList::new))
                    );
               }
               loLogre=true;
               return "eliminacion completa";
                //pw.println(enviar);
                //pw.flush();               
             }
           else{
               System.out.println("La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe");
               loLogre=false;
               return "La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe";
               // pw.println(enviar);
                //pw.flush();
           }
       }
       else{
           System.out.println("La base de datos "+nombreBD+" no existe");
           loLogre=false;
           return "La base de datos "+nombreBD+" no existe";
           //pw.println(enviar);
           //pw.flush();
       }
       
      
   }
   
   public static String eliminaTabla(String nombreBD, String nombreTabla){
       boolean loLogre=false;
       if(MayraDB.containsKey(nombreBD)){
           if(MayraDB.get(nombreBD).containsKey(nombreTabla)){
               MayraDB.get(nombreBD).remove(nombreTabla);
               x.remove(nombreTabla);
               loLogre=true;  
                return "Tabla eliminada correctamente ";
               // pw.println(enviar);
                //pw.flush();              
             }
           else{
               System.out.println("La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe");
                return enviar="La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe";
                //pw.println(enviar);
                //pw.flush(); 
               //loLogre=false;
           }
       }
       else{
           System.out.println("La base de datos "+nombreBD+" no existe");
           loLogre=false;
           return "La base de datos "+nombreBD+" no existe";
                //pw.println(enviar);
                //pw.flush(); 
       }
       
      
   }
   
   public static boolean eliminaBD(String nombreBD){
       boolean loLogre=false;
        if(MayraDB.containsKey(nombreBD)){
            MayraDB.remove(nombreBD);
        }
        else{
            System.out.println("La base de datos "+nombreBD+" no existe en esta sesion de MayraDB");
        }
       return loLogre;
   }
   
    public static boolean isNumeric(String str) {
        int num;
        try{
           num=Integer.parseInt(str); 
        } catch(Exception e){
            return false;
        }
        return true;
    }
    public static boolean isEntero(String str){ 
        int entero; 
        
        boolean esEntero = true; 
        try{ 
            double aDouble = Double.parseDouble(str);
            //entero = (int) aDouble; 
        }catch(Exception e){ 
            esEntero = false; 
        } 
        return esEntero; 
    }
    public static long seleccionaTodoCount(String nombreBD, String nombreTabla){
       Creador misc= new Creador("");
       long count=0;
       if(MayraDB.containsKey(nombreBD)){
           if(MayraDB.get(nombreBD).containsKey(nombreTabla)){
               
               count=MayraDB.get(nombreBD).get(nombreTabla).stream()
                       .count();
               
           }
           else{
               System.out.println("La tabla "+nombreTabla+ " @ "+ nombreBD+" no existe");
           }
       }
       else{
            System.out.println("La base de datos "+nombreBD+" no existe");
       }
       return count;
   }
    
    protected static String Palabra(String cad){
        //System.out.println("Soy el objeto OperacionesDB:"+instance.hashCode()+ " recibi:"+msj);
        
        String path="";
        String nomtabla;
        StringTokenizer tokens=new StringTokenizer(cad);
        try {
            path=tokens.nextToken();
        } catch (Exception e) {
            return "Usted tiene un error de sintaxis";
            //pw.println(enviar);
            //pw.flush();
        }
        if(path.compareToIgnoreCase("create")==0){
            path=tokens.nextToken();
            if(path.compareToIgnoreCase("database")==0){ 
                path=tokens.nextToken();
                StringTokenizer pc=new StringTokenizer(path, ";");
                path=pc.nextToken();
                System.out.println("se quiere crear la base de datos "+path); 
                return creaDB(path);
                //int control=0;
                        //tabla[0].put("",t1);
                /*for(int i=0;i<conthash;i++){
                    if(elementostabla.get(i).equals(path)){
                        System.out.println("base de datos ya creada");
                        control=1;
                    }
                }
                if(control==0){
                    elementostabla.add(path);
                    conthash=conthash+1;
                }*/
                
            }
            else if(path.compareToIgnoreCase("table")==0){
                path=tokens.nextToken();
                StringTokenizer lla=new StringTokenizer(path, "("); 
                path=lla.nextToken();
                nomtabla=path;
                int inicio = cad.indexOf("(");
                int fin = cad.indexOf(";", inicio + 1);
                String operar=cad.substring(inicio + 1, fin);
                ArrayList<String> nombre = new ArrayList<String>();
                ArrayList<String> tipo = new ArrayList<String>();
                ArrayList<String> restriccion = new ArrayList<String>();
                System.out.println(operar);
                int i=1,j=1;
                String vector[]= operar.split(",");
                for(i=0;i<vector.length;i++){
                    System.out.println(vector[i]);
                }
                String restri="";
                for(i=0;i<vector.length;i++){
                String vector2[]= vector[i].split(" ");
                    for(j=1;j<vector2.length;j++){
                        if(j==1){
                            System.out.println("es el nombre "+vector2[j]);
                            vector2[j].trim();
                            nombre.add(vector2[j]);
                        }
                        else if(j==2){
                            
                            vector2[j].trim();
                            System.out.println("es el tipo "+vector2[j]);
                            if(vector2[j].substring(0,1).equals("v")||vector2[j].substring(0,1).equals("V")){
                                inicio = vector2[j].indexOf("(");
                                vector2[j] =vector2[j].substring(0, inicio);  
                            }
                            tipo.add(vector2[j]);
                            System.out.println("es el tipo "+vector2[j]);
                        }
                        if(j>2){
                            System.out.println("es la restriccion "+vector2[j]);
                            vector2[j].trim(); 
                            restri=restri+vector2[j]+" ";
                        }
                        if(vector2.length<=2){
                            restri="NULL";
                        }
                    }
                    restriccion.add(restri);
                    restri="";
                }
                System.out.println("se quiere crear una tabla"+nomtabla+" con los siguientes parametros ");
                metadatos.put(nomtabla, new MultiValueMap());
                int x=nombre.size();
                for(i=0;i<x;i++){
                    try {
                        System.out.println("name "+nombre.get(i));
                        System.out.println("tipo "+tipo.get(i));
                        System.out.println("restriccion "+restriccion.get(i));
                    } catch (Exception e) {
                        return "Usted tiene un error de sintaxis";
                        //pw.println(enviar);
                        //pw.flush();
                    }
                } 
                for(i=0;i<x;i++){
                    if(tipo.get(i).compareToIgnoreCase("integer")==0){
                        metadatos.get(nomtabla).put("entero",nombre.get(i));
                    }
                    else if(tipo.get(i).compareToIgnoreCase("varchar")==0){
                        metadatos.get(nomtabla).put("cadena",nombre.get(i));
                       
                    }
                    else if(tipo.get(i).compareToIgnoreCase("double")==0){
                        metadatos.get(nomtabla).put("dobles",nombre.get(i));
                    }
                }
                return(agregaTabla(BaseActiva,nomtabla));           
            }
        }
        else if(path.compareToIgnoreCase("update")==0){
            path=tokens.nextToken();
            String salida="",salida1="";
            String nombretabla="",columna="",operador="",valorcambiado="",columnacondicion="",valorcondicion="",operadorcondicion="";
            int valorcambiadoint=-1,valorcondicionint=-1;
            double valorcambiadodouble=-1,valorcondiciondouble=-1;
            nombretabla=path;
            path=tokens.nextToken();
            if(path.compareToIgnoreCase("set")==0){
                path=tokens.nextToken();
                columna=path;
                System.out.println("la columna es"+path);
                path=tokens.nextToken();
                operador=path;
                System.out.println("el operador es"+path);
                path=tokens.nextToken();
                valorcambiado=path;
                System.out.println("el valorcambiado es"+path);

               if (isNumeric(valorcambiado)==false){
                    valorcambiado=valorcambiado.substring(1,valorcambiado.length()-1);
                    System.out.println(valorcambiado);
                    salida="cadena";
               }
                else if(isEntero(valorcambiado)==false){
                    valorcambiadodouble=Double.parseDouble(valorcambiado);
                     salida="dobles";
                }
                else{
                    valorcambiadoint=Integer.parseInt(valorcambiado);
                    salida="entero";
                }
                path=tokens.nextToken();
                if(path.compareToIgnoreCase("where")==0){
                    path=tokens.nextToken();
                    columnacondicion=path;
                    System.out.println("la columna condicion es"+path);
                    path=tokens.nextToken();
                    operadorcondicion=path;
                    System.out.println("el operador condicion es"+path);
                    path=tokens.nextToken();
                    valorcondicion=path;
                    System.out.println("el valor condicion es "+path);
                    valorcondicion=valorcondicion.substring(0,valorcondicion.length()-1);
               if (isNumeric(valorcondicion)==false){
                    valorcondicion=valorcondicion.substring(1,valorcondicion.length()-1);
                    System.out.println("de aqui"+valorcondicion);
                    salida1="cadena";
               }
                else if(isEntero(valorcondicion)==false){
                    valorcondiciondouble=Double.parseDouble(valorcondicion);
                     salida1="dobles";
                }
                else{
                    valorcondicionint=Integer.parseInt(valorcondicion);
                    salida1="entero";
                }
                }
            }
            System.out.println("nombretabla "+nombretabla+ "salida1"+ salida1 +"columna condicion "+columnacondicion + " valor condicion "+valorcondicion+" salida "+ salida +" columna "+columna+" valor cambiado "+valorcambiado);
            if(salida.equalsIgnoreCase("cadena")){
                if(salida1.equalsIgnoreCase("cadena")){
                    return actualizaTablaConIgual(BaseActiva,nombretabla,salida1,columnacondicion,valorcondicion,salida,columna, valorcambiado);                    
                }
                if(salida1.equalsIgnoreCase("dobles")){
                     return actualizaTablaConIgual(BaseActiva,nombretabla,salida1,columnacondicion,valorcondiciondouble,salida,columna, valorcambiado);
                }
                if(salida1.equalsIgnoreCase("entero")){
                     return actualizaTablaConIgual(BaseActiva,nombretabla,salida1,columnacondicion,valorcondicionint,salida,columna, valorcambiado);
                }
            }
            if(salida.equalsIgnoreCase("dobles")){
                if(salida1.equalsIgnoreCase("cadena")){
                     return actualizaTablaConIgual(BaseActiva,nombretabla,salida1,columnacondicion,valorcondicion,salida,columna, valorcambiadodouble);
                }
                if(salida1.equalsIgnoreCase("dobles")){
                     return actualizaTablaConIgual(BaseActiva,nombretabla,salida1,columnacondicion,valorcondiciondouble,salida,columna, valorcambiadodouble);
                }
                if(salida1.equalsIgnoreCase("entero")){
                     return actualizaTablaConIgual(BaseActiva,nombretabla,salida1,columnacondicion,valorcondicionint,salida,columna, valorcambiadodouble);              
                }
            }
            if(salida.equalsIgnoreCase("entero")){
                if(salida1.equalsIgnoreCase("cadena")){
                      return actualizaTablaConIgual(BaseActiva,nombretabla,salida1,columnacondicion,valorcondicion,salida,columna, valorcambiadoint);
                }
                if(salida1.equalsIgnoreCase("dobles")){
                    return actualizaTablaConIgual(BaseActiva,nombretabla,salida1,columnacondicion,valorcondiciondouble,salida,columna, valorcambiadoint);                    
                }
                if(salida1.equalsIgnoreCase("entero")){
                     return actualizaTablaConIgual(BaseActiva,nombretabla,salida1,columnacondicion,valorcondicionint,salida,columna, valorcambiadoint);                    
                }
            }
        //System.out.println("UPDATE SOCIO SET VENTAS=180 WHERE ID=2;");
        //actualizaTablaConIgual("Sams", "Socio", "entero", "ID", 2, "entero", "Ventas", 100);
          
        }
        else if(path.compareToIgnoreCase("insert")==0){
            path=tokens.nextToken();
            String vector[] = null;
            int i;
            ArrayList<String> columnas = new ArrayList<String>();
            ArrayList<String> valores = new ArrayList<String>();
            String nombretabla="";
            if(path.compareToIgnoreCase("into")==0){
              path=tokens.nextToken();
              nombretabla=path;
                int inicio = cad.indexOf("(");
                int fin = cad.indexOf(")", inicio + 1); 
                String operar=cad.substring(inicio + 1, fin); 
                inicio = cad.indexOf(";", inicio + 1)-1; 
                String aux=cad.substring(fin+10,inicio);
                System.out.println(aux);
                System.out.println(operar);
                vector= operar.split(",");
                for(i=0;i<vector.length;i++){
                    System.out.println(vector[i]);
                    columnas.add(vector[i]);
                }
                path=tokens.nextToken();
                path=tokens.nextToken();
                if(path.compareToIgnoreCase("values")==0){
                    vector= aux.split(",");
                    for(i=0;i<vector.length;i++){
                        System.out.println(vector[i]);
                        if (isNumeric(vector[i])==false){
                            vector[i]=vector[i].substring(1,vector[i].length()-1);
                        }
                        valores.add(vector[i]);
                        //System.out.println("ya lo asigne: "+vector[i]);
                    }                    
                }
            }
            System.out.println("quiere insertar dentro de"+ nombretabla+ "en la columnas: ");
            enteros.clear();
            dobles.clear();
            cadenas.clear();
            if(metadatos.get(nombretabla).containsKey("entero")){
                // System.out.println("HAY ENTEROS");
                 Collection<String> it= metadatos.get(nombretabla).getCollection("entero");
                 for(int y=0;y<columnas.size();y++){
                     if(it.contains(columnas.get(y))){
                       System.out.println("encontre entero "+ columnas.get(y));
                       enteros.put(columnas.get(y), Integer.parseInt(valores.get(y)));
                     }
                     //enteros.put(it.equals(columnas.get(i)));   
                 }
             }
             if(metadatos.get(nombretabla).containsKey("dobles")){
                // System.out.println("HAY DOBLES");
                 Collection<String> it= metadatos.get(nombretabla).getCollection("dobles");
                 for(int y=0;y<columnas.size();y++){
                     if(it.contains(columnas.get(y))){
                       System.out.println("encontre doble "+ columnas.get(y));
                       dobles.put(columnas.get(y), Double.parseDouble(valores.get(y)));
                     }
                     //enteros.put(it.equals(columnas.get(i)));   
                 }
             }
             if(metadatos.get(nombretabla).containsKey("cadena")){
                 //System.out.println("HAY CADENAS");
                 Collection<String> it= metadatos.get(nombretabla).getCollection("cadena");
                 for(int y=0;y<columnas.size();y++){
                     if(it.contains(columnas.get(y))){
                       System.out.println("encontre cadena "+ columnas.get(y));
                       cadenas.put(columnas.get(y), valores.get(y));
                     }
                     //enteros.put(it.equals(columnas.get(i)));   
                 }             
             }
            if(pasoporinsert==1){
            long er=seleccionaTodoCount(BaseActiva,nombretabla);
                        return insertaEnTabla(BaseActiva,nombretabla,(int)er);
            }
            else{
               return insertaEnTabla(BaseActiva,nombretabla,(int)0);   
            }
        }
        else if(path.compareToIgnoreCase("delete")==0){
            String salida="";
            path=tokens.nextToken();
            String nombretabla="",columnaacomparar="",condicion="",operador="";
            int value=-1;
            if(path.compareToIgnoreCase("from")==0){
                path=tokens.nextToken();
                nombretabla=path;
                System.out.println("quiere borrar de la tabla "+nombretabla);
                int inicio = cad.indexOf(nombretabla)+nombretabla.length()-2;
                int fin = cad.indexOf(";", inicio + 1); 
                String operar=cad.substring(inicio + 1, fin);
                if(operar.length()==0){
                    System.out.println("solo es eso");
                    value=0;
                    
                }
                else{
                    path=tokens.nextToken();
                    System.out.println(path);  
                    if(path.compareToIgnoreCase("where")==0){
                        path=tokens.nextToken();
                        columnaacomparar=path;
                        System.out.println(path);
                        path=tokens.nextToken();
                        operador=path;
                        System.out.println(path);
                        inicio = cad.indexOf(operador)+operador.length();
                        fin = cad.indexOf(";", inicio + 1); 
                        condicion=cad.substring(inicio+1, fin);
                         if (isNumeric(condicion)==false){
                            condicion=condicion.substring(1,condicion.length()-1);
                            System.out.println(condicion);
                          salida="cadena";
                            System.out.println("quiero borrar de la tabla "+ nombretabla+" cuando la tupla "+columnaacomparar+" se le aplica el siguiente operador "+ operador + " para que cumpla la condicion "+condicion);
                                    //System.out.println("DELETE FROM SOCIO WHERE ID=1;");
                            value=1;
                            return eliminaDeTabla(BaseActiva,nombretabla,salida,columnaacomparar, condicion);                          
                        }
                         else if(isEntero(condicion)==false){
                                double num=Double.parseDouble(condicion);
                              salida="dobles";
                            System.out.println("quiero borrar de la tabla "+ nombretabla+" cuando la tupla "+columnaacomparar+" se le aplica el siguiente operador "+ operador + " para que cumpla la condicion "+condicion);
                                    //System.out.println("DELETE FROM SOCIO WHERE ID=1;");
                             value=1;
                            return eliminaDeTabla(BaseActiva,nombretabla,salida,columnaacomparar, num);                               
                         }
                         else{
                             int num=Integer.parseInt(condicion);
                             salida="entero";
                             System.out.println("quiero borrar de la tabla "+ nombretabla+" cuando la tupla "+columnaacomparar+" se le aplica el siguiente operador "+ operador + " para que cumpla la condicion "+condicion);
                        //System.out.println("DELETE FROM SOCIO WHERE ID=1;");
                            value=1;
                            return eliminaDeTabla(BaseActiva,nombretabla,salida,columnaacomparar, num); 
                         }
                        
                    }
                    else{
                        return enviar="Usted tiene un error de sintaxis";
                        //pw.println(enviar);
                        //pw.flush();
                    }
                }
            }
            if(value==0){
                System.out.println("quiero borrar todo lo que hay en la tabla "+ nombretabla);                
            }
          
        }
        else if(path.compareToIgnoreCase("select")==0){
            path=tokens.nextToken();
            String validador="",resultante="";
            String tablachida="",salida="";
            String vector[] = null;
            int i;
            System.out.println("se quiere seleccionar algo");
            ArrayList<String> columnas = new ArrayList<String>();
            if(path.compareToIgnoreCase("*")==0){
                path=tokens.nextToken();
                if(path.compareToIgnoreCase("from")==0){
                int inicio = cad.indexOf("from ")+4;
                //int fin = cad.indexOf(";", inicio + 1); 
                //tablachida=cad.substring(inicio + 1, fin);
                path=tokens.nextToken();
                tablachida=path;
                char simon=';';
                if (tablachida.indexOf(simon)==-1){
                    path=tokens.nextToken();
                    if(path.compareToIgnoreCase("where")==0){
                        System.out.println("llegue aqui");
                        path=tokens.nextToken();
                        validador=path;
                         path=tokens.nextToken();
                         if(path.compareToIgnoreCase("=")==0){
                             path=tokens.nextToken();
                             resultante=path;
                             resultante=resultante.substring(0,resultante.length()-1);
                             System.out.println("el resultante es "+resultante);
                             if (isNumeric(resultante)==false){
                             resultante=resultante.substring(1,resultante.length()-1);
                              salida="cadena";
                              System.out.println("llege aqui y se suna cadena");
                              System.out.println(" La tabla es "+ tablachida+" la salida es "+salida+" el validador es "+ validador+" el resultante es "+resultante);
                              enviar="";
                              return seleccionaConCondicionIgualA(BaseActiva,tablachida,salida,validador,resultante);
                              
                            }
                             else if(isEntero(resultante)==false){
                                  salida="dobles";
                                  double num=Double.parseDouble(resultante);
                                  System.out.println("llege aqui y se suna doble");
                                  System.out.println(" La tabla es "+ tablachida+" la salida es "+salida+" el validador es "+ validador+" el resultante es "+num);
                                  enviar="";
                                  return seleccionaConCondicionIgualA(BaseActiva,tablachida,salida,validador,num);
                             }
                             else{
                                 salida="entero";
                                 int num=Integer.parseInt(resultante);
                                 System.out.println("llege aqui y se suna enntero");
                                 System.out.println(" La tabla es "+ tablachida+" la salida es "+salida+" el validador es "+ validador+" el resultante es "+num);
                                 enviar="";
                                 return seleccionaConCondicionIgualA(BaseActiva,tablachida,salida,validador,num);
                             }
                         }
                    }
                    else{
                            return "Usted tiene un error de sintaxis";
                            //pw.println(enviar);
                            //pw.flush();
                        }
                    }
                    else{
                        System.out.println("quiere seleccionar \n todo de la tabla "+tablachida);
                        tablachida=tablachida.substring(0,tablachida.length()-1);
                        enviar="";
                        return seleccionaTodoTabla(BaseActiva,tablachida);   
                    }
                }

            }
            else{
                int inicio = cad.indexOf(" ");
                int fin = cad.indexOf(" ", inicio + 1); 
                String operar=cad.substring(inicio + 1, fin);
                System.out.println(operar);
                vector= operar.split(",");
                for(i=0;i<vector.length;i++){
                    System.out.println(vector[i]);
                    columnas.add(vector[i]);
                }
                path=tokens.nextToken();
                if(path.compareToIgnoreCase("from")==0){
                inicio = cad.indexOf("from ")+4;
                //int fin = cad.indexOf(";", inicio + 1); 
                //tablachida=cad.substring(inicio + 1, fin);
                path=tokens.nextToken();
                tablachida=path;
                }
                System.out.printf("quiere seleccionar:\n ");
                for(i=0;i<vector.length;i++){
                    System.out.printf(vector[i]+" ");
                }
                System.out.printf("de la tabla "+tablachida);
                //quiere seleccionar ""
                try {
                    String respuesta =seleccionaTuplasTabla(BaseActiva,tablachida.substring(0,tablachida.length()-1),vector);     
                    enviar="";
                    return respuesta;
                } catch (Exception e) {
                    enviar="Usted tiene un error de sintaxis ";
                    //pw.println(enviar);
                   // pw.flush();
                   return enviar;
                }
            }
            
            /*if(condicional==3){
                System.out.println(" La tabla es "+ tablachida+" la salida es "+salida+" el validador es "+ validador+" el resultante es "+resultante);
                seleccionaConCondicionIgualA("Sams", "Socio", "Dobles", "Saldo", 980.70);
                seleccionaConCondicionIgualA(BaseActiva,tablachida,salida,validador,resultante);
            }*/
        }
        else if(path.compareToIgnoreCase("drop")==0){
            path=tokens.nextToken();
            if(path.compareToIgnoreCase("database")==0){
                path=tokens.nextToken();
                StringTokenizer pc=new StringTokenizer(path, ";");
                path=pc.nextToken();
                System.out.println("se quiere borrar la base de datos "+path);
                return(eliminaDB(path));
            }
            else if(path.compareToIgnoreCase("table")==0){
                path=tokens.nextToken();
                StringTokenizer pc=new StringTokenizer(path, ";");
                path=pc.nextToken(); 
                System.out.println("se quiere borrar la tabla "+path);
                return eliminaTabla(BaseActiva,path);
            }
            else{
                enviar="Usted tiene un error de sintaxis";
                //pw.println(enviar);
                //pw.flush();
                return enviar;
            }
        }
        else if(path.compareToIgnoreCase("show")==0){
            path=tokens.nextToken();   
            if(path.compareToIgnoreCase("tables;")==0){
                
                System.out.println("se quieren mostrar las tablas");
                return(muestraTablas(BaseActiva));
                
            }
            if(path.compareToIgnoreCase("databases;")==0){
               System.out.println("se quieren mostrar las bases de datos"); 
                return(muestraDBs());
            }
            else{
                enviar="Usted tiene un error de sintaxis";
                //pw.println(enviar);
                //pw.flush();
                return enviar;
            }
        }
        else if(path.compareToIgnoreCase("use")==0){
          path=tokens.nextToken();   
            path= path.substring(0,path.length()-1);
            System.out.println("se quiere usar la base de datos "+path);  
            BaseActiva=path;
            return "accedio a base de datos "+path;
	    //pw.println(enviar);
            //pw.flush();            
        }
        else if(path.compareToIgnoreCase("desc")==0){
            path=tokens.nextToken();   
            path= path.substring(0,path.length()-1);
            System.out.println("se quiere describir "+path); 
            return describeTabla(BaseActiva,path);
        }
        else{
            return "Usted tiene un error de sintaxis";
            //pw.println(enviar);
            //pw.flush();
        }
    
        
        
        
        
        
        
       return "listo mensaje=";
        
    }
    
    
}
