import java.util.ArrayList;
import java.util.Scanner;

public class GestorProductos {
    private ArrayList<Producto> productos;
    private Scanner scanner;
    private String message = "Ingrese el ID o el nombre del producto a ";

    public GestorProductos() {
        this.productos = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    private void limpiarPantalla() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Si falla el comando, imprimimos líneas en blanco como alternativa
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
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> agregarProducto();
                    case 2 -> listarProductos();
                    case 3 -> buscarProducto(message + "buscar: ");
                    case 4 -> actualizarProducto(message + "actualizar: ");
                    case 5 -> eliminarProducto(message + "eliminar: ");
                    case 6 -> salir = true;
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
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
        limpiarPantalla();
    }

    private void listarProductos() {
        limpiarPantalla();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        System.out.println("\nLista de productos:");
        for (Producto producto : productos) {
            System.out.println(producto);
        }
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    private long buscarProducto(String instruccion) {
        limpiarPantalla();
        System.out.print(instruccion);
        String entrada = scanner.nextLine();
        try {
            long id = Long.parseLong(entrada);
            for (Producto producto : productos) {
                if (producto.getId() == id) {
                    System.out.println("Producto encontrado: " + producto);
                    System.out.println("\nPresione Enter para continuar...");
                    scanner.nextLine();
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
                    System.out.println("\nPresione Enter para continuar...");
                    scanner.nextLine();
                    return producto.getId();
                }
            }
            if (!encontrado) {
                System.out.println("Producto no encontrado por nombre.");
            }
        }
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
        return 0;
    }

    private void actualizarProducto(String instruccion) {
        limpiarPantalla();
        System.out.print(instruccion);
        try {
            long id = this.buscarProducto("");

            for (Producto producto : productos) {
                if (producto.getId() == id) {
                    System.out.print("Nuevo nombre (Enter para mantener actual): ");
                    String nombre = scanner.nextLine();
                    if (!nombre.isEmpty()) {
                        producto.setName(nombre);
                    }

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
                    System.out.println("\nPresione Enter para continuar...");
                    scanner.nextLine();
                    return;
                }
            }
            System.out.println("Producto no encontrado.");
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un ID válido.");
        }
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    private void eliminarProducto(String instruccion) {
        limpiarPantalla();
        System.out.print(instruccion);
        try {
            long id = Long.parseLong(scanner.nextLine());

            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getId() == id) {
                    productos.remove(i);
                    System.out.println("Producto eliminado exitosamente.");
                    System.out.println("\nPresione Enter para continuar...");
                    scanner.nextLine();
                    return;
                }
            }
            System.out.println("Producto no encontrado.");
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un ID válido.");
        }
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}
