/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual;

import dual.modelos.Funcion;
import dual.modelos.FuncionObjetivo;
import dual.modelos.FuncionObjetivo.Caso;
import dual.modelos.Restriccion;
import dual.modelos.Restriccion.Signo;
import dual.modelos.Restricciones;

/**
 *
 * @author rexim de venezuela
 */
public class Dual {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testRestricciones();
        
    }
    
    public static void testFuncion(){
        int cantidad = 3;
        float[] coeficientes = new float[]{0.1f,2.2f,-3.0f};
        
        Funcion prueba = new Funcion(cantidad, coeficientes);
        prueba.imprimir();
        
        Funcion negativos = prueba.negativo();
        negativos.imprimir();
    }
    
    public static void testFuncionObjetivo(){
        int cantidad = 2;
        float[] coeficientes = new float[]{0.2f,8.8f};
        
        FuncionObjetivo prueba = new FuncionObjetivo(Caso.MAX, cantidad, coeficientes);
        prueba.imprimir();
    }
    
    public static void testRestriccion(){
        int cantidad = 2;
        float[] coeficientes = new float[]{20,80};
        Restriccion restriccion = new Restriccion(Signo.MENOR_IGUAL, cantidad, coeficientes, 120);
        restriccion.imprimir();
    }
    
    public static void testRestricciones(){
        int cantidad = 3;
        Restricciones restricciones = new Restricciones(cantidad);
        restricciones.agregarRestriccion(Signo.MENOR_IGUAL, 2, new float[]{1.1f,-50.5f}, 80);
        restricciones.agregarRestriccion(Signo.MAYOR_IGUAL, 3, new float[]{12, -1.1f,-50.5f}, 120);
        restricciones.agregarRestriccion(Signo.IGUAL, 1, new float[]{1.1f}, 10);
        restricciones.imprimir();
    }
}
