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
    
    protected static final int NEGATIVO = -1;
    
    Vector<Float> coeficientes;
    
    public Funcion(int cantidad){
        Float[] temporal = new Float[cantidad];
        
        for(int i = 0; i < cantidad; i++){
            temporal[i] = 0f;
        }
        
        this.coeficientes = new Vector(temporal);
    }
    
    public Funcion(Float[] coeficientes){
        this.coeficientes = new Vector(coeficientes);
    }
    
    public Funcion(float[] coeficientes){
        Float[] temporal = new Float[coeficientes.length];
        int tamano = coeficientes.length;
        for(int i = 0; i < tamano; i++){
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
        
        return new Funcion(negativos);
    }
    
    public int cantidad(){
        return coeficientes.size();
    }
    
    public void imprimir(){
        int x = 1; 
        for(Float coeficiente: coeficientes){
            System.out.print(coeficiente + "x" + x++ + " ");
        }
    }
}
