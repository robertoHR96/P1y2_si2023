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

            byte[] buffer = new byte[1024]; // Tamaño del buffer, puedes ajustarlo según tus necesidades
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = ficheroLeer.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] ciptogram = outputStream.toByteArray();
            outputStream.close();
            ficheroLeer.close();

            return ciptogram;
        } catch (FileNotFoundException e) {
            print("❌ Error 111: al leer el fichero: " + ficheroEntrada);
        } catch (IOException e) {
            print("❌ Error 112: al leer el fichero: " + ficheroEntrada);
        }

        return null;
    }

    /**
     * Lee el contenido de un fichero de entrada destinado al proceso de descifrado.
     *
     * @return Un array de bytes que representa el contenido del fichero de entrada, o null si hay un error.
     */
    public byte[] leerEntradaDesCifrar_() {
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
            print("❌ Error 111: al leer el fichero: " + ficheroEntrada);
        } catch (IOException e) {
            print("❌ Error 112: al leer el fichero: " + ficheroEntrada);
        }

        return null;
    }

    /**
     * Lee la entrada desde un fichero de entrada destinado al proceso de cifrado
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
            print("❌ Error 121: Fichero de entrada no válido: " + ficheroEntrada);
            return null;
        }

        // Convierte el contenido del archivo en una cadena y la devuelve
        return contenido.toString();
    }

    /**
     * Escribe el resultado cifrado en un fichero de salida.
     *
     * @param bufferCifrado El contenido cifrado a escribir en el fichero de salida.
     */
    public void escribirSalidaCifrar(byte[] bufferCifrado) {
        try {
            File fichero = new File(ficheroSalida);

            File directorio = fichero.getParentFile(); // Obtiene el directorio del fichero

            if (!directorio.exists()) {
                // Si el directorio no existe, se crea
                boolean directorioCreado = directorio.mkdirs();
                if (directorioCreado) {
                    print("📁 Directorio creado: " + directorio.getAbsolutePath());
                    if (!fichero.exists()) {
                        // El fichero no existe, por lo que se crea
                        fichero.createNewFile();
                        print("📁 Fichero creado: " + ficheroSalida);
                    }
                } else {
                    print("❌ Error 131: No se pudo crear el directorio: " + directorio.getAbsolutePath());
                }
            }

            FileOutputStream ficheroSal = new FileOutputStream(fichero);
            print("-------------------------------------");
            print("🖨️ Escribiendo fichero de salida: " + ficheroSalida);

            ficheroSal.write(bufferCifrado);
            ficheroSal.close();
        } catch (Exception e) {
            print("❌ Error 132: al escribir en el fichero: " + ficheroSalida);
        }
    }

    /**
     * Escribe una cadena en el fichero de salida.
     *
     * @param salida La cadena a escribir en el fichero de salida.
     */
    public void escribirSalidaDescifrar(String salida) {
        if(salida != null){
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
            print("❌ Error 141: ficheor de salida no valido: " + ficheroSalida);
        }
        }else{

            print("❌ Error 141: No se ha selecionado ningun texto de salida.");
        }
    }

    /**
     * Lee una clave almacenada en un archivo y la devuelve como objeto SecretKey.
     *
     * @return La clave almacenada en el archivo de clave o null si hay un error o el archivo no es válido.
     */
    public SecretKey leerClave() {
        SecretKey clave = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ficheroClave))) {
            clave = (SecretKey) in.readObject();
            print("-------------------------------------");
            print("\uD83D\uDD0FClave seleccionada desde el archivo: " + ficheroClave);
            return clave;
        } catch (IOException | ClassNotFoundException e) {
            print("❌ Error 151: Fichero de clave " + ficheroClave + " no válido");
            return null;
        }
    }

    /**
     * Escribe una clave en un archivo especificado.
     *
     * @param clave La clave a escribir en el archivo.
     */
    public void escribirClave(SecretKey clave) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ficheroClave))) {
            out.writeObject(clave);
            print("-------------------------------------");
            print("\uD83D\uDD10Clave guardada en el archivo: " + ficheroClave);
        } catch (IOException e) {
            print("❌ Error 161: Fichero de clave " + ficheroClave + " no válido");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntradaSalida that)) return false;
        return isTraza() == that.isTraza() && Objects.equals(getFicheroEntrada(), that.getFicheroEntrada()) && Objects.equals(getFicheroSalida(), that.getFicheroSalida()) && Objects.equals(getFicheroClave(), that.getFicheroClave());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFicheroEntrada(), getFicheroSalida(), getFicheroClave(), isTraza());
    }

    @Override
    public String toString() {
        return "EntradaSalida{" +
                "ficheroEntrada='" + ficheroEntrada + '\'' +
                ", ficheroSalida='" + ficheroSalida + '\'' +
                ", ficheroClave='" + ficheroClave + '\'' +
                ", traza=" + traza +
                '}';
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
