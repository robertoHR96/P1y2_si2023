import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

/**
 * Clase para Cifrar y descifrar grupos de bytes usando AES
 */
public class AES {
    // Texto plano sin cifrar/descifrar
    private byte[] entrada;
    // Texto cifrado/desCifrado
    private String salida = "";
    // Variable de estado para la bandera "TRAZA" (inicializada en "true" por defecto)
    private boolean traza = true;
    // Variable de estado para la bandera "CODIFICA" (inicializada en "false" por defecto)
    private boolean codifica = true;

    private Cipher cifrador = null;

    /**
     * Constructor por defecto de la clase
     */
    public AES() {
    }

    /**
     * Constructor parametrizado de la clase
     *
     * @param entrada  Texto de entrada a cifrar/des-cifrar
     * @param salida   Texto de salida cifrado/des-cifrado
     * @param traza    Bandera para mostrar traza
     * @param codifica Bandera para cifrar/des-cifrar
     */
    public AES(String entrada, String salida, boolean traza, boolean codifica) {
        this.salida = salida;
        this.traza = traza;
        this.codifica = codifica;
        this.cifrador = null;
    }

    /**
     * Descifra un texto cifrado utilizando el algoritmo AES en modo ECB (Electronic Codebook).
     *
     * @param conRelleno Indica si se utiliza relleno en el texto cifrado.
     * @param clave La clave secreta utilizada para el descifrado.
     * @return El texto plano descifrado o null si ocurre algún error.
     */
    public String descifrar(boolean conRelleno, SecretKey clave) {
        if(clave ==null){
           print("❌ Error 211: no se ha selecionado ninguan clave");
           return null;
        }
        try {
            if (conRelleno) {
                cifrador = Cipher.getInstance("AES/ECB/PKCS5Padding");
                System.out.println("\u001B[35m------------------------------------- \u001B[0m");
                System.out.println("\u001B[35m-----\u001B[0m Des-cifrando con relleno \u001B[35m------\u001B[0m");
            } else {
                cifrador = Cipher.getInstance("AES/ECB/NOPadding");
                System.out.println("\u001B[35m------------------------------------- \u001B[0m");
                System.out.println("\u001B[35m-----\u001B[0m Des-cifrando sin relleno \u001B[35m------\u001B[0m");
            }

            cifrador.init(Cipher.DECRYPT_MODE, clave);

            byte[] bufferCifrado = null;


            bufferCifrado = cifrador.doFinal(entrada);


            String salida = new String(bufferCifrado);

            System.out.println("📜 \u001B[34mTexto cifrado: " + Base64.getEncoder().encodeToString(entrada));
            System.out.println("📃 \u001B[32mTexto plano des-cifrado: " + salida);
            System.out.println("\u001B[35m------------------------------------- \u001B[0m");

            return salida;
        } catch (BadPaddingException e) {
            print("❌ Error 212: Dado el bloque final no se rellena correctamente. Estos problemas pueden surgir si se utiliza una clave incorrecta durante el descifrado.");
            return null;

        } catch (Exception e) {
            print("❌ Error 213: al des-cifrar");
            return null;
        }
    }

    /**
     * Descifra un texto cifrado utilizando el algoritmo AES en modo CBC (Cipher Block Chaining).
     *
     * @param clave La clave secreta utilizada para el descifrado.
     * @return El texto plano descifrado en forma de array de bytes, o null si ocurre algún error.
     */
    public byte[] descifrarCBC(SecretKey clave) {
        if(clave ==null){
            print("❌ Error 221: no se ha selecionado ninguan clave");
            return null;
        }
        try {

            cifrador = Cipher.getInstance("AES/ECB/NOPadding");

            cifrador.init(Cipher.DECRYPT_MODE, clave);

            return cifrador.doFinal(entrada);

        } catch (BadPaddingException e) {
            print("❌ Error 222: Dado el bloque final no se rellena correctamente. Estos problemas pueden surgir si se utiliza una clave incorrecta durante el descifrado.");
            return null;

        } catch (Exception e) {
            print("❌ Error 223: al des-cifrar");
            return null;
        }
    }

