import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.util.*;

/**
 * Clase que gestiona la configuración y la ejecución de un algoritmo de cifrado Hill Cipher.
 * Permite la lectura de un archivo de configuración para definir acciones y ajustes de cifrado.
 */
public class Controller {
    // Variable para almacenar el nombre del archivo de configuración
    private String ficheroConfig = "";

    // Variable de estado para la bandera "TRAZA" (inicializada en "true" por defecto)
    private boolean traza = true;
    private boolean codifica = true;

    // Objeto para gestionar AES (inicializado con una nueva instancia)
    private AES Aes = new AES();

    private CBC Cbc = new CBC();

    SecretKey claveAES;

    // Objeto para gestionar EntradaSalida (inicializado con una nueva instancia)
    private EntradaSalida entradaSalida = new EntradaSalida();

    /**
     * Constructor por defecto de la clase
     */
    public Controller() {
        this.traza = true;
        Aes.setTraza(true);
        Aes.setCodifica(true);

        Cbc.setTraza(true);
        Cbc.setCodifica(true);
        //Aes.setClaveDefault();
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
                    System.err.println("❌ Error al leer el fichero de entrada");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("❌ Error al leer el fichero de entrada");
        } catch (IOException e) {
            System.err.println("❌ Error al leer el fichero de entrada");
        } finally {
            // Cerrar el archivo de configuración al finalizar.
            try {
                if (obj != null) obj.close();
            } catch (IOException e) {
                System.err.println("❌ Error al cerrar el fichero de entrada");
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
                print("❌ Error: Instrucción no valida: " + strng);
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
            print("❌ Error: Comando no válido");
            return;
        }
        // Obtiene el segundo elemento del array como el comando
        String comando = splitLinea[1];

        // Utiliza un switch para manejar diferentes comandos
        switch (comando) {
            case "CBC":
                // Se comprueba que el vector de inicialización este ok
                if (splitLinea.length == 18) {
                    print("-------------------------------------");
                    print("☑️ Cargando vector de inicializacion");
                    guardaCBC(splitLinea);
                } else print("❌ Error: el comando CBC necesita un vector de inicaliación de 16 bytes");
                break;
            case "AES":
                // se comprueba que el comando este bien
                if (splitLinea.length == 3) ejecutarAES(splitLinea);
                else print("❌ Error: comando 'AES' no valido");
                break;
            case "ficherosalida":
                // Establece el fichero de salida en la clase entradaSalida
                if (splitLinea.length == 3) {
                    print("-------------------------------------");
                    print("☑️ Seleccionado fichero de salida: " + splitLinea[2]);
                    entradaSalida.setFicheroSalida(splitLinea[2]);
                } else print("❌ Error: Comando 'ficherosalida' requiere 2 argumentos");
                break;
            case "ficheroentrada":
                // Establece el fichero de entrada en la clase entradaSalida
                if (splitLinea.length == 3) {
                    print("-------------------------------------");
                    print("☑️ Seleccionado fichero de entrada: " + splitLinea[2]);
                    entradaSalida.setFicheroEntrada(splitLinea[2]);
                } else print("❌ Error: Comando 'ficheroentrada' requiere 2 argumentos");
                break;
            case "fichero_clave":
                // Llama a la función seleccionarClave con el tercer argumento
                if (splitLinea.length == 3) seleccionarFicheroClave(splitLinea[2]);
                else print("❌ Error: Comando 'fichero_clave' requiere especificar el nombre del fichero");
                break;
            case "carga_clave":
                // Se comprueba que el comando esté bien
                if (splitLinea.length == 3) {
                    // Se comprueba que el comando esté bien
                    if (splitLinea[2].equals("AES")) {
                        cargaClaveDeFichero();
                    } else print("❌ Error: comando 'Carga_Clave' no valido");
                } else print("❌ Error: comando 'Carga_Clave' no valido");
                break;
            case "genera_clave":
                if (splitLinea[3].equals("AES")) {
                    // si el tamaño es 4 o la cadena no tiene min 16 caracteres se genera una aletoria
                    if (splitLinea.length == 4) seleccionarClaveAES(splitLinea[2], "");
                    if (splitLinea.length == 5) seleccionarClaveAES(splitLinea[2], splitLinea[4]);
                } else {
                    print("❌ Error: comando 'Genera_Clave' no valido");
                }
                break;
            default:
                // Maneja cualquier otro comando desconocido
                print("❌ Error: Comando no válido: " + comando);
        }

    }


