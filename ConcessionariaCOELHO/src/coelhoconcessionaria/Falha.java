package coelhoconcessionaria;

import java.util.Date;
import java.util.List;

public class Falha {
	
	private List<Falha> falhas;
	
	   private String descricao;
	   private Date dataRegistro;

	    public Falha(String descricao, Date dataRegistro) {
	        this.descricao = descricao;
	        this.dataRegistro = dataRegistro;
	    }

	    public String getDescricao() {
	        return descricao;
	    }

	    public void setDescricao(String descricao) {
	        this.descricao = descricao;
	    }

	    public Date getDataRegistro() {
	        return dataRegistro;
	    }

	    public void setDataRegistro(Date dataRegistro) {
	        this.dataRegistro = dataRegistro;
	    }
	    
	    //MÉTODOS ->
	    
	    public void registrarFalha(Falha falha, List<Reparo> reparos) {
	        falhas.add(falha);
	        if (falha instanceof FalhaDistribuicao) {
	            FalhaDistribuicao falhaDistribuicao = (FalhaDistribuicao) falha;
	            Reparo reparo = new Reparo(falhaDistribuicao, "Reparo necessário", new Date(), null, false);
	            reparos.add(reparo);
	        }
	    }
}

class FalhaDistribuicao extends Falha {
    private String localAfetado;

    public FalhaDistribuicao(String descricao, Date dataRegistro, String localAfetado) {
        super(descricao, dataRegistro);
        this.localAfetado = localAfetado;
    }

    public String getLocalAfetado() {
        return localAfetado;
    }

    public void setLocalAfetado(String localAfetado) {
        this.localAfetado = localAfetado;
    }
    
  
}
