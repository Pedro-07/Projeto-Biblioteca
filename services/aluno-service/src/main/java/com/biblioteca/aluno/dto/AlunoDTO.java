package com.biblioteca.aluno.dto;

public class AlunoDTO {

    private Long id; 
    private String nome;
    private String dataNasc;
    private String email;
    private String turno;
    private Integer matricula;
    


	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public AlunoDTO() {}

    public AlunoDTO(Long id, String nome, String dataNasc, String turno) {
        this.id = id;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.turno = turno;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }


    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
