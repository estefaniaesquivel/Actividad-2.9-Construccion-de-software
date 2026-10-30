import java.util.List;

public class Reserva {
    public int id;
    public Cliente cliente;
    public Funcion funcion;
    public List<String> asientos;
    public double precio;
    public boolean cancelada;

    public Reserva(int id, Cliente cliente, Funcion funcion, List<String> asientos) {
        this.id = id;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asientos = asientos;
    }

    //se usó Remove setting method (problema 12)
    // refactorización: se eliminó el método setCancelada(boolean) genérico y se reemplazó 
    // por un método con nombre claro y orientado al dominio (cancelar) que encapsula 
    // el cambio de estado de la reserva.
    public void cancelar() {
        this.cancelada = true;
    }
}