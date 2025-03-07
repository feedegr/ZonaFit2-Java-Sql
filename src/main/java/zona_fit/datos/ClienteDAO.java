package zona_fit.datos;

import zona_fit.conexion.Conexion;
import zona_fit.dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static zona_fit.conexion.Conexion.getConexion;

public class ClienteDAO implements IClienteDAO{

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        var sql = "SELECT * FROM cliente ORDER BY id";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        }catch (Exception e){
            System.out.println("Error en listar Clientes" + e.getMessage());
        }

        finally {

            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexion" + e.getMessage());
            }
        }

        return clientes;
    }

    @Override
    public boolean buscarCliente(Cliente cli) {
        PreparedStatement ps;
        ResultSet rs;
        boolean resul = false;
        var con = getConexion();
        String sql = "SELECT * FROM cliente WHERE id = ?";

        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, cli.getId());
            rs = ps.executeQuery();
            if (rs.next()){
                cli.setNombre(rs.getString("nombre"));
                cli.setApellido(rs.getString("apellido"));
                cli.setMembresia(rs.getInt("membresia"));
                resul = true;
            }
        }catch (Exception e){
            System.out.println("Error buscar cliente " + e.getMessage());
        }finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("error cerrar conexion" + e.getMessage());
            }
        }
        return resul;
    }

    @Override
    public boolean agregarCliente(Cliente cli) {
        boolean resul = false;
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "INSERT INTO cliente(nombre,apellido,membresia) "
                    + " VALUES(?, ?, ?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, cli.getNombre());
            ps.setString(2, cli.getApellido());
            ps.setInt(3, cli.getMembresia());
            ps.execute();
            resul = true;
        }catch (Exception e){
            System.out.println("Error al agregar cliente: " + e.getMessage());
        }finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("error cerrar conexion" + e.getMessage());
            }
        }
        return resul;
    }

    @Override
    public boolean modificarCliente(Cliente cli) {
        boolean resul = false;
        PreparedStatement ps;
        Connection con = getConexion();
        var sql = "UPDATE cliente SET nombre=?, apellido=?, membresia=? "
                + " WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cli.getNombre());
            ps.setString(2, cli.getApellido());
            ps.setInt(3, cli.getMembresia());
            ps.setInt(4, cli.getId());
            ps.execute();
            resul = true;

        }catch (Exception e){
            System.out.println("Error al modificar cliente " + e.getMessage());
        }
        finally {

            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexcion: " + e.getMessage());
            }
        }
        return resul;
    }

    @Override
    public boolean eliminarCliente(Cliente cli) {
        boolean resul = false;
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "DELETE FROM cliente WHERE id = ?";

        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, cli.getId());
            ps.execute();
            resul = true;
        }catch (Exception e){
            System.out.println("Error al eliminar cliente " + cli);
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
        return resul;
    }

//    public static void main(String[] args) {
//        IClienteDAO clienteDAO = new ClienteDAO();
//
//
//       var nuevocliente = new Cliente("daniel", "ortiz", 300);
//        var agregado = clienteDAO.agregarCliente(nuevocliente);
//        if (agregado) {
//            System.out.println("Cliente agregado" + nuevocliente);
//        } else {
//            System.out.println("no se agrego el cliente: " + nuevocliente);
//        }
//
//        //modificar cliente
//        var modificarCliente = new Cliente(3, "Fede","Gr",300);
//        var modificado = clienteDAO.modificarCliente(modificarCliente);
//        if (modificado){
//            System.out.println("Cliente modificado " + modificado);
//        }else{
//            System.out.println("error no se modifico");
//        }
//
//        //Eliminar cliente
//        var cliElim = new Cliente(3);
//        var eliminado = clienteDAO.eliminarCliente(cliElim);
//        if (eliminado){
//            System.out.println("cliente eliminado " + cliElim);
//        }else{
//            System.out.println("No se elimino");
//        }
//
//        System.out.println("*** Listar Clientes ***");
//        var clientes = clienteDAO.listarClientes();
//        clientes.forEach(System.out::println);
//    }
}
