package lab.laboratorio7;

import java.io.Serializable;

public abstract class Pastas implements Serializable {

    private static final long serialVersionUID = 1L;

    private final TipoPasta tipo;
    private final double cantidad;
    private final double precio;
    private final Unidad unidad;

    protected Pastas(TipoPasta tipo, double cantidad, double precio, Unidad unidad) {
        unidad.validar(cantidad); // lanza PedidoInvalidoException si no es válida
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.precio = precio;
        this.unidad = unidad;
    }

    public static Pastas crear(TipoPasta tipo, double cantidad) {
        switch (tipo) {
            case FIDEOS:
                return new FideosalHuevo(cantidad);
            case RAVIOLES:
                return new Ravioles(cantidad);
            case NOQUIS:
                return new Noquis(cantidad);
            case AGNOLOTIS:
                return new Agnolotis(cantidad);
            default:
                throw new CrearPastaExeption("No existe el tipo de pasta");
        }
    }

    public TipoPasta getTipo() {
        return tipo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public double subtotal() {
        return cantidad * precio;
    }

    public String descripcion() {
        return String.format("%s - %s %s x $%s = $%s",
                tipo, Pedido.formatear(cantidad), unidad,
                Pedido.formatear(precio), Pedido.formatear(subtotal()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pastas other = (Pastas) obj;
        return tipo == other.tipo;
    }

    @Override
    public int hashCode() {
        return tipo.hashCode();
    }
}
