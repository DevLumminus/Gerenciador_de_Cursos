package view;

import controller.ControllerPrincipal;
import model.*;
import java.util.List;
import java.util.Scanner;

public class SistemaEscola {
    private final ControllerPrincipal controller;
    private final Scanner scanner;

    public SistemaEscola(ControllerPrincipal controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    //MÉTODO PRINCIPAL - PADRÃO CONTROLLER
    public void iniciarSistema() {
        System.out.println("SISTEMA DE GESTÃO ACADÊMICA");

        while (true) {
            exibirMenuPrincipal();
            int opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> menuAlunos();
                case 2 -> menuCursos();
                case 3 -> menuMatriculas();
                case 4 -> menuEmails();
                case 5 -> realizarMatriculaCompleta();
                case 6 -> gerarRelatorio();
                case 0 -> {
                    System.out.println("Saindo do sistema...");
                    return;
                }
                default -> System.err.println("Opção inválida!");
            }
        }
    }

    // MENU PRINCIPAL - ALTA COESÃO
    private void exibirMenuPrincipal() {
        System.out.println("\nMENU PRINCIPAL");
        System.out.println("1 - Gestão de Alunos");
        System.out.println("2  - Gestão de Cursos");
        System.out.println("3  - Gestão de Matrículas");
        System.out.println("4 - Gestão de Emails");
        System.out.println("5  - Realizar Matrícula Completa");
        System.out.println("6  - Relatórios");
        System.out.println("0  - Sair");
        System.out.println("----------------------------");
    }

    // MENU ALUNOS
    private void menuAlunos() {
        while (true) {
            System.out.println("\nGESTÃO DE ALUNOS");
            System.out.println("1 - Listar todos os alunos");
            System.out.println("2 - Buscar aluno por ID");
            System.out.println("3 - Buscar aluno por CPF");
            System.out.println("4 - Cadastrar novo aluno");
            System.out.println("5 - Atualizar aluno");
            System.out.println("6 - Excluir aluno");
            System.out.println("7 - Validar aluno para matrícula");
            System.out.println("0 - Voltar ao menu principal");

            int opcao = lerInteiro("Escolha: ");

            switch (opcao) {
                case 1 -> listarAlunos();
                case 2 -> buscarAlunoPorId();
                case 3 -> buscarAlunoPorCpf();
                case 4 -> cadastrarAluno();
                case 5 -> atualizarAluno();
                case 6 -> excluirAluno();
                case 7 -> validarAlunoParaMatricula();
                case 0 -> { return; }
                default -> System.err.println("Opção inválida!");
            }
        }
    }

    // MENU CURSOS
    private void menuCursos() {
        while (true) {
            System.out.println("\nGESTÃO DE CURSOS");
            System.out.println("1 - Listar todos os cursos");
            System.out.println("2 - Listar cursos ativos");
            System.out.println("3 - Listar cursos inativos");
            System.out.println("4 - Buscar curso por ID");
            System.out.println("5 - Buscar curso por nome");
            System.out.println("6 - Cadastrar novo curso");
            System.out.println("7 - Atualizar curso");
            System.out.println("8 - Ativar/Desativar curso");
            System.out.println("9 - Excluir curso");
            System.out.println("0 - Voltar ao menu principal");

            int opcao = lerInteiro("Escolha: ");

            switch (opcao) {
                case 1 -> listarCursos();
                case 2 -> listarCursosAtivos();
                case 3 -> listarCursosInativos();
                case 4 -> buscarCursoPorId();
                case 5 -> buscarCursoPorNome();
                case 6 -> cadastrarCurso();
                case 7 -> atualizarCurso();
                case 8 -> ativarDesativarCurso();
                case 9 -> excluirCurso();
                case 0 -> { return; }
                default -> System.err.println("Opção inválida!");
            }
        }
    }

    // MENU MATRÍCULAS
    private void menuMatriculas() {
        while (true) {
            System.out.println("\nGESTÃO DE MATRÍCULAS");
            System.out.println("1 - Listar todas as matrículas");
            System.out.println("2 - Buscar matrícula por ID");
            System.out.println("3 - Matricular aluno em curso");
            System.out.println("4 - Buscar matrículas por aluno");
            System.out.println("5 - Buscar matrículas por curso");
            System.out.println("6 - Cancelar matrícula");
            System.out.println("7 - Verificar se matrícula existe");
            System.out.println("0 - Voltar ao menu principal");

            int opcao = lerInteiro("Escolha: ");

            switch (opcao) {
                case 1 -> listarMatriculas();
                case 2 -> buscarMatriculaPorId();
                case 3 -> matricularAluno();
                case 4 -> buscarMatriculasPorAluno();
                case 5 -> buscarMatriculasPorCurso();
                case 6 -> cancelarMatricula();
                case 7 -> verificarMatriculaExistente();
                case 0 -> { return; }
                default -> System.err.println("Opção inválida!");
            }
        }
    }

