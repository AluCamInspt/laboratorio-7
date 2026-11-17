package lab.laboratorio7;

public class Ravioles extends Pastas {

    public static final double PRECIO = 6500;

    public Ravioles(double cantidad) {
        super(TipoPasta.RAVIOLES, cantidad, PRECIO, Unidad.CAJAS);
    }
}
