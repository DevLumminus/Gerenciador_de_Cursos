package model;

public class Matricula {
    private int idMatricula;
    private int Aluno;
    private int Curso;

    public Matricula(){}
    public Matricula(int idMatricula, int aluno, int curso) {
        this.idMatricula = idMatricula;
        this.Aluno = aluno;
        this.Curso = curso;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public int getAluno() {
        return Aluno;
    }

    public void setAluno(int aluno) {
        Aluno = aluno;
    }

    public int getCurso() {
        return Curso;
    }

    public void setCurso(int curso) {
        Curso = curso;
    }

    @Override
    public String toString() {
        return "Matricula \nID=" + idMatricula +
                "\nAluno=" + Aluno +
                "\nCurso=" + Curso;
    }
}
