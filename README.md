🌳 Projeto de Implementação de Árvores - TPA

Trabalho prático referente à matéria de Técnicas de Programação Avançada (TPA) do curso de Sistemas de Informação.

O objetivo deste projeto é implementar, do zero, as estruturas de dados de Árvore Binária de Busca (BST) e Árvore AVL em Java, utilizando Generics e a interface Comparator para torná-las flexíveis.

O projeto é dividido em:

    Implementação da Biblioteca: Criação das classes de Árvore (lib).

    Aplicação Prática: Um aplicativo de terminal para gerenciar cadastros de Aluno, utilizando a biblioteca criada (app).

    Análise de Performance: Geração de relatórios comparando o desempenho de árvores balanceadas vs. degeneradas (app).

    Pesquisa: Comparação da biblioteca customizada com as implementações de árvore padrão da biblioteca Java (como TreeSet).

Funcionalidades Principais

    Biblioteca de Árvore Binária Genérica (lib/ArvoreBinariaExemplo.java):

        Implementação completa de uma BST (Adicionar, Remover, Pesquisar, Altura, Quantidade, Caminhamentos).

        Uso de Comparator no construtor para permitir indexação por qualquer critério.

        Método pesquisar(T valor, Comparator comparador) para buscas O(n) por chaves secundárias.

    Biblioteca de Árvore AVL (lib/ArvoreAVLExemplo.java):

        Herda da BST e sobrescreve o método adicionar para incluir lógica de auto-balanceamento (Rotações Simples e Duplas).

        Garante performance O(logn) para inserções, mesmo com dados de entrada ordenados.

    Aplicação Interativa (app/AppCadastroAlunos.java):

        Permite ao usuário escolher a chave de indexação ao iniciar (Matrícula ou Nome).

        Menus interativos para adicionar, remover_matricula, remover_nome, buscar_matricula, buscar_nome e listar_ordem.

        Demonstra o uso dos métodos de busca rápida (O(logn)) e busca lenta (O(n)) da biblioteca.

    Análise e Pesquisa:

        app/AppRelatorioArvoreBinaria.java: Gera dados para o relatório da Etapa 3, comparando performance de árvores balanceadas e degeneradas (demonstrando o StackOverflowError em O(n2)).

        app/AppRelatorioAVL.java: Gera dados para o relatório da Etapa 5, provando que a AVL mantém a altura O(logn) mesmo com input degenerado.

        lib/ExemploTreeSet.java: Código de exemplo da Etapa 6, que demonstra como as coleções padrão do Java (TreeSet) realizam as mesmas tarefas.

🔧 Tecnologias Utilizadas

    Java 11+ (ou superior)

    Interface Comparator

    Generics (<T>)

📂 Estrutura do Projeto

O projeto é organizado em dois pacotes principais, app e lib.

    /
    ├── app/
    │   ├── Aluno.java                     # Classe de modelo (POJO)
    │   ├── ComparadorAlunoPorMatricula.java # Estratégia de comparação
    │   ├── ComparadorAlunoPorNome.java    # Estratégia de comparação
    │   ├── AppCadastroAlunos.java         # (Etapa 2) Aplicação interativa
    │   ├── AppRelatorioArvoreBinaria.java  # (Etapa 3) Teste da BST
    │   └── AppRelatorioAVL.java           # (Etapa 5) Teste da AVL
    │
    ├── lib/
    │   ├── IArvoreBinaria.java           # Interface obrigatória do trabalho
    │   ├── NoExemplo.java                 # Classe do nó (com altura para AVL)
    │   ├── ArvoreBinariaExemplo.java      # (Etapa 1) Implementação da BST
    │   ├── ArvoreAVLExemplo.java          # (Etapa 4) Implementação da AVL
    │   └── ExemploTreeSet.java            # (Etapa 6) Código de pesquisa da bib. Java
    │
    └── README.md

⚙️ Como Compilar e Executar

Este projeto foi desenvolvido e testado em ambiente Linux (Ubuntu) e pode ser compilado e executado diretamente pelo terminal.

1. Pré-requisitos

    Java Development Kit (JDK) instalado.

2. Compilação

Navegue até a pasta raiz do projeto (a que contém app/ e lib/) e execute o comando de compilação:

Bash --> javac app/*.java lib/*.java

3. Executando a Aplicação Principal (Etapa 2)

Este é o programa interativo de cadastro de alunos.

Bash --> java app.AppCadastroAlunos

O programa perguntará se deseja indexar por Matrícula ou Nome e, em seguida, apresentará o menu de comandos (adicionar, remover_nome, listar_ordem, sair, etc.).

4. Executando os Relatórios de Performance (Etapas 3 e 5)

Para gerar os dados do relatório da Etapa 3 (BST):

Bash --> java app.AppRelatorioArvoreBinaria

(É esperado que este comando termine com um StackOverflowError, como explicado no relatório).

Para gerar os dados do relatório da Etapa 5 (AVL):

Bash --> java app.AppRelatorioAVL

(Este comando deve executar com sucesso, demonstrando a eficiência da AVL).

🔬 Etapa 6: Pesquisa (Arquivo ExemploTreeSet.java)

A Etapa 6 do trabalho exigia uma pesquisa sobre as estruturas de árvore da biblioteca padrão do Java. O arquivo lib/ExemploTreeSet.java foi criado para esta finalidade.

Ele demonstra que:

    Classe Utilizada: O java.util.TreeSet é a implementação padrão de árvore do Java (internamente, é uma Árvore Red-Black, similar à AVL em performance).

    Indexação: Assim como nossa biblioteca, o TreeSet aceita um Comparator em seu construtor para definir a ordenação (a "chave principal").

    Busca Rápida (O(logn)): A busca pela chave principal é feita com o método .contains(), que é extremamente rápido.

    Busca por Chave Secundária (O(n)): O TreeSet não oferece um método otimizado para buscar por uma chave secundária (como nosso pesquisar(T, Comparator)). Para fazer isso, é necessário varrer todos os elementos da árvore manualmente, usando stream().filter(), o que resulta na mesma complexidade O(n) (linear) da nossa implementação.

Para executar este arquivo de exemplo:
Bash --> java lib.ExemploTreeSet

👨‍💻 Autores

    Arthur de França Rocha

    Pedro Vitor Santiago Zuqui

    Gabriel Silva de Oliveira
