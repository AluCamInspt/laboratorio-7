package lab.laboratorio7;

import java.util.Scanner;

public class Consola {

    private final Scanner scanner = new Scanner(System.in);

    // ---------- Lecturas básicas ----------
    public String leerLinea(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    // Lee un entero y repite hasta que sea válido
    public int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerLinea(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero.");
            }
        }
    }

    // ---------- Carga de un pedido ----------
    public Pedido pedirPedido(int numero) {
        Cliente cliente = pedirCliente();
        Venta canal = tipoVenta();
        Pedido pedido = new Pedido(numero, cliente, canal);

        boolean otra;
        do {
            pedido.agregarPasta(pedirPasta());
            otra = confirmar("¿Agregar otra pasta al pedido? (S/N): ");
        } while (otra);
        return pedido;
    }
   // ---------- Carga de un cliente ----------
    private Cliente pedirCliente() {
        String nombre = pedirTexto("Nombre: ", true);
        String apellido = pedirTexto("Apellido: ", true);
        String mail = pedirMail();
        String telefono = pedirTelefono();
        String direccion = pedirTexto("Dirección: ", false);
        return new Cliente(nombre, apellido, mail, telefono, direccion);
    }

    private String pedirTexto(String mensaje, boolean soloLetras) {
        while (true) {
            String texto = leerLinea(mensaje);
            try {
                if (texto.isEmpty()) {
                    throw new IllegalArgumentException("No puede quedar vacío.");
                }
                if (soloLetras) {
                    if (texto.length() > 30) {
                        throw new IllegalArgumentException("Máximo 30 caracteres.");
                    }
                    for (char c : texto.toCharArray()) {
                        if (!Character.isLetter(c) && c != ' ') {
                            throw new IllegalArgumentException("Admite solo letras.");
                        }
                    }
                }
                return texto;
            } catch (IllegalArgumentException e) {
                System.out.println("Dato inválido. " + e.getMessage());
            }
        }
    }

    private String pedirMail() {
        while (true) {
            String mail = leerLinea("Email: ");
            if (mail.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+"))
            // [^@\\s]+  -> uno o más caracteres que no sean @ ni espacio (nombre de usuario)
            // @         -> un arroba
            // [^@\\s]+  -> uno o más caracteres que no sean @ ni espacio (dominio, ej: mail)
            // \\.       -> un punto literal (las dos barras porque en Java la \ se escribe doble)
            // [^@\\s]+  -> uno o más caracteres que no sean @ ni espacio (terminación, ej: com)
            {
                return mail;
            }
            System.out.println("Dato inválido. Ingrese un email válido (ej: nombre@dominio.com).");
        }
    }

    private String pedirTelefono() {
        while (true) {
            String tel = leerLinea("Teléfono: ");
            if (tel.matches("[0-9+\\- ]{6,20}")) {
                return tel;
            }
            System.out.println("Dato inválido. Use solo números (6 a 20 caracteres).");
        }
    }

    private Venta tipoVenta() {
        while (true) {
            int opcion = leerEntero("Medio de venta (1.Teléfono 2.Web 3.Redes Sociales): ");
            switch (opcion) {
                case 1:
                    return Venta.TELEFONO;
                case 2:
                    return Venta.WEB;
                case 3:
                    return Venta.REDES_SOCIALES;
                default:
                    System.out.println("Opción inválida. Debe ser 1, 2 o 3.");
            }
        }
    }

    private Pastas pedirPasta() {
        TipoPasta tipo = pedirTipoPasta();
        while (true) {
            try {
                double cantidad = leerCantidad(tipo);
                return Pastas.crear(tipo, cantidad); // valida según la unidad
            } catch (PedidoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private TipoPasta pedirTipoPasta() {
        TipoPasta[] tipos = TipoPasta.values();
        while (true) {
            int opcion = leerEntero("Tipo de pasta (1.Fideos al huevo 2.Ravioles 3.Ñoquis 4.Agnolotis): ");
            if (opcion >= 1 && opcion <= tipos.length) {
                return tipos[opcion - 1];
            }
            System.out.println("Opción inválida.");
        }
    }

    //Lee la cantidad: entero para cajas, decimal (2.5 o 2,5) para kg y no permite letras
    private double leerCantidad(TipoPasta tipo) {
        boolean esKg = tipo == TipoPasta.FIDEOS || tipo == TipoPasta.NOQUIS;
        String texto = leerLinea(esKg ? "Cantidad de " + tipo + " en kg (ej: 2.5): "
                                      : "Cantidad de cajas de " + tipo + ": ");
        String patron = esKg ? "\\d+([.,]\\d+)?" : "\\d+";
        if (!texto.matches(patron)) {
            throw new PedidoInvalidoException(esKg
                    ? "Ingrese un número (sin letras), por ejemplo 2.5"
                    : "Ingrese un número entero de cajas, sin letras ni decimales");
        }
        return Double.parseDouble(texto.replace(',', '.'));
    }

    public boolean confirmar(String mensaje) {
        while (true) {
            String r = leerLinea(mensaje);
            if (r.equalsIgnoreCase("S")) {
                return true;
            }
            if (r.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Debe ingresar S (sí) o N (no).");
        }
    }
}
