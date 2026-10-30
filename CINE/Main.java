public class Main {
    public static void main(String[] args) {
        SistemaReservas s = new SistemaReservas();
        Funcion f = new Funcion("Interestelar", 3, "20:30", 12.0);

        int r1 = s.hacer("Ana", "ana@mail.com", true, f,
                new char[]{'F', 'F'}, new int[]{5, 6}, false, "App");
        System.out.println("Reserva Ana: " + r1);

        // F7 se marca como ocupado antes de fallar en F6 (bug)
        int r2 = s.hacer("Luis", "luis@mail.com", false, f,
                new char[]{'F', 'F'}, new int[]{7, 6}, false, "Taquilla");
        System.out.println("Reserva Luis: " + r2);

        //HIDE METHOD
        //al cambiar la visibilidad de la lista a private en la clase Funcion
        //la propiedad ocupados quedó protegida por el metodo de acceso y ya no se puede acceder 
        //directamente desde fuera de la clase (como antes con f.ocupados)
        System.out.println("Ocupados: " + f.getOcupados());

        Reserva ana = (Reserva) s.buscar(1);
        System.out.println("Precio Ana (socio): " + ana.precio);

        System.out.println("Cancelar 1: " + s.cancelar(1));
        System.out.println("Cancelar 99: " + s.cancelar(99));
    }
}