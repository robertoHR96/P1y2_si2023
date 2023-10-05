import java.util.Objects;

public class Hill {
    private String entrada = "";
    private String salida = "";
    private boolean traza = false;

    public void desCifrar(){
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
        if (!(o instanceof Hill hill)) return false;
        return isTraza() == hill.isTraza() && Objects.equals(getEntrada(), hill.getEntrada()) && Objects.equals(getSalida(), hill.getSalida());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntrada(), getSalida(), isTraza());
    }

    @Override
    public String toString() {
        return "Hill{" + "entrada='" + entrada + '\'' + ", salida='" + salida + '\'' + ", traza=" +  traza + '}';
    }
}
