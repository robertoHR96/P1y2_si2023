import java.io.*;

public class Controller {

    private String ficheroConfig = "";
    private boolean traza = false;
    private Hill hill = new Hill();
    private Formateador formateador = new Formateador();
    private EntradaSalida entradaSalida = new EntradaSalida();

    public void run (){

        File doc = new File(ficheroConfig);
        BufferedReader obj = null;
        String strng = null;
        try{
            obj = new BufferedReader(new FileReader(doc));
            while (true) {
                try {
                    if ((strng = obj.readLine()) == null) break;
                    ejecutarOrden(strng);
                } catch (IOException e) {
                    System.err.println("Error al leer el fichero de entrada");
                }
            }
        } catch(FileNotFoundException e) {
            System.err.println("Error al leer el fichero de entrada");
        }
    }
    public void ejecutarOrden (String strng) {

    }
    public boolean isTraza() {
        return traza;
    }

    public void setTraza(boolean traza) {
        this.traza = traza;
    }

    public Hill getHill() {
        return hill;
    }

    public void setHill(Hill hill) {
        this.hill = hill;
    }

    public Formateador getFormateador() {
        return formateador;
    }

    public void setFormateador(Formateador formateador) {
        this.formateador = formateador;
    }

    public EntradaSalida getEntradaSalida() {
        return entradaSalida;
    }

    public void setEntradaSalida(EntradaSalida entradaSalida) {
        this.entradaSalida = entradaSalida;
    }

    public String getFicheroConfig() {
        return ficheroConfig;
    }

    public void setFicheroConfig(String ficheroConfig) {
        this.ficheroConfig = ficheroConfig;
    }
}
