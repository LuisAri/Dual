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
    int     variables;
    int     holgura;
    
    List<Renglon> renglones;
    
    int     excendencia;
    int     artificial;
    
    public Tabloide(FuncionObjetivo objetivo, Restricciones restricciones){
        this.objetivo = objetivo;
        this.restricciones = restricciones;
        this.renglones = new ArrayList();
        
        this.renglones.add(new Renglon(objetivo));
        for(Restriccion restriccion: restricciones.getRestricciones()){
            this.renglones.add(new Renglon(restriccion));
        }
    }
    
    class Renglon{
        boolean esZ;
        Funcion coeficientes;
        float   ladoDerecho;
        
        Renglon(FuncionObjetivo funcion){
            this.esZ = true;
            this.coeficientes = funcion.negativo();
            this.ladoDerecho = 0;
        }
        
        Renglon(Restriccion restriccion){
            this.esZ = false;
            this.coeficientes = restriccion.estandar();
            
        }
        
        public float obtenerCociente(float pivote){
            return ladoDerecho / pivote;
        }
    }
}
