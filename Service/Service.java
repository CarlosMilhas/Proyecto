package Service;

import java.util.Scanner;

public class Service {
    private Scanner scanner;
    private ProductoService productoService;
    private PedidoService pedidoService;
    private DisplayService displayService;

    public Service() {
        this.scanner = new Scanner(System.in);
        this.displayService = new DisplayService(scanner);
        this.productoService = new ProductoService(scanner, displayService);
        this.pedidoService = new PedidoService(scanner, productoService, displayService);
    }

    public void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            displayService.mostrarMenu();

            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> productoService.agregarProducto();
                    case 2 -> productoService.listarProductos();
                    case 3 -> productoService.buscarProducto();
                    case 4 -> productoService.actualizarProducto();
                    case 5 -> productoService.eliminarProducto();
                    case 6 -> pedidoService.nuevoPedido();
                    case 7 -> pedidoService.verPedidos();
                    case 8 -> salir = true;
                    default -> displayService.mostrarErrorOpcionInvalida();
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
                displayService.esperar();
            }
        }
    }
}
