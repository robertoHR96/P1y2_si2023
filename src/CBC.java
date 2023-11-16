import javax.crypto.SecretKey;
import java.util.Objects;

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

    public void codifica(SecretKey clave) {
        if (codifica) {
            int numBloques = entrada.length % 16;
            byte[] cbcCifrado = new byte[entrada.length];
            if (numBloques == 0) {
                // Se inicializa la clave del aes
                for (int i = 0; i < numBloques; i++) {
                    byte[] bloque = new byte[16];
                    byte[] bloqueAnterior = new byte[16];
                    if(i == 0) {
                        System.arraycopy(entrada, i, bloque, 0, 16);
                        bloque = xorEntrada(bloque, vectorInicializacion);
                    }
                    else{
                        System.arraycopy(cbcCifrado, i, vectorInicializacion, 0, 16);
                        bloque = xorEntrada(bloque, vectorInicializacion);
                    }
                    // se selecciona la entrada para AES con el bloque anterior
                    aes.setEntrada(bloque);
                    // se cifra y se saca el bloque cifrado
                    bloque = aes.cifrar(false, clave);
                    // se añade el bloque al array de bytes final
                    // usar arraycpy
                    //cbcCifrado = anadirBoque(bloque, cbcCifrado, i);
                }
            } else {
                print("❌ Error: La entrada debe ser múltiplo de 16");
            }
        }
        String ss = "";
        this.salida = ss;
    }

    public byte [] xorEntrada (byte [] arrayBytes1, byte [] arrayBytes2){
        byte [] resultadoXOR = new byte[16];
        for (int i = 0; i < 16; i++) {
            resultadoXOR[i] = (byte) (arrayBytes1[i] ^ arrayBytes2[i]);
        }
        return resultadoXOR;
    }


    public byte[] getEntrada() {
        return entrada;
    }

    public void setEntrada(byte[] entrada) {
        this.entrada = entrada;
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

    public AES getAes() {
        return aes;
    }

    public void setAes(AES aes) {
        this.aes = aes;
    }

    public byte[] getVectorInicializacion() {
        return vectorInicializacion;
    }

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
