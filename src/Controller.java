import java.io.*;

public class Controller {
    // Variable para almacenar el nombre del archivo de configuración
    private String ficheroConfig = "";

    // Variable de estado para la bandera "TRAZA" (inicializada en "true" por defecto)
    private boolean traza = true;

    // Variable de estado para la bandera "CODIFICA" (inicializada en "false" por defecto)
    private boolean codifica = false;

    // Objeto para gestionar Hill (inicializado con una nueva instancia)
    private Hill hill = new Hill();

    // Objeto para gestionar Formateador (inicializado con una nueva instancia)
    private Formateador formateador = new Formateador();

    // Objeto para gestionar EntradaSalida (inicializado con una nueva instancia)
    private EntradaSalida entradaSalida = new EntradaSalida();


    /**
     * Método que ejecuta un proceso de inicio a partir de un archivo de configuración.
     *
     * @throws IOException Excepción lanzada en caso de error de lectura o cierre de archivos.
     */
    public void run() {
        // Crear un objeto File que representa el archivo de configuración.
        File doc = new File(ficheroConfig);
        BufferedReader obj = null;
        String strng = null;
        try {
            // Abrir el archivo de configuración para lectura.
            obj = new BufferedReader(new FileReader(doc));

            // Leer el archivo línea por línea hasta llegar al final.
            while (true) {
                try {
                    // Leer una línea del archivo.
                    if ((strng = obj.readLine()) == null) break; // Si no hay más líneas, salir del bucle.

                    // Ejecutar la orden contenida en la línea leída.
                    ejecutarOrden(strng);
                } catch (IOException e) {
                    System.err.println("Error al leer el fichero de entrada");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al leer el fichero de entrada");
        } finally {
            // Cerrar el archivo de configuración al finalizar.
            try {
                if (obj != null) obj.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el fichero de entrada");
            }
        }
    }

    /**
     * Ejecuta una orden basada en un comando en formato de cadena.
     * <p>
     * Esta función analiza la cadena de entrada, divide los elementos separados por espacios
     * y ejecuta acciones según el comando especificado en la primera posición de la cadena.
     *
     * @param strng La cadena de entrada que contiene el comando y sus argumentos, separados por espacios.
     */
    public void ejecutarOrden(String strng) {
        // Dividir la cadena en palabras separadas por espacios
        String[] splitLinea = strng.split(" ");

        // Comprobar si el primer elemento es "@"
        if (splitLinea[0].equals("@")) {
            // Si es "@", llamar a la función updateBandera con los argumentos especificados
            updateBandera(splitLinea[1], splitLinea[2]);
        }

        // Comprobar si el primer elemento es "&"
        if (splitLinea[0].equals("&")) {
            // Si es "&", imprimir "aspersan"
            print("aspersan");
        }
    }

    /**
     * Actualiza el estado de una bandera según su nombre y estado.
     *
     * @param bandera       El nombre de la bandera ("TRAZA" o "CODIFICA").
     * @param estadoBandera El estado de la bandera ("ON" o "OFF").
     */
    public void updateBandera(String bandera, String estadoBandera) {
        boolean estadoBnd = false;

        // Comprueba si el estado de la bandera es "ON"
        if (estadoBandera.equals("ON")) {
            estadoBnd = true;
        }

        // Comprueba si el estado de la bandera es "OFF"
        if (estadoBandera.equals("OFF")) {
            estadoBnd = false;
        }

        // Si la bandera es "TRAZA", actualiza el estado correspondiente
        if (bandera.equals("TRAZA")) {
            updateEstadoTraza(estadoBnd);
        }

        // Si la bandera es "CODIFICA", actualiza el estado correspondiente
        if (bandera.equals("CODIFICA")) {
            setCodifica(estadoBnd);
        }
    }

    /**
     * Cambia el estado de la bandera "TRAZA" y actualiza objetos relacionados.
     *
     * @param estadoBnd El nuevo estado de la bandera "TRAZA".
     */
    public void updateEstadoTraza(boolean estadoBnd) {
        traza = estadoBnd;
        hill.setTraza(estadoBnd);
        formateador.setTraza(estadoBnd);
        entradaSalida.setTraza(estadoBnd);
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

// ... (Métodos getter y setter para otras propiedades) ...

    /**
     * Imprime un texto en la consola si la bandera "TRAZA" está activada.
     *
     * @param text El texto a imprimir.
     */
    public void print(String text) {
        if (traza) {
            System.out.println(text);
        }
    }

};
