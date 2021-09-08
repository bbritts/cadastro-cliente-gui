package model.services;

import java.util.ArrayList;
import java.util.List;

import model.domain.Cliente;

public class ClienteService {

	public List<Cliente> buscarTodos() {
		
		List<Cliente> lista = new ArrayList<>();
		lista.add(new Cliente(1, "Brunno"));
		lista.add(new Cliente(2, "Giovanna"));
		lista.add(new Cliente(3, "Rosangela"));
		
		return lista;
	}
}
