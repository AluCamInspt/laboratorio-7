package lab.laboratorio7;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;

public class GestorPedidos {

    public static final String ARCHIVO_TXT = "pedidos.txt";
    public static final String ARCHIVO_DAT = "pedidos.dat";
    private static final int PRIMER_NUMERO = 1001;

    private ArrayList<Pedido> pedidos = new ArrayList<>();
    private int siguienteNumero = PRIMER_NUMERO;
    //Número que se le asignará al próximo pedido
    public int proximoNumero() {
        return siguienteNumero;
    }

    public void agregar(Pedido pedido) {
        if (buscar(pedido.getNumero()) != null) {
            throw new IllegalArgumentException("Ya existe el pedido " + pedido.getNumero());
        }
        pedidos.add(pedido);
        siguienteNumero = Math.max(siguienteNumero, pedido.getNumero() + 1);
    }

    //Busqueda
    public Pedido buscar(int numero) {
        for (Pedido p : pedidos) {
            if (p.getNumero() == numero) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Pedido> listar() {
        return pedidos;
    }

    public boolean estaVacio() {
        return pedidos.isEmpty();
    }

    public boolean eliminar(int numero) {
        Iterator<Pedido> it = pedidos.iterator();
        while (it.hasNext()) {
            if (it.next().getNumero() == numero) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    // ---------- Archivo de texto ----------
    public void exportarTxt() throws IOException {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(ARCHIVO_TXT)))) {
            boolean primero = true;
            for (Pedido p : pedidos) {
                if (!primero) {
                    pw.println();
                }
                primero = false;
                pw.println("Pedido: " + p.getNumero());
                pw.println("Cliente: " + p.getCliente().getNombreCompleto());
                pw.println("Canal: " + p.getCanal());
                pw.println("Importe: " + Pedido.formatear(p.calcularTotal()));
            }
            if (pw.checkError()) {
                throw new IOException("Error al escribir " + ARCHIVO_TXT);
            }
        }
    }

    // ---------- Serialización ----------
    // Indica si ya existe pedidos.dat (guardar lo sobreescribe)
    public boolean existeArchivoDat() {
        return new File(ARCHIVO_DAT).exists();
    }
    public void guardar() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DAT))) {
            out.writeObject(pedidos);
        }
    }

    /**
     * Reconstruye la colección desde pedidos.dat. Si algo falla, la colección
     * actual queda intacta.
     *
     * @throws FileNotFoundException    si el archivo no existe
     * @throws IOException              error de lectura / archivo dañado
     * @throws ClassNotFoundException   si falta una clase del objeto guardado
     * @throws PedidoInvalidoException  si el contenido no es una lista de pedidos válida
     */
    public void recuperar() throws IOException, ClassNotFoundException {
        Object leido;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARCHIVO_DAT))) {
            leido = in.readObject();
        }
        if (!(leido instanceof ArrayList<?>)) {
            throw new PedidoInvalidoException("El archivo no contiene una lista de pedidos");
        }
        ArrayList<Pedido> recuperados = new ArrayList<>();
        int maximo = PRIMER_NUMERO - 1;
        for (Object o : (ArrayList<?>) leido) {
            if (!(o instanceof Pedido)) {
                throw new PedidoInvalidoException("El archivo contiene datos que no son pedidos");
            }
            Pedido p = (Pedido) o;
            recuperados.add(p);
            maximo = Math.max(maximo, p.getNumero());
        }
        pedidos = recuperados;
        siguienteNumero = maximo + 1;
    }
}
