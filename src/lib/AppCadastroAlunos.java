package lib;


import app.Aluno;
import app.ComparadorAlunoPorMatricula;
import app.ComparadorAlunoPorNome;


import java.util.Comparator;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Aplicativo da Etapa 2 (Versão com remoção específica por nome/matrícula): Cadastro de Alunos.
 */
public class AppCadastroAlunos {

    public static void main(String[] args) {

        // --- 1. Inicialização e Escolha do Comparador ---

        Scanner scanner = new Scanner(System.in);

        // Criar as duas opções de comparadores
        ComparadorAlunoPorMatricula compMatricula = new ComparadorAlunoPorMatricula();
        ComparadorAlunoPorNome compNome = new ComparadorAlunoPorNome();

        Comparator<Aluno> comparadorPrincipal;
        String chavePrincipal;

        System.out.println("--- Sistema de Cadastro de Alunos (Árvore Binária) ---");
        System.out.println("Como você deseja ordenar a árvore de alunos?");
        System.out.println("  1: Por Matrícula (Padrão)");
        System.out.println("  2: Por Nome");
        System.out.print("Escolha a opção (1 ou 2): ");

        String escolha = scanner.nextLine().trim();

        if (escolha.equals("2")) {
            comparadorPrincipal = compNome;
            chavePrincipal = "Nome";
            System.out.println("Árvore será indexada por NOME.");
        } else {
            comparadorPrincipal = compMatricula;
            chavePrincipal = "Matrícula";
            System.out.println("Árvore será indexada por MATRÍCULA.");
        }

        // 2. Instanciar a Árvore Binária com o comparador escolhido
        IArvoreBinaria<Aluno> cadastroAlunos = new ArvoreBinariaExemplo<>(comparadorPrincipal);

        // 3. Iniciar o loop interativo
        imprimirAjuda();

        while (true) {
            System.out.print("\n> ");
            String linha = scanner.nextLine().trim();
            String[] partes = linha.split(" ");
            String comando = partes[0].toLowerCase();

            // Bloco try-catch para evitar que o programa quebre com entradas inválidas
            try {
                switch (comando) {
                    case "adicionar":
                        // (Este método sempre usa o comparador principal, então não muda)
                        if (partes.length < 3) {
                            System.out.println("Erro: Formato inválido. Use: adicionar <matricula> <nome completo>");
                            continue;
                        }
                        int matriculaAdd = Integer.parseInt(partes[1]);
                        String nomeAdd = String.join(" ", Arrays.copyOfRange(partes, 2, partes.length));

                        cadastroAlunos.adicionar(new Aluno(matriculaAdd, nomeAdd));
                        System.out.println("Aluno '" + nomeAdd + "' adicionado com sucesso.");
                        break;

                    // --- NOVO COMANDO: REMOVER_MATRICULA ---
                    case "remover_matricula":
                        if (partes.length != 2) {
                            System.out.println("Erro: Formato inválido. Use: remover_matricula <matricula>");
                            continue;
                        }
                        int matriculaRem = Integer.parseInt(partes[1]);
                        Aluno alunoBuscaMatRem = new Aluno(matriculaRem, "");
                        Aluno alunoRemovidoMat = null;

                        if (chavePrincipal.equals("Matrícula")) {
                            // Matrícula é a chave principal. Remoção direta e rápida.
                            System.out.println("(Removendo pela chave principal...)");
                            alunoRemovidoMat = cadastroAlunos.remover(alunoBuscaMatRem);
                        } else {
                            // Árvore indexada por Nome. Precisamos "Buscar por Matrícula" e depois "Remover por Nome".
                            System.out.println("(Removendo por chave secundária: buscando matrícula...)");
                            // 1. Pesquisa usando o comparador secundário
                            Aluno alunoEncontrado = cadastroAlunos.pesquisar(alunoBuscaMatRem, compMatricula);
                            if (alunoEncontrado != null) {
                                // 2. Remove usando o comparador principal (Nome)
                                System.out.println("(Aluno encontrado, removendo...)");
                                alunoRemovidoMat = cadastroAlunos.remover(alunoEncontrado);
                            }
                        }

                        if (alunoRemovidoMat != null) {
                            System.out.println("Aluno removido: " + alunoRemovidoMat.toString());
                        } else {
                            System.out.println("Erro: Aluno com matrícula " + matriculaRem + " não encontrado.");
                        }
                        break;

                    // --- NOVO COMANDO: REMOVER_NOME ---
                    case "remover_nome":
                        if (partes.length < 2) {
                            System.out.println("Erro: Formato inválido. Use: remover_nome <nome completo>");
                            continue;
                        }
                        String nomeRem = String.join(" ", Arrays.copyOfRange(partes, 1, partes.length));
                        Aluno alunoBuscaNomeRem = new Aluno(0, nomeRem);
                        Aluno alunoRemovidoNome = null;

                        if (chavePrincipal.equals("Nome")) {
                            // Nome é a chave principal. Remoção direta e rápida.
                            System.out.println("(Removendo pela chave principal...)");
                            alunoRemovidoNome = cadastroAlunos.remover(alunoBuscaNomeRem);
                        } else {
                            // Árvore indexada por Matrícula. Precisamos "Buscar por Nome" e depois "Remover por Matrícula".
                            System.out.println("(Removendo por chave secundária: buscando nome...)");
                            // 1. Pesquisa usando o comparador secundário
                            Aluno alunoEncontrado = cadastroAlunos.pesquisar(alunoBuscaNomeRem, compNome);
                            if (alunoEncontrado != null) {
                                // 2. Remove usando o comparador principal (Matrícula)
                                System.out.println("(Aluno encontrado, removendo...)");
                                alunoRemovidoNome = cadastroAlunos.remover(alunoEncontrado);
                            }
                        }

                        if (alunoRemovidoNome != null) {
                            System.out.println("Aluno removido: " + alunoRemovidoNome.toString());
                        } else {
                            System.out.println("Erro: Aluno com nome '" + nomeRem + "' não encontrado.");
                        }
                        break;

                    // --- Comandos de Busca (lógica inalterada, mas importante) ---
                    case "buscar_matricula":
                        if (partes.length != 2) {
                            System.out.println("Erro: Formato inválido. Use: buscar_matricula <matricula>");
                            continue;
                        }
                        int matriculaBusca = Integer.parseInt(partes[1]);
                        Aluno alunoBuscaMat = new Aluno(matriculaBusca, "");

                        Aluno alunoEncontradoMat;
                        if (chavePrincipal.equals("Matrícula")) {
                            System.out.println("(Usando busca por chave principal O(log n) ou O(n))");
                            alunoEncontradoMat = cadastroAlunos.pesquisar(alunoBuscaMat);
                        } else {
                            System.out.println("(Usando busca por chave secundária O(n))");
                            alunoEncontradoMat = cadastroAlunos.pesquisar(alunoBuscaMat, compMatricula); // Usa o comparador específico
                        }

                        if (alunoEncontradoMat != null) {
                            System.out.println("Encontrado: " + alunoEncontradoMat.toString());
                        } else {
                            System.out.println("Aluno com matrícula " + matriculaBusca + " não encontrado.");
                        }
                        break;

                    case "buscar_nome":
                        if (partes.length < 2) {
                            System.out.println("Erro: Formato inválido. Use: buscar_nome <nome completo>");
                            continue;
                        }
                        String nomeBusca = String.join(" ", Arrays.copyOfRange(partes, 1, partes.length));
                        Aluno alunoBuscaNome = new Aluno(0, nomeBusca);

                        Aluno alunoEncontradoNome;
                        if (chavePrincipal.equals("Nome")) {
                            System.out.println("(Usando busca por chave principal O(log n) ou O(n))");
                            alunoEncontradoNome = cadastroAlunos.pesquisar(alunoBuscaNome);
                        } else {
                            System.out.println("(Usando busca por chave secundária O(n))");
                            alunoEncontradoNome = cadastroAlunos.pesquisar(alunoBuscaNome, compNome); // Usa o comparador específico
                        }

                        if (alunoEncontradoNome != null) {
                            System.out.println("Encontrado: " + alunoEncontradoNome.toString());
                        } else {
                            System.out.println("Aluno com nome '" + nomeBusca + "' não encontrado.");
                        }
                        break;

                    case "listar_ordem":
                        System.out.println("--- Lista de Alunos (Ordenada por " + chavePrincipal + ") ---");
                        String lista = cadastroAlunos.caminharEmOrdem();
                        System.out.println(lista);
                        break;

                    case "info":
                        System.out.println("--- Informações da Árvore ---");
                        System.out.println("Indexada por: " + chavePrincipal);
                        System.out.println("Quantidade de Alunos: " + cadastroAlunos.quantidadeNos());
                        System.out.println("Altura da Árvore: " + cadastroAlunos.altura());
                        break;

                    case "ajuda":
                        imprimirAjuda();
                        break;

                    case "sair":
                        System.out.println("Encerrando o programa.");
                        scanner.close();
                        return; // Termina o método main e sai do loop

                    default:
                        System.out.println("Comando inválido. Digite 'ajuda' para ver as opções.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: A matrícula deve ser um número.");
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // --- Menu de Ajuda Atualizado ---
    private static void imprimirAjuda() {
        System.out.println("Comandos disponíveis:");
        System.out.println("  adicionar <matricula> <nome completo>   - Adiciona um novo aluno.");
        System.out.println("  remover_matricula <matricula>         - Remove um aluno pela matrícula.");
        System.out.println("  remover_nome <nome completo>          - Remove um aluno pelo nome.");
        System.out.println("  buscar_matricula <matricula>          - Busca um aluno pela matrícula.");
        System.out.println("  buscar_nome <nome completo>           - Busca um aluno pelo nome.");
        System.out.println("  listar_ordem                          - Lista todos os alunos (ordenado pela chave principal).");
        System.out.println("  info                                  - Mostra a altura e o número de nós da árvore.");
        System.out.println("  ajuda                                 - Mostra este menu de ajuda.");
        System.out.println("  sair                                  - Encerra o programa.");
    }
}