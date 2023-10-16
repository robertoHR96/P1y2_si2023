import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Controller {
    // Variable para almacenar el nombre del archivo de configuración
    private String ficheroConfig = "";

    // Variable de estado para la bandera "TRAZA" (inicializada en "true" por defecto)
    private boolean traza = true;

    // Objeto para gestionar Hill (inicializado con una nueva instancia)
    private Hill hill = new Hill();

    // Objeto para gestionar EntradaSalida (inicializado con una nueva instancia)
    private EntradaSalida entradaSalida = new EntradaSalida();

    public Controller (){
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
        // Se estraen los tokens del string
        StringTokenizer tokenizerStrng = new StringTokenizer(strng);
        ArrayList<String> tokensList = new ArrayList<>();

        // Agregar los tokens al ArrayList
        while (tokenizerStrng.hasMoreTokens()) {
            tokensList.add(tokenizerStrng.nextToken());
        }

        // Convertir el ArrayList a un array de strings
        // Dividir la cadena en palabras separadas por espacios
        String[] splitLinea = tokensList.toArray(new String[tokensList.size()]);

        // Se comprueba que el comando que se quiera ejecutar sea válido
        if (splitLinea.length >= 2) {
            // Si no es ningun comando válido se muestra el mensaje de error
            // En caso contrario se ejecuta el comando seleccionado
            if (!splitLinea[0].equals("@") && !splitLinea[0].equals("&")) print("Error: Bandera no valido");
            else {
                // Comprobar si el primer elemento es "@"
                // Si es "@", llamar a la función updateBandera con los argumentos especificados
                if (splitLinea[0].equals("@")) updateBandera(splitLinea[1], splitLinea[2]);

                // Comprobar si el primer elemento es "&"
                // Si es "&", llamar a la función seleccionarComando con el comando especificado
                if (splitLinea[0].equals("&")) seleccionarComando(splitLinea);
            }

        } else {
            print("Error: Instrucción no valida");
        }
    }

    /**
     * Ejecuta el comando que según mande el fichero de configuración
     *
     * @param splitLinea Array de Strings con la línea del comando
     */
    public void seleccionarComando(String[] splitLinea) {
        // Verifica si el array splitLinea tiene al menos 2 elementos
        if (splitLinea.length < 2) {
            print("Error: Comando no válido");
            return;
        }
        // Obtiene el segundo elemento del array como el comando
        String comando = splitLinea[1];

        // Utiliza un switch para manejar diferentes comandos
        switch (comando) {
            case "hill":
                ejecutarHill();
                break;
            case "ficherosalida":
                // Establece el fichero de salida en la clase entradaSalida
                if (splitLinea.length == 3) entradaSalida.setFicheroSalida(splitLinea[2]);
                else print("Error: Comando 'ficherosalida' requiere 2 argumentos");
                break;
            case "ficheroentrada":
                // Establece el fichero de entrada en la clase entradaSalida
                if (splitLinea.length == 3) entradaSalida.setFicheroEntrada(splitLinea[2]);
                else print("Error: Comando 'ficheroentrada' requiere 2 argumentos");
                break;
            case "clave":
                // Llama a la función seleccionarClave con el tercer argumento
                if (splitLinea.length == 3) seleccionarClave(splitLinea[2]);
                else print("Error: Comando 'clave' requiere 2 argumentos");
                break;
            case "formateaentrada":
                // Llama a la función formatearEntrada
                formatearEntrada();
                break;
            default:
                // Maneja cualquier otro comando desconocido
                print("Error: Comando no válido");
        }
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
        boolean estadoBnd;

        // Verifica si el estado de la bandera es "ON"
        if (estadoBandera.equals("ON")) estadoBnd = true;
            // Si no es "ON", verifica si el estado de la bandera es "OFF"
        else if (estadoBandera.equals("OFF")) estadoBnd = false;
        else {
            // Si el estado de la bandera no es válido, muestra un mensaje de error y retorna
            print("Error: Estado de bandera no válido");
            return;
        }

        // Utiliza un switch para manejar diferentes banderas
        switch (bandera) {
            case "TRAZA":
                // Actualiza el estado correspondiente y muestra un mensaje de actualización
                updateEstadoTraza(estadoBnd);
                print("Actualizando estado de la bandera TRAZA: " + estadoBandera);
                break;
            case "CODIFICA":
                // Actualiza el estado correspondiente de la instancia de la clase Hill y muestra un mensaje de actualización
                hill.setCodifica(estadoBnd);
                print("Actualizando estado de la bandera CODIFICA: " + estadoBandera);
                break;
            default:
                // Maneja cualquier otro tipo de bandera no válida y muestra un mensaje de error
                print("Error: Tipo de bandera no válido");
        }
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
        if (traza) System.out.println(text);
    }

};
