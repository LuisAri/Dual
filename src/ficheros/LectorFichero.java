/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficheros;

import dual.modelos.Simplex;
import dual.modelos.SistemaEcuacion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Clase dedicada a leer ficheros en formato .simplex con el ejercicio,
 * despues utiliza el modulo simplex para poder obtener el resultado final
 * y devolverlo en un fichero con formato .iteracion.
 * 
 * @author Hector
 */
public class LectorFichero {
    
    /**
     * Lector de archivos
     */
    private static FileReader f;
    private static BufferedReader b;
    
    /**
     * Extension de origen y destino.
     */
    private static final String EXTENSION_ORIGEN = ".simplex";
    private static final String EXTENSION_DESTINO = ".iteracion";
    
    /**
     * Lee un archivo en el directorio <code>fileName</code>
     * @param fileName 
     */
    public static void leerFichero(String fileName){
        try{
            if(!fileName.endsWith(EXTENSION_ORIGEN)){
                throw new IOException("[LectorFichero.leerFichero()]Extension invalida.");
            }
            
            String destino = fileName.replaceAll(EXTENSION_ORIGEN, EXTENSION_DESTINO);
            f = new FileReader(fileName);
            b = new BufferedReader(f);
            escribirFichero(destino, b);
            
        }catch(IOException ex){
            System.out.println("[LectorFichero.leerFichero()] No se pudo encontrar el archivo especificado: " + ex);
        }finally{
            try{
                b.close();
                f.close();
            }catch(Exception ex){
                System.out.println("[LectorFichero.leerFichero()] No se pudo cerrar los archivos: " + ex);
            }
        }
    }
    
    /**
     * Escribe un nuevo fichero en el directorio <code>destino</code>
     * @param destino
     * @param b 
     */
    private static void escribirFichero(String destino, BufferedReader b){
        
        File f;
        f = new File(destino);
        
        try{
           String cadena;
           FileWriter w = new FileWriter(f);
           BufferedWriter bw = new BufferedWriter(w);
           PrintWriter wr = new PrintWriter(bw);
           boolean comment = false; 
           String comentario = "Empty";
           SistemaEcuacion sistema = new SistemaEcuacion();
           
            while((cadena = b.readLine())!= null) {
                
                if(cadena.startsWith(";")){
                    if(!comment){
                        comment = true;
                        comentario = cadena.substring(1);
                        continue;
                    }
                    
                    escribirBloque(comentario, sistema, wr);
                    comentario = cadena.substring(1);
                    
                }
                if(cadena.startsWith("MAX") || cadena.startsWith("MIN")){
                    sistema.setFuncionObjetivo(Parser.parsearFuncionObjetivo(cadena));
                    sistema.getRestricciones().clear();
                }
                String digito = "((-|\\+|)(([0-9]*.[0-9]*)|[0-9]*))";
                if(cadena.matches("(" + digito + "(\\s)*)+(<|>)=\\s" + digito)){
                    sistema.agregarRestriccion(Parser.parsearRestriccion(cadena));
                }
            }

            wr.close();
            bw.close();
            w.close();
           
        }catch(Exception e){
            System.out.println(e.getMessage());
        };
    }
    
    /**
     * Escribe un ejercicio simplex en un archivo, cabe destacar que realiza las 
     * iteraciones hasta llegar a un caso especial o  haber encontrado una solucion
     * optima.
     * 
     * @param encabezado
     * @param sistema
     * @param f
     * @throws IOException 
     */
    private static void escribirBloque(String encabezado, SistemaEcuacion sistema, PrintWriter wr) throws IOException{

        wr.write(encabezado + "\n");
        
        Simplex simplex = new Simplex(sistema);
        do{
            wr.write(simplex.toString() + "\n");
        }while(simplex.siguiente() == Simplex.TABLA_SFB);
        
        wr.write(estadoTabla(simplex.siguiente()));
        
        
        wr.write(simplex.getResultado());
    }
    
    private static String estadoTabla(int estado){
        String respuesta = "";
        
        switch(estado){
            case Simplex.TABLA_INFACTIBLE:
                respuesta = "El tabloide es infactible.";
            break;
            case Simplex.TABLA_MULTIPLES_SOLUCIONES:
                respuesta = "El tabloide tiene multiples soluciones.";
            break;
            case Simplex.TABLA_NO_ACOTADA:
                respuesta = "El problema es no acotado.";
            break;
            case Simplex.TABLA_NO_SFB:
                respuesta = "El problema no tiene solucion factible basica inicial.";
            break;
        }
        
        return respuesta + "\n";
    }
}
