package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ExcecaoValidacao extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	
	//Cole��o de pares chave-valor para guardar as mensagens de erro das caixas de texto
	private Map<String, String> erros = new HashMap<>();	
	
	public ExcecaoValidacao(String mensagem) {
		super(mensagem);
	}

	
	//M�todo Getter
	public Map<String, String> getErros() {
		return erros;
	}
	
	//M�todo para adicionar erros no Map
	public void adicionaErros(String campo, String mensagemDeErro) {
		erros.put(campo, mensagemDeErro);
	}
}
