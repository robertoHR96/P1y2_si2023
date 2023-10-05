import java.io.*;
import java.util.Objects;

public class Hill {
    // Texto plano sin cifrar/descifrar
    private String entrada = "";
    // Texto cifrado/desCifrado
    private String salida = "";
    // Variable de estado para la bandera "TRAZA" (inicializada en "true" por defecto)
    private boolean traza = false;
    // Matriz de números con la clave para cifrar los datos
    private Integer [][] clave = new Integer[3][3];
    // Variable de estado para la bandera "CODIFICA" (inicializada en "false" por defecto)
    private boolean codifica = false;

    /**
     * Ejecuta el cifrado/descifrado de la variable entrada y la deja en salida
     *
     * @return Texto cifrado/desCifrado.
     */
    public String cifrar() {
        if(codifica) codificar();
        else desCodificar();
        // si se va a descifrar la entrada tiene que se multiplos de 3
        return "";
    }

    /**
     * Cifra el contenido de la variable entrada
     */
    public void codificar (){}
    public void desCodificar() {}

    /**
     * Establece una clave por defecto
     */
    public void setClaveDefault(){
        // Valores de la primera fila
        clave[0][0] = 1;
        clave[0][1] = 2;
        clave[0][2] = 3;

        // Valores de la segunda fila
        clave[1][0] = 0;
        clave[1][1] = 4;
        clave[1][2] = 5;

        // valores de la tercera fila
        clave[2][0] = 1;
        clave[2][1] = 0;
        clave[2][2] = 6;
    }

    /**
     * Obtine el texto plano sin cifrar
     *
     * @return Texto plano sin cifrar
     */
    public String getEntrada() {
        return entrada;
    }

    /**
     * Establece el valor de la variable entrada
     *
     * @param entrada
     */
    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    /**
     * Obtiene la salida del texto cifrado
     * @return Texto cifrado
     */
    public String getSalida() {
        return salida;
    }

    /**
     * Establece el valor de la variable salida
     *
     * @param salida
     */
    public void setSalida(String salida) {
        this.salida = salida;
    }
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

    /**
     * Obtiene la clave de cifrado y descifrado
     *
     * @return Una matriz con la clave de cifrado
     */
    public Integer[][] getClave() {
        return clave;
    }

    /**
     * Establece el valor de la clave de cifrado y descifrado
     *
     * @param clave
     */
    public void setClave(Integer[][] clave) {
        this.clave = clave;
    }


    /**
     * Obtiene el estado de la bandera "CODIFICA".
     *
     * @return El estado actual de la bandera "CODIFICA".
     */
    public boolean isCodifica() {
        return codifica;
    }

    /**
     * Establece el estado de la bandera "CODIFICA".
     *
     * @param codifica El nuevo estado de la bandera "CODIFICA".
     */
    public void setCodifica(boolean codifica) {
        this.codifica = codifica;
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
