package lab.laboratorio7;

public class Agnolotis extends Pastas {

    public static final double PRECIO = 7000;

    public Agnolotis(double cantidad) {
        super(TipoPasta.AGNOLOTIS, cantidad, PRECIO, Unidad.CAJAS);
    }
}
