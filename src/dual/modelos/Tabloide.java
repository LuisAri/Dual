/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

import dual.modelos.FuncionObjetivo.Caso;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hector
 */
public class Tabloide {
    
    public static final int PRIMER_RENGLON_RESTRICCION = 1;
    
    private FuncionObjetivo objetivo;
    private Restricciones   restricciones;
    
    // Metadatos
    private int renglones;
    private int variables;
    private int holguras;
    
    // Lista de renglones
    List<Renglon> l_renglones;
    
    public Tabloide(FuncionObjetivo objetivo, Restricciones restricciones){
        this.objetivo = objetivo;
        this.restricciones = restricciones;
        
        this.variables = objetivo.cantidad();
        this.renglones = restricciones.getRenglones() + 1;
        this.holguras  = restricciones.getRenglones();
        
        l_renglones = new ArrayList();
        l_renglones.add(new Renglon(variables, holguras, objetivo));
        int i = 1;
        for(Restriccion restriccion : restricciones.getRestricciones()){
            l_renglones.add(new Renglon(i++, variables, holguras, restriccion));
        }
    }
    
    public void imprimir(){
        int i = 0;
        for(Renglon renglon: l_renglones){
            System.out.print(i++ + " | " + renglon.esZ + " \t| ");
            
            int x = 1; 
            for(Float coeficiente: renglon.coeficientes.coeficientes){
                System.out.print(coeficiente + "x" + x++ + " \t");
            }
            
            x = 1;
            for(Float coeficiente: renglon.holguras.coeficientes){
                System.out.print(coeficiente + "s" + x++ + " \t");
            }
            
            System.out.print(renglon.ladoDerecho);
            System.out.println("");
        }
    }
    
    class Renglon {
        boolean esZ;
        Funcion coeficientes;
        Funcion holguras;
        float ladoDerecho;
        
        public Renglon(int variables, int holguras, FuncionObjetivo objetivo){
            this.esZ            = true;
            this.coeficientes   = objetivo.negativo();
            this.holguras       = new Funcion(holguras);
            this.ladoDerecho    = 0;
        }
        
        public Renglon(int renglon, int variables, int holguras, Restriccion restriccion){
            this.esZ          = false;
            this.coeficientes = restriccion.estandar();
            this.holguras     = restriccion.holguras(holguras, renglon);
            this.ladoDerecho  = restriccion.getLadoDerechoEstandar();
        }
        
        public Renglon(boolean esZ, Funcion coeficientes, Funcion holguras, float ladoDerecho){
            this.esZ = esZ;
            this.coeficientes = coeficientes;
            this.holguras = holguras;
            this.ladoDerecho = ladoDerecho;
        }
        
        public boolean esZ(){
            return this.esZ;
        }
        
        public Funcion getCoeficientes(){
            return this.coeficientes;
        }
        
        public Funcion getHolguras(){
            return this.holguras;
        }
        
        public float getLadoDerecho(){
            return this.ladoDerecho;
        }
        
        public Renglon dividir(Renglon divisor){
            boolean t_esZ = true;
            Funcion t_coeficientes = coeficientes.dividir(divisor.getCoeficientes());
            Funcion t_holguras = holguras.dividir(divisor.getHolguras());
            float   t_ladoDerecho = this.ladoDerecho / divisor.getLadoDerecho();
            
            return new Renglon(t_esZ, t_coeficientes, t_holguras, t_ladoDerecho);
        }
        
        public MetaRenglon getMenor(Caso caso){
            MetaRenglon temporal = new MetaRenglon();
            temporal.esCoeficiente = true;
            
            if(caso == Caso.MIN){
                temporal.posicion = coeficientes.menor();
            }else{
                temporal.posicion = coeficientes.absoluto().menor();
            }
            
            return temporal;
        }
        
        public boolean tieneNegativos(){
            if(!coeficientes.tieneNegativos() && !holguras.tieneNegativos()){
                return false;
            }
            
            return true;
        }
        
        public void imprimir(){
            int x = 1; 
            for(Float coeficiente: this.coeficientes.coeficientes){
                System.out.print(coeficiente + "x" + x++ + " \t");
            }
            
            x = 1;
            for(Float coeficiente: this.holguras.coeficientes){
                System.out.print(coeficiente + "s" + x++ + " \t");
            }
            
            System.out.print(this.ladoDerecho);
            System.out.println("");
        }
    }
    
    class MetaRenglon {
        boolean esCoeficiente;
        int  posicion;
    }
    
    public int getRenglonPivote(){
        int i_pivote = PRIMER_RENGLON_RESTRICCION;
        Renglon r_pivote = l_renglones.get(i_pivote);
        
        for(int i = PRIMER_RENGLON_RESTRICCION + 1; i < l_renglones.size(); i++){
            if(r_pivote.ladoDerecho > l_renglones.get(i).ladoDerecho){
                r_pivote = l_renglones.get(i);
                i_pivote = i;
            }
        }
        
        if(r_pivote.ladoDerecho >= 0){
            i_pivote = -1;
        }
        
        return i_pivote;
    }
    
    public int getPivote(){
        if(this.getRenglonPivote() <= 0){
            System.out.println("[Tabloide.getPivote()] No tiene renglon pivote");
            return -1;
        }
        
        Renglon cero    = l_renglones.get(0);
        Renglon pivote  = l_renglones.get(this.getRenglonPivote());
        
        if(!pivote.tieneNegativos()){
            System.out.println("[Tabloide.getPivote()] No tiene negativos");
            return -1;
        }
        
        Renglon division  = cero.dividir(pivote);
        System.out.print("[Division] \t");
        division.imprimir();
        MetaRenglon meta  = division.getMenor(Caso.MAX);
        
        return meta.posicion;
    }
}
