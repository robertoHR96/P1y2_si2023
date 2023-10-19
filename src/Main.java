// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

public class Main {
    public static void main(String[] args) {
        /*
        EntradaSalida ES = new EntradaSalida();
        ES.setFicheroClave("clave");
        Integer [][] inversa = ES.leerClaveInversa();

        for (int i = 0; i<3; i++){
            for (int e = 0; e<3; e++){
                System.out.print(inversa[i][e].toString()+" ");
            }
            System.out.println("");
        }
         */
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

        }
    }

    static void mostrarAyuda() {
        System.out.println("-- Ayuda --");
    }
}