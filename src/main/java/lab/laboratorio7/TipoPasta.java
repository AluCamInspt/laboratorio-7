package lab.laboratorio7;

public enum TipoPasta {
    FIDEOS("Fideos al huevo"),
    RAVIOLES("Ravioles"),
    NOQUIS("Ñoquis"),
    AGNOLOTIS("Agnolotis");

    private final String nombre;

    TipoPasta(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
