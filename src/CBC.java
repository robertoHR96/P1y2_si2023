import javax.crypto.SecretKey;
import java.util.Base64;
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

    public byte[] cifra(SecretKey clave) {
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
            System.out.println("📃 \u001B[32mTexto sin cifrar: " + new String(entrada) );
            System.out.println("📜 \u001B[34mTexto cifrado: " + Base64.getEncoder().encodeToString(cbcCifrado));
            System.out.println("\u001B[35m------------------------------------- \u001B[0m");
        } else {
            print("❌ Error: La entrada debe ser múltiplo de 16");
        }
        return cbcCifrado;
    }

    public String descifra(SecretKey clave) {
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
            System.out.println("📃 \u001B[32mTexto des-cifrar: " + new String(cbcCifrado) );
            System.out.println("\u001B[35m------------------------------------- \u001B[0m");
        } else {
            print("❌ Error: La entrada debe ser múltiplo de 16");
        }

        return new String(cbcCifrado);
    }

    public byte[] xorEntrada(byte[] arrayBytes1, byte[] arrayBytes2) {
        byte[] resultadoXOR = new byte[16];
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
