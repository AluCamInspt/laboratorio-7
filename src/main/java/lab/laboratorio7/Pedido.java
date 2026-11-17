package lab.laboratorio7;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int numero;
    private final Cliente cliente;
    private final Venta canal;
    private final List<Pastas> pastas = new ArrayList<>();

    public Pedido(int numero, Cliente cliente, Venta canal) {
        this.numero = numero;
        this.cliente = cliente;
        this.canal = canal;
    }

    public void agregarPasta(Pastas pasta) {
        pastas.add(pasta);
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Venta getCanal() {
        return canal;
    }

    public List<Pastas> getPastas() {
        return pastas;
    }

    public double calcularTotal() {
        double total = 0;
        for (Pastas p : pastas) {
            total += p.subtotal();
        }
        return total;
    }

    public void mostrarResumen() {
        System.out.println("--------------------------------");
        System.out.println("Pedido: " + numero);
        System.out.println("Cliente: " + cliente.getNombreCompleto());
        System.out.println("Mail: " + cliente.getMail() + " | Tel: " + cliente.getTelefono());
        System.out.println("Dirección: " + cliente.getDireccion());
        System.out.println("Canal: " + canal);
        for (Pastas p : pastas) {
            System.out.println("  " + p.descripcion());
        }
        System.out.println("TOTAL A PAGAR: $" + formatear(calcularTotal()));
        System.out.println("--------------------------------");
    }

    public static String formatear(double valor) {
        // Math.rint redondea al entero más cercano; si es igual al valor, no hay parte decimal
        if (valor == Math.rint(valor)) {
            return String.valueOf((long) valor);
        }
        return String.format(Locale.US, "%.2f", valor);
        // Locale.US fuerza el punto como separador decimal (y no la coma) en cualquier PC
    }

    @Override
    public String toString() {
        return "Pedido " + numero + " - " + cliente + " - " + canal + " - $" + formatear(calcularTotal());
    }
}
