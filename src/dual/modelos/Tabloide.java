/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hector
 */
public class Tabloide {
    
    public static final int PRIMER_RENGLON_RESTRICCION = 1;
    
    private FuncionObjetivo objetivo;
    private Restricciones   restricciones;
    
    // Metadatos
    private int renglones;
    private int variables;
    private int holguras;
    
    // Lista de renglones
    List<Renglon> l_renglones;
    
    public Tabloide(FuncionObjetivo objetivo, Restricciones restricciones){
        this.objetivo = objetivo;
        this.restricciones = restricciones;
        
        this.variables = objetivo.cantidad();
        this.renglones = restricciones.getRenglones() + 1;
        this.holguras  = restricciones.getRenglones();
        
        l_renglones = new ArrayList();
        l_renglones.add(new Renglon(variables, holguras, objetivo));
        int i = 1;
        for(Restriccion restriccion : restricciones.getRestricciones()){
            l_renglones.add(new Renglon(i++, variables, holguras, restriccion));
        }
    }
    
    public void imprimir(){
        int i = 0;
        for(Renglon renglon: l_renglones){
            System.out.print(i++ + " | " + renglon.esZ + " \t| ");
            
            int x = 1; 
            for(Float coeficiente: renglon.coeficientes.coeficientes){
                System.out.print(coeficiente + "x" + x++ + " \t");
            }
            
            x = 1;
            for(Float coeficiente: renglon.holguras.coeficientes){
                System.out.print(coeficiente + "s" + x++ + " \t");
            }
            
            System.out.print(renglon.ladoDerecho);
            System.out.println("");
        }
    }
    
    class Renglon {
        boolean esZ;
        Funcion coeficientes;
        Funcion holguras;
        float ladoDerecho;
        
        public Renglon(int variables, int holguras, FuncionObjetivo objetivo){
            this.esZ            = true;
            this.coeficientes   = objetivo.negativo();
            this.holguras       = new Funcion(holguras);
            this.ladoDerecho    = 0;
        }
        
        public Renglon(int renglon, int variables, int holguras, Restriccion restriccion){
            this.esZ          = false;
            this.coeficientes = restriccion.estandar();
            this.holguras     = restriccion.holguras(holguras, renglon);
            this.ladoDerecho  = restriccion.getLadoDerechoEstandar();
        }
        
        public boolean esZ(){
            return this.esZ;
        }
        
        public float getLadoDerecho(){
            return this.ladoDerecho;
        }
    }
    
    public int getRenglonPivote(){
        int i_pivote = PRIMER_RENGLON_RESTRICCION;
        Renglon r_pivote = l_renglones.get(i_pivote);
        
        for(int i = PRIMER_RENGLON_RESTRICCION + 1; i < l_renglones.size(); i++){
            if(r_pivote.ladoDerecho > l_renglones.get(i).ladoDerecho){
                r_pivote = l_renglones.get(i);
                i_pivote = i;
            }
        }
        
        if(r_pivote.ladoDerecho >= 0){
            i_pivote = -1;
        }
        
        return i_pivote;
    }
}
