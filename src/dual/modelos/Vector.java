/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

import java.util.ArrayList;

/**
 * Clase utilizada para reimplementar la clase ArrayList con valores cualquiera.
 * 
 * @see dual.modelos.Funcion
 * @author Hector
 */
public class Vector<E> extends ArrayList<E>{
    
    /**
     * Constructor para inicializar vector sin ningun elemento.
     */
    public Vector(){
        super();
    }
    
    /**
     * Constructor para inicializar el vector con un array de objetos
     * @param array Array de objetos a ingresar
     */
    public Vector(E array[]){
        for(int i = 0; i < array.length; i++){
            this.add(array[i]);
        }
    }
}
