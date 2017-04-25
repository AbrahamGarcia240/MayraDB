/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorgraphicmayradb;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Locale;
 
import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
 
/**
 *
 * @author abraham
 */
public class Creador {
    private static String classOutputFolder = "./build/classes";
    private String nomTabla;
   // private Hashtable <String, String> Nombres= new Hashtable<String, String>();
    //private ArrayList<String> Nombres=  new ArrayList<>();
    private String variables="";
    private String inicioclass="";
    private String constructorFirma="";
    private String constructorHeap="";
    private String setters="";
    private String getters="";
    private boolean isfirst=true;
    private String getconstraint="public String getConstraint(){ return this.constraint;}";
    private String constraint="";

    public Creador(String nombreTabla) {
        this.nomTabla=nombreTabla;
        this.inicioclass="public class "+nombreTabla+" {"; //cerrarlo
        this.constructorFirma+="public "+nombreTabla+"(){}"; //cerrarlo
        
    }
    
    public void setInteger(String nom,boolean NotNull){
       //Nombres.put("Integer", nom);
       if(NotNull){
           constraint+=nom+" integer (NOT NULL), ";
       }
       else{
           constraint+=nom+" integer, ";
       }
       variables+="public Integer "+nom+";";
       if(isfirst){
            //constructorFirma+="int "+nom+" ";
            isfirst=false;
       }
       else{
           //constructorFirma+=", int "+nom+" ";
       }
       
       //constructorHeap+=" this."+nom+"="+nom+";";
       
       setters+=" public void set"+nom+"(Integer "+nom+"){ this."+nom+"="+nom+"; } ";
       getters+=" public Integer get"+nom+"(){return this."+nom+"; } ";
       
    }
    public void setDouble(String nom, boolean NotNull){
        if(NotNull){
           constraint+=nom+" Double (NOT NULL), ";
       }
       else{
           constraint+=nom+" Double, ";
       }
       //Nombres.put("Integer", nom);
       variables+="public Double "+nom+";";
       
  
       //constructorHeap+=" this."+nom+"="+nom+";";
       
       setters+=" public void set"+nom+"(Double "+nom+"){ this."+nom+"="+nom+"; } ";
       getters+=" public Double get"+nom+"(){return this."+nom+"; } ";
       
    }
    
    public void setVarchar(String nom,boolean NotNull){
        if(NotNull){
           constraint+=nom+" Varchar (NOT NULL), ";
       }
       else{
           constraint+=nom+" Varchar, ";
       }
        
        variables+="public String "+nom+";";
       if(isfirst){
            //constructorFirma+="String "+nom+" ";
            isfirst=false;
       }
       else{
           //constructorFirma+=", String "+nom+" ";
       }
       
       //constructorHeap+=" this."+nom+"="+nom+";";
       
       setters+=" public void set"+nom+"(String "+nom+"){ this."+nom+"="+nom+"; } ";
       getters+=" public String get"+nom+"(){return this."+nom+"; } ";
    }
    
   
    
