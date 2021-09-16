package model.domain.enums;

public enum SiglaEstado {
	
	AC ("AC"), //Acre
	AL ("AL"), //Alagoas
	AM ("AM"), //Amazonas
	AP ("AP"), //Amapá
	BA ("BA"), //Bahia
	CE ("CE"), //Ceará
	DF ("DF"), //Distrito Federal
	ES ("ES"), //Espírito Santo
	GO ("GO"), //Goiás
	MA ("MA"), //Maranhão
	MT ("MT"), //Mato Grosso
	MS ("MS"), //Mato Grosso do Sul
	MG ("MG"), //Minas Gerais
	PA ("PA"), //Pará
	PB ("PB"), //Paraíba
	PE ("PE"), //Pernambuco
	PR ("PR"), //Paraná
	PI ("PI"), //Piauí
	RR ("RR"), //Roraima
	RO ("RO"), //Rondônia
	RJ ("RJ"), //Rio de Janeiro
	RN ("RN"), //Rio Grande do Norte
	RS ("RS"), //Rio Grande do Sul
	SC ("SC"), //Santa Catarina
	SP ("SP"), //São Paulo
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
