package pedidos;

import java.util.List;

public class Pedido {
    static int count = 0;
    private int id;
    private List<LineaPedido> myList;

    public Pedido(List<LineaPedido> myList) {
        this.myList = myList;
        this.id = ++count;
    }

    public int getId() {
        return id;
    }

    public List<LineaPedido> getMyList() {
        return myList;
    }

    public void setMyList(List<LineaPedido> myList) {
        this.myList = myList;
    }

}
