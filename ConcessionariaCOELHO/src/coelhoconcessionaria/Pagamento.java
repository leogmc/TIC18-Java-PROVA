package coelhoconcessionaria;

import java.util.Date;
import java.util.List;

public class Pagamento {
	
	private List<Pagamento> pagamentos;
	
    private Date data;
    private double valor;

    public Pagamento(Date data, double valor) {
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
    
    //MÉTODOS ->
    
    public void registrarPagamento(Fatura fatura, double valorPago, List<Reembolso> reembolsos) {
        if (!fatura.isQuitado()) {
            Pagamento pagamento = new Pagamento(new Date(), valorPago);
            pagamentos.add(pagamento);

            double valorRestante = fatura.getValor() - valorPago;
            if (valorRestante <= 0) {
                fatura.setQuitado(true);
                if (valorRestante < 0) {
                    Reembolso reembolso = new Reembolso(new Date(), Math.abs(valorRestante));
                    reembolsos.add(reembolso);
                }
            }
        }
    }

    public List<Pagamento> listarPagamentos() {
        return pagamentos;
    }

}
