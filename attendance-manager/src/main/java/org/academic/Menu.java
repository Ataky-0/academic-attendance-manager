package org.academic;

// import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Menu {

    static Scanner scanner = new Scanner(System.in);

    
    public static void init() throws SQLException { // Inicia menu principal
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

    private static void drawMenu(String title, String[] opcoesMenu) { // Desenha estrutura menu
        if (title != null) {
            System.out.printf("<-\t | %s | \t->\n", title);
        }
        if (opcoesMenu != null) {
            for (int i = 0; i < opcoesMenu.length; i++)
                System.out.println((i + 1) + ". " + opcoesMenu[i]);
            System.out.printf("Escolha uma opção:\n");
        }
    }

    private static int getUserChoice(Scanner scanner, int numOpcoes) { // Obtém escolha do usuário com guard clauses
        System.out.print("-> ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha >= 1 && escolha <= numOpcoes) {
            return escolha;
        } else {
            return -1;
        }
    }

    // --> Disciplina
    static void DisciplinaChooser() throws SQLException { // Responsável pela escolha de disciplinas
        System.out.printf("<-\t | Disciplinas | \t->\n");
        Boolean existeDisciplina = Disciplina.existeDisciplinas();
        if (existeDisciplina) {
            Disciplina.printParcialDisciplinas();
            System.out.println("(0 para cancelar, 1 para criar, 2 para deletar)");
            System.out.printf("Ou escolha uma disciplina através do seu código:\n-> ");
            String escolha = scanner.nextLine(); // Seria interessante adicionar .guard clauses
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
                        disciplina.close();
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

    static void createDisciplina() { // Cria uma disciplina
        System.out.printf("Digite o nome da disciplina:\n-> ");
        String nome = scanner.nextLine();
        System.out.printf("Digite o código da disciplina:\n-> ");
        String codigo = scanner.nextLine();
        System.out.println("Verifique a carga horária de cada disciplina no seu sistema acadêmico.");
        System.out.printf("Digite a carga horária da disciplina (horas):\n-> ");
        int cargaHoraria = scanner.nextInt();
        Disciplina.Create(nome, codigo, cargaHoraria);
    }

    static void deleteDisciplina() throws SQLException { // Deleta uma disciplina
        System.out.printf("Digite o código da disciplina:\n-> ");
        String codigo = scanner.nextLine();
        Disciplina.Delete(codigo);
    }

    // --> Frequencia
    static void frequenciaManagement(String codigoDisciplina) throws SQLException { // Menu para frequencia
        drawMenu("Frequências", null);
        Frequencia.printParcialFrequencias(codigoDisciplina);
        System.out.printf("(0 para cancelar, 1 para registrar, 2 para deletar, 3 para autoavaliação)\n-> ");
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

    static void registerFrequencia(String codigoDiscplina){ // Registrar uma frequencia em uma disciplina
        System.out.println("Digite uma data (yyyy/MM/dd):");
        Date data = getDate();
        // Verificar se não existe frequência com essa data
        System.out.println("Você estava presente? (1 para Sim, 2 para Não)");
        int presencaAusencia = getUserChoice(scanner, 2);
        presencaAusencia = (presencaAusencia==1) ? 0: 1;
        if (presencaAusencia == 1){
            Frequencia.Create(data, presencaAusencia, codigoDiscplina, 2);
        } else {
            System.out.printf("Quantas aulas assistiu? (1 ou 2)");
            int faltas = 2-getUserChoice(scanner, 2);
            Frequencia.Create(data, presencaAusencia, codigoDiscplina, faltas);
        }
    }

    static void deleteFrequencia(String codigoDisciplina) throws SQLException { // Deletar uma frequencia de uma disciplina
        System.out.println("Digite a data da frequência (yyyy/MM/dd):");
        Date data = getDate();
        Frequencia.Delete(codigoDisciplina,data);
    }

    static void avaliarFrequencia(String codigoDisciplina) throws SQLException{ // Linkar Autoavaliacao à frequencia
        System.out.println("Digite a data da frequência (yyyy/MM/dd):");
        Date data = getDate();
        int frequencia_id = Frequencia.getId(codigoDisciplina, data);
        if (Autoavaliacao.existByFrequencia(frequencia_id)){
            System.out.println("\""+Autoavaliacao.getComentario(frequencia_id)+"\"");
            System.out.println("Deseja refazer este comentário? (1 para Sim, 2 para Não)");
            int escolha = getUserChoice(scanner, 2);
            if (escolha == 2)
                return;
            else {
                System.out.printf("Refaça o comentário deste dia:\n-> ");
                String comentario = scanner.nextLine();
                Autoavaliacao.Update(frequencia_id, comentario);
                return;
            }
        }
        System.out.printf("Digite um comentário para este dia:\n-> ");
        String comentario = scanner.nextLine();
        Autoavaliacao.Create(frequencia_id,comentario);
        
    }
    // --> Notas


    // --> RelatorioGeral
    static void DisplayRelatorioGeral() { // Apenas para organizar, só chama um método do  RelatorioGeral
        RelatorioGeral.printDisciplinasFrequencias();
    }

    private static Date getDate(){ // Obtém uma data com guard clauses
        Date data;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        scanner.nextLine();
        while (true) {
            System.out.print("-> ");
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

    private static boolean isDateValid(String dateStr, SimpleDateFormat dateFormat) { // Subordinada do GetDate, serve exclusivamente para verificar se confere o formato
        dateFormat.setLenient(false); // Não tolera erros
        try {
            dateFormat.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}