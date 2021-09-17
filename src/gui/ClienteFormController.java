package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import database.DBException;
import gui.listeners.ListenerDadosAlterados;
import gui.util.Alertas;
import gui.util.Restricoes;
import gui.util.Utilitarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.domain.Cliente;
import model.domain.enums.SiglaEstado;
import model.domain.enums.TipoTelefone;
import model.exceptions.ExcecaoValidacao;
import model.services.ClienteService;

public class ClienteFormController implements Initializable {

	private Cliente cliente;
	private ClienteService servico;
	final ToggleGroup grupoRadioButton = new ToggleGroup();
	
	//Cria lista de listeners de eventos
	private List<ListenerDadosAlterados> listenersDadosAlterados = new ArrayList<>();
	
	//Declaração de um Map com nome do campo como chave e seu respectivo label como valor	
	public Map<String, Label> associaLabelErro = new HashMap<>();	

	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtSobrenome;
	@FXML
	private TextField txtCpf;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtRua;
	@FXML
	private TextField txtNumero;
	@FXML
	private TextField txtBairro;
	@FXML
	private TextField txtComplemento;
	@FXML
	private TextField txtCidade;
	@FXML
	private TextField txtDdd;
	@FXML
	private TextField txtNumeroTel;

	@FXML
	private Label labelErroNome;
	@FXML
	private Label labelErroSobrenome;
	@FXML
	private Label labelErroCpf;
	@FXML
	private Label labelErroEmail;
	@FXML
	private Label labelErroRua;
	@FXML
	private Label labelErroNumero;
	@FXML
	private Label labelErroBairro;
	@FXML
	private Label labelErroComplemento;
	@FXML
	private Label labelErroCidade;
	@FXML
	private Label labelErroEstado;
	@FXML
	private Label labelErroDdd;
	@FXML
	private Label labelErroNumeroTel;
	@FXML
	private Label labelErroTipoTel;

	@FXML
	private ComboBox<SiglaEstado> cbxEstado;

	@FXML
	private RadioButton rbResidencial;
	@FXML
	private RadioButton rbCelular;
	@FXML
	private RadioButton rbComercial;

	@FXML
	private Button btSalvar;
	@FXML
	private Button btCancelar;

	// Métodos setter para injetar a dependência

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setClienteService(ClienteService servico) {
		this.servico = servico;
	}
	
	//Método responsável por inscrever um objeto na lista de listeners de eventos de dados alterados
	public void adicionaListenerDadosAlterados(ListenerDadosAlterados listener) {
		listenersDadosAlterados.add(listener);
	}

	// Métodos a executar ao clicar nos botões
	@FXML
	public void onBtSalvarAction(ActionEvent evento) {
		if (cliente == null) {
			throw new IllegalStateException("Cliente estava nulo");
		}
		
		if (servico == null) {
			throw new IllegalStateException("Serviço estava nulo");
		}
		
		try {
			
			//Instancia um objeto cliente com os dados capturados do formulário
			cliente = capturaDadosForm();
			
			servico.cadastrarOuAtualizar(cliente);
			
			//Notifica os listeners que um evento de dados alterados ocorreu
			notificaListenersDadosAlterados();
			
			//Método para fechar a janela após os dados serem salvos
			Utilitarios.stageAtual(evento).close();
		}
		catch (ExcecaoValidacao e) {
			mostraMsgErroValidacao(e.getErros());
		}
		catch (DBException e) {
			Alertas.mostraAlerta("Erro ao salvar o objeto", null, e.getMessage(), AlertType.ERROR);
		}		
	}

	@FXML
	public void onBtCancelarAction(ActionEvent evento) {
		//Método para fechar a janela após os dados serem salvos
		Utilitarios.stageAtual(evento).close();
	}