    // MENU EMAILS
    private void menuEmails() {
        while (true) {
            System.out.println("\nGESTÃO DE EMAILS");
            System.out.println("1 - Listar todos os emails");
            System.out.println("2 - Buscar email por ID");
            System.out.println("3 - Buscar email por endereço");
            System.out.println("4 - Cadastrar novo email");
            System.out.println("5 - Atualizar email");
            System.out.println("6 - Excluir email");
            System.out.println("0 - Voltar ao menu principal");

            int opcao = lerInteiro("Escolha: ");

            switch (opcao) {
                case 1 -> listarEmails();
                case 2 -> buscarEmailPorId();
                case 3 -> buscarEmailPorEndereco();
                case 4 -> cadastrarEmail();
                case 5 -> atualizarEmail();
                case 6 -> excluirEmail();
                case 0 -> { return; }
                default -> System.err.println("Opção inválida!");
            }
        }
    }

    //MÉTODOS PARA ALUNOS - BAIXO ACOPLAMENTO
    private void listarAlunos() {
        try {
            List<Aluno> alunos = controller.getAlunoController().listarTodos();
            System.out.println("\nLISTA DE ALUNOS:");
            if (alunos.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado.");
            } else {
                alunos.forEach(aluno ->
                        System.out.printf("ID: %d | Nome: %s | CPF: %s | Telefone: %s%n",
                                aluno.getIdAlunos(), aluno.getNome(), aluno.getCpf(), aluno.getTelefone())
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        }
    }

    private void buscarAlunoPorId() {
        try {
            int id = lerInteiro("ID do aluno: ");
            Aluno aluno = controller.getAlunoController().buscarPorId(id);
            if (aluno != null) {
                System.out.println("Aluno encontrado:");
                System.out.printf("ID: %d | Nome: %s | CPF: %s | Telefone: %s%n",
                        aluno.getIdAlunos(), aluno.getNome(), aluno.getCpf(), aluno.getTelefone());
            } else {
                System.err.println("Aluno não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar aluno: " + e.getMessage());
        }
    }

    private void cadastrarAluno() {
        try {
            System.out.println("\nCADASTRAR NOVO ALUNO:");
            String nome = lerString("Nome: ");
            String telefone = lerString("Telefone: ");
            String cpf = lerString("CPF (apenas números): ");
            String email = lerString("Email: ");

            Aluno aluno = controller.getAlunoController().matricularAlunoComEmail(nome, telefone, cpf, email);
            System.out.println("Aluno cadastrado com sucesso! ID: " + aluno.getIdAlunos());
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    private void buscarAlunoPorCpf() {
        try {
            String cpf = lerString("CPF do aluno: ");
            Aluno aluno = controller.getAlunoController().buscarPorCpf(cpf);
            if (aluno != null) {
                System.out.println("Aluno encontrado:");
                System.out.printf("ID: %d | Nome: %s | CPF: %s | Telefone: %s%n",
                        aluno.getIdAlunos(), aluno.getNome(), aluno.getCpf(), aluno.getTelefone());
            } else {
                System.out.println("Aluno não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar aluno por CPF: " + e.getMessage());
        }
    }

    private void atualizarAluno() {
        try {
            int id = lerInteiro("ID do aluno a ser atualizado: ");
            Aluno alunoExistente = controller.getAlunoController().buscarPorId(id);

            if (alunoExistente == null) {
                System.err.println("Aluno não encontrado.");
                return;
            }

            System.out.println("Deixe em branco para manter o valor atual:");
            String nome = lerStringOpcional("Novo nome [" + alunoExistente.getNome() + "]: ");
            String telefone = lerStringOpcional("Novo telefone [" + alunoExistente.getTelefone() + "]: ");

            if (!nome.isEmpty()) alunoExistente.setNome(nome);
            if (!telefone.isEmpty()) alunoExistente.setTelefone(telefone);

            boolean sucesso = controller.getAlunoController().atualizar(alunoExistente);
            if (sucesso) {
                System.out.println("Aluno atualizado com sucesso!");
            } else {
                System.err.println("Falha ao atualizar aluno.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    private void excluirAluno() {
        try {
            int id = lerInteiro("ID do aluno a ser excluído: ");
            boolean sucesso = controller.getAlunoController().excluir(id);
            if (sucesso) {
                System.out.println("Aluno excluído com sucesso!");
            } else {
                System.err.println("Falha ao excluir aluno.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir aluno: " + e.getMessage());
        }
    }

    private void validarAlunoParaMatricula() {
        try {
            int id = lerInteiro("ID do aluno: ");
            boolean valido = controller.getAlunoController().validarAlunoParaMatricula(id);
            if (valido) {
                System.out.println("Aluno válido para matrícula!");
            } else {
                System.err.println("Aluno não está válido para matrícula.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao validar aluno: " + e.getMessage());
        }
    }

    //MÉTODOS PARA CURSOS
    private void listarCursos() {
        try {
            List<Cursos> cursos = controller.getCursoController().listarTodos();
            System.out.println("\nLISTA DE CURSOS:");
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso cadastrado.");
            } else {
                cursos.forEach(curso ->
                        System.out.printf("ID: %d | Nome: %s | Tipo: %s | Status: %s%n",
                                curso.getIdCursos(), curso.getNome(), curso.getTipo(),
                                curso.isAtivação() ? "Ativo" : "Inativo")
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar cursos: " + e.getMessage());
        }
    }

    private void cadastrarCurso() {
        try {
            System.out.println("\nCADASTRAR NOVO CURSO:");
            String nome = lerString("Nome do curso: ");
            String tipo = lerString("Tipo do curso: ");

            Cursos curso = new Cursos(0, nome, tipo, true);
            Cursos cursoCriado = controller.getCursoController().criar(curso);
            System.out.println("Curso cadastrado com sucesso! ID: " + cursoCriado.getIdCursos());
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar curso: " + e.getMessage());
        }
    }

    private void buscarCursoPorId() {
        try {
            int id = lerInteiro("ID do curso: ");
            Cursos curso = controller.getCursoController().buscarPorId(id);
            if (curso != null) {
                System.out.println("Curso encontrado:");
                System.out.printf("ID: %d | Nome: %s | Tipo: %s | Status: %s%n",
                        curso.getIdCursos(), curso.getNome(), curso.getTipo(),
                        curso.isAtivação() ? "Ativo" : "Inativo");
            } else {
                System.err.println("Curso não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar curso: " + e.getMessage());
        }
    }

    private void buscarCursoPorNome() {
        try {
            String nome = lerString("Nome do curso: ");
            Cursos curso = controller.getCursoController().buscarPorNome(nome);
            if (curso != null) {
                System.out.println("Curso encontrado:");
                System.out.printf("ID: %d | Nome: %s | Tipo: %s | Status: %s%n",
                        curso.getIdCursos(), curso.getNome(), curso.getTipo(),
                        curso.isAtivação() ? "Ativo" : "Inativo");
            } else {
                System.err.println("Curso não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar curso por nome: " + e.getMessage());
        }
    }

    private void listarCursosAtivos() {
        try {
            List<Cursos> cursos = controller.getCursoController().listarAtivos();
            System.out.println("\nCURSOS ATIVOS:");
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso ativo.");
            } else {
                cursos.forEach(curso ->
                        System.out.printf("ID: %d | Nome: %s | Tipo: %s%n",
                                curso.getIdCursos(), curso.getNome(), curso.getTipo())
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar cursos ativos: " + e.getMessage());
        }
    }

    private void listarCursosInativos() {
        try {
            List<Cursos> cursos = controller.getCursoController().listarInativos();
            System.out.println("\nCURSOS INATIVOS:");
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso inativo.");
            } else {
                cursos.forEach(curso ->
                        System.out.printf("ID: %d | Nome: %s | Tipo: %s%n",
                                curso.getIdCursos(), curso.getNome(), curso.getTipo())
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar cursos inativos: " + e.getMessage());
        }
    }

    private void ativarDesativarCurso() {
        try {
            int id = lerInteiro("ID do curso: ");
            Cursos curso = controller.getCursoController().buscarPorId(id);

            if (curso == null) {
                System.err.println("Curso não encontrado.");
                return;
            }

            System.out.println("Status atual: " + (curso.isAtivação() ? "Ativo" : "Inativo"));
            System.out.println("1 - Ativar curso");
            System.out.println("2 - Desativar curso");
            int opcao = lerInteiro("Escolha: ");

            boolean sucesso = false;
            if (opcao == 1) {
                sucesso = controller.getCursoController().ativarCurso(id);
            } else if (opcao == 2) {
                sucesso = controller.getCursoController().desativarCurso(id);
            } else {
                System.err.println("Opção inválida.");
                return;
            }

            if (sucesso) {
                System.out.println("Status do curso atualizado com sucesso!");
            } else {
                System.err.println("Falha ao atualizar status do curso.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao alterar status do curso: " + e.getMessage());
        }
    }

    private void atualizarCurso() {
        try {
            int id = lerInteiro("ID do curso a ser atualizado: ");
            Cursos cursoExistente = controller.getCursoController().buscarPorId(id);

            if (cursoExistente == null) {
                System.err.println("Curso não encontrado.");
                return;
            }

            System.out.println("Deixe em branco para manter o valor atual:");
            String nome = lerStringOpcional("Novo nome [" + cursoExistente.getNome() + "]: ");
            String tipo = lerStringOpcional("Novo tipo [" + cursoExistente.getTipo() + "]: ");

            if (!nome.isEmpty()) cursoExistente.setNome(nome);
            if (!tipo.isEmpty()) cursoExistente.setTipo(tipo);

            boolean sucesso = controller.getCursoController().atualizar(cursoExistente);
            if (sucesso) {
                System.out.println("Curso atualizado com sucesso!");
            } else {
                System.err.println("Falha ao atualizar curso.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar curso: " + e.getMessage());
        }
    }

    private void excluirCurso() {
        try {
            int id = lerInteiro("ID do curso a ser excluído: ");
            boolean sucesso = controller.getCursoController().excluir(id);
            if (sucesso) {
                System.out.println("Curso excluído com sucesso!");
            } else {
                System.err.println("Falha ao excluir curso.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir curso: " + e.getMessage());
        }
    }

    //MÉTODOS PARA MATRÍCULAS
    private void listarMatriculas() {
        try {
            List<Matricula> matriculas = controller.getMatriculaController().listarTodos();
            System.out.println("\nLISTA DE MATRÍCULAS:");
            if (matriculas.isEmpty()) {
                System.out.println("Nenhuma matrícula cadastrada.");
            } else {
                matriculas.forEach(matricula ->
                        System.out.printf("ID: %d | Aluno ID: %d | Curso ID: %d%n",
                                matricula.getIdMatricula(), matricula.getAluno(), matricula.getCurso())
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar matrículas: " + e.getMessage());
        }
    }

    private void matricularAluno() {
        try {
            System.out.println("\nMATRICULAR ALUNO EM CURSO:");
            int idAluno = lerInteiro("ID do aluno: ");
            int idCurso = lerInteiro("ID do curso: ");

            Matricula matricula = controller.getMatriculaController().matricularAluno(idAluno, idCurso);
            System.out.println("Matrícula realizada com sucesso! ID: " + matricula.getIdMatricula());
        } catch (Exception e) {
            System.err.println("Erro ao matricular aluno: " + e.getMessage());
        }
    }

    private void buscarMatriculaPorId() {
        try {
            int id = lerInteiro("ID da matrícula: ");
            Matricula matricula = controller.getMatriculaController().buscarPorId(id);
            if (matricula != null) {
                System.out.println("Matrícula encontrada:");
                System.out.printf("ID: %d | Aluno ID: %d | Curso ID: %d%n",
                        matricula.getIdMatricula(), matricula.getAluno(), matricula.getCurso());
            } else {
                System.err.println("Matrícula não encontrada.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar matrícula: " + e.getMessage());
        }
    }

    private void buscarMatriculasPorAluno() {
        try {
            int idAluno = lerInteiro("ID do aluno: ");
            List<Matricula> matriculas = controller.getMatriculaController().buscarPorAluno(idAluno);
            System.out.println("\nMATRÍCULAS DO ALUNO " + idAluno + ":");
            if (matriculas.isEmpty()) {
                System.out.println("Nenhuma matrícula encontrada.");
            } else {
                matriculas.forEach(matricula ->
                        System.out.printf("ID Matrícula: %d | Curso ID: %d%n",
                                matricula.getIdMatricula(), matricula.getCurso())
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar matrículas por aluno: " + e.getMessage());
        }
    }

    private void buscarMatriculasPorCurso() {
        try {
            int idCurso = lerInteiro("ID do curso: ");
            List<Matricula> matriculas = controller.getMatriculaController().buscarPorCurso(idCurso);
            System.out.println("\nMATRÍCULAS DO CURSO " + idCurso + ":");
            if (matriculas.isEmpty()) {
                System.out.println("Nenhuma matrícula encontrada.");
            } else {
                matriculas.forEach(matricula ->
                        System.out.printf("ID Matrícula: %d | Aluno ID: %d%n",
                                matricula.getIdMatricula(), matricula.getAluno())
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar matrículas por curso: " + e.getMessage());
        }
    }

    private void cancelarMatricula() {
        try {
            int id = lerInteiro("ID da matrícula a ser cancelada: ");
            boolean sucesso = controller.getMatriculaController().cancelarMatricula(id);
            if (sucesso) {
                System.out.println("Matrícula cancelada com sucesso!");
            } else {
                System.err.println("Falha ao cancelar matrícula.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao cancelar matrícula: " + e.getMessage());
        }
    }

    private void verificarMatriculaExistente() {
        try {
            int idAluno = lerInteiro("ID do aluno: ");
            int idCurso = lerInteiro("ID do curso: ");
            boolean existe = controller.getMatriculaController().verificarMatriculaExistente(idAluno, idCurso);
            if (existe) {
                System.out.println("Matrícula já existe!");
            } else {
                System.err.println("Matrícula não existe.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao verificar matrícula: " + e.getMessage());
        }
    }

    // MÉTODOS PARA EMAILS
    private void listarEmails() {
        try {
            List<Email> emails = controller.getEmailController().listarTodos();
            System.out.println("\nLISTA DE EMAILS:");
            if (emails.isEmpty()) {
                System.out.println("Nenhum email cadastrado.");
            } else {
                emails.forEach(email ->
                        System.out.printf("ID: %d | Email: %s%n", email.getIdEmail(), email.getEmail())
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar emails: " + e.getMessage());
        }
    }

    private void cadastrarEmail() {
        try {
            System.out.println("\nCADASTRAR NOVO EMAIL:");
            String enderecoEmail = lerString("Endereço de email: ");

            Email email = new Email(0, enderecoEmail);
            Email emailCriado = controller.getEmailController().criar(email);
            System.out.println("Email cadastrado com sucesso! ID: " + emailCriado.getIdEmail());
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar email: " + e.getMessage());
        }
    }

    private void buscarEmailPorId() {
        try {
            int id = lerInteiro("ID do email: ");
            Email email = controller.getEmailController().buscarPorId(id);
            if (email != null) {
                System.out.println("Email encontrado:");
                System.out.printf("ID: %d | Email: %s%n", email.getIdEmail(), email.getEmail());
            } else {
                System.err.println("Email não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar email: " + e.getMessage());
        }
    }

    private void buscarEmailPorEndereco() {
        try {
            String endereco = lerString("Endereço de email: ");
            Email email = controller.getEmailController().buscarEmailPorEndereco(endereco);
            if (email != null) {
                System.out.println("Email encontrado:");
                System.out.printf("ID: %d | Email: %s%n", email.getIdEmail(), email.getEmail());
            } else {
                System.err.println("Email não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar email por endereço: " + e.getMessage());
        }
    }

    private void atualizarEmail() {
        try {
            int id = lerInteiro("ID do email a ser atualizado: ");
            Email emailExistente = controller.getEmailController().buscarPorId(id);

            if (emailExistente == null) {
                System.err.println("Email não encontrado.");
                return;
            }

            String novoEmail = lerString("Novo endereço de email: ");
            emailExistente.setEmail(novoEmail);

            boolean sucesso = controller.getEmailController().atualizar(emailExistente);
            if (sucesso) {
                System.out.println("Email atualizado com sucesso!");
            } else {
                System.err.println("Falha ao atualizar email.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar email: " + e.getMessage());
        }
    }

    private void excluirEmail() {
        try {
            int id = lerInteiro("ID do email a ser excluído: ");
            boolean sucesso = controller.getEmailController().excluir(id);
            if (sucesso) {
                System.out.println("Email excluído com sucesso!");
            } else {
                System.err.println("Falha ao excluir email.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir email: " + e.getMessage());
        }
    }

    // MÉTODOS ESPECIAIS DO CONTROLLER PRINCIPAL
    private void realizarMatriculaCompleta() {
        try {
            System.out.println("\nMATRÍCULA COMPLETA");
            String nomeAluno = lerString("Nome do aluno: ");
            String telefone = lerString("Telefone: ");
            String cpf = lerString("CPF (apenas números): ");
            String email = lerString("Email: ");
            String nomeCurso = lerString("Nome do curso: ");

            controller.realizarMatriculaCompleta(nomeAluno, telefone, cpf, email, nomeCurso);
            System.out.println("Matrícula completa realizada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro na matrícula completa: " + e.getMessage());
        }
    }

    private void gerarRelatorio() {
        try {
            controller.gerarRelatorioMatriculas();
        } catch (Exception e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    // MÉTODOS AUXILIARES - ALTA COESÃO
    private int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Por favor, digite um número válido.");
            }
        }
    }

    private String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private String lerStringOpcional(String mensagem) {
        System.out.print(mensagem);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? "" : input;
    }
}
