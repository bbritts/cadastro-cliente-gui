package model.domain.enums;

public enum SiglaEstado {
	
	AC ("AC"), //Acre
	AL ("AL"), //Alagoas
	AM ("AM"), //Amazonas
	AP ("AP"), //Amap�
	BA ("BA"), //Bahia
	CE ("CE"), //Cear�
	DF ("DF"), //Distrito Federal
	ES ("ES"), //Esp�rito Santo
	GO ("GO"), //Goi�s
	MA ("MA"), //Maranh�o
	MT ("MT"), //Mato Grosso
	MS ("MS"), //Mato Grosso do Sul
	MG ("MG"), //Minas Gerais
	PA ("PA"), //Par�
	PB ("PB"), //Para�ba
	PE ("PE"), //Pernambuco
	PR ("PR"), //Paran�
	PI ("PI"), //Piau�
	RR ("RR"), //Roraima
	RO ("RO"), //Rond�nia
	RJ ("RJ"), //Rio de Janeiro
	RN ("RN"), //Rio Grande do Norte
	RS ("RS"), //Rio Grande do Sul
	SC ("SC"), //Santa Catarina
	SP ("SP"), //S�o Paulo
	SE ("SE"), //Sergipe
	TO ("TO"); //Tocantins
	
	private String string;
	
	SiglaEstado(String string) {
		this.string = string;
	}
	
	public String toString() {
		return string;
	}
}
