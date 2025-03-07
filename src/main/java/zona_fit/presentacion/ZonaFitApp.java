package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.Scanner;

public class ZonaFitApp {
    public static void main(String[] args) {
        zonaFitApp();
    }
    private static void zonaFitApp(){
        boolean salir = false;
        var consola = new Scanner(System.in);

        //crea objeto clase clienteDao
        IClienteDAO clienteDao = new ClienteDAO();
        while (!salir) {
            try {
                var opcion = mostrarMenu(consola);
                salir = ejecutarOpciones(consola, opcion, clienteDao);
            } catch (Exception e) {
                System.out.println("Error al ejecutar opcion " + e.getMessage());
            }
        }
        System.out.println();
    }

    private static int mostrarMenu(Scanner consola){
        System.out.println("""
                *** Zona Fit ***
                1. Listar Clientes
                2. Buscar Cliente
                3. Agregar Cliente
                4. Modificar cliente
                5. Eliminar Cliente
                6. Salir
                Elije una opcion:\s""");
        return Integer.parseInt(consola.nextLine());
    }

    private static boolean ejecutarOpciones(Scanner consola, int opcion, IClienteDAO clienteDAO){

        var salir = false;
        switch (opcion){
            case 1 -> {//listar
                System.out.println("--- Listado de cliente ---");
                var clientes = clienteDAO.listarClientes();
                clientes.forEach(System.out::println);
            }
            case 2 -> {//buscar
                System.out.println("Ingrese Id del cliente");
                var idCliente = Integer.parseInt(consola.nextLine());
                Cliente cliente = new Cliente(idCliente);
                var encontrado = clienteDAO.buscarCliente(cliente);
                if (encontrado) {
                    System.out.println("cliente encontrado " + cliente);
                } else {
                    System.out.println("cliente no encontrado");
                }
            }
            case 3 -> {
                System.out.println("--- Agregar cliente ---");
                System.out.println("Ingrese Nombre");
                var nombre = consola.nextLine();
                System.out.println("Ingrese Apellido");
                var apellido = consola.nextLine();
                System.out.println("Ingrese membresia");
                var membresia = Integer.parseInt(consola.nextLine());
                //crear objeto
                Cliente clienteFinal = new Cliente(nombre,apellido,membresia);
                var agregado = clienteDAO.agregarCliente(clienteFinal);
                if (agregado){
                    System.out.println("Se agrego cliente " + clienteFinal);
                }else {
                    System.out.println("Error al agregar cliente");
                }
            }
            case 4 -> {//Modificar cliente
                System.out.println("--- Modificar cliente ---");
                System.out.print("Id Cliente");
                var idCliente = Integer.parseInt(consola.nextLine());
                System.out.print("Nombre");
                var nombre = consola.nextLine();
                System.out.print("Apellido");
                var apellido = consola.nextLine();
                System.out.print("membresia");
                var membresia = Integer.parseInt(consola.nextLine());
                var cliente = new Cliente(idCliente, nombre, apellido, membresia);
                var modificado = clienteDAO.modificarCliente(cliente);
                if (modificado) {
                    System.out.println("Se modifico el cliente a " + cliente);
                } else {
                    System.out.println("Error al modificar");
                }
            }
                case 5 -> {// eliminar
                    System.out.println("Eliminar cliente");
                    System.out.println("Ingrese id");
                    var idCli = Integer.parseInt(consola.nextLine());
                    Cliente eliminarCliente = new Cliente(idCli);
                    var eliminado = clienteDAO.eliminarCliente(eliminarCliente);
                    if (eliminado){
                        System.out.println("Se elimino correctamente " + eliminarCliente);
                    }else {
                        System.out.println("Error no se elimino " + eliminarCliente);
                    }
                }
            case 6 -> {
                System.out.println("Adios");
                salir = true;
            }

            default -> throw new IllegalStateException("Unexpected value: " + opcion);
        }

        return salir;
    }
}

