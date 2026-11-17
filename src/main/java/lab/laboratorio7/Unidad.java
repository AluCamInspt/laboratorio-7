package lab.laboratorio7;

public enum Unidad {
    KILOGRAMOS("kg") {
        @Override
        public void validar(double cantidad) {
            if (!(cantidad > 0) || cantidad > 10) {
                throw new PedidoInvalidoException(
                        "La cantidad debe ser mayor a 0 y no puede superar los 10 kg");
            }
        }
    },
    CAJAS("caja/s") {
        @Override
        public void validar(double cantidad) {
            if (!(cantidad >= 1) || Double.isInfinite(cantidad) || cantidad != Math.rint(cantidad)) {
                throw new PedidoInvalidoException(
                        "La cantidad de cajas debe ser un número entero mayor a 0");
            }
        }
    };

    private final String abreviatura;

    Unidad(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public abstract void validar(double cantidad);

    @Override
    public String toString() {
        return abreviatura;
    }
}
