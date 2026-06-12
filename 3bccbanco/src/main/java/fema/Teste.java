package fema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Scanner;

public class Teste {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection cnn = null;

        try {
            cnn = DriverManager.getConnection("jdbc:h2:mem:banco;DB_CLOSE_DELAY=-1", "sa", "");
            inicializarBanco(cnn);

            TimeDao timeDao = new TimeDao(cnn);
            JogadorDao jogadorDao = new JogadorDao(cnn);
            PremioDao premioDao = new PremioDao(cnn); // Ativado!

            int opcao = -1;

            while (opcao != 0) {
                System.out.println("\n========================================");
                System.out.println("      SISTEMA DE GESTÃO ESPORTIVA       ");
                System.out.println("========================================");
                System.out.println("1 - Cadastrar Novo Time");
                System.out.println("2 - Listar Todos os Times");
                System.out.println("3 - Cadastrar Novo Jogador");
                System.out.println("4 - Listar Todos os Jogadores");
                System.out.println("5 - Cadastrar Novo Prêmio");
                System.out.println("6 - Listar Todos os Prêmios");
                System.out.println("0 - Sair do Sistema");
                System.out.print("Escolha uma opção: ");
                
                opcao = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcao) {
                    case 1:
                        System.out.print("\nDigite o ID do Time: ");
                        int idTime = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Digite o Nome do Time: ");
                        String nomeTime = scanner.nextLine();
                        System.out.print("Digite a Cidade do Time: ");
                        String cidadeTime = scanner.nextLine();

                        Time t = new Time();
                        t.setId(idTime);
                        t.setNome(nomeTime);
                        t.setCidade(cidadeTime);
                        timeDao.inserir(t);
                        System.out.println("👉 Time cadastrado com sucesso!");
                        break;

                    case 2:
                        System.out.println("\n--- LISTA DE TIMES ---");
                        List<Time> times = timeDao.recuperar();
                        if (times.isEmpty()) {
                            System.out.println("Nenhum time cadastrado.");
                        } else {
                            for (Time tm : times) {
                                System.out.println("ID: " + tm.getId() + " | Nome: " + tm.getNome() + " | Cidade: " + tm.getCidade());
                            }
                        }
                        break;

                    case 3:
                        System.out.print("\nDigite o ID do Jogador: ");
                        int idJog = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Digite o Nome do Jogador: ");
                        String nomeJog = scanner.nextLine();
                        System.out.print("Digite a Posição: ");
                        String posJog = scanner.nextLine();
                        System.out.print("Digite o ID do Time deste jogador: ");
                        int idTimeFK = scanner.nextInt();

                        Jogador j = new Jogador();
                        j.setId(idJog);
                        j.setNome(nomeJog);
                        j.setPosicao(posJog);
                        j.setTimeId(idTimeFK);
                        jogadorDao.inserir(j);
                        System.out.println("👉 Jogador cadastrado com sucesso!");
                        break;

                    case 4:
                        System.out.println("\n--- LISTA DE JOGADORES ---");
                        List<Jogador> jogadores = jogadorDao.recuperar();
                        if (jogadores.isEmpty()) {
                            System.out.println("Nenhum jogador cadastrado.");
                        } else {
                            for (Jogador jg : jogadores) {
                                System.out.println("ID: " + jg.getId() + " | Nome: " + jg.getNome() + " | Posição: " + jg.getPosicao() + " | ID Time: " + jg.getTimeId());
                            }
                        }
                        break;

                    case 5:
                        System.out.print("\nDigite o ID do Prêmio: ");
                        int idPremio = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Digite o Nome do Prêmio: ");
                        String nomePremio = scanner.nextLine();
                        System.out.print("Digite o Ano do Prêmio: ");
                        int anoPremio = scanner.nextInt();
                        System.out.print("Digite o ID do Jogador que ganhou este prêmio: ");
                        int idJogFK = scanner.nextInt();

                        Premio p = new Premio();
                        p.setId(idPremio);
                        p.setNome(nomePremio);
                        p.setAno(anoPremio);
                        p.setJogadorId(idJogFK);
                        premioDao.inserir(p);
                        System.out.println("👉 Prêmio cadastrado com sucesso!");
                        break;

                    case 6:
                        System.out.println("\n--- LISTA DE PRÊMIOS ---");
                        List<Premio> premios = premioDao.recuperar();
                        if (premios.isEmpty()) {
                            System.out.println("Nenhum prêmio cadastrado.");
                        } else {
                            for (Premio pr : premios) {
                                System.out.println("ID: " + pr.getId() + " | Nome: " + pr.getNome() + " | Ano: " + pr.getAno() + " | ID Jogador: " + pr.getJogadorId());
                            }
                        }
                        break;

                    case 0:
                        System.out.println("\nEncerrando o sistema... Até logo!");
                        break;

                    default:
                        System.out.println("⚠️ Opção inválida! Tente novamente.");
                }
            }

        } catch (Exception e) {
            System.out.println("⚠️ Ocorreu um erro no sistema:");
            e.printStackTrace();
        } finally {
            try {
                if (cnn != null) cnn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }

    private static void inicializarBanco(Connection cnn) throws Exception {
        String sqlTime = "CREATE TABLE IF NOT EXISTS TIME (ID INTEGER NOT NULL PRIMARY KEY, NOME VARCHAR(100), CIDADE VARCHAR(100));";
        String sqlJogador = "CREATE TABLE IF NOT EXISTS JOGADOR (ID INTEGER NOT NULL PRIMARY KEY, NOME VARCHAR(100), POSICAO VARCHAR(50), TIME_ID INTEGER NOT NULL, CONSTRAINT JOGADOR_TIME_FK FOREIGN KEY (TIME_ID) REFERENCES TIME (ID));";
        String sqlPremio = "CREATE TABLE IF NOT EXISTS PREMIO (ID INTEGER NOT NULL PRIMARY KEY, NOME VARCHAR(100), ANO INTEGER, JOGADOR_ID INTEGER NOT NULL, CONSTRAINT PREMIO_JOGADOR_FK FOREIGN KEY (JOGADOR_ID) REFERENCES JOGADOR (ID));";
        
        java.sql.Statement st = cnn.createStatement();
        st.execute(sqlTime);
        st.execute(sqlJogador);
        st.execute(sqlPremio);
        st.close();
    }
}
