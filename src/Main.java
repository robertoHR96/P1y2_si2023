// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

public class Main {
    public static void main(String[] args) {

        Controller controller = new Controller();
        switch (args.length) {
            case 1:
                if (args[0].equals("-h")) mostrarAyuda();
                else System.out.println("" +
                        "Error de argumentos.\n" +
                        "-f fichero: Para ejecutar el programa.\n" +
                        "-h : Para obtener ayuda.\n" +
                        "");
                break;
            case 2:
                if (args[0].equals("-f")) {
                    controller.setFicheroConfig(args[1]);
                    //controller.setFicheroConfig("config");
                    controller.run();
                } else System.out.println("" +
                        "Error de argumentos.\n" +
                        "-f fichero: Para ejecutar el programa.\n" +
                        "-h : Para obtener ayuda.\n" +
                        "");
                break;
            default:
                System.out.println("" +
                        "Error de argumentos.\n" +
                        "-f fichero: Para ejecutar el programa.\n" +
                        "-h : Para obtener ayuda.\n" +
                        "");
                break;
        }
    }

    static void mostrarAyuda() {
        System.out.println("-- Ayuda --");
        System.out.println("" +
                "Para inicializar el programa debe ejecutarse con el argumento -f seguido del nombre del fichero de configuración" +
                "Ejem: P1y2_si2023 -f config.txt" +
                "El fichero de configuracion puede ejecutar los siguientes comandos: " +
                "& ficheroentrada <nombre-fichero-entrada> para selecionar el fichero donde se encuentra" +
                "el texto a cifrar/descifrar, en caso de no seleccionar fichero" +
                "la entrada se tomara de entrada.txt" +
                "& ficherosalida <nombre-fichero-salida> para selecionar el fichero donde se quiere" +
                "alojar el texto cifrado/descifrado, en caso de no seleccionar fichero" +
                "la slaida se guardara en salida.txt" +
                "& clave <fichero-clave> para selecionar el fichero con la clave" +
                "en caso de no selecionar una clave esta por defecto sera -> 2 0 1 3 0 0 5 1 1 " +
                "& hill siver para ejecutar el cifrado o descifrado en base al texto alojada en & ficheroentrada" +
                "& formateaentrada sirve para formatear la entrada del ficheroentrada y guardarla en ficherosalida" +
                "@ traza ON / OFF selecciona si se desea que se muestre la traza de la ejecucion del programa" +
                "@ codfica ON / OFF selecciona si se va a cifrar o descifrar texto" +
                "");
    }
}