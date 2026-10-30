import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Funcion {
    public String titulo;
    public int sala;
    public String hora;
    public double precio;
    
    // Ocultamos la colección (de public a private)
    private final List<String> ocupados = new ArrayList<>();

    public Funcion(String titulo, int sala, String hora, double precio) {
        this.titulo = titulo;
        this.sala = sala;
        this.hora = hora;
        this.precio = precio;
    }

    // TECNICA APLICADA: Separate query from modifier
    // REFACTOR:
    // Consulta (Query): Devuelve si el asiento está ocupado sin alterar el estado
    public boolean estaOcupado(char fila, int numero) {
        String codigo = generarCodigoAsiento(fila, numero);
        return getOcupadosInterno().contains(codigo);
    }

    // Modificador (Modifier): Cambia el estado de la lista de asientos ocupados
    public void ocupar(char fila, int numero) {
        String codigo = generarCodigoAsiento(fila, numero);
        if (estaOcupado(fila, numero)) {
            throw new IllegalStateException("Asiento ocupado: " + codigo);
        }
        getOcupadosInterno().add(codigo);
    }

    // TECNICA APLICADA: Hide Method
    // Los métodos auxiliares o de acceso directo a la lista mutable se mantienen privados
    private String generarCodigoAsiento(char fila, int numero) {
        return "" + fila + numero;
    }

    private List<String> getOcupadosInterno() {
        return this.ocupados;
    }

    // Cuando otras clases necesitan leer la lista de ocupados, se devuelve una copia de solo lectura
    public List<String> getOcupados() {
        return Collections.unmodifiableList(this.ocupados);
    }
    
    // Se crea una función para desocupar los asientos.
    public void desocupar(String codigoAsiento) {
        this.ocupados.remove(codigoAsiento);
    }

    // TECNICA APLICADA: Remove setting method
    // REFACTOR: Se elimina setPrecio(double precio) y se expone únicamente el getter de lectura
    public double getPrecio() {
        return this.precio;
    }
}