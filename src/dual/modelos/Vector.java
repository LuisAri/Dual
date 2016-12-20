/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

import java.util.ArrayList;

/**
 *
 * @author rexim de venezuela
 */
public class Vector<E> extends ArrayList<E>{
    
    public Vector(){
        super();
    }
    
    public Vector(E array[]){
        for(int i = 0; i < array.length; i++){
            this.add(array[i]);
        }
    }
}
