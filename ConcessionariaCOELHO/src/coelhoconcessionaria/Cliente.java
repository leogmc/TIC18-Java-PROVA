package coelhoconcessionaria;

import java.util.List;

public class Cliente {
	
		private List<Cliente> clientes;
		
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
	    
	    
	    // MÉTODOS ->
	    
	    public void adicionarCliente(Cliente cliente) {
	        clientes.add(cliente);
	    }

	    public List<Cliente> listarClientes() {
	        return clientes;
	    }
	}


