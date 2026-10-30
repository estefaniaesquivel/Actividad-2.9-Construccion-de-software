import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaReservas {
    
    //Tecnica: Encapsulate downcast (Problema 13)
    //Se cambió la estructura interna de almacenamiento a un Map tipado y se encapsuló el casteo 
    //dentro del método buscar(id) para que devuelva directamente objetos Reserva y no Object.
    private Map<Integer, Reserva> db = new HashMap<>();
    private int contadorId = 1;

    //Tecnica: Rename method (Problema 1)
    //Se cambió el nombre genérico hacer por crearReserva para reflejar claramente su propósito.
    //Tecnica: Remove parameter (Problema 2)
    //Se eliminó el parámetro booleano imprimir ya que no se utilizaba en la lógica
    //Tecnica: Add parameter / Uso de parámetro (Problema 3)
    //Se mantuvo y documentó el parámetro canal para registrar el origen de la transacción.
    //Tecnica: Preserve whole object (Problema 4)
    //En lugar de recibir parámetros sueltos (nombre, email, socio), ahora recibe el objeto Cliente completo
    //Tecnica: Introduce parameter object (Problema 5)
    //Se reemplazaron los arrays paralelos de caracteres y enteros (filas y números) por una List<String> asientos
    //Tecnica: Replace error code with exception (Problema 14)
    //Se eliminó el retorno de códigos numéricos de error (-1, -2, -3) y se reemplazó por excepciones descriptivas (IllegalArgumentException, IllegalStateException).
    //Tecnica: Replace exception with test (Problema 15)
    //Se corrigió el bug de bloqueo validando primero con estaOcupado todos los asientos solicitados 
    // antes de proceder a marcar y registrar la reserva, evitando que asientos parciales queden bloqueados si uno falla
    public int crearReserva(Cliente cliente, Funcion funcion, List<String> asientos, String canal) {
        if (cliente == null) {
            throw new IllegalArgumentException("Error: El cliente no puede ser nulo.");
        }
        if (funcion == null) {
            throw new IllegalArgumentException("Error: La función no puede ser nula.");
        }
        if (asientos == null || asientos.isEmpty()) {
            throw new IllegalArgumentException("Error: Debe seleccionar al menos un asiento.");
        }

        // Validación previa (Replace exception with test) para evitar bloqueo por asientos ocupados parciales
        for (String asientoStr : asientos) {
            char fila = asientoStr.charAt(0);
            int numero = Integer.parseInt(asientoStr.substring(1));
            if (funcion.estaOcupado(fila, numero)) {
                throw new IllegalStateException("Error: El asiento " + asientoStr + " ya está ocupado.");
            }
        }

        //Si pasa todas las validaciones, se ocupan formalmente los asientos
        for (String asientoStr : asientos) {
            char fila = asientoStr.charAt(0);
            int numero = Integer.parseInt(asientoStr.substring(1));
            funcion.ocuparAsiento(fila, numero);
        }

        int idReserva = contadorId++;
        Reserva reserva = new Reserva(idReserva, cliente, funcion, asientos);
        
        //Cálculo del precio aplicando las técnicas de métodos de cálculo explícitos y parametrizados
        reserva.precio = calcularPrecioReserva(reserva);
        
        db.put(idReserva, reserva);
        return idReserva;
    }

    //Tecnica: Replace parameter with method (Problema 6)
    //Se eliminó el parámetro innecesario tipo ya que el precio base se obtiene directamente de la función asociada.
    //Tecnica: Replace parameter with explicit method (Problema 7)
    //Se separó la lógica de cálculo según el tipo de tarifa mediante métodos explícitos (tarifa socio vs general).
    //Tecnica: Parameterize method (Problema 8)
    //Se unificaron los descuentos duplicados (descuentoDel15 y descuentoDel25) en un método parametrizado 'aplicarDescuento'.
    public double calcularPrecioReserva(Reserva reserva) {
        double precioBase = reserva.funcion.precio * reserva.asientos.size();
        if (reserva.cliente.socio) {
            return aplicarDescuento(precioBase, 0.15); // Descuento de socio parametrizado al 15%
        } else {
            return precioBase; // Tarifa general
        }
    }

    //Tecnica: Parameterize method (Problema 8)
    //Método genérico que recibe el porcentaje como parámetro para evitar duplicidad de código en descuentos.
    private double aplicarDescuento(double monto, double porcentaje) {
        return monto * (1.0 - porcentaje);
    }

    //Tecnica: Encapsulate downcast (Problema 13)
    //El método buscar realiza la búsqueda de forma segura y tipada devolviendo directamente un objeto Reserva.
    public Reserva buscar(int id) {
        Reserva reserva = db.get(id);
        if (reserva == null) {
            throw new IllegalArgumentException("Error: No se encontró la reserva con ID " + id);
        }
        return reserva;
    }

    public boolean cancelar(int id) {
        try {
            Reserva reserva = buscar(id);
            if (reserva.cancelada) {
                return false;
            }
            // Liberar asientos en la función
            for (String asientoStr : reserva.asientos) {
                char fila = asientoStr.charAt(0);
                int numero = Integer.parseInt(asientoStr.substring(1));
                reserva.funcion.desocuparAsiento("" + fila + numero);
            }
            // Utilizando el método del dominio en lugar del setter genérico (Remove setting method)
            reserva.cancelar();
            return true;
        } catch (IllegalArgumentException e) {
            // Manejo de error controlado ante una reserva inexistente
            return false;
        }
    }
}