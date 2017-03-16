/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual;

import dual.vistas.MainView;
import dual.modelos.Funcion;
import dual.modelos.FuncionObjetivo;
import dual.modelos.FuncionObjetivo.Caso;
import dual.modelos.Restriccion;
import dual.modelos.Restriccion.Signo;
import dual.modelos.old.Restricciones;
import dual.modelos.Simplex;
import dual.modelos.SimplexDual;
import dual.modelos.SistemaEcuacion;
import dual.modelos.old.Tabloide;
import ficheros.LectorFichero;

/**
 * Por hacer
 * 
 * (LISTO) - Hacer que funcione con simplex normal
 * (LISTO) - Hacer herencia con dual simplex
 *  
 *  (LISTO) - Detectar casos especiales 
 *      (LISTO) - Detectar Multiples soluciones
 *      (LISTO) - Detectar no acotamiento
 *  
 * (LISTO) - Modulo de lectura de ficheros
 *  (LISTO) - Imprimir el resultado
 *  (LISTO) - Escribir multiples ficheros.
 *      
 * - Terminar vista
 *  - Mostrar solucion optima
 *  (LISTO) - Validar datos de entrada
 *  (LISTO) - Quitar preparar
 *  - Agregar Iteraciones en resolver
 *  - Hacer pruebas unitarias
 * 
 *  - Hacer que detecte ejercicios con simplex normal y resolverlos
 *  - Detectar infactibilidad
 * 
 * @author Hector
 */
public class Dual {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //*
        MainView ventana = new MainView();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true); // */
        
        //testFichero();
    }
    
    public static void testFichero(){
        LectorFichero.leerFichero("D://ejercicios.simplex");
    }
}
