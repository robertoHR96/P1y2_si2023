import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

/**
 * La clase `Hill` implementa un cifrado y descifrado de texto utilizando el cifrado Hill.
 * Este cifrado opera en bloques de texto y utiliza matrices como parte del proceso.
 * La clase proporciona métodos para cifrar y descifrar texto, así como configurar claves y controlar opciones de traza.
 */
public class Hill {
    // Texto plano sin cifrar/descifrar
    private String entrada = "";
    // Texto cifrado/desCifrado
    private String salida = "";
    // Variable de estado para la bandera "TRAZA" (inicializada en "true" por defecto)
    private boolean traza = true;
    // Matriz de números con la clave.txt para cifrar los datos
    private Integer[][] clave = new Integer[3][3];
    // Matriz inversa de números con la clave.txt para cifrar los datos
    private Integer[][] claveInversa = new Integer[3][3];
    // Variable de estado para la bandera "CODIFICA" (inicializada en "false" por defecto)
    private boolean codifica = true;
    // Lista de letras par ael cifrado
    private String[] listaLetras = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * Constructor por defecto de la clase
     */
    public Hill() {
        this.codifica = true;
        this.traza = true;
        this.salida = "";
        this.entrada = "";
    }

    /**
     * Ejecuta el cifrado/descifrado de la variable entrada y la deja en salida
     *
     * @return Texto cifrado/desCifrado.
     */
    public String cifrar() {
        codificar();
        // si se va a descifrar la entrada tiene que se multiplos de 3
        return this.salida;
    }

    /**
     * Realiza la codificación o descodificación de un texto utilizando matrices y una clave de cifrado.
     * <p>
     * Este método lleva a cabo la codificación o descodificación de un texto de entrada utilizando matrices y una clave de cifrado.
     * El proceso implica el cálculo de matrices, la multiplicación de estas matrices y la generación del texto de salida.
     * <p>
     * Si la variable de clase 'codifica' es verdadera, se realizará una codificación. Si es falsa, se llevará a cabo
     * una descodificación. El resultado se almacena en la variable de instancia 'salida'.
     */
    public void codificar() {


        LinkedList<Integer[][]> listaMatrices = new LinkedList<Integer[][]>();
        LinkedList<Integer[][]> listaMatricesCifradas = new LinkedList<Integer[][]>();

        print("-------------------------------------");
        if (codifica) print("Calculando matrices para cifrar texto...");
        else print("Calculando matrices para des-cifrar texto...");

        calculoDeMatrices(listaMatrices);

        print("-------------------------------------");
        if (codifica) print("Multiplicando matrices para cifrar texto...");
        else print("Multiplicando matrices para des-cifrar texto...");

        Iterator it = listaMatrices.iterator();
        while (it.hasNext()) {
            Integer[][] matriz = (Integer[][]) it.next();
            Integer[][] matrizCifrada = multiplicarMatrices(matriz);
            listaMatricesCifradas.add(matrizCifrada);
            print("-------------------------------------");
            print("Texto  x  Clave = matriz cifrada");
            if (this.traza) mostrarMatrices(matriz, matrizCifrada);
        }

        this.salida = generarTextoSalida(listaMatricesCifradas);

        print("-------------------------------------");
        if (codifica) print("Texto sin cifrado: " + this.entrada);
        else print("Texto sin des-cifrado: " + this.entrada);
        print("-------------------------------------");
        if (codifica) print("Texto cifrado: " + this.salida);
        else print("Texto des-cifrado: " + this.salida);

    }

    /**
     * Genera un texto de salida a partir de una lista de matrices cifradas.
     * <p>
     * Este método toma una lista de matrices cifradas, donde cada matriz es una matriz de enteros 3x3,
     * y convierte estas matrices en un texto de salida. Cada elemento de la matriz se usa como índice para
     * acceder a un arreglo de letras, y estas letras se concatenan para formar el texto de salida.
     *
     * @param listaMatricesCifradas Una lista de matrices cifradas. Cada matriz debe ser de tamaño 3x3 y contener enteros.
     * @return El texto de salida generado a partir de las matrices cifradas.
     */
    public String generarTextoSalida(LinkedList<Integer[][]> listaMatricesCifradas) {
        String exit = "";
        Iterator it = listaMatricesCifradas.iterator();
        while (it.hasNext()) {
            Integer[][] matriz = (Integer[][]) it.next();
            for (int e = 0; e < 3; e++) {
                for (int j = 0; j < 3; j++) {
                    exit = exit + this.listaLetras[matriz[j][e]];
                }
            }
        }
        return exit;
    }