    public void guardaCBC(String[] splitLinea) {
        byte [] vectorInicializacion = cargaCBC(splitLinea);
        if (vectorInicializacion != null) {
            // se carga el vestor de inizializacion
            Cbc.setVectorInicializacion(vectorInicializacion);
            if (codifica){
                // se carga la entrada que se va a codificar
                Cbc.setEntrada(entradaSalida.leerEntradaCifrar().getBytes());
                entradaSalida.escribirSalidaCifrar( Cbc.cifra(claveAES));
            }
            else{
                Cbc.setEntrada(entradaSalida.leerEntradaDesCifrar());
                entradaSalida.escribirSalidaDescifrar(getCbc().descifra(claveAES));
            }

            // se le dice que codificique / descodificque segun este la flag
            // se escribe la salida en el fichero seleccionado como tal
            //entradaSalida.escribirSalida(Cbc.getSalida());
        }
    }

    /**
     * Genera un vector de inicialización para CBC
     *
     * @param splitLinea Array de string con el comando seleccionado
     * @return String [] Vector de inicialización para CBC
     */
    public byte [] cargaCBC(String[] splitLinea) {
        byte [] vectorInicializacionByte = new byte[16];
        for (int i = 2; i < 18; i++) {
            vectorInicializacionByte[i - 2] = splitLinea[i].getBytes()[0];
            /*
            if (splitLinea[i].getBytes().length == 1) {
            } else {
                print("❌ Error: byte no valido en en vector de inicialización");
                return null;
            }
             */
        }
        return vectorInicializacionByte;
    }

    public void seleccionarClaveAES(String tamanio, String clave) {
        SecretKey claveSecret = null;
        Integer tamClave = Integer.parseInt((tamanio));
        if (tamClave == 128 || tamClave == 192 || tamClave == 256 || (tamClave % 4 == 0)) {
            try {

                if (clave.length() >= 16) { // ??

                    byte[] usuarioClaveByte = clave.getBytes("UTF-8");
                    // se usa sha-256 para que el array de bytes sea de 32 bytes ya que la sha-1 da un array de 20 bytes
                    MessageDigest sh = MessageDigest.getInstance("SHA-256");
                    usuarioClaveByte = sh.digest(usuarioClaveByte);
                    usuarioClaveByte = Arrays.copyOf(usuarioClaveByte, tamClave / 8); // Determina el tamaño basado en tamClave
                    claveSecret = new SecretKeySpec(usuarioClaveByte, "AES");
                    //arrayClave=claveSecret.getEncoded();
                    //System.out.printf("%x:",arrayClave[i]);
                    // puede dar problemas si arrayClave[i] == FF
                    entradaSalida.escribirClave(claveSecret);
                } else {
                    KeyGenerator generadorAES = KeyGenerator.getInstance("AES");
                    generadorAES.init(tamClave);
                    claveSecret = generadorAES.generateKey();
                    entradaSalida.escribirClave(claveSecret);
                }
                cargaClaveDeFichero();
            } catch (Exception e) {
                print("❌ Error: El tamaño introducido como calve no es valido");
            }
        } else {
            print("❌ Error: Tamaño de clave no válido para AES. Debe ser 128, 192 o 256 bits o un numero múltiplo de 4.");
        }

    }

    public void cargaClaveDeFichero() {
        //System.out.println(Base64.getEncoder().encodeToString(entradaSalida.leerClave().getEncoded()));
        SecretKey sck = entradaSalida.leerClave();
        byte[] listaBytesClave = sck.getEncoded();
        for (int i = 0; i < listaBytesClave.length; i++) {
            if (i == 0) System.out.print("\u001B[36m-------------------------------------\n\uD83D\uDD11Clave: ");
            System.out.printf("%x", listaBytesClave[i]);
            if ( i < listaBytesClave.length - 1 ) System.out.print(":");
        }
        System.out.println("\n-------------------------------------\u001B[0m");
        claveAES = sck;
        // ahora se carga la clave en el AES
    }

    /**
     * Método para ejecutar el algoritmo AES en función de ciertos parámetros.
     *
     * @param splitLinea Arreglo que contiene los parámetros necesarios para la ejecución.
     */
    public void ejecutarAES(String[] splitLinea) {
        // Se comprueba cómo leer la entrada en función de la bandera "codifica"
        if (codifica) Aes.setEntrada(entradaSalida.leerEntradaCifrar().getBytes());
        else Aes.setEntrada(entradaSalida.leerEntradaDesCifrar());

        // Según si se va a realizar con relleno o no, se cambia la inicialización del Cipher de AES
        switch (splitLinea[2]) {
            case "conRelleno":
                procesarAES(true, Aes.getEntrada());
                break;
            case "sinRelleno":
                procesarAES(false, Aes.getEntrada());
                break;
            default:
                print("❌ Error: Comando no válido");
                break;
        }
    }

