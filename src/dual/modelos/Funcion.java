/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

/**
 * Clase que representa una funcion lineal en el programa. Consta de metodos
 * para realizar operaciones basicas y escalares entre funciones.
 * 
 * Atributos:
 * 
 * - Constante NEGATIVO.
 * - Vector que son los coeficientes
 * 
 * Metodos:
 * 
 * - Sumar
 * - Restar
 * - Multiplicacion entre vectores y escalar
 * - Division entre vectores y escalar
 * 
 * - Negativo
 * - Menor
 * - Absoluto
 * - Cantidad
 * - Tiene negativos
 * - Tiene ceros
 * - Imprimir
 * 
 * @see dual.modelos.Vector
 * @see dual.modelos.FuncionObjetivo
 * @see dual.modelos.Restriccion
 * @author Hector
 */
public class Funcion {
    
    /**
     * Constante que contiene valor negativo.
     */
    public static final int NEGATIVO = -1;
    
    /**
     * Coeficientes de la funcion.
     */
    protected Vector<Float> coeficientes;
    
    /**
     * Constructor que permite inicializar el vector de la funcion sin ningun elemento.
     */
    public Funcion(){
        coeficientes = new Vector();
    }
    
    /**
     * Constructor que permite inicializar el vector de la funcion y llena con
     * ceros.
     * @throws NumberFormatException si el valor de la cantidad es menor a 1.
     * @param cantidad Cantidad de elementos iguales a cero ingresados en la funcion.
     */
    public Funcion(int cantidad){
        if(cantidad < 1){
            throw new NumberFormatException();
        }
        
        Float[] temporal = new Float[cantidad];
        
        for(int i = 0; i < cantidad; i++){
            temporal[i] = 0f;
        }
        
        this.coeficientes = new Vector(temporal);
    }
    
    /**
     * Constructor que inicializa el vector de la funcion con un array de 
     * coeficientes. 
     * @param coeficientes Array de objetos Float.
     */
    public Funcion(Float[] coeficientes){
        this.coeficientes = new Vector(coeficientes);
    }
    
    /**
     * Constructor que inicializa el vector de la funcion con un array de 
     * coeficientes.
     * @param coeficientes Array de datos primitivos float.
     */
    public Funcion(float[] coeficientes){
        Float[] temporal = new Float[coeficientes.length];
        int tamano = coeficientes.length;
        
        for(int i = 0; i < tamano; i++){
            temporal[i] = coeficientes[i];
        }
            
        this.coeficientes = new Vector(temporal);
    }
    
    /**
     * Retorna el vector de coeficientes de la funcion.
     * @return 
     */
    public Vector<Float> getCoeficientes(){
        return coeficientes;
    }
    
    /**
     * Retorna el valor del vector en una posicion especifica.
     * @param i El index de posicion en el vector.
     * @return El valor de la funcion en la posicion del index.
     */
    public Float get(int i){
        return coeficientes.get(i);
    }
    
    /**
     * Suma dos funciones entre si, sin importar su tamaño.
     * @param funcion Funcion a sumar
     * @return Sumatoria de las dos funciones
     */
    public Funcion sumar(Funcion funcion){
        // Obtiene el mayor tamaño entre los dos vectores.
        int tamano = this.cantidad() > funcion.cantidad()? 
                this.cantidad() : funcion.cantidad();
        
        // Crea un array temporal con el tamaño mas grande y les asigna valor 0.
        float[] temporal = new float[tamano];
        for(int i = 0; i < tamano; i++){
            temporal[i] = 0;
        }
        
        // Recorre el vector temporal para ir sumando cada valor de las dos funciones.
        for(int i = 0; i < tamano; i++){
            if(this.cantidad() < i || funcion.cantidad() < i){
                temporal[i] = 0;
            }else{
                temporal[i] = this.get(i) + funcion.get(i);                
            }
        }
        
        return new Funcion(temporal);
    }
    
    /**
     * Resta dos funciones entre si, sin importar su tamaño.
     * @param funcion Funcion restadora
     * @return Diferencia de las dos funciones
     */
    public Funcion restar(Funcion funcion){
        // Obtiene el mayor tamaño entre los dos vectores.
        int tamano = this.cantidad() > funcion.cantidad()? 
                this.cantidad() : funcion.cantidad();
        
        // Crea un array temporal con el tamaño mas grande y les asigna valor 0.
        float[] temporal = new float[tamano];
        for(int i = 0; i < tamano; i++){
            temporal[i] = 0;
        }
        
        // Recorre el vector temporal para ir restando cada valor de las dos funciones.
        for(int i = 0; i < tamano; i++){
            if(this.cantidad() < i || funcion.cantidad() < i){
                temporal[i] = 0;
            }else{
                temporal[i] = this.get(i) - funcion.get(i);                
            }
        }
        
        return new Funcion(temporal);
    }
    
    /**
     * Hace la multiplicacion escalar entre la funcion actual y un valor decimal.
     * @param valor Valor escalar a multiplicar
     * @return Multiplicacion escalar
     */
    public Funcion multiplicar(float valor){
        // Obtiene el tamaño del array
        int tamano = this.cantidad();
        
        // Crea un array temporal y le asigna 0 a todas sus posiciones.
        float[] temporal = new float[tamano];
        for(int i = 0; i < tamano; i++){
            temporal[i] = 0;
        }
        
        // Recorre el array para realizar la multiplicacion escalar.
        for(int i = 0; i < tamano; i++){
            temporal[i] = this.get(i) * valor;
        }
        
        return new Funcion(temporal);
    }
    
