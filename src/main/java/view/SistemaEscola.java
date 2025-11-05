package view;

import controller.ControladorEscola;
import model.*;
import java.util.List;
import java.util.Scanner;

public class SistemaEscola {
    private ControladorEscola controlador;
    private Scanner scanner;

    public SistemaEscola() {
        this.controlador = new ControladorEscola();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenuPrincipal() {
        int opcao;
        do {
            System.out.println("\n🎓 SISTEMA DE CONTROLE DE CURSOS 🎓");
            System.out.println("=====================================");
            System.out.println("1. 📚 Menu Cursos");
            System.out.println("2. 👨‍🎓 Menu Alunos");
            System.out.println("3. 📧 Menu Emails");
            System.out.println("4. 📋 Menu Matrículas");
            System.out.println("5. 📊 Relatórios Gerais");
            System.out.println("0. ❌ Sair");
            System.out.println("=====================================");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> menuCursos();
                case 2 -> menuAlunos();
                case 3 -> menuEmails();
                case 4 -> menuMatriculas();
                case 5 -> menuRelatorios();
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 0);
    }

    // ========== MENU CURSOS ==========
    private void menuCursos() {
        int opcao;
        do {
            System.out.println("\n📚 MENU CURSOS");
            System.out.println("==================");
            System.out.println("1. ➕ Cadastrar Curso");
            System.out.println("2. 📋 Listar Todos os Cursos");
            System.out.println("3. 🔍 Buscar Curso por ID");
            System.out.println("4. 📝 Atualizar Curso");
            System.out.println("5. 🚫 Desativar/Ativar Curso");
            System.out.println("6. ❌ Excluir Curso");
            System.out.println("7. ✅ Listar Cursos Ativos");
            System.out.println("8. 🔎 Buscar por Tipo");
            System.out.println("0. ↩️ Voltar");
            System.out.println("==================");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> cadastrarCurso();
                case 2 -> listarTodosCursos();
                case 3 -> buscarCursoPorId();
                case 4 -> atualizarCurso();
                case 5 -> ativarDesativarCurso();
                case 6 -> excluirCurso();
                case 7 -> listarCursosAtivos();
                case 8 -> buscarCursosPorTipo();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarCurso() {
        System.out.println("\n➕ CADASTRAR NOVO CURSO");
        System.out.print("Nome do curso: ");
        String nome = scanner.nextLine();

        System.out.print("Tipo do curso: ");
        String tipo = scanner.nextLine();

        try {
            Cursos curso = controlador.cadastrarCurso(nome, tipo);
            System.out.println("✅ Curso cadastrado com sucesso! ID: " + curso.getIdCursos());
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar curso: " + e.getMessage());
        }
    }

    private void listarTodosCursos() {
        System.out.println("\n📋 LISTA DE TODOS OS CURSOS");
        List<Cursos> cursos = controlador.listarTodosCursos();

        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
        } else {
            for (Cursos curso : cursos) {
                System.out.println("-----------------------------------");
                System.out.println(curso.toString());
            }
        }
    }

    private void buscarCursoPorId() {
        System.out.println("\n🔍 BUSCAR CURSO POR ID");
        System.out.print("Digite o ID do curso: ");
        int id = lerInteiro();

        Cursos curso = controlador.buscarCursoPorId(id);
        if (curso != null) {
            System.out.println("✅ Curso encontrado:");
            System.out.println(curso.toString());
        } else {
            System.out.println("❌ Curso não encontrado!");
        }
    }

    private void atualizarCurso() {
        System.out.println("\n📝 ATUALIZAR CURSO");
        System.out.print("Digite o ID do curso a ser atualizado: ");
        int id = lerInteiro();

        Cursos curso = controlador.buscarCursoPorId(id);
        if (curso == null) {
            System.out.println("❌ Curso não encontrado!");
            return;
        }

        System.out.println("Curso atual: " + curso.toString());
        System.out.print("Novo nome (enter para manter atual): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.trim().isEmpty()) {
            curso.setNome(novoNome);
        }

        System.out.print("Novo tipo (enter para manter atual): ");
        String novoTipo = scanner.nextLine();
        if (!novoTipo.trim().isEmpty()) {
            curso.setTipo(novoTipo);
        }

        try {
            if (controlador.atualizarCurso(curso)) {
                System.out.println("✅ Curso atualizado com sucesso!");
            } else {
                System.out.println("❌ Erro ao atualizar curso!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void ativarDesativarCurso() {
        System.out.println("\n🚫 ATIVAR/DESATIVAR CURSO");
        System.out.print("Digite o ID do curso: ");
        int id = lerInteiro();

        Cursos curso = controlador.buscarCursoPorId(id);
        if (curso == null) {
            System.out.println("❌ Curso não encontrado!");
            return;
        }

        System.out.println("Status atual: " + (curso.isAtivação() ? "ATIVO" : "INATIVO"));
        System.out.print("Deseja " + (curso.isAtivação() ? "DESATIVAR" : "ATIVAR") + " o curso? (s/n): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {
            try {
                boolean sucesso;
                if (curso.isAtivação()) {
                    sucesso = controlador.desativarCurso(id);
                } else {
                    sucesso = controlador.ativarCurso(id);
                }

                if (sucesso) {
                    System.out.println("✅ Curso " + (curso.isAtivação() ? "desativado" : "ativado") + " com sucesso!");
                } else {
                    System.out.println("❌ Erro ao alterar status do curso!");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro: " + e.getMessage());
            }
        }
    }

    private void excluirCurso() {
        System.out.println("\n❌ EXCLUIR CURSO");
        System.out.print("Digite o ID do curso a ser excluído: ");
        int id = lerInteiro();

        System.out.print("⚠️  Tem certeza que deseja excluir o curso? (s/n): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {
            try {
                if (controlador.excluirCurso(id)) {
                    System.out.println("✅ Curso excluído com sucesso!");
                } else {
                    System.out.println("❌ Erro ao excluir curso!");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro: " + e.getMessage());
            }
        }
    }

    private void listarCursosAtivos() {
        System.out.println("\n✅ CURSOS ATIVOS");
        List<Cursos> cursos = controlador.listarCursosAtivos();

        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso ativo no momento.");
        } else {
            for (Cursos curso : cursos) {
                System.out.println("-----------------------------------");
                System.out.println(curso.toString());
            }
        }
    }

    private void buscarCursosPorTipo() {
        System.out.println("\n🔎 BUSCAR CURSOS POR TIPO");
        System.out.print("Digite o tipo: ");
        String tipo = scanner.nextLine();

        List<Cursos> cursos = controlador.buscarCursosPorTipo(tipo);

        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso encontrado com o tipo: " + tipo);
        } else {
            System.out.println("Cursos encontrados:");
            for (Cursos curso : cursos) {
                System.out.println("-----------------------------------");
                System.out.println(curso.toString());
            }
        }
    }

    // ========== MENU ALUNOS ==========
    private void menuAlunos() {
        int opcao;
        do {
            System.out.println("\n👨‍🎓 MENU ALUNOS");
            System.out.println("==================");
            System.out.println("1. ➕ Cadastrar Aluno (com email)");
            System.out.println("2. ➕ Cadastrar Aluno (email existente)");
            System.out.println("3. 📋 Listar Todos os Alunos");
            System.out.println("4. 🔍 Buscar Aluno por ID");
            System.out.println("5. 🔍 Buscar Aluno por CPF");
            System.out.println("6. 🔎 Buscar Alunos por Nome");
            System.out.println("7. 📝 Atualizar Aluno");
            System.out.println("8. ❌ Excluir Aluno");
            System.out.println("0. ↩️ Voltar");
            System.out.println("==================");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> cadastrarAlunoCompleto();
                case 2 -> cadastrarAlunoComEmailExistente();
                case 3 -> listarTodosAlunos();
                case 4 -> buscarAlunoPorId();
                case 5 -> buscarAlunoPorCpf();
                case 6 -> buscarAlunosPorNome();
                case 7 -> atualizarAluno();
                case 8 -> excluirAluno();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarAlunoCompleto() {
        System.out.println("\n➕ CADASTRAR ALUNO COMPLETO");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        try {
            Aluno aluno = controlador.cadastrarAlunoCompleto(nome, telefone, email, cpf);
            System.out.println("✅ Aluno cadastrado com sucesso! ID: " + aluno.getIdAlunos());
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    private void cadastrarAlunoComEmailExistente() {
        System.out.println("\n➕ CADASTRAR ALUNO COM EMAIL EXISTENTE");

        // Listar emails disponíveis
        List<Email> emails = controlador.listarTodosEmails();
        if (emails.isEmpty()) {
            System.out.println("❌ Nenhum email cadastrado. Cadastre um email primeiro.");
            return;
        }

        System.out.println("Emails disponíveis:");
        for (Email email : emails) {
            System.out.println("ID: " + email.getIdEmail() + " - " + email.getEmail());
        }

        System.out.print("ID do email: ");
        int idEmail = lerInteiro();

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        try {
            Aluno aluno = controlador.cadastrarAluno(nome, telefone, idEmail, cpf);
            System.out.println("✅ Aluno cadastrado com sucesso! ID: " + aluno.getIdAlunos());
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    private void listarTodosAlunos() {
        System.out.println("\n📋 LISTA DE TODOS OS ALUNOS");
        List<Aluno> alunos = controlador.listarTodosAlunos();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            for (Aluno aluno : alunos) {
                System.out.println("-----------------------------------");
                System.out.println(aluno.toString());
            }
        }
    }

    private void buscarAlunoPorId() {
        System.out.println("\n🔍 BUSCAR ALUNO POR ID");
        System.out.print("Digite o ID do aluno: ");
        int id = lerInteiro();

        Aluno aluno = controlador.buscarAlunoPorId(id);
        if (aluno != null) {
            System.out.println("✅ Aluno encontrado:");
            System.out.println(aluno.toString());
        } else {
            System.out.println("❌ Aluno não encontrado!");
        }
    }

    private void buscarAlunoPorCpf() {
        System.out.println("\n🔍 BUSCAR ALUNO POR CPF");
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        Aluno aluno = controlador.buscarAlunoPorCpf(cpf);
        if (aluno != null) {
            System.out.println("✅ Aluno encontrado:");
            System.out.println(aluno.toString());
        } else {
            System.out.println("❌ Aluno não encontrado!");
        }
    }

    private void buscarAlunosPorNome() {
        System.out.println("\n🔎 BUSCAR ALUNOS POR NOME");
        System.out.print("Digite o nome (ou parte): ");
        String nome = scanner.nextLine();

        List<Aluno> alunos = controlador.buscarAlunosPorNome(nome);

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno encontrado com o nome: " + nome);
        } else {
            System.out.println("Alunos encontrados:");
            for (Aluno aluno : alunos) {
                System.out.println("-----------------------------------");
                System.out.println(aluno.toString());
            }
        }
    }

    private void atualizarAluno() {
        System.out.println("\n📝 ATUALIZAR ALUNO");
        System.out.print("Digite o ID do aluno a ser atualizado: ");
        int id = lerInteiro();

        Aluno aluno = controlador.buscarAlunoPorId(id);
        if (aluno == null) {
            System.out.println("❌ Aluno não encontrado!");
            return;
        }

        System.out.println("Aluno atual: " + aluno.toString());
        System.out.print("Novo nome (enter para manter atual): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.trim().isEmpty()) {
            aluno.setNome(novoNome);
        }

        System.out.print("Novo telefone (enter para manter atual): ");
        String novoTelefone = scanner.nextLine();
        if (!novoTelefone.trim().isEmpty()) {
            aluno.setTelefone(novoTelefone);
        }

        System.out.print("Novo CPF (enter para manter atual): ");
        String novoCpf = scanner.nextLine();
        if (!novoCpf.trim().isEmpty()) {
            aluno.setCpf(novoCpf);
        }

        try {
            if (controlador.atualizarAluno(aluno)) {
                System.out.println("✅ Aluno atualizado com sucesso!");
            } else {
                System.out.println("❌ Erro ao atualizar aluno!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void excluirAluno() {
        System.out.println("\n❌ EXCLUIR ALUNO");
        System.out.print("Digite o ID do aluno a ser excluído: ");
        int id = lerInteiro();

        System.out.print("⚠️  Tem certeza que deseja excluir o aluno? (s/n): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {
            try {
                if (controlador.excluirAluno(id)) {
                    System.out.println("✅ Aluno excluído com sucesso!");
                } else {
                    System.out.println("❌ Erro ao excluir aluno!");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro: " + e.getMessage());
            }
        }
    }

    // ========== MENU EMAILS ==========
    private void menuEmails() {
        int opcao;
        do {
            System.out.println("\n📧 MENU EMAILS");
            System.out.println("==================");
            System.out.println("1. ➕ Cadastrar Email");
            System.out.println("2. 📋 Listar Todos os Emails");
            System.out.println("3. 🔍 Buscar Email por ID");
            System.out.println("4. 🔍 Buscar Email por Endereço");
            System.out.println("5. 📝 Atualizar Email");
            System.out.println("6. ❌ Excluir Email");
            System.out.println("0. ↩️ Voltar");
            System.out.println("==================");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> cadastrarEmail();
                case 2 -> listarTodosEmails();
                case 3 -> buscarEmailPorId();
                case 4 -> buscarEmailPorEndereco();
                case 5 -> atualizarEmail();
                case 6 -> excluirEmail();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarEmail() {
        System.out.println("\n➕ CADASTRAR EMAIL");
        System.out.print("Endereço de email: ");
        String endereco = scanner.nextLine();

        try {
            Email email = controlador.cadastrarEmail(endereco);
            System.out.println("✅ Email cadastrado com sucesso! ID: " + email.getIdEmail());
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar email: " + e.getMessage());
        }
    }

    private void listarTodosEmails() {
        System.out.println("\n📋 LISTA DE TODOS OS EMAILS");
        List<Email> emails = controlador.listarTodosEmails();

        if (emails.isEmpty()) {
            System.out.println("Nenhum email cadastrado.");
        } else {
            for (Email email : emails) {
                System.out.println("ID: " + email.getIdEmail() + " - " + email.getEmail());
            }
        }
    }

    private void buscarEmailPorId() {
        System.out.println("\n🔍 BUSCAR EMAIL POR ID");
        System.out.print("Digite o ID do email: ");
        int id = lerInteiro();

        Email email = controlador.buscarEmailPorId(id);
        if (email != null) {
            System.out.println("✅ Email encontrado:");
            System.out.println("ID: " + email.getIdEmail() + " - " + email.getEmail());
        } else {
            System.out.println("❌ Email não encontrado!");
        }
    }

    private void buscarEmailPorEndereco() {
        System.out.println("\n🔍 BUSCAR EMAIL POR ENDEREÇO");
        System.out.print("Digite o endereço de email: ");
        String endereco = scanner.nextLine();

        Email email = controlador.buscarEmailPorEndereco(endereco);
        if (email != null) {
            System.out.println("✅ Email encontrado:");
            System.out.println("ID: " + email.getIdEmail() + " - " + email.getEmail());
        } else {
            System.out.println("❌ Email não encontrado!");
        }
    }

    private void atualizarEmail() {
        System.out.println("\n📝 ATUALIZAR EMAIL");
        System.out.print("Digite o ID do email a ser atualizado: ");
        int id = lerInteiro();

        Email email = controlador.buscarEmailPorId(id);
        if (email == null) {
            System.out.println("❌ Email não encontrado!");
            return;
        }

        System.out.println("Email atual: " + email.getEmail());
        System.out.print("Novo endereço de email: ");
        String novoEndereco = scanner.nextLine();

        if (!novoEndereco.trim().isEmpty()) {
            email.setEmail(novoEndereco);

            try {
                if (controlador.atualizarEmail(email)) {
                    System.out.println("✅ Email atualizado com sucesso!");
                } else {
                    System.out.println("❌ Erro ao atualizar email!");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro: " + e.getMessage());
            }
        }
    }

    private void excluirEmail() {
        System.out.println("\n❌ EXCLUIR EMAIL");
        System.out.print("Digite o ID do email a ser excluído: ");
        int id = lerInteiro();

        System.out.print("⚠️  Tem certeza que deseja excluir o email? (s/n): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {
            try {
                if (controlador.excluirEmail(id)) {
                    System.out.println("✅ Email excluído com sucesso!");
                } else {
                    System.out.println("❌ Erro ao excluir email!");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro: " + e.getMessage());
            }
        }
    }

    // ========== MENU MATRÍCULAS ==========
    private void menuMatriculas() {
        int opcao;
        do {
            System.out.println("\n📋 MENU MATRÍCULAS");
            System.out.println("==================");
            System.out.println("1. ➕ Nova Matrícula");
            System.out.println("2. 📋 Listar Todas as Matrículas");
            System.out.println("3. 🔍 Buscar Matrícula por ID");
            System.out.println("4. 👨‍🎓 Listar Matrículas por Aluno");
            System.out.println("5. 📚 Listar Matrículas por Curso");
            System.out.println("6. 🗑️  Cancelar Matrícula");
            System.out.println("7. 🔢 Contar Matrículas por Curso");
            System.out.println("8. 🔢 Contar Matrículas por Aluno");
            System.out.println("0. ↩️ Voltar");
            System.out.println("==================");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> novaMatricula();
                case 2 -> listarTodasMatriculas();
                case 3 -> buscarMatriculaPorId();
                case 4 -> listarMatriculasPorAluno();
                case 5 -> listarMatriculasPorCurso();
                case 6 -> cancelarMatricula();
                case 7 -> contarMatriculasPorCurso();
                case 8 -> contarMatriculasPorAluno();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void novaMatricula() {
        System.out.println("\n➕ NOVA MATRÍCULA");

        // Listar alunos
        List<Aluno> alunos = controlador.listarTodosAlunos();
        if (alunos.isEmpty()) {
            System.out.println("❌ Nenhum aluno cadastrado. Cadastre um aluno primeiro.");
            return;
        }

        System.out.println("Alunos disponíveis:");
        for (Aluno aluno : alunos) {
            System.out.println("ID: " + aluno.getIdAlunos() + " - " + aluno.getNome());
        }

        System.out.print("ID do aluno: ");
        int idAluno = lerInteiro();

        // Listar cursos ativos
        List<Cursos> cursos = controlador.listarCursosAtivos();
        if (cursos.isEmpty()) {
            System.out.println("❌ Nenhum curso ativo disponível.");
            return;
        }

        System.out.println("Cursos ativos disponíveis:");
        for (Cursos curso : cursos) {
            System.out.println("ID: " + curso.getIdCursos() + " - " + curso.getNome() + " (" + curso.getTipo() + ")");
        }

        System.out.print("ID do curso: ");
        int idCurso = lerInteiro();

        try {
            Matricula matricula = controlador.matricularAluno(idAluno, idCurso);
            System.out.println("✅ Matrícula realizada com sucesso! ID: " + matricula.getIdMatricula());
        } catch (Exception e) {
            System.out.println("❌ Erro ao realizar matrícula: " + e.getMessage());
        }
    }

    private void listarTodasMatriculas() {
        System.out.println("\n📋 LISTA DE TODAS AS MATRÍCULAS");
        List<Matricula> matriculas = controlador.listarTodasMatriculas();

        if (matriculas.isEmpty()) {
            System.out.println("Nenhuma matrícula cadastrada.");
        } else {
            for (Matricula matricula : matriculas) {
                System.out.println("-----------------------------------");
                System.out.println(matricula.toString());
            }
        }
    }

    private void buscarMatriculaPorId() {
        System.out.println("\n🔍 BUSCAR MATRÍCULA POR ID");
        System.out.print("Digite o ID da matrícula: ");
        int id = lerInteiro();

        Matricula matricula = controlador.buscarMatriculaPorId(id);
        if (matricula != null) {
            System.out.println("✅ Matrícula encontrada:");
            System.out.println(matricula.toString());
        } else {
            System.out.println("❌ Matrícula não encontrada!");
        }
    }

    private void listarMatriculasPorAluno() {
        System.out.println("\n👨‍🎓 MATRÍCULAS POR ALUNO");
        System.out.print("Digite o ID do aluno: ");
        int idAluno = lerInteiro();

        List<Matricula> matriculas = controlador.listarMatriculasPorAluno(idAluno);

        if (matriculas.isEmpty()) {
            System.out.println("Nenhuma matrícula encontrada para este aluno.");
        } else {
            System.out.println("Matrículas do aluno:");
            for (Matricula matricula : matriculas) {
                System.out.println("-----------------------------------");
                System.out.println(matricula.toString());
            }
        }
    }

    private void listarMatriculasPorCurso() {
        System.out.println("\n📚 MATRÍCULAS POR CURSO");
        System.out.print("Digite o ID do curso: ");
        int idCurso = lerInteiro();

        List<Matricula> matriculas = controlador.listarMatriculasPorCurso(idCurso);

        if (matriculas.isEmpty()) {
            System.out.println("Nenhuma matrícula encontrada para este curso.");
        } else {
            System.out.println("Matrículas do curso:");
            for (Matricula matricula : matriculas) {
                System.out.println("-----------------------------------");
                System.out.println(matricula.toString());
            }
        }
    }

    private void cancelarMatricula() {
        System.out.println("\n🗑️  CANCELAR MATRÍCULA");
        System.out.print("Digite o ID da matrícula a ser cancelada: ");
        int id = lerInteiro();

        System.out.print("⚠️  Tem certeza que deseja cancelar a matrícula? (s/n): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {
            try {
                if (controlador.cancelarMatricula(id)) {
                    System.out.println("✅ Matrícula cancelada com sucesso!");
                } else {
                    System.out.println("❌ Erro ao cancelar matrícula!");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro: " + e.getMessage());
            }
        }
    }

    private void contarMatriculasPorCurso() {
        System.out.println("\n🔢 CONTAR MATRÍCULAS POR CURSO");
        System.out.print("Digite o ID do curso: ");
        int idCurso = lerInteiro();

        int quantidade = controlador.contarMatriculasPorCurso(idCurso);
        System.out.println("📊 Total de matrículas no curso: " + quantidade);
    }

    private void contarMatriculasPorAluno() {
        System.out.println("\n🔢 CONTAR MATRÍCULAS POR ALUNO");
        System.out.print("Digite o ID do aluno: ");
        int idAluno = lerInteiro();

        int quantidade = controlador.contarMatriculasPorAluno(idAluno);
        System.out.println("📊 Total de matrículas do aluno: " + quantidade);
    }

    // ========== MENU RELATÓRIOS ==========
    private void menuRelatorios() {
        int opcao;
        do {
            System.out.println("\n📊 RELATÓRIOS");
            System.out.println("==================");
            System.out.println("1. 📈 Relatório Geral");
            System.out.println("2. 📋 Relatório Completo de Cursos");
            System.out.println("3. 👨‍🎓 Relatório Completo de Alunos");
            System.out.println("4. 📧 Relatório de Emails");
            System.out.println("5. 📋 Relatório de Matrículas");
            System.out.println("0. ↩️ Voltar");
            System.out.println("==================");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> relatorioGeral();
                case 2 -> relatorioCursosCompleto();
                case 3 -> relatorioAlunosCompleto();
                case 4 -> relatorioEmails();
                case 5 -> relatorioMatriculas();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void relatorioGeral() {
        System.out.println("\n📈 RELATÓRIO GERAL DA PLATAFORMA");
        System.out.println("=====================================");
        System.out.println("Total de Alunos: " + controlador.listarTodosAlunos().size());
        System.out.println("Total de Cursos: " + controlador.listarTodosCursos().size());
        System.out.println("Total de Cursos Ativos: " + controlador.listarCursosAtivos().size());
        System.out.println("Total de Cursos Inativos: " + controlador.listarCursosInativos().size());
        System.out.println("Total de Matrículas: " + controlador.listarTodasMatriculas().size());
        System.out.println("Total de Emails: " + controlador.listarTodosEmails().size());
        System.out.println("=====================================");
    }

    private void relatorioCursosCompleto() {
        System.out.println("\n📋 RELATÓRIO COMPLETO DE CURSOS");
        List<Cursos> cursos = controlador.listarTodosCursos();

        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
        } else {
            for (Cursos curso : cursos) {
                System.out.println("-----------------------------------");
                System.out.println(curso.toString());
                int matriculas = controlador.contarMatriculasPorCurso(curso.getIdCursos());
                System.out.println("Matrículas ativas: " + matriculas);
            }
        }
    }

    private void relatorioAlunosCompleto() {
        System.out.println("\n👨‍🎓 RELATÓRIO COMPLETO DE ALUNOS");
        List<Aluno> alunos = controlador.listarTodosAlunos();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            for (Aluno aluno : alunos) {
                System.out.println("-----------------------------------");
                System.out.println(aluno.toString());
                int matriculas = controlador.contarMatriculasPorAluno(aluno.getIdAlunos());
                System.out.println("Total de matrículas: " + matriculas);
            }
        }
    }

    private void relatorioEmails() {
        System.out.println("\n📧 RELATÓRIO DE EMAILS");
        List<Email> emails = controlador.listarTodosEmails();

        if (emails.isEmpty()) {
            System.out.println("Nenhum email cadastrado.");
        } else {
            for (Email email : emails) {
                System.out.println("ID: " + email.getIdEmail() + " - " + email.getEmail());
            }
        }
    }

    private void relatorioMatriculas() {
        System.out.println("\n📋 RELATÓRIO DE MATRÍCULAS");
        List<Matricula> matriculas = controlador.listarTodasMatriculas();

        if (matriculas.isEmpty()) {
            System.out.println("Nenhuma matrícula cadastrada.");
        } else {
            for (Matricula matricula : matriculas) {
                System.out.println("-----------------------------------");
                System.out.println(matricula.toString());

                // Buscar informações adicionais
                Aluno aluno = controlador.buscarAlunoPorId(matricula.getAluno());
                Cursos curso = controlador.buscarCursoPorId(matricula.getCurso());

                if (aluno != null && curso != null) {
                    System.out.println("Aluno: " + aluno.getNome());
                    System.out.println("Curso: " + curso.getNome());
                }
            }
        }
    }

    // ========== MÉTODO AUXILIAR ==========
    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("❌ Por favor, digite um número válido: ");
            }
        }
    }
}