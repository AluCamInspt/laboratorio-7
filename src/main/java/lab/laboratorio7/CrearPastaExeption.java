package lab.laboratorio7;

public class CrearPastaExeption extends RuntimeException {

    public CrearPastaExeption() {
        super();
    }

    public CrearPastaExeption(String mensaje) {
        super(mensaje);
    }

    public CrearPastaExeption(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }
}