    /**
     * Hace la division escalar entre la funcion actual y un valor decimal.
     * @param divisor Valor escalar que dividira la funcion.
     * @return Division escalar
     */
    public Funcion dividir(float divisor){
        // Obtiene el tamaño del array
        int tamano = this.cantidad();
        
        // Crea un array temporal y le asigna 0 a todas sus posiciones.
        float[] temporal = new float[tamano];
        for(int i = 0; i < tamano; i++){
            temporal[i] = 0;
        }
        
        /* Recorre el array para realizar la divison escalar, teniendo cuidado
           de que si el valor del divisor es cero, todas las posiciones sean 
           iguales a cero. */
        for(int i = 0; i < tamano; i++){
            if(divisor == 0){
                temporal[i] = 0;
            }else{
                temporal[i] = this.get(i) / divisor;
            }
        }
        
        return new Funcion(temporal);
    }
    
    /**
     * Hace la division entre dos funciones. Cabe destacar que esta division
     * no generara un matriz, sino que devolvera la divison entre los index
     * de ambas funciones. Si la primera funcion es 2x1 + 4x2 + 6x3 y la 
     * funcion divisor es 2x1 - 1x2 + 3x3, el resultado sera una funcion
     * con valores 1x1 - 4x2 + 2x3.
     * 
     * @param divisor Funcion divisora.
     * @return funcion resultado de la division de las dos funciones.
     */
    public Funcion dividir(Funcion divisor){
        // Obtiene el mayor tamaño entre los dos vectores.
        int tamano = this.cantidad() > divisor.cantidad()? 
                this.cantidad() : divisor.cantidad();
        
        // Crea un array temporal y le asigna 0 a todas sus posiciones.
        float[] temporal = new float[tamano];
        for(int i = 0; i < tamano; i++){
            temporal[i] = 0;
        }
        
        /* Recorre el array para realizar la divison entre las dos funciones.
           teniendo en cuenta de que si alguno de los elemtentos de la funcion
           divisor es igual a cero, el valor de la funcion resultante en esa posicion
           tambien sera cero.
        */
        for(int i = 0; i < tamano; i++){
            // Si el tamaño de algunas de las funciones es menor al del array tempora, asigno cero
            if(this.cantidad() < i || divisor.cantidad() < i){ 
                temporal[i] = 0;
            }else if(divisor.get(i) == 0){ // Si el divisor en la posicion i es cero
                temporal[i] = 0;
            }else{ 
                temporal[i] = this.get(i) / divisor.get(i);
            }
        }
        
        return new Funcion(temporal);
    }
    
    /**
     * Retorna la funcion a la inversa al ser multiplicado por -1.
     * @return La inversa de la funcion.
     */
    public Funcion negativo(){
        return this.multiplicar(NEGATIVO);
    }
    
    /**
     * Retorna la funcion con todos los valores absolutos (positivos).
     * @return La funcion con valor absoluto.
     */
    public Funcion absoluto(){
        // Crea un array temporal y le asigna el valor absoluto de coeficientes.get(i) 
        // a todas sus posiciones.
        float[] temporal = new float[this.cantidad()];
        for(int i = 0; i < cantidad(); i++){
            temporal[i] = coeficientes.get(i) < 0? 
                    coeficientes.get(i) * NEGATIVO : coeficientes.get(i);
        }
        
        return new Funcion(temporal);
    }
    
    /**
     * Devuelve el valor mas pequeño de la funcion, dando la opcion de ignorar,
     * el cero.
     * @param ignorarCero Indica si el cero debe ser tomado como valor mas pequeño.
     * @return 
     */
    public int menor(boolean ignorarCero){
        
        int menor = 0;
        if(ignorarCero){
            while(coeficientes.get(menor) == 0 && menor < cantidad()){
                menor++;
            }
            if(coeficientes.get(menor) == 0){
                System.out.println("[Funcion.menor()] Todos los valores de la funcion son cero");
            }
        }
        
        for(int i = menor + 1; i < cantidad(); i++){
            if(coeficientes.get(menor) > coeficientes.get(i)){
                if(ignorarCero){
                    menor = coeficientes.get(i) == 0? menor: i;
                }else{
                    menor = i;
                }
            }
        }
        
        return menor;
    }
    
    /**
     * Retorna la cantidad de coeficientes de la funcion.
     * @return Cantidad de coeficientes.
     */
    public int cantidad(){
        return coeficientes.size();
    }
    
    /**
     * Indica si algun elemento de la funcion es negativo.
     * @return booleano, true si algun elemento es negativo, false si no hay negativos.
     */
    public boolean tieneNegativos(){
        for(Float f: coeficientes){
            if(f < 0){
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Indica si todos los elementos de la funcion son negativos, se considera el
     * valor cero como un negativo.
     * @return booleano, true si todos son negativos, false en caso contrario.
     */
    public boolean todosNegativos(){
        for(Float f: coeficientes){
            if(f > 0){
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Indica si todos los elementos de la funcion son cero.
     * @return booleano, true si todos valen cero, false en caso contrario.
     */
    public boolean tieneCeros(){
        for(Float f: coeficientes){
            if(f != 0){
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Imprime la funcion lineal bajo el formato ax1+bx2+cx3...zxN.
     */
    public void imprimir(){
        int x = 1; 
        for(Float coeficiente: coeficientes){
            System.out.print(coeficiente + "x" + x++ + " ");
        }
    }
}
