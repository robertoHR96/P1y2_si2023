import java.io.*;
import java.util.Objects;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que gestiona la entrada y salida de datos, incluyendo la lectura de ficheros y escritura en ficheros.
 * Además, proporciona funcionalidad para trabajar con ficheros de clave.txt y realizar cálculos relacionados con el cifrado AES.
 */
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

    /**
     * Constructor por defecto de la clase
     */
    public EntradaSalida() {
        this.traza = true;
        this.ficheroEntrada = "entrada.txt";
        this.ficheroSalida = "salida.txt";

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
    public String leerClave() {
        // Crea una matriz de 3x3 para almacenar la clave.txt
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroClave), "UTF-8"))) {
            // Lee el fichero de clave.txt línea por línea
            return br.readLine();

        } catch (FileNotFoundException e) {
            // Maneja la excepción si el fichero de clave.txt no se encuentra
            print("Error: Fichero de clave "+ficheroClave+" no válido");
            return null;
        } catch (IOException e) {
            // Maneja la excepción si ocurre un error de lectura en el fichero de clave.txt
            print("Error: Fichero de clave "+ficheroClave+" no válido");
            return null;
        }
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
     * Realiza una copia pofrunda de la isntancia del objeto
     * @return Copia profrunda de la instancia del propio objeto
     */
    public EntradaSalida copy(){
        EntradaSalida es = new EntradaSalida();
        es.ficheroSalida = this.ficheroSalida;
        es.ficheroEntrada = this.ficheroEntrada;
        es.traza = this.traza;
        es.ficheroClave = this.ficheroClave;
        return es;
    }

}
