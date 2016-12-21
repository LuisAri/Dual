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
public class FuncionObjetivo extends Funcion{
    
    public enum Caso {
        MAX,
        MIN
    }
    
    private Caso caso;
    
    public FuncionObjetivo(Caso caso, Float[] coeficientes){
        super(coeficientes);
        
        this.caso = caso;
    }
    
    public FuncionObjetivo(Caso caso, float[] coeficientes){
        super(coeficientes);
        
        this.caso = caso;
    }
    
    public void imprimir(){
        System.out.print(caso.name() + " ");
        super.imprimir();
    }
}
