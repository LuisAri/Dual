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
    int renglones;
    int variables;
    int holguras;
    int excedencia;
    int artificial;
    
    public Tabloide(FuncionObjetivo objetivo, Restricciones restricciones){
        this.objetivo = objetivo;
        this.restricciones = restricciones;
        
        
    }
    
}
