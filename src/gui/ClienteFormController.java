package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import model.services.ClienteService;

public class ClienteFormController implements Initializable {

	private Cliente cliente;
	private ClienteService servico;
	final ToggleGroup grupoRadioButton = new ToggleGroup();
	private List<ListenerDadosAlterados> listenersDadosAlterados = new ArrayList<>();

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
	private Label labelErroDdd;
	@FXML
	private Label labelErroNumeroTel;

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

		Cliente novoCliente = new Cliente();

		//novoCliente.setId(Utilitarios.tentaConverterParaInt(txtId.getText()));
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

		RadioButton radioButtonSelecionado = (RadioButton) grupoRadioButton.getSelectedToggle();
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
}
