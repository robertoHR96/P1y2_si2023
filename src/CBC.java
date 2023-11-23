import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Objects;

/**
 * Clase para Cifrar y descifrar grupos de bytes usando AES-CBC
 */
public class CBC {
    // Texto plano sin cifrar/descifrar
    private byte[] entrada;
    // Texto cifrado/desCifrado
    private String salida = "";
    // Variable de estado para la bandera "TRAZA" (inicializada en "true" por defecto)
    private boolean traza = true;
    // Variable de estado para la bandera "CODIFICA" (inicializada en "false" por defecto)
    private boolean codifica = true;
    private AES aes = new AES();
    private byte[] vectorInicializacion = new byte[16];

    /**
     * Realiza el cifrado de un texto utilizando el algoritmo AES en modo CBC (Cipher Block Chaining).
     *
     * @param clave La clave secreta utilizada para el cifrado.
     * @return El texto cifrado en forma de array de bytes o un array vacío si hay un error en el cifrado.
     */
    public byte[] cifra(SecretKey clave) {
        if (clave == null) {
            print("❌ Error 311: no se ha selecionado ninguan clave");
            return null;
        }
        int numBloques = entrada.length % 16;
        byte[] cbcCifrado = new byte[entrada.length];
        if ((numBloques >= 0) || (numBloques < 16)) {
            // Se inicializa la clave del aes
            for (int i = 0; i < (entrada.length / 16); i++) {
                byte[] bloque = new byte[16];
                if (i == 0) {
                    System.arraycopy(entrada, 0, bloque, 0, 16);
                    bloque = xorEntrada(bloque, vectorInicializacion);
                } else {
                    System.arraycopy(cbcCifrado, ((i - 1) * 16), vectorInicializacion, 0, 16);
                    System.arraycopy(entrada, (i * 16), bloque, 0, 16);
                    bloque = xorEntrada(bloque, vectorInicializacion);
                }
                // se selecciona la entrada para AES con el bloque anterior
                aes.setEntrada(bloque);
                // se cifra y se saca el bloque cifrado
                bloque = aes.cifrarCBC(clave);
                // se añade el bloque al array de bytes final
                System.arraycopy(bloque, 0, cbcCifrado, i * 16, 16);
            }

            System.out.println("\u001B[35m------------------------------------- \u001B[0m");
            System.out.println("\u001B[35m-------\u001B[0m Cifrando con CBC \u001B[35m--------\u001B[0m");
            System.out.println("📃 \u001B[32mTexto sin cifrar: " + new String(entrada));
            System.out.println("📜 \u001B[34mTexto cifrado: " + Base64.getEncoder().encodeToString(cbcCifrado));
            System.out.println("\u001B[35m------------------------------------- \u001B[0m");
        } else {
            print("❌ Error 312: La entrada debe ser múltiplo de 16");
        }
        return cbcCifrado;
    }

    /**
     * Realiza el descifrado de un texto cifrado utilizando el algoritmo AES en modo CBC (Cipher Block Chaining).
     *
     * @param clave La clave secreta utilizada para el descifrado.
     * @return El texto descifrado en formato de cadena de caracteres, o una cadena vacía si hay un error en el descifrado.
     */
    public String descifra(SecretKey clave) {
        if (clave == null) {
            print("❌ Error 321: no se ha selecionado ninguan clave");
            return null;
        }
        int numBloques = entrada.length % 16;
        byte[] cbcCifrado = new byte[entrada.length];
        if ((numBloques >= 0) || (numBloques < 16)) {
            // Se inicializa la clave del aes
            for (int i = 0; i < (entrada.length / 16); i++) {
                byte[] bloque = new byte[16];
                System.arraycopy(entrada, (i * 16), bloque, 0, 16);
                aes.setEntrada(bloque);
                byte[] salidaDes_cifrada = aes.descifrarCBC(clave);
                if (i != 0) {
                    System.arraycopy(entrada, ((i - 1) * 16), vectorInicializacion, 0, 16);
                }
                bloque = xorEntrada(salidaDes_cifrada, vectorInicializacion);
                System.arraycopy(bloque, 0, cbcCifrado, i * 16, 16);
            }
            System.out.println("\u001B[35m------------------------------------- \u001B[0m");
            System.out.println("\u001B[35m-------\u001B[0m Des-Cifrando con CBC \u001B[35m--------\u001B[0m");
            System.out.println("📜 \u001B[34mTexto cifrado: " + Base64.getEncoder().encodeToString(entrada));
            System.out.println("📃 \u001B[32mTexto des-cifrar: " + new String(cbcCifrado));
            System.out.println("\u001B[35m------------------------------------- \u001B[0m");
        } else {
            print("❌ Error 322: La entrada debe ser múltiplo de 16");
        }

        return new String(cbcCifrado);
    }

