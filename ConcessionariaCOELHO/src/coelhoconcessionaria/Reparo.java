package coelhoconcessionaria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reparo {
	
		private List<Reparo> reparos;
	
	    private Falha falhaAssociada;
	    private String descricaoAtividade;
	    private Date dataInicio;
	    private Date dataFim;
	    private boolean resolvido;

	    public Reparo(Falha falhaAssociada, String descricaoAtividade, Date dataInicio, Date dataFim, boolean resolvido) {
	        this.falhaAssociada = falhaAssociada;
	        this.setDescricaoAtividade(descricaoAtividade);
	        this.setDataInicio(dataInicio);
	        this.dataFim = dataFim;
	        this.resolvido = resolvido;
	    }

	    public void setFinalizado(boolean finalizado) {
	        this.resolvido = finalizado;
	    }

	    public void setResolvido(boolean resolvido) {
	        this.resolvido = resolvido;
	    }

	    public void setDataFim(Date dataFim) {
	        this.dataFim = dataFim;
	    }

	    public Falha getFalhaAssociada() {
	        return falhaAssociada;
	    }
	    
		public String getDescricaoAtividade() {
			return descricaoAtividade;
		}

		public void setDescricaoAtividade(String descricaoAtividade) {
			this.descricaoAtividade = descricaoAtividade;
		}

		public Date getDataInicio() {
			return dataInicio;
		}
		
		public Date getDataFim() {
			return dataFim;
		}

		public void setDataInicio(Date dataInicio) {
			this.dataInicio = dataInicio;
		}

	    public boolean isFinalizado() {
	        return resolvido;
	    }
	    
	    //MÉTODOS - >
	    
	    public List<Reparo> listarReparosEmAberto() {
	        List<Reparo> reparosEmAberto = new ArrayList<>();
	        for (Reparo r : reparos) {
	            if (!r.isFinalizado()) {
	                reparosEmAberto.add(r);
	            }
	        }
	        return reparosEmAberto;
	    }

	    public void encerrarReparo(Reparo reparo, boolean resolvido, String descricao) {
	        reparo.setFinalizado(true);
	        reparo.setResolvido(resolvido);
	        reparo.setDataFim(new Date());
	        if (!resolvido) {
	            Falha falha = reparo.getFalhaAssociada();
	            FalhaDistribuicao falhaDistribuicao = (FalhaDistribuicao) falha;
	            Reparo novoReparo = new Reparo(falhaDistribuicao, descricao, new Date(), null, false);
	            reparos.add(novoReparo);
	        }
	    }

	
	}

