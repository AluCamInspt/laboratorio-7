package lab.laboratorio7;

public class FideosalHuevo extends Pastas {

    public static final double PRECIO = 6000;

    public FideosalHuevo(double cantidad) {
        super(TipoPasta.FIDEOS, cantidad, PRECIO, Unidad.KILOGRAMOS);
    }
}
