package model.domain;

import java.io.Serializable;
import model.domain.enums.SiglaEstado;
import model.domain.enums.TipoTelefone;

public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String sobrenome;
	private String cpf;
	private String email;
	private Endereco endereco;
	private Telefone telefone;
	
	//Construtor Padrão
	public Cliente() {		
	}
	
	public Cliente(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	//Construtor
	public Cliente(Integer id, String nome, String sobrenome, String cpf, 
			String email, String rua, String numero, String bairro, 
			String complemento, String cidade, SiglaEstado sigla_estado,
			String ddd, String telefone, TipoTelefone tipo) {		
		
		setNome(nome);
		setSobrenome(sobrenome);
		setCpf(cpf);
		setEmail(email);
		setEndereco(new Endereco(rua, numero, bairro, 
								complemento, cidade, sigla_estado));
		setTelefone(new Telefone(ddd, telefone, tipo));

	}
	
	//Getters e Setters

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", sobrenome=" + sobrenome + ", cpf=" + cpf + ", email=" + email
				+ ", endereco=" + endereco + ", telefone=" + telefone + "]";
	}

}
