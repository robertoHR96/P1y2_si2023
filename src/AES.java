import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.util.Base64;

public class AES {
    // Texto plano sin cifrar/descifrar
    private byte [] entrada;
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
    public AES() {}

    /**
     * Constructor parametrizado de la clase
     * @param entrada Texto de entrada a cifrar/des-cifrar
     * @param salida Texto de salida cifrado/des-cifrado
     * @param traza Bandera para mostrar traza
     * @param codifica Bandera para cifrar/des-cifrar
     */
    public AES(String entrada, String salida, boolean traza, boolean codifica) {
        this.entrada = entrada;
        this.salida = salida;
        this.traza = traza;
        this.codifica = codifica;
        this.cifrador = null;
    }

    public String cifrar(boolean conRelleno, SecretKey clave){
        String salida = null;
        try {
            // si se cifra con relleno se añade algo al final para saer cuanto se ha rellenado

            /**
             *
             * cifrar con relleno da igual, si se cifra sin relleno n%16 y si se descifra siempre n%16
             *
             */

            // Se inicializa el cifrador en modo cifrado con relleno o sin el
            if(!conRelleno) cifrador = Cipher.getInstance ("AES/ECB/PKCS5Padding");
            else cifrador = Cipher.getInstance ("AES/ECB/NOPadding");

            // Se inicializa en modo cifrar o descifrar
            if(codifica) cifrador.init(Cipher.ENCRYPT_MODE, clave);
            else cifrador.init(Cipher.DECRYPT_MODE, clave);

            byte[] bufferCifrado = null;
            bufferCifrado = cifrador.doFinal(entrada);
            salida = Base64.getEncoder().encodeToString(bufferCifrado);
            return salida;
        }catch ( Exception e){
            print("-- Error al cifrar");
            return null;
        }
    }
    public void anadirRelleno(){

    }

    public byte[] getEntrada() {
        return entrada;
    }

    public void setEntrada(byte[] entrada) {
        this.entrada = entrada;
    }

    public Cipher getCifrador() {
        return cifrador;
    }

    public void setCifrador(Cipher cifrador) {
        this.cifrador = cifrador;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public boolean isTraza() {
        return traza;
    }

    public void setTraza(boolean traza) {
        this.traza = traza;
    }

    public boolean isCodifica() {
        return codifica;
    }

    public void setCodifica(boolean codifica) {
        this.codifica = codifica;
    }


    public void print(String text) {
        if (traza) System.out.println(text);
    }
}
