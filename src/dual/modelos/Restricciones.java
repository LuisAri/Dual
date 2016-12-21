/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

import dual.modelos.Restriccion.Signo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rexim de venezuela
 */
public class Restricciones {
    
    private int cantidad;
    private List<Restriccion> restricciones;
    
    public Restricciones(int cantidad){
        this.cantidad = cantidad;
        restricciones = new ArrayList();
    }
    
    public void agregarRestriccion(Signo signo, int cantidad, float[] coeficientes, float ladoDerecho){
        Restriccion nueva = new Restriccion(signo, cantidad, coeficientes, ladoDerecho);
        restricciones.add(nueva);
    }
    
    public void removerRestriccion(int renglon){
        restricciones.remove(renglon);
    }
    
    public List<Restriccion> getRestricciones(){
        return restricciones;
    }
    
    public int getVariables(){
        int variables = 0;
        
        for(Restriccion restriccion: restricciones){
            variables = variables > restriccion.cantidad ? variables: restriccion.cantidad;
        }
        
        return variables;
    }
    
    public void imprimir(){
        System.out.println("Sujeto A");
        for(Restriccion restriccion : restricciones){
            System.out.println("\t");
            restriccion.imprimir();
        }
        System.out.println("");
    }
}
