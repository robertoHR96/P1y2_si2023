import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AES {
    // Texto plano sin cifrar/descifrar
    private String entrada = "";
    // Texto cifrado/desCifrado
    private String salida = "";
    // Variable de estado para la bandera "TRAZA" (inicializada en "true" por defecto)
    private boolean traza = true;
    // Variable de estado para la bandera "CODIFICA" (inicializada en "false" por defecto)
    private boolean codifica = true;

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
    }

    public String cifrar(boolean conRelleno){
        String salida = "";
        if(conRelleno){
            anadirRelleno();
        }

        return salida;
    }
    public void anadirRelleno(){

    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
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

    public AES copy(){
        return new AES(this.entrada, this.salida, this.traza, this.codifica);
    }
}
