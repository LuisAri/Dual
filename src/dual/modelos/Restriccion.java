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
public class Restriccion extends Funcion{
    
    public enum Signo {
        IGUAL,
        MAYOR_IGUAL,
        MENOR_IGUAL
    }
    
    private Signo signo;
    private float ladoDerecho;
    
    public Restriccion(Signo signo, float[] coeficientes, float ladoDerecho){
        super(coeficientes);
        
        this.signo = signo;
        this.ladoDerecho = ladoDerecho;
    }
    
    public Funcion estandar(){
        if(signo == Signo.MAYOR_IGUAL){
            return this.negativo();
        }
        
        return this;
    }
    
    public void imprimir(){
        super.imprimir();
        System.out.println(" " + signo.name() + " " + ladoDerecho);
    }
}
