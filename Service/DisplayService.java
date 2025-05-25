package Service;

import java.util.Scanner;

public class DisplayService {
    private Scanner scanner;

    public DisplayService(Scanner scanner) {
        this.scanner = scanner;
    }

    // https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
    // encontre esto por ahi, para limpiar la consola y emprolijar la cuestion.
    public void limpiarPantalla() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
        }
    }

    // esto para que no se borre la pantalla antes de mostrar algun mensaje deseado.
    public void esperar() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public boolean confirmar() {
        System.out.print("¿Está seguro? (Y/N): ");
        return scanner.nextLine().equalsIgnoreCase("y");
    }

    public void mostrarMenu() {
        limpiarPantalla();
        System.out.println("1. Agregar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar producto");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Nuevo Pedido");
        System.out.println("7. Ver Pedidos");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public void mostrarErrorOpcionInvalida() {
        System.out.println("Opción inválida. Por favor, intente de nuevo.");
        esperar();
    }
}
