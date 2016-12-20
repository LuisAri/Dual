/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual;

import dual.modelos.Funcion;

/**
 *
 * @author rexim de venezuela
 */
public class Dual {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int cantidad = 3;
        float[] coeficientes = new float[]{0.1f,2.2f,-3.0f};
        
        Funcion prueba = new Funcion(cantidad, coeficientes);
        prueba.imprimir();
        
        Funcion negativos = prueba.negativo();
        negativos.imprimir();
    }
    
}
