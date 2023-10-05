import java.util.Objects;

public class EntradaSalida {
    private String ficheroEntrada = "";
    private String ficheroSalida = "";
    private String ficheroClave = "";
    private boolean traza = false;

    public String leerEntrada() {
        return "";
    }

    public Integer[][] leerClave() {
        Integer [][] clave = new Integer[3][3];
        return clave;
    }

    public void escribirSalida(String salida) {

    }

    public String getFicheroEntrada() {
        return ficheroEntrada;
    }

    public void setFicheroEntrada(String ficheroEntrada) {
        this.ficheroEntrada = ficheroEntrada;
    }

    public String getFicheroSalida() {
        return ficheroSalida;
    }

    public void setFicheroSalida(String ficheroSalida) {
        this.ficheroSalida = ficheroSalida;
    }

    public boolean isTraza() {
        return traza;
    }

    public void setTraza(boolean traza) {
        this.traza = traza;
    }

    public String getFicheroClave() {
        return ficheroClave;
    }

    public void setFicheroClave(String ficheroClave) {
        this.ficheroClave = ficheroClave;
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
}
