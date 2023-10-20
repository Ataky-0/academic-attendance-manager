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
        System.out.printf("\nSe conectando ao banco de dados..\n\n");
        Database.getConnection();
        while (true) {
            String[] opcoesMenu = {
                    "Disciplinas | Gerenciar Disciplinas",
                    "Relatorio Geral | Consultar relatório",
                    "Sair"
            };
            drawMenu("Menu", opcoesMenu);
            int escolha = getUserChoice(scanner, 1, opcoesMenu.length);
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

    public static int getUserChoice(Scanner scanner,int minOpcoes, int numOpcoes) { // Obtém escolha do usuário com guard clauses
        while (true){
            System.out.print("-> ");
            int escolha = scanner.nextInt();
            scanner.nextLine();
            if (escolha >= minOpcoes && escolha <= numOpcoes) {
                return escolha;
            } else {
                System.out.println("Valor inválido.");
            }
        }
    }

    // --> Disciplina
    static void DisciplinaChooser() throws SQLException { // Responsável pela escolha de disciplinas
        System.out.printf("<-\t | Disciplinas | \t->\n");
        Boolean existeDisciplina = Disciplina.existeDisciplinas();
        if (existeDisciplina) {
            Disciplina.printParcialDisciplinas();
            System.out.println("(0 para cancelar, 1 para criar, 2 para deletar)");
            System.out.println("Ou escolha uma disciplina através do seu código:");
            while (true) {
                System.out.print("-> ");
                String escolha = scanner.nextLine(); // Seria interessante adicionar guard clauses
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
                if (disciplina.next()) {
                    System.out.printf("Você selecionou a disciplina %s.\n", disciplina.getString("nome"));
                    disciplinaManagement(disciplina.getString("codigo"));
                    disciplina.close();
                    return;
                } else {
                    System.out.println("Valor passado é inválido.");
                }
            }
        } else {
            System.out.println("Não existem disciplinas registradas.");
            System.out.println("Você irá agora criar uma nova disciplina.");
            createDisciplina();
        }
    }

    static void createDisciplina() throws SQLException { // Cria uma disciplina
        System.out.printf("Digite o nome da disciplina:\n-> ");
        String nome = scanner.nextLine();
        System.out.printf("Digite o código da disciplina:\n-> ");
        String codigo = scanner.nextLine();
        System.out.println("Verifique a carga horária de cada disciplina no seu sistema acadêmico.");
        System.out.printf("Digite a carga horária da disciplina (horas):\n-> ");
        int cargaHoraria = scanner.nextInt();
        scanner.nextLine();
        if (Disciplina.getDisciplinaByCode(codigo).next()){
            System.out.println("Disciplina com este código já existe.");
        } else {
            Disciplina.Create(nome, codigo, cargaHoraria);
        }
        DisciplinaChooser();
    }

    static void deleteDisciplina() throws SQLException { // Deleta uma disciplina
        System.out.printf("Digite o código da disciplina:\n-> ");
        String codigo = scanner.nextLine();
        if ( Disciplina.getDisciplinaByCode(codigo).next() ){
            Disciplina.Delete(codigo);
        } else {
            System.out.println("Disciplina inválida.");
        }
        DisciplinaChooser();
    }

    static void disciplinaManagement(String codigo) throws SQLException{ // Sub menu de DisciplinaChooser
        String[] opcoesMenu = {
            "Frequencia | Registrar faltas",
            "Notas | Gerenciar notas",
            "Voltar"
        };
        drawMenu("Disciplina", opcoesMenu);
        int escolha = getUserChoice(scanner, 1 , 3);
        switch (escolha) {
            case 1:
                frequenciaManagement(codigo);
                break;
            case 2:
                notasManagement(codigo);
                break;
            case 3:
                DisciplinaChooser();
                break;
        }
    }

    // --> Frequencia
    static void frequenciaManagement(String codigoDisciplina) throws SQLException { // Menu para frequencia
        drawMenu("Frequências", null);
        Frequencia.printParcialFrequencias(codigoDisciplina);
        System.out.printf("\n(0 para cancelar, 1 para registrar, 2 para deletar, 3 para autoavaliação)\n");
        int escolha = getUserChoice(scanner, 0, 3);
        switch (escolha) {
            case 1:
                registerFrequencia(codigoDisciplina);
                break;
            case 2:
                deleteFrequencia(codigoDisciplina);
                break;
            case 3:
                avaliarFrequencia(codigoDisciplina);
                break;
            case 0:
                disciplinaManagement(codigoDisciplina);
                break;
        }
    }

    static void registerFrequencia(String codigoDisciplina) throws SQLException{ // Registrar uma frequencia em uma disciplina
        System.out.println("Digite uma data (yyyy/MM/dd):");
        Date data = getDate(scanner);
        // Verificar se não existe frequência com essa data
        if (Frequencia.getId(codigoDisciplina, data)!=-1){
            System.out.printf("\nFrequência já existe.\n");
        } else {
            System.out.println("Você estava presente? (1 para Sim, 2 para Não)");
            int presencaAusencia = getUserChoice(scanner,1, 2);
            presencaAusencia = (presencaAusencia==2) ? 0: 1;
            if (presencaAusencia == 0){
                Frequencia.Create(data, presencaAusencia, codigoDisciplina, 2);
            } else {
                System.out.printf("Quantas aulas assistiu? (1 ou 2)");
                int faltas = 2-getUserChoice(scanner, 1, 2);
                Frequencia.Create(data, presencaAusencia, codigoDisciplina, faltas);
            }
        }
        frequenciaManagement(codigoDisciplina);
    }

    static void deleteFrequencia(String codigoDisciplina) throws SQLException { // Deletar uma frequencia de uma disciplina
        System.out.println("Digite a data da frequência (yyyy/MM/dd):");
        Date data = getDate(scanner);
        if (Frequencia.getId(codigoDisciplina, data)==-1)
            System.out.printf("\nFrequência não existe.\n");
        else
            Frequencia.Delete(codigoDisciplina,data);
        frequenciaManagement(codigoDisciplina);
    }

    static void avaliarFrequencia(String codigoDisciplina) throws SQLException{ // Linkar Autoavaliacao à frequencia
        System.out.println("Digite a data da frequência (yyyy/MM/dd):");
        Date data = getDate(scanner);
        int frequencia_id = Frequencia.getId(codigoDisciplina, data);
        if (frequencia_id == -1)
            System.out.printf("\nFrequência não existe. Crie-a primeiro!\n");
        else {
            if (Autoavaliacao.existByFrequencia(frequencia_id)){
                System.out.println("\""+Autoavaliacao.getComentario(frequencia_id)+"\"");
                System.out.println("Deseja refazer este comentário? (1 para Sim, 2 para Não)");
                int escolha = getUserChoice(scanner, 1, 2);
                if (escolha == 1){
                    System.out.printf("Refaça o comentário deste dia:\n-> ");
                    String comentario = scanner.nextLine();
                    Autoavaliacao.Update(frequencia_id, comentario);
                }
                frequenciaManagement(codigoDisciplina);
                return;
            }
            System.out.printf("Digite um comentário para este dia:\n-> ");
            String comentario = scanner.nextLine();
            Autoavaliacao.Create(frequencia_id,comentario);
        }
        frequenciaManagement(codigoDisciplina);
    }

    public static Date getDate(Scanner scanner){ // Obtém uma data com guard clauses
        Date data;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
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
    // --> Notas
    static void notasManagement(String codigo) throws SQLException{
        drawMenu("Notas", null);
        Notas.printTotal(codigo);
        System.out.printf("\n(0 para cancelar, 1 para editar)\n-> ");
        int escolha = scanner.nextInt();
        switch (escolha) {
            case 1:
                editarNotas(codigo);
                break;
            case 0:
                disciplinaManagement(codigo);
                break;
        }
    }

    static void editarNotas(String codigo) throws SQLException{
        float MP = Notas.getMediaParcial(codigo);
        int unidades = (MP>=3.5f && MP <7f && !Notas.anyZero(codigo)) ? 4: 3;
        System.out.printf("Qual nota você deseja editar? (Escolha entre 1 e %d):\n",unidades);
        int notaPos = getUserChoice(scanner, 1, unidades);
        System.out.println("Agora dite a nova nota para esta unidade: ");
        float nota = getUserFloat(scanner, 0.0f, 10.0f);
        Notas.Update(codigo, notaPos, nota);
        notasManagement(codigo);
    }

    static float getUserFloat(Scanner scanner, float min, float max){
        String input = "0f";
        while (true){
            System.out.printf("-> ");
            input = scanner.next();
            input = input.replace(",", ".");
            if (Float.parseFloat(input) < min || Float.parseFloat(input) > max)
                System.out.printf("Digite um valor entre %.2f e %.2f\n",min,max);
            else break;
        }
        return Float.parseFloat(input);
    }

    // --> RelatorioGeral
    static void DisplayRelatorioGeral() { // Apenas para organizar, só chama um método do  RelatorioGeral
        RelatorioGeral.printDisciplinasFrequencias();
    }

}