package main;

import controller.ControllerPrincipal;
import view.SistemaEscola;

public class Main {
    public static void main(String[] args) {
        try {
            // Acessa o controller principal
            ControllerPrincipal controller = new ControllerPrincipal();

            // Cria a view e inicia o sistema
            SistemaEscola sistema = new SistemaEscola(controller);
            sistema.iniciarSistema();

        } catch (Exception e) {
            System.err.println("Erro ao iniciar o sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
