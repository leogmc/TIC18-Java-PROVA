import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class CoelhoConcessionaria {
    private List<Cliente> clientes;
    private List<Imovel> imoveis;
    private List<Fatura> faturas;
    private List<Pagamento> pagamentos;
    private List<Reembolso> reembolsos;
    private List<Falha> falhas;
    private List<Reparo> reparos;

    public CoelhoConcessionaria() {
        clientes = new ArrayList<>();
        imoveis = new ArrayList<>();
        faturas = new ArrayList<>();
        pagamentos = new ArrayList<>();
        reembolsos = new ArrayList<>();
        falhas = new ArrayList<>();
        reparos = new ArrayList<>();
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<Cliente> listarClientes() {
        return clientes;
    }

    public void adicionarImovel(Imovel imovel) {
        imoveis.add(imovel);
    }

    public List<Imovel> listarImoveis() {
        return imoveis;
    }

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

    public void registrarPagamento(Fatura fatura, double valorPago) {
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

    public void registrarFalha(Falha falha) {
        falhas.add(falha);
        if (falha instanceof FalhaDistribuicao) {
            FalhaDistribuicao falhaDistribuicao = (FalhaDistribuicao) falha;
            Reparo reparo = new Reparo(falhaDistribuicao, "Reparo necessário", new Date(), null, false);
            reparos.add(reparo);
        }
    }

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

    private Imovel encontrarImovelPorMatricula(int matricula) {
        for (Imovel i : imoveis) {
            if (i.getMatricula() == matricula) {
                return i;
            }
        }
        return null;
    }
}

class Cliente {
    private String cpf;
    private String nome;

    public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

class Imovel {
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
}

class Fatura {
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
}

class Pagamento {
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
}

class Reembolso {
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

class Falha {
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

class Reparo {
    private Falha falhaAssociada;
    private String descricaoAtividade;
    private Date dataInicio;
    private Date dataFim;
    private boolean resolvido;

    public Reparo(Falha falhaAssociada, String descricaoAtividade, Date dataInicio, Date dataFim, boolean resolvido) {
        this.falhaAssociada = falhaAssociada;
        this.descricaoAtividade = descricaoAtividade;
        this.dataInicio = dataInicio;
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

    public boolean isFinalizado() {
        return resolvido;
    }
}
