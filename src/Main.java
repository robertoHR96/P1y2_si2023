/**
 * La clase `Main` es la clase principal del programa que inicia la aplicación de cifrado/descifrado
 * basada en el cifrado Hill. Acepta argumentos de línea de comandos para configurar y ejecutar el programa.
 */
public class Main {
    public static void main(String[] args) {

        Controller controller = new Controller();
        if (args.length == 2 && args[0].equals("-f")) {
            // Si se proporcionan 2 argumentos y el primero es "-f", configurar el fichero de configuración y ejecutar.
            controller.setFicheroConfig(args[1]);
            controller.run();
        } else if (args.length == 1 && args[0].equals("-h")) {
            // Si se proporciona un solo argumento y es "-h", mostrar la ayuda.
            mostrarAyuda();
        } else {
            // En caso de argumentos inválidos, mostrar un mensaje de error.
            mostrarErrorDeArgumentos();
        }
    }
    static void mostrarAyuda() {
        System.out.println("-- Ayuda --");
        System.out.println("Para inicializar el programa, ejecute con el argumento -f seguido del nombre del fichero de configuración.");
        System.out.println("Ejemplo: java Main -f config.txt");
        System.out.println("El fichero de configuración puede contener los siguientes comandos:");
        System.out.println("& ficheroentrada <nombre-fichero-entrada>: Especifica el archivo de entrada. Por defecto: entrada.txt");
        System.out.println("& ficherosalida <nombre-fichero-salida>: Especifica el archivo de salida. Por defecto: salida.txt");
        System.out.println("& clave <fichero-clave>: Especifica el archivo con la clave. Por defecto: 1 2 3 0 4 5 1 0 6");
        System.out.println("& hill : Ejecuta el cifrado o descifrado basado en el archivo de entrada.");
        System.out.println("& formateaentrada: Formatea la entrada y la guarda en el archivo de salida.");
        System.out.println("@ traza ON / OFF: Activa o desactiva la traza de la ejecución del programa (por defecto: ON).");
        System.out.println("@ codifica ON / OFF: Cifra o descifra texto (por defecto: ON).");
    }

    static void mostrarErrorDeArgumentos() {
        System.out.println("Error de argumentos.");
        System.out.println("Uso: java Main -f <nombre-fichero-configuracion> o java Main -h para ver la ayuda.");
    }
}