    /**
     * Procesa la operación AES según se especifique con o sin relleno.
     *
     * @param conRelleno Booleano que indica si se debe realizar con relleno o no.
     * @param entrada    Arreglo de bytes que representa la entrada para la operación.
     */
    private void procesarAES(boolean conRelleno, byte[] entrada) {
        // Verifica si la entrada es múltiplo de 16 bytes
        if ((codifica && conRelleno) && (entrada != null)) {
            byte[] salida = Aes.cifrar(conRelleno, claveAES);
            if (salida != null) entradaSalida.escribirSalidaCifrar(salida);
        } else {
            if (entrada != null && entrada.length % 16 == 0) {
                // Realiza la operación de cifrado o descifrado según el caso
                if (codifica) {
                    byte[] salida = Aes.cifrar(conRelleno, claveAES);
                    if (salida != null) entradaSalida.escribirSalidaCifrar(salida);

                } else {
                    String salida = Aes.descifrar(conRelleno, claveAES);
                    if (salida != null) entradaSalida.escribirSalidaDescifrar(salida);
                }
            } else {
                print("❌ Error: La entrada debe ser múltiplo de 16");
            }
        }
    }

    /**
     * Selecciona la clave.txt para cifrar y descifrar desde un fichero
     *
     * @param ficheroClave String con el nombre del fichero que contiene la clave.txt
     */
    public void seleccionarFicheroClave(String ficheroClave) {
        // Se selecciona el fichero con la clave.txt en el objeto entradaSalida
        entradaSalida.setFicheroClave(ficheroClave);
    }

    /**
     * Actualiza el estado de una bandera según su nombre y estado.
     *
     * @param bandera       El nombre de la bandera ("TRAZA" o "CODIFICA").
     * @param estadoBandera El estado de la bandera ("ON" o "OFF").
     */
    public void updateBandera(String bandera, String estadoBandera) {
        boolean estadoBnd;
        String iconEstado = "";


        // Verifica si el estado de la bandera es "ON"
        if (estadoBandera.equals("ON")) {
            estadoBnd = true;
            iconEstado = "🟩";
        } else if (estadoBandera.equals("OFF")) {
            // Si no es "ON", verifica si el estado de la bandera es "OFF"
            estadoBnd = false;
            iconEstado = "🟥";
        } else {
            // Si el estado de la bandera no es válido, muestra un mensaje de error y retorna
            print("❌ Error: Estado de bandera no válido");
            return;
        }

        // Utiliza un switch para manejar diferentes banderas
        switch (bandera) {
            case "traza":
                // Actualiza el estado correspondiente y muestra un mensaje de actualización
                if (estadoBnd) {
                    updateEstadoTraza(estadoBnd);
                    print("-------------------------------------");
                    print(iconEstado + " Actualizando estado de la bandera TRAZA: " + estadoBandera);
                } else {
                    print("-------------------------------------");
                    print(iconEstado + " Actualizando estado de la bandera TRAZA: " + estadoBandera);
                    updateEstadoTraza(estadoBnd);
                }
                break;
            case "codifica":
                // Actualiza el estado correspondiente de la instancia de la clase Hill y muestra un mensaje de actualización
                print("-------------------------------------");
                print(iconEstado + " Actualizando estado de la bandera CODIFICA: " + estadoBandera);
                Aes.setCodifica(estadoBnd);
                Cbc.setCodifica(estadoBnd);
                this.codifica = estadoBnd;
                break;
            default:
                // Maneja cualquier otro tipo de bandera no válida y muestra un mensaje de error
                print("❌ Error: Tipo de bandera no válido");
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
        Aes.setTraza(estadoBnd);
        Cbc.setTraza(estadoBnd);
        // Se establece el valor del atributo traza de la instancia de la clase entradaSalida
        entradaSalida.setTraza(estadoBnd);
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
     * Obtiene una instancia de la clase AES.
     *
     * @return Una instancia de la clase AES.
     */
    public AES getAes() {
        return this.Aes;
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
        this.entradaSalida = entradaSalida.copy();
    }

    public CBC getCbc() {
        return Cbc;
    }

    public void setCbc(CBC cbc) {
        Cbc = cbc;
    }

    public SecretKey getClaveAES() {
        return claveAES;
    }

    public void setClaveAES(SecretKey claveAES) {
        this.claveAES = claveAES;
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
