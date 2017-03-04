/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

/**
 * Representacion del renglon en el tabloide.
 * 
 * @author Hector
 */
public class Renglon {
    
    /**
     * Indica si el renglon actual es la funcion objetivo.
     */
    boolean esZ;
    
    /**
     * Conjunto de coeficientes del renglon.
     */
    Funcion coeficientes;
    
    /**
     * Conjunto de variables de holguras del renglon.
     */
    Funcion holguras;
    
    /**
     * Lado derecho de la ecuacion.
     */
    float ladoDerecho;

    /**
     * Constructor inicial del renglon para la funcion objetivo.
     * @param variables
     * @param holguras
     * @param objetivo 
     */
    public Renglon(int variables, int holguras, FuncionObjetivo objetivo){
        this.esZ            = true;
        this.coeficientes   = objetivo.negativo();
        this.holguras       = new Funcion(holguras);
        this.ladoDerecho    = 0;
    }

    /**
     * Constructor inicial del renglon para la restriccion.
     * @param renglon
     * @param variables
     * @param holguras
     * @param restriccion 
     */
    public Renglon(int renglon, int variables, int holguras, Restriccion restriccion){
        this.esZ          = false;
        this.coeficientes = restriccion.estandar();
        this.holguras     = restriccion.holguras(holguras, renglon);
        this.ladoDerecho  = restriccion.getLadoDerechoEstandar();
    }

    /**
     * Constructor inicial del renglon para ingresar cada valor por separado.
     * @param esZ
     * @param coeficientes
     * @param holguras
     * @param ladoDerecho 
     */
    public Renglon(boolean esZ, Funcion coeficientes, Funcion holguras, float ladoDerecho){
        this.esZ = esZ;
        this.coeficientes = coeficientes;
        this.holguras = holguras;
        this.ladoDerecho = ladoDerecho;
    }

    /**
     * Indica si el renglon es una funcion objetivo.
     * @return 
     */
    public boolean esZ(){
        return this.esZ;
    }

    /**
     * Retorna el conjunto de coeficientes.
     * @return 
     */
    public Funcion getCoeficientes(){
        return this.coeficientes;
    }

    /**
     * Retorna el conjunto de variables de holgura.
     * @return 
     */
    public Funcion getHolguras(){
        return this.holguras;
    }

    /**
     * Retorna el lado derecho del renglon.
     * @return 
     */
    public float getLadoDerecho(){
        return this.ladoDerecho;
    }

    /**
     * Indica si todos los elementos del renglon son negativos, cabe indicar
     * que en este metodo solo considera los coeficientes y las variables de holgura
     * como todos los elementos.
     * @return 
     */
    public boolean tieneNegativos(){
        if(!coeficientes.tieneNegativos() && !holguras.tieneNegativos()){
            return false;
        }

        return true;
    }

    /**
     * Indica si todos los elementos del renglon valen cero, cabe indicar
     * que en este metodo solo considera los coeficientes y las variables de holgura
     * como todos los elementos.
     * @return 
     */
    public boolean tieneCeros(){
        if(coeficientes.tieneCeros() && holguras.tieneCeros()){
            return true;
        }

        return false;
    }

    /**
     * Realiza la suma entre dos renglones.
     * 
     * @param renglon
     * @return 
     */
    public Renglon sumar(Renglon renglon){
        boolean t_esZ = false;
        Funcion t_coeficientes = coeficientes.sumar(renglon.getCoeficientes());
        Funcion t_holguras = holguras.sumar(renglon.getHolguras());
        float   t_ladoDerecho = this.ladoDerecho + renglon.getLadoDerecho();
        //System.out.println("Lado derecho: " + this.ladoDerecho + "  +  " + renglon.getLadoDerecho());
        return new Renglon(t_esZ, t_coeficientes, t_holguras, t_ladoDerecho);
    }

    /**
     * Realiza la multiplicacion escalar entre el renglon y el valor escalar.
     * @param valor
     * @return 
     */
    public Renglon multiplicar(float valor){
        boolean t_esZ = false;
        Funcion t_coeficientes = coeficientes.multiplicar(valor);
        Funcion t_holguras = holguras.multiplicar(valor);
        float   t_ladoDerecho = this.ladoDerecho * valor;

        return new Renglon(t_esZ, t_coeficientes, t_holguras, t_ladoDerecho);
    }

    /**
     * Realiza la division escalar entre el renglon y el valor escalar.
     * @param divisor
     * @return 
     */
    public Renglon dividir(float divisor){
        boolean t_esZ = false;
        Funcion t_coeficientes = coeficientes.dividir(divisor);
        Funcion t_holguras = holguras.dividir(divisor);
        float   t_ladoDerecho = this.ladoDerecho / divisor;

        return new Renglon(t_esZ, t_coeficientes, t_holguras, t_ladoDerecho);
    }

    /**
     * Realiza la division entre dos renglones, la division sera entre los elementos
     * respectivos de los dos renglones.
     * 
     * @param divisor
     * @return 
     */
    public Renglon dividir(Renglon divisor){
        boolean t_esZ = true;
        Funcion t_coeficientes = coeficientes.dividir(divisor.getCoeficientes());
        Funcion t_holguras = holguras.dividir(divisor.getHolguras());
        float   t_ladoDerecho = this.ladoDerecho / divisor.getLadoDerecho();

        return new Renglon(t_esZ, t_coeficientes, t_holguras, t_ladoDerecho);
    }

    /**
     * Retorna metadatos del menor valor del renglon en el coeficiente.
     * @param caso
     * @return 
     */
    public MetaRenglon getMenor(FuncionObjetivo.Caso caso){
        MetaRenglon temporal = new MetaRenglon();
        temporal.setEsCoeficiente(true);

        if(caso == FuncionObjetivo.Caso.MIN){
            temporal.setPosicion(coeficientes.menor(true));
        }else{
            temporal.setPosicion(coeficientes.absoluto().menor(true));
        }

        return temporal;
    }

    /**
     * Imprime el renglon.
     */
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
