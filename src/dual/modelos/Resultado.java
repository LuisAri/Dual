/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

import java.util.ArrayList;

/**
 * Clase que contiene el resultado de la solucion basica.
 * @author Hector
 */
public class Resultado {
    /**
     * El valor de z
     */
    public float z;
    
    /**
     * Las variables basicas
     */
    public Vector<Basica> variables;
    
    /**
     * Clase que representa la variable basica, indicando la posicion de la variable
     * y su valor del lado derecho.
     */
    class Basica{
        int x;
        float valor;
        
        Basica(int x, float valor){
            this.x = x;
            this.valor = valor;
        }
    }
    
    /**
     * Constructor default.
     * @param renglones
     */
    public Resultado(ArrayList<Renglon> renglones){
        Renglon obj = renglones.get(0);
        z = obj.getLadoDerecho();
        variables = new Vector();
        int contador = 0;
        boolean continuar = true;
        for(int i = 0; i < renglones.get(0).getCoeficientes().cantidad(); i++){
            int posicion_1 = 1;
            if(obj.getCoeficientes().get(i) == 0){
                for(Renglon r: renglones){
                    if(r.getCoeficientes().get(i) != 0 &&
                       r.getCoeficientes().get(i) != 1){
                        continuar = false;
                        break;
                    }
                    
                    contador += r.getCoeficientes().get(i) == 1? 1:0;
                    posicion_1 = r.getCoeficientes().get(i) == 1? renglones.indexOf(r):posicion_1;
                    if(contador > 1){
                        continuar = false;
                        break;
                    }
                }
                if(!continuar){
                    contador = 0;
                    continuar = true;
                    continue;
                }
                variables.add(new Basica(i,renglones.get(posicion_1).getLadoDerecho()));
                contador = 0;
            }
        }
    }
}
