package lab.laboratorio7;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Run {

    public static void main(String[] args) {
        Consola consola = new Consola();
        GestorPedidos gestor = new GestorPedidos();
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = consola.leerEntero("Opción: ");
            try {
                switch (opcion) {
                    case 1:
                        agregarPedido(consola, gestor);
                        break;
                    case 2:
                        buscarPedido(consola, gestor);
                        break;
                    case 3:
                        listarPedidos(gestor);
                        break;
                    case 4:
                        eliminarPedido(consola, gestor);
                        break;
                    case 5:
                        gestor.exportarTxt();
                        System.out.println("Pedidos exportados a " + GestorPedidos.ARCHIVO_TXT);
                        break;
                    case 6:
                        guardarPedidos(consola, gestor);
                        break;
                    case 7:
                        recuperarPedidos(consola, gestor);
                        break;
                    case 8:
                        salir = true;
                        System.out.println("Saliendo del sistema..");
                        break;
                    default:
                        System.out.println("Opción inválida. Elija un número del 1 al 8.");
                }
            } catch (FileNotFoundException e) { // debe ir antes que IOException (es su subclase)
                System.out.println("Error: el archivo no existe o no se puede abrir (" + e.getMessage() + ")");
            } catch (IOException e) {
                System.out.println("Error de lectura/escritura: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Error: no se encontró una clase del archivo guardado (" + e.getMessage() + ")");
            } catch (PedidoInvalidoException e) {
                System.out.println("Datos inválidos: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e);
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== MAT-NOODLES SRL =====");
        System.out.println("1. Agregar pedido");
        System.out.println("2. Buscar pedido");
        System.out.println("3. Listar pedidos");
        System.out.println("4. Eliminar pedido");
        System.out.println("5. Exportar pedidos a TXT");
        System.out.println("6. Guardar pedidos (Serialización)");
        System.out.println("7. Recuperar pedidos (Deserialización)");
        System.out.println("8. Salir");
    }

    private static void agregarPedido(Consola consola, GestorPedidos gestor) {
        Pedido pedido = consola.pedirPedido(gestor.proximoNumero());
        gestor.agregar(pedido);
        System.out.println("Pedido registrado con el número " + pedido.getNumero());
        pedido.mostrarResumen();
    }

    private static void buscarPedido(Consola consola, GestorPedidos gestor) {
        int numero = consola.leerEntero("Número de pedido a buscar: ");
        Pedido p = gestor.buscar(numero);
        if (p == null) {
            System.out.println("No existe el pedido " + numero);
        } else {
            p.mostrarResumen();
        }
    }

    private static void listarPedidos(GestorPedidos gestor) {
        if (gestor.estaVacio()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }
        for (Pedido p : gestor.listar()) {
            p.mostrarResumen();
        }
    }

    private static void eliminarPedido(Consola consola, GestorPedidos gestor) {
        int numero = consola.leerEntero("Número de pedido a eliminar: ");
        if (gestor.eliminar(numero)) {
            System.out.println("Pedido " + numero + " eliminado.");
        } else {
            System.out.println("No existe el pedido " + numero);
        }
    }
        private static void guardarPedidos(Consola consola, GestorPedidos gestor) throws IOException {
        // Avisa antes de pisar un archivo existente
        if (gestor.existeArchivoDat() && !consola.confirmar("Ya existe " + GestorPedidos.ARCHIVO_DAT
                + ": se SOBRESCRIBIRÁ y se perderán los pedidos que contenga. ¿Continuar? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }
        gestor.guardar();
        System.out.println("Pedidos guardados en " + GestorPedidos.ARCHIVO_DAT);
    }

    private static void recuperarPedidos(Consola consola, GestorPedidos gestor)
            throws IOException, ClassNotFoundException {
        // Avisa antes de reemplazar los pedidos que hay en memoria
        if (!gestor.estaVacio() && !consola.confirmar("Hay " + gestor.listar().size()
                + " pedido/s cargado/s que se PERDERÁN al recuperar el archivo (los no guardados). ¿Continuar? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }
        gestor.recuperar();
        System.out.println("Pedidos recuperados: " + gestor.listar().size());
    }
}
