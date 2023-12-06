
public void menuFaturas() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Menu Faturas");
            System.out.println("1. Consultar fatura por matrícula");
            System.out.println("2. Listar todas as faturas");
            System.out.println("3. Listar faturas em aberto");
            System.out.println("4. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite a matrícula do imóvel: ");
                    int matricula = scanner.nextInt();
                    consultarFaturaPorMatricula(matricula);
                    break;
                case 2:
                    listarTodasAsFaturas();
                    break;
                case 3:
                    listarFaturasEmAberto();
                    break;
                case 4:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Escolha novamente.");
            }
        } while (opcao != 4);
    }

    private void consultarFaturaPorMatricula(int matricula) {
        Imovel imovelEncontrado = encontrarImovelPorMatricula(matricula);
        if (imovelEncontrado != null) {
            System.out.println("Faturas para a matrícula " + matricula + ":");
            for (Fatura fatura : faturas) {
                if (fatura.getImovel().equals(imovelEncontrado)) {
                    System.out.println(fatura.getData() + " - Valor: " + fatura.getValor() + " - Quitado: " + (fatura.isQuitado() ? "Sim" : "Não"));
                }
            }
        } else {
            System.out.println("Imóvel não encontrado para a matrícula " + matricula);
        }
    }

    private void listarTodasAsFaturas() {
        System.out.println("Todas as faturas:");
        for (Fatura fatura : faturas) {
            System.out.println(fatura.getData() + " - Valor: " + fatura.getValor() + " - Quitado: " + (fatura.isQuitado() ? "Sim" : "Não"));
        }
    }

    private void listarFaturasEmAberto() {
        List<Fatura> faturasEmAberto = listarFaturasEmAberto();
        System.out.println("Faturas em aberto:");
        for (Fatura fatura : faturasEmAberto) {
            System.out.println(fatura.getData() + " - Valor: " + fatura.getValor());
        }
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
    private Imovel imovel;

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

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
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
