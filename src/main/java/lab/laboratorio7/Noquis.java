package lab.laboratorio7;

public class Noquis extends Pastas {

    public static final double PRECIO = 5500;

    public Noquis(double cantidad) {
        super(TipoPasta.NOQUIS, cantidad, PRECIO, Unidad.KILOGRAMOS);
    }
}