    public Object Finaliza(){
        //constructorFirma+="){";
        //constructorHeap+="}";
        JavaFileObject filen = getJavaFileObject();
        Iterable<? extends JavaFileObject> files = Arrays.asList(filen);
        compile(files);
 
        //3.Load your class by URLClassLoader, then instantiate the instance, and call method by reflection
        //runIt();
        /* Creo una instancia*/
        File file = new File(classOutputFolder);
 
        try
        {
            // Convert File to a URL
            URL url = file.toURL(); // file:/classes/demo
            URL[] urls = new URL[] { url };
            //System.out.println("dentro del metodo invoke..");
            // Create a new class loader with the directory
            ClassLoader loader = new URLClassLoader(urls);
            //System.out.println("crea cargador de clase");
            // Load in the class; Class.childclass should be located in
            // the directory file:/class/demo/
            Class thisClass = loader.loadClass(nomTabla);
            Class params[] = {};
            //System.out.println("Cargo bien la clase");
            //Object paramsObj[] = {};
            /*Object o = (Object)new String("Juancho");
            Object[] param = new Object[]{o};
            Object[] paramsObj = new Object[]{param};
            Object instance = thisClass.newInstance();
            Method thisMethod = thisClass.getDeclaredMethod("setNombre", params);
            */
            /*******************/
        String ClassName = nomTabla;
        Class<?> tClass = Class.forName(ClassName); // convert string classname to class
        Object tabla = tClass.newInstance(); // invoke empty constructor
            //System.out.println("Genero bien instancia "+tabla.getClass().getName());
        String methodName = "";
        return  tabla;
     
        
        }catch (MalformedURLException e)
        {
        }
        catch (ClassNotFoundException e)
        {
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
       return new Object();
    }
    
    public boolean setterVarchar(Object tabla, String nombreTabla, String elemento){
        try {
             String methodName = "";
            // with single parameter, return void
            methodName = "set"+nombreTabla;
            Method setNameMethod = tabla.getClass().getMethod(methodName, String.class);
            setNameMethod.invoke(tabla, elemento); // pass arg
            //System.out.println("Acado de ingresar "+elemento+" a "+nombreTabla);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e);
        }
        return false;
    }
    
     public String getterVarchar(Object tabla, String nombreTabla){
        try {
             String methodName = "";
             methodName = "get"+nombreTabla;
             Method getNameMethod = tabla.getClass().getMethod(methodName);
            String name = (String) getNameMethod.invoke(tabla); // explicit cast
            return name;
            // with single parameter, return void
           
        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e);
        }
        return "ERROR";
    }
     public boolean setterInteger(Object tabla, String nombreTabla, Integer elemento){
        try {
             String methodName = "";
             methodName = "set"+nombreTabla;
            Method setApp = tabla.getClass().getMethod(methodName, Integer.class);
            setApp.invoke(tabla, elemento); // pass arg
            //System.out.println("Acabo de ingresar "+elemento+" a "+nombreTabla);
            // with single parameter, return void
            return true;
        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e);
        }
        return false;
    }
    
     public Double getterDouble(Object tabla, String nombreTabla){
        try {
             String methodName = "";
            
             methodName = "get"+nombreTabla;
             Method getAppMethod = tabla.getClass().getMethod(methodName);
             Double n = (Double) getAppMethod.invoke(tabla); // explicit cast
             return n;
            // with single parameter, return void
           
        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e);
            e.printStackTrace();
        }
        return Double.valueOf(0);
    }
     
    public boolean setterDouble(Object tabla, String nombreTabla, Double elemento){
        try {
             String methodName = "";
             methodName = "set"+nombreTabla;
            Method setApp = tabla.getClass().getMethod(methodName, Double.class);
            setApp.invoke(tabla, elemento); // pass arg
            //System.out.println("Acabo de ingresar "+elemento+" a "+nombreTabla);
            // with single parameter, return void
            return true;
        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e);
        }
        return false;
    }
    
     public Integer getterInteger(Object tabla, String nombreTabla){
        try {
             String methodName = "";
            
             methodName = "get"+nombreTabla;
             Method getAppMethod = tabla.getClass().getMethod(methodName);
             Integer n = (Integer) getAppMethod.invoke(tabla); // explicit cast
             return n;
            // with single parameter, return void
           
        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e);
            e.printStackTrace();
        }
        return Integer.valueOf(0);
    }
    
    
     public static class MyDiagnosticListener implements DiagnosticListener<JavaFileObject>
    {
        public void report(Diagnostic<? extends JavaFileObject> diagnostic)
        {
 
            System.out.println("Line Number->" + diagnostic.getLineNumber());
            System.out.println("code->" + diagnostic.getCode());
            System.out.println("Message->"
                               + diagnostic.getMessage(Locale.ENGLISH));
            System.out.println("Source->" + diagnostic.getSource());
            System.out.println(" ");
        }
    }
     public static class InMemoryJavaFileObject extends SimpleJavaFileObject
    {
        private String contents = null;
 
        public InMemoryJavaFileObject(String className, String contents) throws Exception
        {
            super(URI.create("string:///" + className.replace('.', '/')
                             + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
            this.contents = contents;
        }
 
        public CharSequence getCharContent(boolean ignoreEncodingErrors)
                throws IOException
        {
            return contents;
        }
    }
    private  JavaFileObject getJavaFileObject()
    {
        StringBuilder contents = new StringBuilder(inicioclass+variables+" public String constraint=\""+constraint+"\";"+"public static void main(String args[]){}"+constructorFirma+constructorHeap+getconstraint+getters+setters+"}");
        JavaFileObject jfo = null;
        try
        {
            jfo = new InMemoryJavaFileObject(nomTabla, contents.toString());
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jfo;
    }
    public static void compile(Iterable<? extends JavaFileObject> files)
    {
        //get system compiler:
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
 
        // for compilation diagnostic message processing on compilation WARNING/ERROR
        MyDiagnosticListener c = new MyDiagnosticListener();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(c,
                                                                              Locale.ENGLISH,
                                                                              null);
        //specify classes output folder
        Iterable options = Arrays.asList("-d", classOutputFolder);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
                                                             c, options, null,
                                                             files);
        Boolean result = task.call();
        if (result == true)
        {
            //System.out.println("Succeeded");
        }
    }
    
    public void Imprime(){
        System.out.println(inicioclass+variables+" public String constraint=\""+constraint+"\";"+"public static void main(String args[]){}"+constructorFirma+constructorHeap+getconstraint+getters+setters+"}");
    }
    
   
     
}

class JavaSourceFromString extends SimpleJavaFileObject {
  final String code;

  JavaSourceFromString(String name, String code) {
    super(URI.create("string:///" + name.replace('.','/') + JavaFileObject.Kind.SOURCE.extension),JavaFileObject.Kind.SOURCE);
    this.code = code;
  }

  @Override
  public CharSequence getCharContent(boolean ignoreEncodingErrors) {
    return code;
  }
  
    
}
