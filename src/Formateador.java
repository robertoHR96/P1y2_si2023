import java.util.Objects;

public class Formateador {
    private String entrada = "";
    private String salida = "";
    private boolean traza = false;



    public void formatear(){
        setSalida("");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Formateador that)) return false;
        return isTraza() == that.isTraza() && Objects.equals(getEntrada(), that.getEntrada()) && Objects.equals(getSalida(), that.getSalida());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntrada(), getSalida(), isTraza());
    }

    @Override
    public String toString() {
        return "Formateador{" +
                "entrada='" + entrada + '\'' +
                ", salida='" + salida + '\'' +
                ", traza=" + traza +
                '}';
    }
}
