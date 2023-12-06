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

    public void menuFaturas() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Menu Faturas");
            System.out.println("1. Consultar fatura por matrícula");
            System.out.println("2. Listar todas as faturas");
            System.out.println("3. Listar faturas em aberto");
            System.out.println("4. Criar fatura");
            System.out.println("5. Voltar ao menu principal");
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
                    System.out.println("Criando nova fatura...");
                    System.out.print("Digite a matrícula do imóvel: ");
                    int matriculaImovel = scanner.nextInt();
                    System.out.print("Digite a leitura atual: ");
                    double leituraAtual = scanner.nextDouble();
                    Imovel imovel = encontrarImovelPorMatricula(matriculaImovel);
                    if (imovel != null) {
                        gerarFatura(imovel, leituraAtual);
                        System.out.println("Fatura criada com sucesso para o imóvel de matrícula " + matriculaImovel);
                    } else {
                        System.out.println("Imóvel não encontrado para a matrícula " + matriculaImovel);
                    }
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Escolha novamente.");
            }
        } while (opcao != 5);
    }

    private void consultarFaturaPorMatricula(int matricula) {
        Imovel imovelEncontrado = encontrarImovelPorMatricula(matricula);
        if (imovelEncontrado != null) {
            System.out.println("Faturas para a matrícula " + matricula);
            for (Fatura fatura : faturas) {
                if (fatura.getImovel() == imovelEncontrado) {
                    System.out.println("Data: " + fatura.getData() + " - Valor: " + fatura.getValor());
                }
            }
        } else {
            System.out.println("Imóvel não encontrado para a matrícula " + matricula);
        }
    }

    private void listarTodasAsFaturas() {
        System.out.println("Todas as faturas:");
        for (Fatura fatura : faturas) {
            System.out.println("Data: " + fatura.getData() + " - Valor: " + fatura.getValor());
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

    public Fatura(Date data, double ultimaLeitura, double penultimaLeitura, double leituraAtual, Imovel imovel) {
        this.data = data;
        this.ultimaLeitura = ultimaLeitura;
        this.penultimaLeitura = penultimaLeitura;
        this.quitado = false;
        this.imovel = imovel;
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

    public Date getData() {
        return data;
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

    public double getValor() {
        return valor;
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

    public double getValor() {
        return valor;
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

    public Date getDataRegistro() {
        return dataRegistro;
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
