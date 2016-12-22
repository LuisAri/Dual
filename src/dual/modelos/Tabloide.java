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
            System.out.print(i++ + " - " + renglon.esZ + " - ");
            
            int x = 1; 
            for(Float coeficiente: renglon.coeficientes.coeficientes){
                System.out.print(coeficiente + "x" + x++ + " ");
            }
            
            x = 1;
            for(Float coeficiente: renglon.holguras.coeficientes){
                System.out.print(coeficiente + "s" + x++ + " ");
            }
            
            System.out.print(renglon.ladoDerecho);
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
    }
}
