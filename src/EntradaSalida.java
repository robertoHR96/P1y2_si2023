import java.io.*;
import java.util.Objects;
import java.util.StringTokenizer;


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
     * Representa el nombre del fichero de clave.
     */
    private String ficheroClave = "";

    /**
     * Indica si la traza está habilitada o deshabilitada.
     */
    private boolean traza = false;

    /**
     * Lee la entrada desde un fichero.
     * @return El contenido del fichero de entrada.
     */
    public String leerEntrada() {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroEntrada))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea);
                contenido.append(System.lineSeparator()); // Agregar un separador de línea si es necesario
            }
        } catch (IOException e) {
            print("Error: Fichero de entrada no valido");
        }
        return contenido.toString();
    }

    /**
     * Lee una matriz de enteros (clave) desde un fichero.
     * @return Una matriz de enteros 3x3 que representa la clave.
     */
    public Integer[][] leerClave() {
        Integer [][] matriz = new Integer[3][3];
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroClave))) {
            for (int i = 0; i < 3; i++) {
                String linea = br.readLine();
                StringTokenizer tokenizer = new StringTokenizer(linea);
                for (int j = 0; j < 3; j++) {
                    if (tokenizer.hasMoreTokens()) {
                        matriz[i][j] = mod27(Integer.parseInt(tokenizer.nextToken()));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            print("Error: Fichero de clave no valido");
        } catch (IOException e) {
            print("Error: Fichero de clave no valido");
        }
        return matriz;
    }

    /**
     * Escribe una cadena en el fichero de salida.
     * @param salida La cadena a escribir en el fichero de salida.
     */
    public void escribirSalida(String salida) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida))) {
            bw.write(salida);
        } catch (IOException e) {
        }
    }

    /**
     * Obtiene el nombre del fichero de entrada.
     * @return El nombre del fichero de entrada.
     */
    public String getFicheroEntrada() {
        return ficheroEntrada;
    }

    /**
     * Establece el nombre del fichero de entrada.
     * @param ficheroEntrada El nombre del fichero de entrada a establecer.
     */
    public void setFicheroEntrada(String ficheroEntrada) {
        this.ficheroEntrada = ficheroEntrada;
    }

    /**
     * Obtiene el nombre del fichero de salida.
     * @return El nombre del fichero de salida.
     */
    public String getFicheroSalida() {
        return ficheroSalida;
    }

    /**
     * Establece el nombre del fichero de salida.
     * @param ficheroSalida El nombre del fichero de salida a establecer.
     */
    public void setFicheroSalida(String ficheroSalida) {
        this.ficheroSalida = ficheroSalida;
    }

    /**
     * Obtiene el estado de la traza.
     * @return true si la traza está habilitada, false si está deshabilitada.
     */
    public boolean isTraza() {
        return traza;
    }

    /**
     * Establece el estado de la traza.
     * @param traza true para habilitar la traza, false para deshabilitarla.
     */
    public void setTraza(boolean traza) {
        this.traza = traza;
    }

    /**
     * Obtiene el nombre del fichero de clave.
     * @return El nombre del fichero de clave.
     */
    public String getFicheroClave() {
        return ficheroClave;
    }

    /**
     * Establece el nombre del fichero de clave.
     * @param ficheroClave El nombre del fichero de clave a establecer.
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
