package pedidos;

import productos.Producto;

public class LineaPedido {
    private Producto myProducto;
    private int cantidad;

    public LineaPedido(Producto myProducto, int cantidad) {
        this.myProducto = myProducto;
        this.cantidad = cantidad;
    }

    public Producto getMyProducto() {
        return myProducto;
    }

    public void setMyProducto(Producto myProducto) {
        this.myProducto = myProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}