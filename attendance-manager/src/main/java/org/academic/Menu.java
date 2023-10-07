package org.academic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    static Scanner scanner = new Scanner(System.in);

    public static void init() {
        System.out.printf("<-\t | Menu | \t->\n");
        while (true) {
            System.out.println("1. Aluno");
            System.out.println("2. Disciplinas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();
            System.out.printf("<-\t##\t->\n");

            switch (escolha) {
                case 1:
                    AlunoManagement();
                    break;
                case 2:
                    System.out.println("Você selecionou a Opção 2");
                    break;
                case 0:
                    System.out.println("Saindo do programa.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void AlunoManagement() {
        Connection Conexao = Database.conectar();

        System.out.printf("<-\t | Login | \t->\n");
        System.out.print("Usuário: ");
        String user = scanner.nextLine();
        System.out.print("Senha: ");
        String pass = scanner.nextLine();

        try (PreparedStatement consult = Conexao
                .prepareStatement("SELECT * FROM Aluno WHERE nome = ? AND senha = ?")) {
            consult.setString(1, user);
            consult.setString(2, pass);
            ResultSet result = consult.executeQuery();
            if (result.next()) {
                System.out.println("Autenticação bem-sucedida!");
            } else {
                System.out.println("Usuário não encontrado. Deseja registrar-se?");
                System.out.println("1. Sim");
                System.out.println("0. Não");
                int escolha = scanner.nextInt();
                // Implementar registro de aluno
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
