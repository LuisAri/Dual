/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

/**
 *
 * @author rexim de venezuela
 */
public class Funcion {
    
    private static final int NEGATIVO = -1;
    
    int cantidad;
    Vector<Float> coeficientes;
    
    public Funcion(int cantidad, Float[] coeficientes){
        this.cantidad = cantidad;
        this.coeficientes = new Vector(coeficientes);
    }
    
    public Funcion(int cantidad, float[] coeficientes){
        this.cantidad = cantidad;
        
        Float[] temporal = new Float[coeficientes.length];
        for(int i = 0; i < cantidad; i++){
            temporal[i] = coeficientes[i];
        }
            
        this.coeficientes = new Vector(temporal);
    }
    
    public Funcion negativo(){
        Float[] negativos = new Float[coeficientes.size()];
        int i = 0;
        for(Float coeficiente: coeficientes){
            negativos[i++] = (coeficiente * NEGATIVO);
        }
        
        return new Funcion(cantidad, negativos);
    }
    
    public void imprimir(){
        int x = coeficientes.size();
        for(Float coeficiente: coeficientes){
            System.out.print(coeficiente + "x" + x-- + " ");
        }
        System.out.println("");
    }
}
