package coelhoconcessionaria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fatura {
	
	private List<Fatura> faturas;
	
    private Date data;
    private double ultimaLeitura;
    private double penultimaLeitura;
    private double valor;
    private boolean quitado;

    private static final double CUSTO_POR_KWH = 0.5;

    public Fatura(Date data, double ultimaLeitura, double penultimaLeitura, double leituraAtual) {
        this.data = data;
        this.ultimaLeitura = ultimaLeitura;
        this.penultimaLeitura = penultimaLeitura;
        this.quitado = false;
        calcularValorFatura(leituraAtual);
    }

    private void calcularValorFatura(double leituraAtual) {
        double consumo = leituraAtual - ultimaLeitura;
        double valorConsumo = consumo * CUSTO_POR_KWH;
        valor = valorConsumo;
    }

    public boolean isQuitado() {
        return quitado;
    }

    public void setQuitado(boolean quitado) {
        this.quitado = quitado;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    //MÉTODOS ->
    
    public void gerarFatura(Imovel imovel, double leituraAtual) {
        Imovel imovelEncontrado = encontrarImovelPorMatricula(imovel.getMatricula());

        if (imovelEncontrado != null) {
            double ultimaLeitura = imovelEncontrado.getUltimaLeitura();
            double penultimaLeitura = imovelEncontrado.getPenultimaLeitura();
            Fatura fatura = new Fatura(new Date(), ultimaLeitura, penultimaLeitura, leituraAtual);
            faturas.add(fatura);
            imovelEncontrado.setPenultimaLeitura(ultimaLeitura);
            imovelEncontrado.setUltimaLeitura(leituraAtual);
        }
    }

    

	public List<Fatura> listarFaturasEmAberto() {
        List<Fatura> faturasEmAberto = new ArrayList<>();
        for (Fatura f : faturas) {
            if (!f.isQuitado()) {
                faturasEmAberto.add(f);
            }
        }
        return faturasEmAberto;
    }
    


}