	private Cliente capturaDadosForm() {
		
		ExcecaoValidacao excecao = new ExcecaoValidacao("Erro ao validar os dados");

		Cliente novoCliente = new Cliente();
		
		RadioButton radioButtonSelecionado = (RadioButton) grupoRadioButton.getSelectedToggle();
		
		validaDados(excecao, radioButtonSelecionado);		
	
		novoCliente.setId(Utilitarios.tentaConverterParaInt(txtId.getText()));
		novoCliente.setNome(txtNome.getText());
		novoCliente.setSobrenome(txtSobrenome.getText());
		novoCliente.setCpf(txtCpf.getText());		
		novoCliente.setEmail(txtEmail.getText());
		novoCliente.getEndereco().setRua(txtRua.getText());
		novoCliente.getEndereco().setNumero(txtNumero.getText());
		novoCliente.getEndereco().setBairro(txtBairro.getText());
		novoCliente.getEndereco().setComplemento(txtComplemento.getText());
		novoCliente.getEndereco().setCidade(txtCidade.getText());
		novoCliente.getEndereco().setSigla_estado(cbxEstado.getValue());
		novoCliente.getTelefone().setDdd(txtDdd.getText());
		novoCliente.getTelefone().setTelefone(txtNumeroTel.getText());				
		novoCliente.getTelefone().setTipo(TipoTelefone.valueOf(radioButtonSelecionado.getText().toUpperCase()));		
		
		return novoCliente;
	}
	
	private void notificaListenersDadosAlterados() {
		
		for(ListenerDadosAlterados listener : listenersDadosAlterados) {
			listener.qdoDadoAlterado();
		}		
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// Insere as restrições de caracteres nas caixas de texto
		inicializaTextFields();
		// Cria grupo dos botões para tipo de telefone
		agrupaRadioButtons();
		// Popula o combobox com as siglas dos estados
		preencheComboBox();
	}

	public void inicializaTextFields() {

		// Métodos encontrados na classe Restricoes no /gui/util
		Restricoes.textFieldInteiro(txtId);
		Restricoes.textFieldApenasLetras(txtNome);
		Restricoes.textFieldTamanhoMaximo(txtNome, 32);
		Restricoes.textFieldApenasLetras(txtSobrenome);
		Restricoes.textFieldTamanhoMaximo(txtSobrenome, 64);
		Restricoes.textFieldInteiro(txtCpf);
		Restricoes.textFieldTamanhoMaximo(txtCpf, 11);
		Restricoes.textFieldTamanhoMaximo(txtEmail, 64);
		Restricoes.textFieldTamanhoMaximo(txtRua, 64);
		Restricoes.textFieldTamanhoMaximo(txtNumero, 8);
		Restricoes.textFieldTamanhoMaximo(txtBairro, 64);
		Restricoes.textFieldTamanhoMaximo(txtComplemento, 128);
		Restricoes.textFieldApenasLetras(txtCidade);
		Restricoes.textFieldTamanhoMaximo(txtCidade, 64);
		Restricoes.textFieldInteiro(txtDdd);
		Restricoes.textFieldTamanhoMaximo(txtDdd, 2);
		Restricoes.textFieldInteiro(txtNumeroTel);
		Restricoes.textFieldTamanhoMaximo(txtNumeroTel, 12);
	}

	private void preencheComboBox() {
		cbxEstado.getItems().addAll(SiglaEstado.values());
	}

	private void agrupaRadioButtons() {
		rbResidencial.setToggleGroup(grupoRadioButton);
		rbCelular.setToggleGroup(grupoRadioButton);
		rbComercial.setToggleGroup(grupoRadioButton);
	}

	public void atualizaDadosForm() {

		// Trata o erro do cliente estar vazio
		if (cliente == null) {
			throw new IllegalArgumentException("Cliente estava vazio");
		}

		// Mostra os valores do cliente instanciado nas caixas de texto
		txtId.setText(String.valueOf(cliente.getId()));
		txtNome.setText(cliente.getNome());
		txtSobrenome.setText(cliente.getSobrenome());
		txtCpf.setText(cliente.getCpf());
		txtEmail.setText(cliente.getEmail());
		txtRua.setText(cliente.getEndereco().getRua());
		txtNumero.setText(cliente.getEndereco().getNumero());
		txtBairro.setText(cliente.getEndereco().getBairro());
		txtComplemento.setText(cliente.getEndereco().getComplemento());
		txtCidade.setText(cliente.getEndereco().getCidade());
		txtDdd.setText(cliente.getTelefone().getDdd());
		txtNumeroTel.setText(cliente.getTelefone().getTelefone());

		// Verifica qual dos radio buttons deve ser selecionado
		selecionaRadioButton();

		selecionaItemComboBox();
	}

