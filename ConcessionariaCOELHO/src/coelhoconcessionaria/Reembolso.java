package coelhoconcessionaria;

import java.util.Date;

public class Reembolso {
	
	  private Date data;
	    private double valor;

	    public Reembolso(Date data, double valor) {
	        this.data = data;
	        this.valor = valor;
	    }

	    public Date getData() {
	        return data;
	    }

	    public void setData(Date data) {
	        this.data = data;
	    }

	    public double getValor() {
	        return valor;
	    }

	    public void setValor(double valor) {
	        this.valor = valor;
	    }

}
