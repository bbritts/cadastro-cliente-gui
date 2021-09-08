package model.domain;

import java.io.Serializable;

import model.domain.enums.TipoTelefone;

public class Telefone implements Serializable{	

	private static final long serialVersionUID = 1L;
	
	private String ddd;
	private String telefone;
	private TipoTelefone tipo;
	
	//Construtor Padrão
	public Telefone() {		
	}
	
	//Construtor
	public Telefone(String ddd, String telefone, TipoTelefone tipo) {
		setDdd(ddd);
		setTelefone(telefone);
		setTipo(tipo);
	}

	//Getters e Setters

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public TipoTelefone getTipo() {
		return tipo;
	}

	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo;
	}

	
	@Override
	public String toString() {
		return "[ddd=" + ddd + ", telefone=" + telefone 
					   + ", tipo=" + tipo + "]";
	}
	
	

}
