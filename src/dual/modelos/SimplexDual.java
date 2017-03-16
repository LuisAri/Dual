/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

/**
 * Representacion del metodo dual simplex.
 * @author Hector
 */
public class SimplexDual extends Simplex{
    
    /**
     * Constructor inicial.
     * @param sistema 
     */
    public SimplexDual(SistemaEcuacion sistema) {
        super(sistema);
    }
    
    /**
     * Para encontrar que variable de holgura sale, se busca el lado derecho
     * mas negativo del tabloide. Si no hay, el problema tiene solucion optima.
     * @return 
    */
    @Override
    public int getFilaPivote(){
        // Obtiene el primer renglon restriccion del tabloide.
        int i_pivote = RENGLON_RESTRICCION;
        Renglon r_pivote = renglones.get(i_pivote);
        
        /* Va recorriendo cada renglon restriccion distinto al primero
           y compara el lado derecho de cada renglon hasta encontrar el 
           menor valor negativo. */
        for(int i = RENGLON_RESTRICCION + 1; i < renglones.size(); i++){
            if(r_pivote.getLadoDerecho() > renglones.get(i).getLadoDerecho()){
                r_pivote = renglones.get(i);
                i_pivote = i;
            }
        }
        
        // En caso de no haber lado derecho negativo, retorno un valor de error.
        if(r_pivote.getLadoDerecho() >= 0){
            i_pivote = -1;
        }
        
        return i_pivote;
    }
    
    /**
     * Para escojer la variable de decision que entra, se divide el renglon objetivo
     * por el renglon pivote, y se elija la columna con el menor cociente en minimizar,
     * y el menor valor absoluto en maximizar. Si todos los valores son mayores o iguales
     * a cero, el problema sera infactible.
     * 
     * @return Posicion de la columna pivote en los coeficientes tecnologicos.
     */
    @Override
    public int getColumnaPivote(){
        // Verifica si el renglon pivote es menor o igual a 0
        if(this.getFilaPivote() <= 0){
            System.out.println("[SimplexDual.getColumnaPivote()] No tiene renglon pivote");
            return -1;
        }
        
        // Asigno el renglon cero y el pivote
        Renglon cero    = renglones.get(RENGLON_OBJETIVO);
        Renglon pivote  = renglones.get(this.getFilaPivote());
        
        // Verifica si todos los elementos del renglon pivote valen cero.
        if(pivote.tieneCeros()){
            System.out.println("[SimplexDual.getPivote()] Todos los denominadores son cero, no es factible");
            return -1;
        }
        
        // Divide el renglon de la funcion objetivo entre el renglon pivote.
        Renglon division  = cero.dividir(pivote);
        
        // Retorna la posicion del menor elemento de los coeficientes del renglon.
        return division.getMenorDual(sistema.getCaso());
    }
    
    /**
     * Permite dirigirse a una iteracion especifica del tabloide si no se 
     * presenta ningun caso especial o la solucion optima del problema.
     * 
     * @param iteracion
     */
    @Override
    public void iterar(int iteracion){
        while(siguiente() == TABLA_SFB && this.iteracion != iteracion){
            System.out.println("[SimplexDual.siguiente()] Iteracion #" + this.iteracion);
        }
    }
    
    /**
     * Determina si la sfb actual es no acotada. En dual simplex, este caso se omite.
     * @return 
     */
    @Override
    public boolean esNoAcotada(){
        return false;
    }
    
    /**
     * Determina si la solucion factible basica tiene multiples soluciones.
     * En dual simplex, este caso se omite.
     * 
     * @return 
     */
    @Override
    public boolean esMultipleSoluciones(){
        return false;
    }
    
    /**
     * Indica si el tabloide es optimo. La condicion para que un tabloide sea
     * optimo es si todos los lado derecho de los renglones son positivos.
     * 
     * @return 
     */
    @Override
    public boolean esOptima(){
        for(int i = RENGLON_RESTRICCION; i < this.renglones.size(); i++){
            if(renglones.get(i).getLadoDerecho() < 0){
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Determina si la sfb es infactible, una sfb es infactible cuando alguna de 
     * sus restricciones no se puede satisfacer de forma simultanea con el resto
     * de las demas restricciones, solo se presenta en casos donde haya restricciones
     * mayor que en el mismo problema lineal.
     * 
     * @return 
     */
    @Override
    public boolean esInfactible(){
        return (this.getColumnaPivote() == -1 && !this.esOptima());
    }
    
    /**
     * Determina si existe una sfbi. En dual simplex, este caso se omite.
     */
    public boolean esNoSfb(){
        return false;
    }
}
