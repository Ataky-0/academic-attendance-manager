package org.academic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Menu {

    static Scanner scanner = new Scanner(System.in);

    public static void init() {
        while (true) {
            String[] opcoesMenu = {
                    "Disciplinas | Gerenciar Disciplinas",
                    "Relatorio Geral | Consultar relatório",
                    "Sair"
            };
            drawMenu("Menu", opcoesMenu);
            int escolha = getUserChoice(scanner, opcoesMenu.length);

            switch (escolha) {
                case 1:
                    DisciplinaChooser();
                    break;
                case 2:
                    DisplayRelatorioGeral();
                    break;
                case 3:
                    System.out.println("Saindo do programa.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void drawMenu(String title, String[] opcoesMenu) {
        if (title != null) {
            System.out.printf("<-\t | %s | \t->\n", title);
        }
        if (opcoesMenu != null) {
            for (int i = 0; i < opcoesMenu.length; i++)
                System.out.println((i + 1) + ". " + opcoesMenu[i]);
            System.out.print("Escolha uma opção: ");
        }
    }

    private static int getUserChoice(Scanner scanner, int numOpcoes) {
        int escolha = scanner.nextInt();
        scanner.nextLine();
        System.out.printf("<-\t##\t->\n");

        if (escolha >= 1 && escolha <= numOpcoes) {
            return escolha;
        } else {
            return -1;
        }
    }

    static void DisplayRelatorioGeral() {
        RelatorioGeral.printDisciplinasFrequencias();
    }

    static void DisciplinaChooser() {
        System.out.printf("<-\t | Disciplinas | \t->\n");
        Boolean existeDisciplina = Disciplina.existeDisciplinas();
        if (existeDisciplina) {
            Disciplina.printParcialDisciplinas();
            System.out.println("(0 para cancelar, 1 para criar, 2 para deletar)");
            System.out.println("Escolha uma disciplina através do seu código: ");
            String escolha = scanner.nextLine(); // Adicionar classguard para limitar as opções.
            if (escolha.equals("0")) {
                return;
            } else if (escolha.equals("1")) {
                createDisciplina();
                return;
            } else if (escolha.equals("2")) {
                deleteDisciplina();
                return;
            }
            ResultSet disciplina = Disciplina.getDisciplinaByCode(escolha);
            try {
                if (disciplina.next()) {
                    try {
                        System.out.printf("Você selecionou a disciplina %s.\n", disciplina.getString("nome"));
                        frequenciaManagement(disciplina.getString("codigo"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Não existem disciplinas registradas.");
            System.out.println("Você irá agora criar uma nova disciplina.");
            createDisciplina();
        }
    }

    static void frequenciaManagement(String codigoDisciplina) { // Frequência
        drawMenu("Frequências", null);
        Frequencia.printParcialFrequencias(codigoDisciplina);
        System.out.println("(0 para cancelar, 1 para registrar, 2 para deletar, 3 para autoavaliação)");
        int escolha = scanner.nextInt();
        switch (escolha) {
            case 3:
                avaliarFrequencia(codigoDisciplina);
                break;
            case 2:
                deleteFrequencia(codigoDisciplina);
                break;
            case 1:
                registerFrequencia(codigoDisciplina);
                break;
            case 0:
                break;
        }
    }

    static void registerFrequencia(String codigoDiscplina){
        System.out.println("Digite uma data (yyyy/MM/dd): ");
        Date data = getDate();
        // Verificar se não existe frequência com essa data
        System.out.println("Você estava presente? (1 para Não, 2 para Sim)");
        int presencaAusencia = getUserChoice(scanner, 2);
        presencaAusencia = (presencaAusencia==1) ? 0: 1;
        if (presencaAusencia == 0){
            Frequencia.Create(data, presencaAusencia, codigoDiscplina, 2);
        } else {
            System.out.println("Quantas aulas assistiu? (1 ou 2)");
            int faltas = 2-getUserChoice(scanner, 2);
            Frequencia.Create(data, presencaAusencia, codigoDiscplina, faltas);
        }
    }

    static void deleteFrequencia(String codigoDisciplina) {
        System.out.println("Digite a data da frequência (yyyy/MM/dd): ");
        Date data = getDate();
        Frequencia.Delete(codigoDisciplina,data);
    }

    static void avaliarFrequencia(String codigoDisciplina){
        System.out.println("Digite a data da frequência (yyyy/MM/dd): ");
        Date data = getDate();
        System.out.println("Dite uma nota para este dia de aula (1 a 5): ");
        int autoNota = Menu.getUserChoice(scanner,5);
        
    }

    static void createDisciplina() {
        System.out.println("Digite o nome da disciplina: ");
        String nome = scanner.nextLine();
        System.out.println("Digite o código da disciplina: ");
        String codigo = scanner.nextLine();
        System.out.println("Verifique a carga horária de cada disciplina no seu sistema acadêmico.");
        System.out.println("Digite a carga horária da disciplina (horas): ");
        int cargaHoraria = scanner.nextInt();
        Disciplina.Create(nome, codigo, cargaHoraria);
    }

    static void deleteDisciplina() {
        System.out.println("Digite o código da disciplina: ");
        String codigo = scanner.nextLine();
        Disciplina.Delete(codigo);
    }

    private static Date getDate(){
        Date data;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        scanner.nextLine();
        while (true) {
            String userInput = scanner.nextLine();
            try {
                data = dateFormat.parse(userInput);
                if (isDateValid(userInput, dateFormat)) {
                    break;
                } else {
                    System.err.println("Data inválida. Por favor, insira uma data válida.");
                }
            } catch (ParseException e) {
                System.err.println("Formato de data inválido. Certifique-se de usar o formato yyyy/MM/dd.");
            }
        }
        return data;
    }

    private static boolean isDateValid(String dateStr, SimpleDateFormat dateFormat) {
        dateFormat.setLenient(false); // Desativa a tolerância a datas inválidas
        try {
            dateFormat.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
