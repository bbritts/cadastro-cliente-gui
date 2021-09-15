package gui;


import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Restricoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.domain.enums.SiglaEstado;

public class ClienteFormController implements Initializable{
	
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
	private ComboBox<SiglaEstado> cbEstado;
	
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
	
	//Métodos a executar ao clicar nos botões
	@FXML
	public void onBtSalvarAction() {
		System.out.println("Salvei!");
	}
	@FXML
	public void onBtCancelarAction() {
		System.out.println("Cancelei!");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {		
	}
	
	public void inicializaCampos() {
		
		//Métodos encontrados na classe Restricoes no /gui/util 
		Restricoes.textFieldInteiro(txtId);
		Restricoes.textFieldApenasLetras(txtNome);
		Restricoes.textFieldTamanhoMaximo(txtNome, 64);
		Restricoes.textFieldApenasLetras(txtSobrenome);
		Restricoes.textFieldTamanhoMaximo(txtSobrenome, 128);		
	}
}
