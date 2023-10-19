import java.io.*;
import java.util.Objects;

public class Hill {
    // Texto plano sin cifrar/descifrar
    private String entrada = "";
    // Texto cifrado/desCifrado
    private String salida = "";
    // Variable de estado para la bandera "TRAZA" (inicializada en "true" por defecto)
    private boolean traza = false;
    // Matriz de números con la clave para cifrar los datos
    private Integer[][] clave = new Integer[3][3];
    // Variable de estado para la bandera "CODIFICA" (inicializada en "false" por defecto)
    private boolean codifica = false;

    /**
     * Ejecuta el cifrado/descifrado de la variable entrada y la deja en salida
     *
     * @return Texto cifrado/desCifrado.
     */
    public String cifrar() {
        if (codifica) codificar();
        else desCodificar();
        // si se va a descifrar la entrada tiene que se multiplos de 3
        return this.salida;
    }

    /**
     * Cifra el contenido de la variable entrada
     */
    public void codificar() {

        Integer[][] matrizCifrar = new Integer[3][3];

        print(this.entrada);
        matrizCifrar[0][0] = 4;
        matrizCifrar[0][1] = 12;
        matrizCifrar[0][2] = 15;

        // Valores de la segunda fila
        matrizCifrar[1][0] = 9;
        matrizCifrar[1][1] = 16;
        matrizCifrar[1][2] = 19;

        // valores de la tercera fila
        matrizCifrar[2][0] = 4;
        matrizCifrar[2][1] = 11;
        matrizCifrar[2][2] = 0;


        print("-------------------------------------");
        print("Multiplicando matrices para cifrar texto...");

        Integer[][] resultadoMultiplicarMatrices = multiplicarMatrices(matrizCifrar);
        print("-------------------------------------");
        print("Texto  x  Clave = matriz cifrada");
        if(this.traza) mostrarMatrices(matrizCifrar, resultadoMultiplicarMatrices);
        setSalida(this.entrada);
    }

    /**
     * Muestra tres matrices en formato especial, con una 'x' y un '=' en la fila del medio,
     * para visualizar la multiplicación de matrices.
     *
     * @param resultadoMultiplicarMatrices La matriz resultante de la multiplicación.
     * @param matrizCifrar La primera matriz la perteneciente al texto plano.
     */
    public void mostrarMatrices(Integer [][] resultadoMultiplicarMatrices, Integer [][] matrizCifrar) {
        for (int i = 0; i < 3; i++) {
            // Imprimir la fila de la matriz 1
            for (int j = 0; j < 3; j++) {
                System.out.print(matrizCifrar[i][j] + " ");
            }

            // Imprimir una 'x' en la fila del medio
            if (i == 1) {
                System.out.print("x ");
            } else {
                System.out.print("  "); // Espacio en blanco en lugar de números
            }

            // Imprimir la fila de la matriz 2
            for (int j = 0; j < 3; j++) {
                System.out.print(this.clave[i][j] + " ");
            }

            // Imprimir un '=' en la fila del medio
            if (i == 1) {
                System.out.print("= ");
            } else {
                System.out.print("  "); // Espacio en blanco en lugar de números
            }

            // Imprimir la fila de la matriz 3
            for (int j = 0; j < 3; j++) {
                System.out.print(resultadoMultiplicarMatrices[i][j] + " ");
            }

            // Ir a la siguiente línea para la siguiente fila
            System.out.println();
        }
    }

    /**
     * Descifra el contenido de la variable de entrada
     */
    public void desCodificar() {
    }

    /**
     * Realiza la multiplicación de dos matrices cuadradas de enteros.
     * <p>
     * Esta función toma una matriz cuadrada de enteros y la multiplica por sí misma.
     * La matriz resultante tendrá el mismo tamaño que la matriz de entrada y contendrá
     * el producto de las dos matrices.
     *
     * @param matrizCifrar La matriz cuadrada de enteros que se va a multiplicar.
     * @return Una nueva matriz cuadrada que representa el resultado de la multiplicación.
     * Si la matriz de entrada no es de tamaño 3x3, se devuelve una matriz vacía de 3x3.
     */
    public Integer[][] multiplicarMatrices(Integer[][] matrizCifrar) {

        Integer[][] matrizMultiplicada = new Integer[3][3];
        int contador = 0;

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                int val1 = 0;
                for (int e = 0; e < 3; e++) {
                    val1 = ( matrizCifrar[e][i] * this.clave[j][e] ) + val1;
                }
                matrizMultiplicada[j][i] = mod27(val1);
            }
        }
        return matrizMultiplicada;
    }

    /**
     * Establece una clave por defecto
     */
    public void setClaveDefault() {
        // Valores de la primera fila

        clave[0][0] = 1;
        clave[0][1] = 2;
        clave[0][2] = 3;

        // Valores de la segunda fila
        clave[1][0] = 0;
        clave[1][1] = 4;
        clave[1][2] = 5;

        // valores de la tercera fila
        clave[2][0] = 1;
        clave[2][1] = 0;
        clave[2][2] = 6;
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
     * @param entrada
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
     * @param salida
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
     * Obtiene la clave de cifrado y descifrado
     *
     * @return Una matriz con la clave de cifrado
     */
    public Integer[][] getClave() {
        return clave;
    }

    /**
     * Establece el valor de la clave de cifrado y descifrado
     *
     * @param clave
     */
    public void setClave(Integer[][] clave) {
        for (int i = 0; i < 3; i++) {
            for (int e = 0; e < 3; e++) {
                this.clave[i][e] = clave[i][e];
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
