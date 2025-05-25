package Service;

import java.util.ArrayList;
import java.util.Scanner;
import productos.Producto;

public class ProductoService {
    private ArrayList<Producto> productos;
    private Scanner scanner;
    private DisplayService display;

    public ProductoService(Scanner scanner, DisplayService display) {
        this.productos = new ArrayList<>();
        this.scanner = scanner;
        this.display = display;
    }

    public void agregarProducto() {
        display.limpiarPantalla();
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();

        try {
            System.out.print("Ingrese el precio del producto: ");
            double precio = Double.parseDouble(scanner.nextLine());

            System.out.print("Ingrese el stock del producto: ");
            int stock = Integer.parseInt(scanner.nextLine());

            Producto producto = new Producto(nombre, precio, stock);
            productos.add(producto);
            System.out.println("Producto agregado exitosamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, ingrese valores numéricos válidos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        display.esperar();
    }

    public void listarProductos() {
        display.limpiarPantalla();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            display.esperar();
            return;
        }
        System.out.println("\nLista de productos:");
        for (Producto producto : productos) {
            System.out.println(producto);
        }
        display.esperar();
    }

    public long buscarProducto() {
        display.limpiarPantalla();
        System.out.println("Ingrese el id o nombre del producto: ");
        String entrada = scanner.nextLine();
        try {
            long id = Long.parseLong(entrada);
            for (Producto producto : productos) {
                if (producto.getId() == id) {
                    System.out.println("Producto encontrado: " + producto);
                    display.esperar();
                    return producto.getId();
                }
            }

        } catch (NumberFormatException e) {
            boolean encontrado = false;
            for (Producto producto : productos) {
                if (producto.getName().equalsIgnoreCase(entrada)) {
                    System.out.println("Producto encontrado: " + producto);
                    encontrado = true;
                    display.esperar();
                    return producto.getId();
                }
            }
            if (!encontrado) {
                System.out.println("Producto no encontrado");
            }
        }
        display.esperar();
        return 0;
    }

    public void actualizarProducto() {
        display.limpiarPantalla();

        try {
            long id = this.buscarProducto();
            for (Producto producto : productos) {
                if (producto.getId() == id) {
                    System.out.print("Nuevo precio: ");
                    String precioStr = scanner.nextLine();
                    if (!precioStr.isEmpty()) {
                        try {
                            producto.setPrice(Double.parseDouble(precioStr));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }

                    System.out.print("Nuevo stock: ");
                    String stockStr = scanner.nextLine();
                    if (!stockStr.isEmpty()) {
                        try {
                            producto.setStock(Integer.parseInt(stockStr));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }

                    System.out.println("Producto actualizado exitosamente.");
                    display.esperar();
                    return;
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un ID válido.");
        }
        display.esperar();

    }

    public void eliminarProducto() {
        display.limpiarPantalla();

        try {
            long id = this.buscarProducto();

            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getId() == id) {
                    if (display.confirmar()) {
                        productos.remove(i);
                        System.out.println("Producto eliminado exitosamente.");
                    } else {
                        System.out.println("Eliminación cancelada...");
                    }
                    display.esperar();
                    return;
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un ID válido.");
        }
        display.esperar();
    }

    public Producto getProductoPorId(long id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public boolean hayProductos() {
        return !productos.isEmpty();
    }

    public ArrayList<Producto> getProductos() {
        return this.productos;
    }
}
