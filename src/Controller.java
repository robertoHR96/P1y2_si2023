import java.io.*;

public class Controller {
    // Variable para almacenar el nombre del archivo de configuración
    private String ficheroConfig = "";

    // Variable de estado para la bandera "TRAZA" (inicializada en "true" por defecto)
    private boolean traza = true;

    // Objeto para gestionar Hill (inicializado con una nueva instancia)
    private Hill hill = new Hill();

    // Objeto para gestionar EntradaSalida (inicializado con una nueva instancia)
    private EntradaSalida entradaSalida = new EntradaSalida();

    /**
     * Se ajustan lo valores a la configuración por defecto
     */
    public void ajustarDefault(){
        this.traza = true;
        hill.setTraza(true);
        hill.setCodifica(true);
        hill.setClaveDefault();
        entradaSalida.setTraza(true);
        entradaSalida.setFicheroEntrada("entrada.txt");
        entradaSalida.setFicheroSalida("salida.txt");

    }
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

        // Se comprueba que el comando que se quiera ejecutar sea válido
        if (splitLinea.length >= 2) {
            // Comprobar si el primer elemento es "@"
            // Si es "@", llamar a la función updateBandera con los argumentos especificados
            if (splitLinea[0].equals("@")) updateBandera(splitLinea[1], splitLinea[2]);

            // Comprobar si el primer elemento es "&"
            // Si es "&", llamar a la función seleccionarComando con el comando especificado
            if (splitLinea[0].equals("&")) seleccionarComando(splitLinea);
        }
    }

    /**
     * Ejecuta el comando que según mande el fichero de configuración
     *
     * @param splitLinea Array de Strings con la línea del comando
     */
    public void seleccionarComando(String[] splitLinea) {
        // Si es "hill", llamar a la función ejecutarHill
        if (splitLinea[1].equals("hill")) ejecutarHill();
        // Si es "ficherosalida" se selecciona el fichero de salida en la clase entradaSalia
        if (splitLinea[1].equals("ficherosalida") && splitLinea.length == 3)
            entradaSalida.setFicheroSalida(splitLinea[2]);
        // Si es "ficheroentrada" se selecciona el fichero de entrada en la clase entradaSalia
        if (splitLinea[1].equals("ficheroentrada") && splitLinea.length == 3)
            entradaSalida.setFicheroEntrada(splitLinea[2]);
        // Si es "clave" se llama a la función getFicheroFormateado con el fichero que contiene la clave
        if (splitLinea[1].equals("clave") && splitLinea.length == 3) seleccionarClave(splitLinea[2]);
        // Si es "formatearentrada" se llama a la función formatearEntrada
        if (splitLinea[1].equals("formateaentrada")) formatearEntrada();
    }

    /**
     * Lee los datos del fichero de entrada y los guarda formateados en el fichero de salida
     */
    public void formatearEntrada() {
        // Se obtienen los datos del fichero de entrada
        String dataFicheroEntrada = entradaSalida.leerEntrada();
        // Se formatea el texto obtenido
        dataFicheroEntrada = formatearTexto(dataFicheroEntrada);
        // Se guarda el texto en el fichero de salida
        entradaSalida.escribirSalida(dataFicheroEntrada);
    }

    /**
     * Selecciona la clave para cifrar y descifrar desde un fichero
     *
     * @param ficheroClave String con el nombre del fichero que contiene la clave
     */
    public void seleccionarClave(String ficheroClave) {
        // Se selecciona el fichero con la clave en el objeto entradaSalida
        entradaSalida.setFicheroClave(ficheroClave);
        // Se lee la clave de entradaSalida y se la asignamos a hill
        hill.setClave(entradaSalida.leerClave());
    }

    /**
     * Realiza el proceso de cifrado/descifrado, recoge la salida de este y la guarda en el fichero
     * de salida.
     */
    public void ejecutarHill() {
        // Se guarda en el objeto hill el texto desde el fichero de entrada que se halla seleccionado
        hill.setEntrada(entradaSalida.leerEntrada());

        // Se escribe el fichero de salida a través del método escribirSalida del objeto entradaSalida
        entradaSalida.escribirSalida(hill.cifrar());
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
        if (estadoBandera.equals("ON")) estadoBnd = true;

        // Comprueba si el estado de la bandera es "OFF"
        if (estadoBandera.equals("OFF")) estadoBnd = false;

        // Si la bandera es "TRAZA", actualiza el estado correspondiente
        if (bandera.equals("TRAZA")) updateEstadoTraza(estadoBnd);

        // Si la bandera es "CODIFICA", actualiza el estado correspondiente
        // de la instancia de la clase Hill
        if (bandera.equals("CODIFICA")) hill.setCodifica(estadoBnd);
    }

    /**
     * Cambia el estado de la bandera "TRAZA" y actualiza objetos relacionados.
     *
     * @param estadoBnd El nuevo estado de la bandera "TRAZA".
     */
    public void updateEstadoTraza(boolean estadoBnd) {
        // Se establece el valor de la variable traza propia
        this.traza = estadoBnd;
        // Se establece el valor del atributo traza de la instancia de la clase hill
        hill.setTraza(estadoBnd);
        // Se establece el valor del atributo traza de la instancia de la clase entradaSalida
        entradaSalida.setTraza(estadoBnd);
    }


    /****************** Getter, Setter, hascode, equlas and toString *****************/
    /******************************************************/
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


// ... (Métodos getter y setter para otras propiedades) ...

    /**
     * Obtiene el nombre del fichero de configuración.
     *
     * @return El nombre del fichero de configuración.
     */
    public String getFicheroConfig() {
        return ficheroConfig;
    }

    /**
     * Establece el nombre del fichero de configuración.
     *
     * @param ficheroConfig El nombre del fichero de configuración a establecer.
     */
    public void setFicheroConfig(String ficheroConfig) {
        this.ficheroConfig = ficheroConfig;
    }

    /**
     * Obtiene una instancia de la clase Hill.
     *
     * @return Una instancia de la clase Hill.
     */
    public Hill getHill() {
        return hill;
    }

    /**
     * Establece una instancia de la clase Hill.
     *
     * @param hill La instancia de la clase Hill a establecer.
     */
    public void setHill(Hill hill) {
        this.hill = hill;
    }

    /**
     * Obtiene una instancia de la clase EntradaSalida.
     *
     * @return Una instancia de la clase EntradaSalida.
     */
    public EntradaSalida getEntradaSalida() {
        return entradaSalida;
    }

    /**
     * Establece una instancia de la clase EntradaSalida.
     *
     * @param entradaSalida La instancia de la clase EntradaSalida a establecer.
     */
    public void setEntradaSalida(EntradaSalida entradaSalida) {
        this.entradaSalida = entradaSalida;
    }

    public String formatearTexto(String texto) {
        return texto;
    }

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
