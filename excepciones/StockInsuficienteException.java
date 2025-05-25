package excepciones;

public class StockInsuficienteException extends Exception {

    private final int stockDisponible;
    private final int cantidadSolicitada;

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
        this.stockDisponible = 0;
        this.cantidadSolicitada = 0;
    }

    public StockInsuficienteException(int stockDisponible, int cantidadSolicitada) {
        super("Stock insuficiente. Solicitado: " + cantidadSolicitada + ", Disponible: " + stockDisponible);
        this.stockDisponible = stockDisponible;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }
}
