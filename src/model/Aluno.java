package model;

public class Aluno {
    private int idAlunos;
    private String Nome;
    private String Telefone;
    private int Email;
    private String cpf;

    public Aluno() {}

    public Aluno(int idAlunos, String nome, int email, String cpf) {
        this.idAlunos = idAlunos;
        this.Nome = nome;
        this.Email = email;
        this.cpf = cpf;
    } //Sem telefone

    public Aluno(int idAlunos, String nome, String telefone, int email, String cpf) {
        this.idAlunos = idAlunos;
        this.Nome = nome;
        this.Telefone = telefone;
        this.Email = email;
        this.cpf = cpf;
    } //Tudo

    public int getIdAlunos() {
        return idAlunos;
    }

    public void setIdAlunos(int idAlunos) {
        this.idAlunos = idAlunos;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public int getEmail() {
        return Email;
    }

    public void setEmail(int email) {
        Email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Aluno \nID = " + idAlunos +
                "\nNome = " + Nome +
                "\nTelefone = " + (Telefone != null ? Telefone: "Não tem Telefone") +
                "\nEmail = " + Email +
                "\nCPF = " + cpf;
    }
}
