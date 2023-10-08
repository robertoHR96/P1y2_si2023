import java.util.Objects;

public class EntradaSalida {
    /**
     * Representa el nombre del fichero de entrada.
     */
    private String ficheroEntrada = "";

    /**
     * Representa el nombre del fichero de salida.
     */
    private String ficheroSalida = "";

    /**
     * Representa el nombre del fichero de clave.
     */
    private String ficheroClave = "";

    /**
     * Indica si la traza está habilitada o deshabilitada.
     */
    private boolean traza = false;

    /**
     * Lee la entrada desde un fichero.
     * @return El contenido del fichero de entrada.
     */
    public String leerEntrada() {
        return "";
    }

    /**
     * Lee una matriz de enteros (clave) desde un fichero.
     * @return Una matriz de enteros 3x3 que representa la clave.
     */
    public Integer[][] leerClave() {
        Integer[][] clave = new Integer[3][3];
        return clave;
    }

    /**
     * Escribe una cadena en el fichero de salida.
     * @param salida La cadena a escribir en el fichero de salida.
     */
    public void escribirSalida(String salida) {

    }

    /**
     * Obtiene el nombre del fichero de entrada.
     * @return El nombre del fichero de entrada.
     */
    public String getFicheroEntrada() {
        return ficheroEntrada;
    }

    /**
     * Establece el nombre del fichero de entrada.
     * @param ficheroEntrada El nombre del fichero de entrada a establecer.
     */
    public void setFicheroEntrada(String ficheroEntrada) {
        this.ficheroEntrada = ficheroEntrada;
    }

    /**
     * Obtiene el nombre del fichero de salida.
     * @return El nombre del fichero de salida.
     */
    public String getFicheroSalida() {
        return ficheroSalida;
    }

    /**
     * Establece el nombre del fichero de salida.
     * @param ficheroSalida El nombre del fichero de salida a establecer.
     */
    public void setFicheroSalida(String ficheroSalida) {
        this.ficheroSalida = ficheroSalida;
    }

    /**
     * Obtiene el estado de la traza.
     * @return true si la traza está habilitada, false si está deshabilitada.
     */
    public boolean isTraza() {
        return traza;
    }

    /**
     * Establece el estado de la traza.
     * @param traza true para habilitar la traza, false para deshabilitarla.
     */
    public void setTraza(boolean traza) {
        this.traza = traza;
    }

    /**
     * Obtiene el nombre del fichero de clave.
     * @return El nombre del fichero de clave.
     */
    public String getFicheroClave() {
        return ficheroClave;
    }

    /**
     * Establece el nombre del fichero de clave.
     * @param ficheroClave El nombre del fichero de clave a establecer.
     */
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
