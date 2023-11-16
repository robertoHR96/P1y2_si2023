import javax.crypto.SecretKey;
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

    public byte[] leerEntradaDesCifrar() {
        File fichero;
        try {
            fichero = new File(ficheroEntrada);
            FileInputStream ficheroLeer = new FileInputStream(fichero);
            print("-------------------------------------");
            print("📖 Leyendo fichero de entrada: " + ficheroEntrada);
            byte b;
            byte[] ciptogram = new byte[(int) fichero.length()];
            int i = 0;
            while ((b = (byte) ficheroLeer.read()) != -1) {
                ciptogram[i] = b;

                i++;
            }
            return ciptogram;
        } catch (FileNotFoundException e) {
            print("-- Error al leer el fichero");
        } catch (IOException e) {
            print("-- Error al leer el fichero");
        }

        return null;
    }

    /**
     * Lee la entrada desde un fichero.
     *
     * @return El contenido del fichero de entrada como una cadena.
     */
    public String leerEntradaCifrar() {
        // StringBuilder para almacenar el contenido del archivo
        StringBuilder contenido = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroEntrada), "UTF-8"))) {
            // Imprime un mensaje informando que se está leyendo el archivo de entrada
            print("-------------------------------------");
            print("📖 Leyendo fichero de entrada: " + ficheroEntrada);
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

    public void escribirSalidaCifrar(byte[] bufferCifrado) {
        try {
            File fichero;
            fichero = new File(ficheroSalida);
            FileOutputStream ficheroSal;
            ficheroSal = new FileOutputStream(fichero);
            print("-------------------------------------");
            print("🖨️ Escribiendo fichero de salida: " + ficheroSalida);

            ficheroSal.write(bufferCifrado);
            ficheroSal.close();
        } catch (Exception e) {
            print("-- Error al escribir en el fichero");
        }
    }

    /**
     * Escribe una cadena en el fichero de salida.
     *
     * @param salida La cadena a escribir en el fichero de salida.
     */
    public void escribirSalidaDescifrar(String salida) {
        try {
            File archivo = new File(this.ficheroSalida);
            // Si el archivo ya existe, lo borramos
            if (archivo.exists()) {
                archivo.delete();
            }
            // Creamos un nuevo archivo
            archivo.createNewFile();
            print("-------------------------------------");
            print("🖨️ Escribiendo fichero de salida: " + ficheroSalida);
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo), "UTF-8"))) {
                bw.write(salida);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
    }

    public SecretKey leerClave() {
        SecretKey clave = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ficheroClave))) {
            clave = (SecretKey) in.readObject();
            print("-------------------------------------");
            print("\uD83D\uDD0FClave leída desde el archivo: " + ficheroClave);
        } catch (IOException | ClassNotFoundException e) {
            print("Error: Fichero de clave " + ficheroClave + " no válido");
        }
        return clave;
    }
    public void escribirClave(SecretKey clave){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ficheroClave))) {
            out.writeObject(clave);
            print("-------------------------------------");
            print("\uD83D\uDD10Clave guardada en el archivo: " + ficheroClave);
        } catch (IOException e) {
            print("Error: Fichero de clave " + ficheroClave + " no válido");
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
     *
     * @return Copia profrunda de la instancia del propio objeto
     */
    public EntradaSalida copy() {
        EntradaSalida es = new EntradaSalida();
        es.ficheroSalida = this.ficheroSalida;
        es.ficheroEntrada = this.ficheroEntrada;
        es.traza = this.traza;
        es.ficheroClave = this.ficheroClave;
        return es;
    }

}
