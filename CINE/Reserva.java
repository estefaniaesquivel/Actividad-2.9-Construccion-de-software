import java.util.List;

public class Reserva {
    public int id;
    public Cliente cliente;
    public Funcion funcion;
    public List<String> asientos;
    public double precio;
    private boolean cancelada; // Se encapsula para evitar modificaciones directas

    public Reserva(int id, Cliente cliente, Funcion funcion, List<String> asientos) {
        this.id = id;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asientos = asientos;
        this.cancelada = false; // Valor inicial por defecto
    }

    // TECNICA APLICADA: Remove setting method
    // REFACTOR: Reemplaza a setCancelada(boolean) con una acción de dominio clara
    public void cancelar() {
        this.cancelada = true;
    }

    public boolean isCancelada() {
        return this.cancelada;
    }
}