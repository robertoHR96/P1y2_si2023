import java.util.Objects;

public class EntradaSalida {
    private String ficheroEntrada = "";
    private String ficheroSalida = "";
    private boolean traza = false;
    public String leerEntrada() {
        return "";
    }

    public void escribirSalida() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntradaSalida that)) return false;
        return isTraza() == that.isTraza() && Objects.equals(getFicheroEntrada(), that.getFicheroEntrada()) && Objects.equals(getFicheroSalida(), that.getFicheroSalida());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFicheroEntrada(), getFicheroSalida(), isTraza());
    }

    @Override
    public String toString() {
        return "EntradaSalida{" +
                "ficheroEntrada='" + ficheroEntrada + '\'' +
                ", ficheroSalida='" + ficheroSalida + '\'' +
                ", traza=" + traza +
                '}';
    }
}
