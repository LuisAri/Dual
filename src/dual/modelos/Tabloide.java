/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

import static dual.modelos.Funcion.NEGATIVO;
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
        
        public boolean tieneNegativos(){
            if(!coeficientes.tieneNegativos() && !holguras.tieneNegativos()){
                return false;
            }
            
            return true;
        }
        
        public boolean tieneCeros(){
            if(coeficientes.tieneCeros() && holguras.tieneCeros()){
                return true;
            }

            return false;
        }
        
        public Renglon sumar(Renglon renglon){
            boolean t_esZ = false;
            Funcion t_coeficientes = coeficientes.sumar(renglon.getCoeficientes());
            Funcion t_holguras = holguras.sumar(renglon.getHolguras());
            float   t_ladoDerecho = this.ladoDerecho + renglon.getLadoDerecho();
            
            return new Renglon(t_esZ, t_coeficientes, t_holguras, t_ladoDerecho);
        }
        
        public Renglon multiplicar(float valor){
            boolean t_esZ = false;
            Funcion t_coeficientes = coeficientes.multiplicar(valor);
            Funcion t_holguras = holguras.multiplicar(valor);
            float   t_ladoDerecho = this.ladoDerecho * valor;
            
            return new Renglon(t_esZ, t_coeficientes, t_holguras, t_ladoDerecho);
        }
        
        public Renglon dividir(float divisor){
            boolean t_esZ = false;
            Funcion t_coeficientes = coeficientes.dividir(divisor);
            Funcion t_holguras = holguras.dividir(divisor);
            float   t_ladoDerecho = this.ladoDerecho / divisor;
            
            return new Renglon(t_esZ, t_coeficientes, t_holguras, t_ladoDerecho);
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
    
    public void set(int posicion, Renglon renglon){
        l_renglones.set(posicion, renglon);
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
        
        if(pivote.tieneCeros()){
            System.out.println("[Tabloide.getPivote()] Todos los denominadores son cero");
            return -1;
        }
        
        Renglon division  = cero.dividir(pivote);
        System.out.print("[getPivote() - Division] \t");
        division.imprimir();
        MetaRenglon meta  = division.getMenor(Caso.MAX);
        
        return meta.posicion;
    }
    
    public float getValor(int renglon, int columna){
        Renglon pivote = l_renglones.get(renglon);
        return pivote.getCoeficientes().getCoeficientes().get(columna);
    }
    
    public Renglon dividir(int renglon, float valor){
        return l_renglones.get(renglon).dividir(valor);
    }
    
    public Tabloide iterar(int iteraciones){ // Agregar variable debug
        Tabloide tabloide = new Tabloide(this.objetivo, this.restricciones);
        
        for(int i = 0; i < iteraciones; i++){
            int r_pivote = tabloide.getRenglonPivote();
            int c_pivote = tabloide.getPivote();
            float v_pivote = tabloide.getValor(r_pivote, c_pivote);
            
            System.out.println("[Tabloide.iterar() - Valor pivote] " + v_pivote);
            Renglon division = tabloide.dividir(r_pivote, v_pivote);
            l_renglones.set(r_pivote, division);
            
            for(int j = 0; j < l_renglones.size(); j++){
                if(j != r_pivote){
                    float v_columna_pivote = tabloide.getValor(j, c_pivote);
                    Renglon multiplicacion;
                    if(v_columna_pivote > 0){
                        multiplicacion = division.multiplicar(v_columna_pivote * NEGATIVO);
                    }else{
                        multiplicacion = division.multiplicar(v_columna_pivote);
                    }
                    
                    Renglon suma = l_renglones.get(j).sumar(multiplicacion);
                    l_renglones.set(j, suma);
                }
            }
            
            tabloide.imprimir();
        }
        
        return tabloide;
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
}
