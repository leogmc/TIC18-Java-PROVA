package coelhoconcessionaria;

import java.util.List;

public class Imovel {
	
	private static List<Imovel> imoveis;
	
	private int matricula;
    private String endereco;
    private double ultimaLeitura;
    private double penultimaLeitura;

    public Imovel(int matricula, String endereco) {
        this.matricula = matricula;
        this.endereco = endereco;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getUltimaLeitura() {
        return ultimaLeitura;
    }

    public void setUltimaLeitura(double ultimaLeitura) {
        this.ultimaLeitura = ultimaLeitura;
    }

    public double getPenultimaLeitura() {
        return penultimaLeitura;
    }

    public void setPenultimaLeitura(double penultimaLeitura) {
        this.penultimaLeitura = penultimaLeitura;
    }
    
    
    // MÉTODOS ->
    
    public void adicionarImovel(Imovel imovel) {
        imoveis.add(imovel);
    }

    public List<Imovel> listarImoveis() {
        return imoveis;
    }
    
    public static Imovel encontrarImovelPorMatricula(int matricula) {
        for (Imovel i : imoveis) {
            if (i.getMatricula() == matricula) {
                return i;
            }
        }
        return null;
    }

}