    /**
     * Realiza una operación XOR (OR exclusivo) entre dos arrays de bytes.
     *
     * @param arrayBytes1 El primer array de bytes.
     * @param arrayBytes2 El segundo array de bytes.
     * @return El resultado de la operación XOR entre los dos arrays como un nuevo array de bytes.
     */
    public byte[] xorEntrada(byte[] arrayBytes1, byte[] arrayBytes2) {
        try {
            byte[] resultadoXOR = new byte[16];
            for (int i = 0; i < 16; i++) {
                resultadoXOR[i] = (byte) (arrayBytes1[i] ^ arrayBytes2[i]);
            }
            return resultadoXOR;
        } catch (Exception e) {
            print("❌ Error 331: Error al ejecutar la funcion XOR en CBC");
            return null;
        }
    }


    /**
     * Obtiene la entrada de datos.
     *
     * @return El arreglo de bytes de la entrada de datos.
     */
    public byte[] getEntrada() {
        return entrada;
    }

    /**
     * Establece la entrada de datos.
     *
     * @param entrada El arreglo de bytes que representa la entrada de datos.
     */
    public void setEntrada(byte[] entrada) {
        this.entrada = entrada;
    }

    /**
     * Obtiene la salida de datos.
     *
     * @return La cadena que representa la salida de datos.
     */
    public String getSalida() {
        return salida;
    }

    /**
     * Establece la salida de datos.
     *
     * @param salida La cadena que representa la salida de datos.
     */
    public void setSalida(String salida) {
        this.salida = salida;
    }

    /**
     * Verifica si la traza está habilitada.
     *
     * @return true si la traza está habilitada, false de lo contrario.
     */
    public boolean isTraza() {
        return traza;
    }

    /**
     * Establece si se debe habilitar la traza.
     *
     * @param traza Valor booleano para habilitar o deshabilitar la traza.
     */
    public void setTraza(boolean traza) {
        this.traza = traza;
    }

    /**
     * Verifica si la codificación está habilitada.
     *
     * @return true si la codificación está habilitada, false de lo contrario.
     */
    public boolean isCodifica() {
        return codifica;
    }

    /**
     * Establece si se debe habilitar la codificación.
     *
     * @param codifica Valor booleano para habilitar o deshabilitar la codificación.
     */
    public void setCodifica(boolean codifica) {
        this.codifica = codifica;
    }

    /**
     * Obtiene el objeto AES.
     *
     * @return El objeto AES.
     */
    public AES getAes() {
        return aes;
    }

    /**
     * Establece el objeto AES.
     *
     * @param aes El objeto AES a establecer.
     */
    public void setAes(AES aes) {
        this.aes = aes;
    }

    /**
     * Obtiene el vector de inicialización.
     *
     * @return El arreglo de bytes que representa el vector de inicialización.
     */
    public byte[] getVectorInicializacion() {
        return vectorInicializacion;
    }

    /**
     * Establece el vector de inicialización.
     *
     * @param vectorInicializacion El arreglo de bytes que representa el vector de inicialización.
     */
    public void setVectorInicializacion(byte[] vectorInicializacion) {
        this.vectorInicializacion = vectorInicializacion;
    }

    /**
     * Imprime un texto en la consola si la bandera "TRAZA" está activada.
     *
     * @param text El texto a imprimir.
     */
    public void print(String text) {
        if (traza) System.out.println(text);

    }
}
