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
import dual.modelos.Tabloide;

/**
 *
 * @author rexim de venezuela
 */
public class Dual {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testTabloide2();
    }
    
    public static void testFuncion(){
        float[] coeficientes = new float[]{0.1f,2.2f,-3.0f};
        
        Funcion prueba = new Funcion(coeficientes);
        prueba.imprimir();
        
        Funcion negativos = prueba.negativo();
        negativos.imprimir();
    }
    
    public static void testFuncionObjetivo(){
        float[] coeficientes = new float[]{0.2f,8.8f};
        
        FuncionObjetivo prueba = new FuncionObjetivo(Caso.MAX, coeficientes);
        prueba.imprimir();
    }
    
    public static void testRestriccion(){
        float[] coeficientes = new float[]{20,80};
        Restriccion restriccion = new Restriccion(Signo.MENOR_IGUAL, coeficientes, 120);
        restriccion.imprimir();
    }
    
    public static void testRestricciones(){
        Restricciones restricciones = new Restricciones();
        restricciones.agregarRestriccion(Signo.MENOR_IGUAL, new float[]{1.1f,-50.5f}, 80);
        restricciones.agregarRestriccion(Signo.MAYOR_IGUAL, new float[]{12, -1.1f,-50.5f}, 120);
        restricciones.agregarRestriccion(Signo.IGUAL, new float[]{1.1f}, 10);
        restricciones.imprimir();
    }
    
    public static void testTabloide(){
        System.out.println("[Funcion Objetivo]");
        FuncionObjetivo objetivo = new FuncionObjetivo(Caso.MAX, new Float[]{10f, 6f});
        objetivo.imprimir();
        System.out.println("\n\n[Restricciones]");
        
        Restricciones restricciones = new Restricciones();
        restricciones.agregarRestriccion(Signo.MENOR_IGUAL, new float[]{2f,3f}, -90);
        restricciones.agregarRestriccion(Signo.MENOR_IGUAL, new float[]{20, 50f}, 3000);
        restricciones.imprimir();
        
        System.out.println("\n\n[Tabloide]");
        Tabloide tabla = new Tabloide(objetivo, restricciones);
        tabla.imprimir();
        
        System.out.println("\n\n[Renglon Pivote]");
        System.out.println(tabla.getRenglonPivote());
        System.out.println("\n\n[Pivote]");
        System.out.println(tabla.getPivote());
        
        tabla.iterar(2);
    }
    
    public static void testTabloide2(){
        System.out.println("[Funcion Objetivo]");
        FuncionObjetivo objetivo = new FuncionObjetivo(Caso.MIN, new Float[]{2f, 1f});
        objetivo.imprimir();
        System.out.println("\n\n[Restricciones]");
        
        Restricciones restricciones = new Restricciones();
        restricciones.agregarRestriccion(Signo.MAYOR_IGUAL, new float[]{3f,1f}, 3);
        restricciones.agregarRestriccion(Signo.MAYOR_IGUAL, new float[]{4f, 3f}, 6);
        restricciones.agregarRestriccion(Signo.IGUAL, new float[]{1f, 2f}, 3);
        restricciones.imprimir();
        
        System.out.println("\n\n[Tabloide]");
        Tabloide tabla = new Tabloide(objetivo, restricciones);
        tabla.imprimir();
        
        System.out.println("\n\n[Renglon Pivote]");
        System.out.println(tabla.getRenglonPivote());
        System.out.println("\n\n[Pivote]");
        System.out.println(tabla.getPivote());
        
        System.out.println("\n\n[Iteracion]");
        tabla.iterar(2);
    }
}
