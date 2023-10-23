import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Clase que gestiona la configuración y la ejecución de un algoritmo de cifrado Hill Cipher.
 * Permite la lectura de un archivo de configuración para definir acciones y ajustes de cifrado.
 */
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
     * Constructor por defecto de la clase
     */
    public Controller() {
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
        //formatearConfig();
        // Crear un objeto File que representa el archivo de configuración.
        File doc = new File(ficheroConfig);
        BufferedReader obj = null;
        String strng = null;
        try {
            // Abrir el archivo de configuración para lectura.
            //obj = new BufferedReader(new FileReader(doc));
            obj = new BufferedReader(new InputStreamReader(new FileInputStream(doc), "UTF-8"));
            // Resto del código de lectura...


            // Leer el archivo línea por línea hasta llegar al final.
            while (true) {
                try {
                    // Leer una línea del archivo.
                    if ((strng = obj.readLine()) == null) break; // Si no hay más líneas, salir del bucle.
                    if (!strng.equals("")) ejecutarOrden(strng);
                } catch (IOException e) {
                    System.err.println("	 -- Error al leer el fichero de entrada");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("	 -- Error al leer el fichero de entrada");
        } catch (IOException e) {
            System.err.println("	 -- Error al leer el fichero de entrada");
        } finally {
            // Cerrar el archivo de configuración al finalizar.
            try {
                if (obj != null) obj.close();
            } catch (IOException e) {
                System.err.println("	 -- Error al cerrar el fichero de entrada");
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
            if (splitLinea[0].equals("@") || splitLinea[0].equals("&")) {
                // Comprobar si el primer elemento es "@"
                // Si es "@", llamar a la función updateBandera con los argumentos especificados
                if (splitLinea[0].equals("@")) updateBandera(splitLinea[1], splitLinea[2]);

                // Comprobar si el primer elemento es "&"
                // Si es "&", llamar a la función seleccionarComando con el comando especificado
                if (splitLinea[0].equals("&")) seleccionarComando(splitLinea);
            }
        } else {
            if (!strng.equals(" ")) {
                print("	 -- Error: Instrucción no valida: " + strng);
            }
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
            print("	 -- Error: Comando no válido");
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
                if (splitLinea.length == 3) {
                    print("-------------------------------------");
                    print("Seleccionado fichero de salida: " + splitLinea[2]);
                    entradaSalida.setFicheroSalida(splitLinea[2]);
                } else print("	 -- Error: Comando 'ficherosalida' requiere 2 argumentos");
                break;
            case "ficheroentrada":
                // Establece el fichero de entrada en la clase entradaSalida
                if (splitLinea.length == 3) {
                    print("-------------------------------------");
                    print("Seleccionado fichero de entrada: " + splitLinea[2]);
                    entradaSalida.setFicheroEntrada(splitLinea[2]);
                } else print("  -- Error: Comando 'ficheroentrada' requiere 2 argumentos");
                break;
            case "clave":
                // Llama a la función seleccionarClave con el tercer argumento
                if (splitLinea.length == 3) seleccionarClave(splitLinea[2]);
                else print("  -- Error: Comando 'clave.txt' requiere 2 argumentos");
                break;
            case "formateaentrada":
                // Llama a la función formatearEntrada
                formatearEntrada();
                break;
            default:
                // Maneja cualquier otro comando desconocido
                print("	 -- Error: Comando no válido: " + comando);
        }
    }


    /**
     * Selecciona la clave.txt para cifrar y descifrar desde un fichero
     *
     * @param ficheroClave String con el nombre del fichero que contiene la clave.txt
     */
    public void seleccionarClave(String ficheroClave) {
        // Se selecciona el fichero con la clave.txt en el objeto entradaSalida
        entradaSalida.setFicheroClave(ficheroClave);
        // Se lee la clave.txt de entradaSalida y se las asignamos a hill
        Integer[][] clave = entradaSalida.leerClave();
        if (clave != null) {
            if (clave[0][0] != null) {
                hill.setClave(clave);
                hill.setClaveInversa(entradaSalida.leerClaveInversa());
            }
        }
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
            print("	 -- Error: Estado de bandera no válido");
            return;
        }

        // Utiliza un switch para manejar diferentes banderas
        switch (bandera) {
            case "traza":
                // Actualiza el estado correspondiente y muestra un mensaje de actualización
                print("-------------------------------------");
                print("Actualizando estado de la bandera TRAZA: " + estadoBandera);
                updateEstadoTraza(estadoBnd);
                break;
            case "codifica":
                // Actualiza el estado correspondiente de la instancia de la clase Hill y muestra un mensaje de actualización
                print("-------------------------------------");
                print("Actualizando estado de la bandera CODIFICA: " + estadoBandera);
                hill.setCodifica(estadoBnd);
                break;
            default:
                // Maneja cualquier otro tipo de bandera no válida y muestra un mensaje de error
                print("	 -- Error: Tipo de bandera no válido");
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

    /**
     * Lee los datos del fichero de entrada y los guarda formateados en el fichero de salida
     */
    public void formatearEntrada() {
        // Se obtienen los datos del fichero de entrada
        String dataFicheroEntrada = entradaSalida.leerEntrada();
        // Se formatea el texto obtenido
        print("-------------------------------------");
        print("Formateando entrada del fichero: " + entradaSalida.getFicheroEntrada());
        dataFicheroEntrada = formatearTexto(dataFicheroEntrada);
        // Se guarda el texto en el fichero de salida
        entradaSalida.escribirSalida(dataFicheroEntrada);
    }

    /**
     * Formatea la configuración predeterminada del programa.
     * Configura los archivos de entrada y salida, habilita trazas,
     * y establece valores predeterminados para un algoritmo de Hill Cipher.
     */
    public void formatearConfig() {
        // Configurar archivos de entrada y salida con el archivo de configuración
        entradaSalida.setFicheroEntrada(ficheroConfig);
        entradaSalida.setFicheroSalida(ficheroConfig);

        // Formatear la entrada de datos
        formatearEntrada();

        // Habilitar la traza global
        this.traza = true;
        hill.setTraza(true);

        // Configurar el algoritmo Hill Cipher
        hill.setCodifica(true);
        hill.setClaveDefault();

        // Habilitar la traza para las operaciones de entrada y salida
        entradaSalida.setTraza(true);

        // Establecer archivos de entrada y salida predeterminados
        entradaSalida.setFicheroEntrada("entrada.txt");
        entradaSalida.setFicheroSalida("salida.txt");
    }

    /**
     * Formatea un texto eliminando espacios en blanco y convirtiéndolo a mayúsculas.
     *
     * @param texto El texto que se va a formatear.
     * @return El texto formateado sin espacios en blanco y en mayúsculas.
     */
    public String formatearTexto(String texto) {
        // Utilizar una expresión regular para filtrar solo letras de la "a" a la "z" (incluyendo la "ñ")
        // y luego convertir a mayúsculas
        return texto.replaceAll("[^a-zA-ZñÑ]", "").toUpperCase();
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


    /**
     * Imprime un texto en la consola si la bandera "TRAZA" está activada.
     *
     * @param text El texto a imprimir.
     */
    public void print(String text) {
        if (traza) System.out.println(text);
    }

};
