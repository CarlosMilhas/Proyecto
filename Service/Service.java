package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import excepciones.StockInsuficienteException;
import pedidos.LineaPedido;
import pedidos.Pedido;
import productos.Producto;

public class Service {
    private ArrayList<Producto> productos;
    private Scanner scanner;
    private ArrayList<Pedido> pedidos;

    public Service() {
        this.productos = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.pedidos = new ArrayList<>();
    }

    private void limpiarPantalla() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            limpiarPantalla();
            System.out.println("\n1. Agregar producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Buscar producto");
            System.out.println("4. Actualizar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Nuevo Pedido");
            System.out.println("7. Ver Pedidos");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> agregarProducto();
                    case 2 -> listarProductos();
                    case 3 -> buscarProducto();
                    case 4 -> actualizarProducto();
                    case 5 -> eliminarProducto();
                    case 6 -> nuevoPedido();
                    case 7 -> verPedidos();
                    case 8 -> salir = true;
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private void verPedidos() {
        limpiarPantalla();
        if (pedidos == null || pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            esperar();
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

                System.out.printf("- %s x%d: $%.2f ($%.2f c/u)%n",
                        p.getName(), cant, subtotal, p.getPrice());
            }

            System.out.printf("Total del pedido: $%.2f%n%n", total);
        }
        esperar();
    }

    private void nuevoPedido() {
        limpiarPantalla();
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles para crear un pedido.");
            esperar();
            return;
        }

        List<LineaPedido> lineas = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            try {
                long idProducto = buscarProducto();
                if (idProducto == 0) {
                    break;
                }
                Producto productoSeleccionado = null;
                for (Producto p : productos) {
                    if (p.getId() == idProducto) {
                        productoSeleccionado = p;
                        break;
                    }
                }

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

                esperar();

            }
        }

        if (!lineas.isEmpty()) {
            if (pedidos == null) {
                pedidos = new ArrayList<>();
            }

            Pedido nuevoPedido = new Pedido(lineas);
            pedidos.add(nuevoPedido);
            System.out.println("Pedido #" + nuevoPedido.getId() + " creado exitosamente.");
        } else {
            System.out.println("No se ha creado el pedido porque está vacío.");
        }
        esperar();

    }

    private void agregarProducto() {
        limpiarPantalla();
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el precio del producto: ");
        double precio = Double.parseDouble(scanner.nextLine());

        System.out.print("Ingrese el stock del producto: ");
        int stock = Integer.parseInt(scanner.nextLine());

        try {
            Producto producto = new Producto(nombre, precio, stock);
            productos.add(producto);
            System.out.println("Producto agregado exitosamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        esperar();

    }

    private void listarProductos() {
        limpiarPantalla();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            esperar();
            return;
        }
        System.out.println("\nLista de productos:");
        for (Producto producto : productos) {
            System.out.println(producto);
        }
        esperar();
    }

    private long buscarProducto() {
        limpiarPantalla();
        System.out.println("Ingrese el id o nombre del producto: ");
        String entrada = scanner.nextLine();
        try {
            long id = Long.parseLong(entrada);
            for (Producto producto : productos) {
                if (producto.getId() == id) {
                    System.out.println("Producto encontrado: " + producto);
                    esperar();
                    return producto.getId();
                }
            }
            System.out.println("Producto no encontrado por ID.");

        } catch (NumberFormatException e) {
            boolean encontrado = false;
            for (Producto producto : productos) {
                if (producto.getName().equalsIgnoreCase(entrada)) {
                    System.out.println("Producto encontrado: " + producto);
                    encontrado = true;
                    esperar();
                    return producto.getId();
                }
            }
            if (!encontrado) {
                System.out.println("Producto no encontrado por nombre.");
            }
        }
        esperar();
        return 0;
    }

    private void actualizarProducto() {
        limpiarPantalla();

        try {
            long id = this.buscarProducto();
            for (Producto producto : productos) {
                if (producto.getId() == id) {
                    System.out.print("Nuevo precio (Enter para mantener actual): ");
                    String precioStr = scanner.nextLine();
                    if (!precioStr.isEmpty()) {
                        try {
                            producto.setPrice(Double.parseDouble(precioStr));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }

                    System.out.print("Nuevo stock (Enter para mantener actual): ");
                    String stockStr = scanner.nextLine();
                    if (!stockStr.isEmpty()) {
                        try {
                            producto.setStock(Integer.parseInt(stockStr));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }

                    System.out.println("Producto actualizado exitosamente.");
                    esperar();
                    return;
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un ID válido.");
        }
        esperar();
    }

    private void eliminarProducto() {
        limpiarPantalla();

        try {
            long id = this.buscarProducto();

            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getId() == id) {
                    if (this.confirmar()) {
                        productos.remove(i);
                        System.out.println("Producto eliminado exitosamente.");
                    } else {
                        System.out.println("Eliminzación cancelada...");
                    }
                    esperar();
                    return;
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un ID válido.");
        }
        esperar();
    }

    private void esperar() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    private boolean confirmar() {
        System.out.print("Estas Seguro? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }
}