    /**
     * Realiza un cálculo de matrices a partir de una entrada de texto y almacena las matrices en una lista.
     *
     * @param listaMatrices Una lista en la que se almacenarán las matrices calculadas.
     */
    public void calculoDeMatrices(LinkedList<Integer[][]> listaMatrices) {
        // Divide la entrada en caracteres individuales
        String[] splitTexto = this.entrada.split("");

        // Asegura que la longitud de splitTexto sea un múltiplo de 9, rellenando con 'A' si es necesario
        // Crear una instancia de Random
        Random random = new Random();

// Generar un número aleatorio en el rango de 0 a 25 (incluyendo 0 y 25)
        while ((splitTexto.length % 9) != 0) {
            this.entrada = this.entrada + listaLetras[random.nextInt(26)];
            splitTexto = this.entrada.split("");
        }

        // Matriz auxiliar para almacenar temporalmente las submatrices
        Integer[][] matrizAux = new Integer[3][3];

        // Procesa el texto dividido en matrices de 3x3 y las agrega a la lista de matrices
        int contador = 0;
        for (int i = 0; i < splitTexto.length; i = i + 9) {
            for (int e = 0; e < 3; e++) {
                for (int j = 0; j < 3; j++) {
                    // Convierte el carácter en un valor entero y lo almacena en la matriz auxiliar
                    matrizAux[j][e] = pasarCaracterMod27(splitTexto[contador]);
                    contador++;
                }
            }
            listaMatrices.add(matrizAux);
            matrizAux = new Integer[3][3];
        }
    }

    /**
     * Convierte un carácter en un valor entero modulo 27.
     *
     * @param str El carácter a convertir en entero.
     * @return El valor entero resultante, 0 si el carácter no se encuentra en la lista de letras.
     */
    public Integer pasarCaracterMod27(String str) {
        // Busca el carácter en la lista de letras y devuelve su índice como entero
        for (Integer i = 0; i < listaLetras.length; i++) {
            if (listaLetras[i].equals(str)) {
                return i;
            }
        }
        // Si el carácter no se encuentra en la lista, devuelve 0
        return 0;
    }


    /**
     * Muestra tres matrices en formato especial, con una 'x' y un '=' en la fila del medio,
     * para visualizar la multiplicación de matrices.
     *
     * @param matriz        La matriz del texto plano.
     * @param matrizCifrada La matriz cifrada.
     */
    public void mostrarMatrices(Integer[][] matriz, Integer[][] matrizCifrada) {
        for (int i = 0; i < 3; i++) {
            // Imprimir la fila de la matriz 1
            for (int j = 0; j < 3; j++) {
                System.out.print(matriz[i][j] + " ");
            }

            // Imprimir una 'x' en la fila del medio
            if (i == 1) {
                System.out.print("x ");
            } else {
                System.out.print("  "); // Espacio en blanco en lugar de números
            }

            // Imprimir la fila de la matriz 2
            for (int j = 0; j < 3; j++) {
                if (codifica) System.out.print(this.clave[i][j] + " ");
                else System.out.print(this.claveInversa[i][j] + " ");
            }

            // Imprimir un '=' en la fila del medio
            if (i == 1) {
                System.out.print("= ");
            } else {
                System.out.print("  "); // Espacio en blanco en lugar de números
            }

            // Imprimir la fila de la matriz 3
            for (int j = 0; j < 3; j++) {
                System.out.print(matrizCifrada[i][j] + " ");
            }

            // Ir a la siguiente línea para la siguiente fila
            System.out.println();
        }
    }


