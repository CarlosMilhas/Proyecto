package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import excepciones.StockInsuficienteException;
import pedidos.LineaPedido;
import pedidos.Pedido;
import productos.Producto;

public class PedidoService {
    private Scanner scanner;
    private ArrayList<Pedido> pedidos;
    private ProductoService productoService;
    private DisplayService display;

    public PedidoService(Scanner scanner, ProductoService productoService, DisplayService display) {
        this.scanner = scanner;
        this.pedidos = new ArrayList<>();
        this.productoService = productoService;
        this.display = display;
    }

    public void verPedidos() {
        display.limpiarPantalla();
        if (pedidos == null || pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            display.esperar();
            return;
        }

        System.out.println("\nLista de Pedidos:");
        for (Pedido pedido : pedidos) {
            System.out.println("Pedido #" + pedido.getId());
            System.out.println("Productos:");
            double total = 0;

            for (LineaPedido linea : pedido.getMyList()) {
                Producto p = linea.getMyProducto();
                int cant = linea.getCantidad();
                double subtotal = p.getPrice() * cant;
                total += subtotal;

                System.out.println(
                        p.getName() + " " + cant + " unidades por $" + subtotal + " precio unitario: $" + p.getPrice());
            }

            System.out.println("Total del pedido: $" + total);
        }
        display.esperar();
    }

    public void nuevoPedido() {
        display.limpiarPantalla();
        if (!productoService.hayProductos()) {
            System.out.println("No hay productos disponibles para crear un pedido.");
            display.esperar();
            return;
        }

        List<LineaPedido> lineas = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            try {
                long idProducto = productoService.buscarProducto();
                if (idProducto == 0) {
                    break;
                }

                Producto productoSeleccionado = productoService.getProductoPorId(idProducto);
                if (productoSeleccionado == null) {
                    System.out.println("Error: Producto no encontrado.");
                    continue;
                }

                System.out.print("Ingrese la cantidad: ");
                int cantidad = Integer.parseInt(scanner.nextLine());
                if (cantidad <= 0) {
                    System.out.println("La cantidad debe ser mayor a cero.");
                    continue;
                }

                if (cantidad > productoSeleccionado.getStock()) {
                    throw new StockInsuficienteException(productoSeleccionado.getStock(), cantidad);
                }

                LineaPedido linea = new LineaPedido(productoSeleccionado, cantidad);
                lineas.add(linea);
                productoSeleccionado.setStock(productoSeleccionado.getStock() - cantidad);

                System.out.print("¿Desea añadir otro producto? (Y/N): ");
                continuar = scanner.nextLine().equalsIgnoreCase("Y");

            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            } catch (StockInsuficienteException e) {
                System.out.println("Error: " + e.getMessage());
                display.esperar();
            }
        }

        if (!lineas.isEmpty()) {
            Pedido nuevoPedido = new Pedido(lineas);
            pedidos.add(nuevoPedido);
            System.out.println("Pedido #" + nuevoPedido.getId() + " creado exitosamente.");
        } else {
            System.out.println("No se ha creado el pedido porque está vacío.");
        }
        display.esperar();
    }

    public ArrayList<Pedido> getPedidos() {
        return this.pedidos;
    }
}
