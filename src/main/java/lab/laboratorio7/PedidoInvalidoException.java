package lab.laboratorio7;

public class PedidoInvalidoException extends RuntimeException {

    public PedidoInvalidoException() {
        super();
    }

    public PedidoInvalidoException(String mensaje) {
        super(mensaje);
    }

    public PedidoInvalidoException(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }
}
