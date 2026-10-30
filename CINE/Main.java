import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SistemaReservas s = new SistemaReservas();
        Funcion f = new Funcion("Interestelar", 3, "20:30", 12.0);

        Cliente anaCliente = new Cliente("Ana", "ana@mail.com", true);
        int r1 = s.crearReserva(anaCliente, f, Arrays.asList("F5", "F6"), "App");
        System.out.println("Reserva Ana: " + r1);

        Cliente luisCliente = new Cliente("Luis", "luis@mail.com", false);
        try {
            int r2 = s.crearReserva(luisCliente, f, Arrays.asList("F7", "F6"), "Taquilla");
            System.out.println("Reserva Luis: " + r2);
        } catch (IllegalStateException e) {
            System.out.println("Reserva Luis rechazada: " + e.getMessage());
        }
        
        //HIDE METHOD
        //al cambiar la visibilidad de la lista a private en la clase Funcion
        //la propiedad ocupados quedó protegida por el metodo de acceso y ya no se puede acceder 
        //directamente desde fuera de la clase (como antes con f.ocupados)
        System.out.println("Ocupados: " + f.getOcupados());

        Reserva reservaAna = s.buscar(r1);
        System.out.println("Precio Ana (socio): " + reservaAna.precio);

        System.out.println("Cancelar " + r1 + ": " + s.cancelar(r1));
        System.out.println("Cancelar 99: " + s.cancelar(99));
    }
}
