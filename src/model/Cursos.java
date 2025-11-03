package model;

public class Cursos {
    private int idCursos;
    private String Nome;
    private String Tipo;
    private boolean Ativação; //default 0 vem de padrão

    public Cursos(){}

    public Cursos(int idCursos, String nome, String tipo, boolean ativação) {
        this.idCursos = idCursos;
        this.Nome = nome;
        this.Tipo = tipo;
        this.Ativação = ativação;
    }

    public int getIdCursos() {
        return idCursos;
    }

    public void setIdCursos(int idCursos) {
        this.idCursos = idCursos;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public boolean isAtivação() {
        return Ativação;
    }

    public void setAtivação(boolean ativação) {
        Ativação = ativação;
    }

    @Override
    public String toString() {
        return "Curso \nID = " + idCursos +
                "\nNome do curso = " + Nome +
                "\nTipo = " + Tipo +
                "\nEstá ativo = " + (Ativação ? "Ativo":"Desativado");
    } //O ultimo é para aparecer se for 1 ou 0
}
