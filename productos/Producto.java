package productos;

public class Producto {
    private static long contador = 0;
    private long id;
    private int stock;
    private String name;
    private double price;

    public Producto(String name, double price, int stock) {
        this.id = ++contador;
        this.stock = stock;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Producto: id: " + id + ", nombre: " + name + ", precio: $" + price + ", stock: " + stock;

    }

    public static long getContador() {
        return contador;
    }

    public static void setContador(long contador) {
        Producto.contador = contador;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");

        }
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.price = price;
    }

}
