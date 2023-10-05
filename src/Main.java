// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        switch (args.length) {
            case 1:
                if (args[0].equals("-h")) {
                    mostrarAyuda();
                } else {
                    System.out.println("" +
                            "Error de argumentos." +
                            "-f fichero: Para ejecutar el programa." +
                            "-h : Para obtener ayuda." +
                            "");
                }
                break;
            case 2:
                if(args[0].equals("-f")) {
                    controller.setFicheroConfig(args[1]);
                    controller.run();
                }else {
                    System.out.println("" +
                            "Error de argumentos." +
                            "-f fichero: Para ejecutar el programa." +
                            "-h : Para obtener ayuda." +
                            "");
                }
                break;
            default:
                System.out.println("" +
                        "Error de argumentos." +
                        "-f fichero: Para ejecutar el programa." +
                        "-h : Para obtener ayuda." +
                        "");
                break;
        }
    }
    static void mostrarAyuda() {
        System.out.println("-- Ayuda --");
    }
}

    /*



    File doc = new File("./ejeamplo.txt");

    BufferedReader obj = null;
    String strng = null;
        try

    {
        obj = new BufferedReader(new FileReader(doc));
        while (true) {
            try {
                if (!((strng = obj.readLine()) != null)) break;
            } catch (IOException e) {
                System.err.println("Error al leer el fichero de entrada");
            }
            System.out.println(strng);
        }
    } catch(
    FileNotFoundException e)

    {
        System.err.println("El fichero de entrada no es valido");
    }

}

    static void mostrarAyuda() {
        System.out.println("-- Ayuda --");
    }
}
     */
