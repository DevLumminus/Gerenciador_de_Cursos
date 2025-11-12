package controller;

import dao.*;
import model.*;

public class ControllerPrincipal {

    private final alunocontroller alunoController;
    private final cursocontroller cursoController;
    private final matriculacontroller matriculaController;
    private final emailcontroller emailController;

    public ControllerPrincipal() {
        // Criar DAOs diretamente
        EmailDAO emailDAO = new EmailDAO();
        AlunoDAO alunoDAO = new AlunoDAO(emailDAO);
        CursoDAO cursoDAO = new CursoDAO();
        MatriculaDAO matriculaDAO = new MatriculaDAO();

        // Chamada de DAOs nos controllers
        this.emailController = new emailcontroller(emailDAO);
        this.alunoController = new alunocontroller(alunoDAO, emailDAO);
        this.cursoController = new cursocontroller(cursoDAO);
        this.matriculaController = new matriculacontroller(matriculaDAO);
    }

    public void realizarMatriculaCompleta(String nomeAluno, String telefone, String cpf,
                                          String email, String nomeCurso) {
        try {
            System.out.println("Iniciando matrícula completa...");

            // Buscar curso por nome
            Cursos curso = cursoController.buscarPorNome(nomeCurso);
            if (curso == null) {
                throw new IllegalArgumentException("Curso não encontrado: " + nomeCurso);
            }

            // Matricular aluno com email
            Aluno aluno = alunoController.matricularAlunoComEmail(nomeAluno, telefone, cpf, email);

            // Matricular no curso usando o método específico
            Matricula matricula = matriculaController.matricularAluno(aluno.getIdAlunos(), curso.getIdCursos());

            System.out.println("Matrícula realizada com sucesso!");
            System.out.println("Aluno: " + aluno.getNome());
            System.out.println("Curso: " + curso.getNome());
            System.out.println("Email: " + email);

        } catch (Exception e) {
            System.err.println("Erro na matrícula: " + e.getMessage());
            throw e;
        }
    }

    public void gerarRelatorioMatriculas() {
        System.out.println("\nRELATÓRIO DE MATRÍCULAS");
        System.out.println("____________________________");

        // Alunos
        var alunos = alunoController.listarTodos();
        System.out.println("Total de Alunos: " + alunos.size());

        // Cursos
        var cursos = cursoController.listarTodos();
        System.out.println("Total de Cursos: " + cursos.size());

        // Matrículas
        var matriculas = matriculaController.listarTodos();
        System.out.println("Total de Matrículas: " + matriculas.size());

        // Detalhes por curso
        System.out.println("\nMatrículas por Curso:");
        for (Cursos curso : cursos) {
            int count = matriculaController.contarMatriculasPorCurso(curso.getIdCursos());
            System.out.println("  - " + curso.getNome() + ": " + count + " matrículas");
        }
    }

    // Getters
    public alunocontroller getAlunoController() { return alunoController; }
    public cursocontroller getCursoController() { return cursoController; }
    public matriculacontroller getMatriculaController() { return matriculaController; }
    public emailcontroller getEmailController() { return emailController; }
}
