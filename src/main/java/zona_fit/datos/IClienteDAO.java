package zona_fit.datos;
import zona_fit.dominio.Cliente;

import java.util.List;
public interface IClienteDAO {

    List<Cliente> listarClientes();
    boolean buscarCliente(Cliente cli);
    boolean agregarCliente(Cliente cli);
    boolean modificarCliente(Cliente cli);
    boolean eliminarCliente(Cliente cli);

}
