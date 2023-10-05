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
                    //controller.setFicheroConfig("ejemplo.txt");
                    controller.run();
                } else System.out.println("" +
                        "Error de argumentos.\n" +
                        "-f fichero: Para ejecutar el programa.\n" +
                        "-h : Para obtener ayuda.\n" +
                        "");
                break;

        }
    }

    static void mostrarAyuda() {
        System.out.println("-- Ayuda --");
    }
}