import java.io.*;
import java.util.Objects;
import java.util.StringTokenizer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class EntradaSalida {
    /**
     * Representa el nombre del fichero de entrada.
     */
    private String ficheroEntrada = "";

    /**
     * Representa el nombre del fichero de salida.
     */
    private String ficheroSalida = "";

    /**
     * Representa el nombre del fichero de clave.txt.
     */
    private String ficheroClave = "";

    /**
     * Indica si la traza está habilitada o deshabilitada.
     */
    private boolean traza = true;
    public EntradaSalida(){
        this.traza = true;
        this.ficheroEntrada="entrada.txt";
        this.ficheroSalida="salida.txt";

    }
    /**
     * Lee la entrada desde un fichero.
     *
     * @return El contenido del fichero de entrada como una cadena.
     */
    public String leerEntrada() {
        // StringBuilder para almacenar el contenido del archivo
        StringBuilder contenido = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroEntrada), "UTF-8"))) {
            // Imprime un mensaje informando que se está leyendo el archivo de entrada
            print("-------------------------------------");
            print("Leyendo fichero de entrada: " + ficheroEntrada);

            String linea;
            // Lee cada línea del archivo y la agrega al StringBuilder
            while ((linea = br.readLine()) != null) {
                contenido.append(linea);
                // Verifica si se ha alcanzado el límite de 1000 caracteres y sale del bucle si es así
                if (contenido.length() >= 1000) {
                    break;
                }
            }
        } catch (IOException e) {
            // En caso de un error de lectura, imprime un mensaje de error
            print("Error: Fichero de entrada no válido");
        }

        // Convierte el contenido del archivo en una cadena y la devuelve
        return contenido.toString();
    }

    /**
     * Escribe una cadena en el fichero de salida.
     *
     * @param salida La cadena a escribir en el fichero de salida.
     */
    public void escribirSalida(String salida) {
        print("-------------------------------------");
        print("Escribiendo en fichero de salia: " + ficheroSalida);
        try {
            File archivo = new File(this.ficheroSalida);

            // Si el archivo ya existe, lo borramos
            if (archivo.exists()) {
                archivo.delete();
            }

            // Creamos un nuevo archivo
            archivo.createNewFile();

            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo), "UTF-8"))) {
                // Resto del código de escritura...

                bw.write(salida);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
    }

    /**
     * Lee una matriz de enteros (clave.txt) desde un fichero.
     *
     * @return Una matriz de enteros 3x3 que representa la clave.txt.
     */
    public Integer[][] leerClave() {
        // Crea una matriz de 3x3 para almacenar la clave.txt
        Integer[][] matriz = new Integer[3][3];

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroClave), "UTF-8"))) {
            // Lee el fichero de clave.txt línea por línea
            String linea = br.readLine();
            StringTokenizer tokenizer = new StringTokenizer(linea);
            for (int i = 0; i < 3; i++) {
                // Procesa cada token en la línea y llena la matriz
                for (int j = 0; j < 3; j++) {
                    if (tokenizer.hasMoreTokens()) {
                        // Convierte el token a un entero y aplica la función mod27
                        matriz[i][j] = mod27(Integer.parseInt(tokenizer.nextToken()));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Maneja la excepción si el fichero de clave.txt no se encuentra
            print("Error: Fichero de clave.txt no válido");
        } catch (IOException e) {
            // Maneja la excepción si ocurre un error de lectura en el fichero de clave.txt
            print("Error: Fichero de clave.txt no válido");
        }

        // Devuelve la matriz de clave.txt procesada
        return matriz;
    }


    public Integer[][] leerClaveInversa() {
        return calcularMatrizInversa(leerClave());
    }

    /**
     * Calcula la matriz inversa de una matriz dada.
     *
     * @param matriz La matriz de entrada de la que se calculará la inversa.
     * @return La matriz inversa o null si la matriz no es invertible.
     */
    public Integer[][] calcularMatrizInversa(Integer[][] matriz) {
        // Calcula el determinante de la matriz de entrada.
        int det = calcularDeterminante(matriz);

        // Calcula la matriz adjunta de la matriz de entrada.
        Integer[][] adjunta = calcularMatrizAdjunta(matriz);

        // Inicializa la matriz inversa con el mismo tamaño que la matriz de entrada.
        Integer[][] inversa = new Integer[matriz.length][matriz[0].length];

        // Verifica si la matriz es invertible (determinante no igual a 0).
        if (det == 0) {
            System.out.println("Error: La matriz no es invertible, el determinante es 0");
            return null; // La matriz no es invertible, por lo que no se puede calcular la inversa.
        } else {
            // Calcula los elementos de la matriz inversa dividiendo los elementos de la adjunta por el determinante.
            det = modInverse(det);
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    inversa[i][j] = mod27(adjunta[i][j] * det); // multiplico por el inverso del determinante (algoritmo extendido de uclides)
                }
            }
        }

        return inversa;
    }

    /**
     * Calcula el inverso del determinante utilizando el algoritmo extendido de Euclides.
     *
     * @param det El determinante de la matriz.
     * @return El inverso del determinante.
     */
    public int modInverse(int det) {
        det = mod27(det);
        for (int x = 1; x < 27; x++) {
            if (mod27(det * x) == 1) {
                return x;
            }
        }
        return -1; // Inverso no encontrado (matriz no invertible).
    }

    /**
     * Calcula el determinante de una matriz 3x3.
     *
     * @param matriz La matriz 3x3 de la cual se calculará el determinante.
     * @return El valor del determinante de la matriz.
     */
    public int calcularDeterminante(Integer[][] matriz) {
        int det = 0;
        if (matriz.length != 3 || matriz[0].length != 3)
            print("Error: (Calculo determinante) La matriz ha de ser de 3x3");
        else {
            // Calcula el determinante usando la fórmula específica para matrices 3x3.
            det = matriz[0][0] * mod27(matriz[1][1] * matriz[2][2] - matriz[1][2] * matriz[2][1])
                    - matriz[0][1] * mod27((matriz[1][0]) * (matriz[2][2]) - (matriz[1][2]) * (matriz[2][0]))
                    + matriz[0][2] * mod27((matriz[1][0]) * (matriz[2][1]) - (matriz[1][1]) * (matriz[2][0]));
        }
        return mod27(det);
    }

    /**
     * Calcula la matriz adjunta de una matriz 3x3.
     *
     * @param matriz La matriz 3x3 de la cual se calculará la matriz adjunta.
     * @return La matriz adjunta de la matriz de entrada.
     */
    public Integer[][] calcularMatrizAdjunta(Integer[][] matriz) {
        Integer[][] matrizAdjunta = new Integer[3][3];
        if (matriz.length != 3 || matriz[0].length != 3) print("Error: (Calculo adjunta) La matriz ha de ser de 3x3");
        else {
            // Calcula la matriz adjunta utilizando la fórmula específica para matrices 3x3.
            matrizAdjunta[0][0] = mod27(matriz[1][1] * matriz[2][2] - matriz[1][2] * matriz[2][1]);
            matrizAdjunta[0][1] = -mod27(matriz[0][1] * matriz[2][2] - matriz[0][2] * matriz[2][1]);
            matrizAdjunta[0][2] = mod27(matriz[0][1] * matriz[1][2] - matriz[0][2] * matriz[1][1]);

            matrizAdjunta[1][0] = -mod27(matriz[1][0] * matriz[2][2] - matriz[1][2] * matriz[2][0]);
            matrizAdjunta[1][1] = mod27(matriz[0][0] * matriz[2][2] - matriz[0][2] * matriz[2][0]);
            matrizAdjunta[1][2] = -mod27(matriz[0][0] * matriz[1][2] - matriz[0][2] * matriz[1][0]);

            matrizAdjunta[2][0] = mod27(matriz[1][0] * matriz[2][1] - matriz[1][1] * matriz[2][0]);
            matrizAdjunta[2][1] = -mod27(matriz[0][0] * matriz[2][1] - matriz[0][1] * matriz[2][0]);
            matrizAdjunta[2][2] = mod27(matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0]);
        }

        return matrizAdjunta;
    }


    /**
     * Obtiene el nombre del fichero de entrada.
     *
     * @return El nombre del fichero de entrada.
     */
    public String getFicheroEntrada() {
        return ficheroEntrada;
    }

    /**
     * Establece el nombre del fichero de entrada.
     *
     * @param ficheroEntrada El nombre del fichero de entrada a establecer.
     */
    public void setFicheroEntrada(String ficheroEntrada) {
        this.ficheroEntrada = ficheroEntrada;
    }

    /**
     * Obtiene el nombre del fichero de salida.
     *
     * @return El nombre del fichero de salida.
     */
    public String getFicheroSalida() {
        return ficheroSalida;
    }

    /**
     * Establece el nombre del fichero de salida.
     *
     * @param ficheroSalida El nombre del fichero de salida a establecer.
     */
    public void setFicheroSalida(String ficheroSalida) {
        this.ficheroSalida = ficheroSalida;
    }

    /**
     * Obtiene el estado de la traza.
     *
     * @return true si la traza está habilitada, false si está deshabilitada.
     */
    public boolean isTraza() {
        return traza;
    }

    /**
     * Establece el estado de la traza.
     *
     * @param traza true para habilitar la traza, false para deshabilitarla.
     */
    public void setTraza(boolean traza) {
        this.traza = traza;
    }

    /**
     * Obtiene el nombre del fichero de clave.txt.
     *
     * @return El nombre del fichero de clave.txt.
     */
    public String getFicheroClave() {
        return ficheroClave;
    }

    /**
     * Establece el nombre del fichero de clave.txt.
     *
     * @param ficheroClave El nombre del fichero de clave.txt a establecer.
     */
    public void setFicheroClave(String ficheroClave) {
        this.ficheroClave = ficheroClave;
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
     * Calcula el módulo 26 de un número entero.
     *
     * @param numero El número entero del cual se calculará el módulo 26.
     * @return El resultado del cálculo del módulo 26.
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
