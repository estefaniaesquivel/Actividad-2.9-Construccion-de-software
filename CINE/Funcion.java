import java.util.ArrayList;
import java.util.Collections; 
import java.util.List;

public class Funcion {
    public String titulo;
    public int sala;
    public String hora;
    public double precio;
    
    //TECNICA UTILIZADA:Hide method PROBLEMA 10
    //REFACTOR:Al aplicar esta técnica se hizo la lista ocupados privada (antes estaba public) para evitar que 
    //cualquier clase externa la modifique directamente
    private List<String> ocupados = new ArrayList<>();

    public Funcion(String titulo, int sala, String hora, double precio) {
        this.titulo = titulo;
        this.sala = sala;
        this.hora = hora;
        this.precio = precio;
    }

    //HIDE METHOD
    //al ocultar el atributo ocupados haciéndolo private en la clase Funcion 
    //(para evitar que cualquier clase externa lo modifique), se perdió la capacidad de consultar qué asientos 
    //ya están reservados desde otras clases, entonces se le hizo su método de acceso
    //se utiliza Collections.unmodifiableList() dentro del método para proteger la integridad del encapsulamiento
    public List<String> getOcupados() {
        return Collections.unmodifiableList(ocupados);
    }


    //TECNICA UTILIZADA:Separate query from modifier PROBLEMA 9
    //REFACTOR:Al aplicar esta técnica se separó la consulta (verificar si el asiento está ocupado) 
    //de la modificación (marcarlo como ocupado), permitiendo validar todos los asientos antes de 
    //realizar cambios y corregir el bug de bloqueo; ya que antes en el código original, el método 
    //ocupar mezclaba la validación con la modificación en medio de un ciclo. 
    //y si se iba a reservar dos asientos y el segundo fallaba, el primero ya se había quedado marcado como ocupado
    public boolean estaOcupado(char fila, int numero) {
        String codigo = "" + fila + numero;
        return ocupados.contains(codigo);
    }
    //al aplicar esta técnica se separaron las operaciones de escritura  y modificación 
    //creando ocuparAsiento y desocuparAsiento de forma independiente a las validaciones de lectura, evitando el bloqueo
    public void ocuparAsiento(char fila, int numero) {
        String codigo = "" + fila + numero;
        if (!ocupados.contains(codigo)) {
            ocupados.add(codigo);
        }
    }
    
    public void desocuparAsiento(String codigo) {
        ocupados.remove(codigo);
    }

    
    // TÉCNICA UTILIZADA:Remove setting method PROBLEMA 11
    // REFACTOR:Al aplicar esta técnica se eliminó el método setPrecio(double) ya que el precio 
    // de la función no debe modificarse mediante un setter genérico
}