	// TODO - Não testado
	private void selecionaItemComboBox() {

		for (SiglaEstado sigla : SiglaEstado.values()) {

			if (cliente.getEndereco().getSigla_estado() == sigla) {
				cbxEstado.setValue(sigla);
			}
		}
	}

	// TODO - Não testado
	private void selecionaRadioButton() {
		if (cliente.getTelefone().getTipo() != null) {

			if (cliente.getTelefone().getTipo() == TipoTelefone.RESIDENCIAL) {
				rbResidencial.setSelected(true);
			} else if (cliente.getTelefone().getTipo() == TipoTelefone.CELULAR) {
				rbCelular.setSelected(true);
			} else {
				rbComercial.setSelected(true);
			}
		}
	}
	
	private void validaDados(ExcecaoValidacao excecao, RadioButton rb) {
		
		String erroCampoVazio = "Esse campo não pode estar vazio!";	
		
		if(txtNome.getText() == null || txtNome.getText().trim() == "") {
			excecao.adicionaErros("nome", erroCampoVazio);
		}
		
		if(txtSobrenome.getText() == null || txtSobrenome.getText().trim() == "") {
			excecao.adicionaErros("sobrenome", erroCampoVazio);
		}
		
		if(txtCpf.getText() == null || txtCpf.getText().trim() == "") {
			excecao.adicionaErros("cpf", erroCampoVazio);
		}
		
		if(txtEmail.getText() == null || txtEmail.getText().trim() == "") {
			excecao.adicionaErros("email", erroCampoVazio);
		}
		
		if(txtRua.getText() == null || txtRua.getText().trim() == "") {
			excecao.adicionaErros("rua", erroCampoVazio);
		}	
		
		if(txtNumero.getText() == null || txtNumero.getText().trim() == "") {
			excecao.adicionaErros("numero", erroCampoVazio);
		}
		
		if(txtBairro.getText() == null || txtBairro.getText().trim() == "") {
			excecao.adicionaErros("bairro", erroCampoVazio);
		}
		
		if(txtCidade.getText() == null || txtCidade.getText().trim() == "") {
			excecao.adicionaErros("cidade", erroCampoVazio);			
		}
		
		if(cbxEstado.getValue() == null) {
			excecao.adicionaErros("estado", "Você deve selecionar um estado!");
		}
		
		if(txtDdd.getText() == null || txtDdd.getText().trim() == "") {
			excecao.adicionaErros("ddd", erroCampoVazio);
		}
		
		if(txtNumeroTel.getText() == null || txtNumeroTel.getText().trim() == "") {
			excecao.adicionaErros("numtel", erroCampoVazio);
		}	
		
		if(rb == null) {
			excecao.adicionaErros("tipotel", "Você deve selecionar uma categoria!");
		}
		
		if(excecao.getErros().size() > 0) {
			throw excecao;
		}
	}
	
	private void associaLabelCampo() {
		associaLabelErro.put("nome", labelErroNome);
		associaLabelErro.put("sobrenome", labelErroSobrenome);
		associaLabelErro.put("cpf", labelErroCpf);
		associaLabelErro.put("email", labelErroEmail);
		associaLabelErro.put("rua", labelErroRua);
		associaLabelErro.put("numero", labelErroNumero);
		associaLabelErro.put("bairro", labelErroBairro);
		associaLabelErro.put("cidade", labelErroCidade);
		associaLabelErro.put("estado", labelErroEstado);
		associaLabelErro.put("ddd", labelErroDdd);
		associaLabelErro.put("numtel", labelErroNumeroTel);
		associaLabelErro.put("tipotel", labelErroTipoTel);		
	}
	
	private void mostraMsgErroValidacao(Map<String, String> erros) {
		
		associaLabelCampo();
		
		Set<String> camposErros = erros.keySet();
		Set<String> nomesCamposLabels = associaLabelErro.keySet();		
		
		for(String campoChave : nomesCamposLabels) {
			if(camposErros.contains(campoChave)) {
				associaLabelErro.get(campoChave).setText(erros.get(campoChave));
			}
		}		
	}
}