    /**
     * Cifra un texto utilizando el algoritmo AES en modo ECB (Electronic Codebook).
     *
     * @param conRelleno Indica si se utiliza relleno en el texto cifrado.
     * @param clave La clave secreta utilizada para el descifrado.
     * @return El texto cifrado en Bytes o null si ocurre algún error.
     */
    public byte[] cifrar(boolean conRelleno, SecretKey clave) {
        if(clave ==null){
            print("❌ Error 231: no se ha selecionado ninguan clave");
            return null;
        }
        try {
            if (conRelleno) {
                cifrador = Cipher.getInstance("AES/ECB/PKCS5Padding");
                System.out.println("\u001B[35m------------------------------------- \u001B[0m");
                System.out.println("\u001B[35m-------\u001B[0m Cifrando con relleno \u001B[35m--------\u001B[0m");
            } else {
                cifrador = Cipher.getInstance("AES/ECB/NOPadding");
                System.out.println("\u001B[35m------------------------------------- \u001B[0m");
                System.out.println("\u001B[35m-------\u001B[0m Cifrando sin relleno \u001B[35m--------\u001B[0m");
            }

            // Se inicializa en modo cifrar o descifrar
            cifrador.init(Cipher.ENCRYPT_MODE, clave);
            byte[] bufferCifrado = cifrador.doFinal(entrada);

            String ent = new String(entrada);

            System.out.println("📃 \u001B[32mTexto sin cifrar: " + ent);
            System.out.println("📜 \u001B[34mTexto cifrado: " + Base64.getEncoder().encodeToString(bufferCifrado));
            System.out.println("\u001B[35m------------------------------------- \u001B[0m");

            return bufferCifrado;
        } catch (Exception e) {
            print("❌ Error 232: al cifrar");
            return null;
        }
    }

    /**
     * Cifra un texto utilizando el algoritmo AES en modo CBC (Cipher Block Chaining).
     *
     * @param clave La clave secreta utilizada para el descifrado.
     * @return El texto cifrado en forma de array de bytes, o null si ocurre algún error.
     */
    public byte [] cifrarCBC(SecretKey clave){
        if(clave ==null){
            print("❌ Error 241: no se ha selecionado ninguan clave");
            return null;
        }
        try {

            cifrador = Cipher.getInstance("AES/ECB/NOPadding");
            // Se inicializa en modo cifrar o descifrar
            cifrador.init(Cipher.ENCRYPT_MODE, clave);
            byte[] bufferCifrado = cifrador.doFinal(entrada);

            return bufferCifrado;
        } catch (Exception e) {
            print("❌ Error 242: al cifrar");
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
     * Obtiene el objeto Cipher utilizado para cifrar/descifrar.
     *
     * @return El objeto Cipher utilizado.
     */
    public Cipher getCifrador() {
        return cifrador;
    }

    /**
     * Establece el objeto Cipher para cifrar/descifrar.
     *
     * @param cifrador El objeto Cipher a establecer.
     */
    public void setCifrador(Cipher cifrador) {
        this.cifrador = cifrador;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AES aes)) return false;
        return isTraza() == aes.isTraza() && isCodifica() == aes.isCodifica() && Arrays.equals(getEntrada(), aes.getEntrada()) && Objects.equals(getSalida(), aes.getSalida()) && Objects.equals(getCifrador(), aes.getCifrador());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getSalida(), isTraza(), isCodifica(), getCifrador());
        result = 31 * result + Arrays.hashCode(getEntrada());
        return result;
    }

    @Override
    public String toString() {
        return "AES{" +
                "entrada=" + Arrays.toString(entrada) +
                ", salida='" + salida + '\'' +
                ", traza=" + traza +
                ", codifica=" + codifica +
                ", cifrador=" + cifrador +
                '}';
    }

    /**
     * Imprime el texto si la traza está habilitada.
     *
     * @param text El texto a imprimir.
     */
    public void print(String text) {
        if (traza) System.out.println(text);
    }
}
