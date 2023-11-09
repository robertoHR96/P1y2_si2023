import java.util.Objects;

public class CBC {
    // Texto plano sin cifrar/descifrar
    private byte [] entrada;
    // Texto cifrado/desCifrado
    private String salida = "";
    // Variable de estado para la bandera "TRAZA" (inicializada en "true" por defecto)
    private boolean traza = true;
    // Variable de estado para la bandera "CODIFICA" (inicializada en "false" por defecto)
    private boolean codifica = true;
    private AES aes = new AES();
    private String [] vectorInicializacion = new String[16];

    public void codifica() {
        String ss = "";
        this.salida = ss;
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

    public String[] getVectorInicializacion() {
        return vectorInicializacion;
    }

    public void setVectorInicializacion(String[] vectorInicializacion) {
        this.vectorInicializacion = vectorInicializacion;
    }
}