    /**
     * Realiza la multiplicación de dos matrices cuadradas de enteros.
     * <p>
     * Esta función toma una matriz cuadrada de enteros y la multiplica por la matriz clave.txt.
     * La matriz resultante tendrá el mismo tamaño que la matriz de entrada y contendrá
     * el producto de las dos matrices.
     *
     * @param matrizCifrar La matriz cuadrada de enteros que se va a multiplicar.
     * @return Una nueva matriz cuadrada que representa el resultado de la multiplicación.
     * Si la matriz de entrada no es de tamaño 3x3, se devuelve una matriz vacía de 3x3.
     */
    public Integer[][] multiplicarMatrices(Integer[][] matrizCifrar) {

        // Matriz para almacenar el resultado de la multiplicación
        Integer[][] matrizMultiplicada = new Integer[3][3];

        // Contador para iterar a través de las filas de la matriz resultado
        int contador = 0;

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                int val1 = 0;
                // Realiza la multiplicación y acumula el resultado en val1
                for (int e = 0; e < 3; e++) {
                    if (codifica) val1 = (matrizCifrar[e][i] * this.clave[j][e]) + val1;
                    else val1 = (matrizCifrar[e][i] * this.claveInversa[j][e]) + val1;
                }
                // Aplica módulo 27 al resultado y almacénalo en la matriz resultado
                matrizMultiplicada[j][i] = mod27(val1);
            }
        }
        return matrizMultiplicada;
    }

    /**
     * Establece una clave.txt por defecto
     */
    public void setClaveDefault() {
        // Valores de la primera fila

        this.clave[0][0] = 1;
        this.clave[0][1] = 2;
        this.clave[0][2] = 3;

        // Valores de la segunda fila
        this.clave[1][0] = 0;
        this.clave[1][1] = 4;
        this.clave[1][2] = 5;

        // valores de la tercera fila
        this.clave[2][0] = 1;
        this.clave[2][1] = 0;
        this.clave[2][2] = 6;
        EntradaSalida es = new EntradaSalida();
        Integer[][] clavein = es.calcularMatrizInversa(this.clave);
        if (clavein != null) {
            setClaveInversa(clavein);
        }
    }

    /**
     * Obtine el texto plano sin cifrar
     *
     * @return Texto plano sin cifrar
     */
    public String getEntrada() {
        return entrada;
    }

    /**
     * Establece el valor de la variable entrada
     *
     * @param entrada Cadena de entrada de texto a cifrar/descifrar
     */
    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    /**
     * Obtiene la salida del texto cifrado
     *
     * @return Texto cifrado
     */
    public String getSalida() {
        return salida;
    }

    /**
     * Establece el valor de la variable salida
     *
     * @param salida Cadena de texto cifrado/descifrar a devolver
     */
    public void setSalida(String salida) {
        this.salida = salida;
    }

    /**
     * Obtiene el estado de la bandera "TRAZA".
     *
     * @return El estado actual de la bandera "TRAZA".
     */
    public boolean isTraza() {
        return traza;
    }

    /**
     * Establece el estado de la bandera "TRAZA".
     *
     * @param traza El nuevo estado de la bandera "TRAZA".
     */
    public void setTraza(boolean traza) {
        this.traza = traza;
    }

    /**
     * Obtiene la clave.txt de cifrado y descifrado
     *
     * @return Una matriz con la clave.txt de cifrado
     */
    public Integer[][] getClave() {
        return clave;
    }

    /**
     * Establece el valor de la clave.txt de cifrado y descifrado
     *
     * @param clave Clave con la que se va a cifrar el texto
     */
    public void setClave(Integer[][] clave) {
            for (int i = 0; i < 3; i++) {
                for (int e = 0; e < 3; e++) {
                    this.clave[i][e] = clave[i][e];
                }
            }
    }

    /**
     * Obtiene la clave.txt de cifrado y descifrado
     *
     * @return Una matriz con la clave.txt de cifrado
     */
    public Integer[][] getClaveInversa() {
        return claveInversa;
    }

    /**
     * Establece el valor de la clave.txt de cifrado y descifrado
     *
     * @param clave Clave con la que se va a descifrar el texto
     */
    public void setClaveInversa(Integer[][] clave) {
        for (int i = 0; i < 3; i++) {
            for (int e = 0; e < 3; e++) {
                this.claveInversa[i][e] = clave[i][e];
            }
        }
    }

    /**
     * Obtiene el estado de la bandera "CODIFICA".
     *
     * @return El estado actual de la bandera "CODIFICA".
     */
    public boolean isCodifica() {
        return codifica;
    }

    /**
     * Establece el estado de la bandera "CODIFICA".
     *
     * @param codifica El nuevo estado de la bandera "CODIFICA".
     */
    public void setCodifica(boolean codifica) {
        this.codifica = codifica;
    }

    /**
     * Obtine la lista de letras para el cifrado/descifrado
     *
     * @return Lista de letras del abecedario en mayusculas
     */
    public String[] getListaLetras() {
        return listaLetras;
    }

    /**
     * Define la lista de letras
     *
     * @param listaLetras Lista de caracteres que se pueden cifrar
     */
    public void setListaLetras(String[] listaLetras) {
        this.listaLetras = listaLetras;
    }

    /**
     * Imprime un texto en la consola si la bandera "TRAZA" está activada.
     *
     * @param text El texto a imprimir.
     */
    public void printS(String text) {
        if (traza) System.out.print(text);
    }

    /**
     * Imprime un texto en la consola si la bandera "TRAZA" está activada.
     *
     * @param text El texto a imprimir.
     */
    public void print(String text) {
        if (traza) System.out.println(text);
    }


    /**
     * Devuelve un entero en módulo 27
     *
     * @param numero Numero que se le quiere aplicar
     * @return Número en módulo 27
     */
    public Integer mod27(Integer numero) {
        // Utilizamos el operador de módulo (%) para calcular el módulo 26.
        // Aseguramos que el resultado esté en el rango [0, 25] inclusive.
        Integer resultado = numero % 27;
        if (resultado < 0) {
            // Si el resultado es negativo, lo ajustamos sumando 26.
            resultado += 27;
        }
        return resultado;
    }

}
