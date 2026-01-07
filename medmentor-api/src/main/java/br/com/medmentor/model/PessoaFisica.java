package br.com.medmentor.model;

import java.io.Serializable;
import java.time.LocalDate;

public class PessoaFisica implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private Integer id;
    private Pessoa pessoa; 
    private String cpf;
    private LocalDate dataNascimento;
    private Integer idade;

    public PessoaFisica() {}

    public PessoaFisica(Pessoa pessoa, String cpf, LocalDate dataNascimento, Integer idade) {
        this.pessoa = pessoa;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	@Override
    public String toString() {
       return "PessoaFisica{" +
              "id=" + id +
              ", pessoa=" + (pessoa != null ? pessoa.getId() : "null") +
              ", cpf='" + cpf + '\'' +
              ", idade=" + idade +
              '}';
    }
}