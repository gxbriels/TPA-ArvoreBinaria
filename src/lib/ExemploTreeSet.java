package lib;

import java.util.Comparator;
import java.util.Optional;
import java.util.TreeSet;
import app.Aluno;
import app.ComparadorAlunoPorMatricula;
import app.ComparadorAlunoPorNome;


public class ExemploTreeSet {

    public static void main(String[] args) {


        Comparator<Aluno> compMatricula = new ComparadorAlunoPorMatricula();
        TreeSet<Aluno> cadastroAlunos = new TreeSet<>(compMatricula);
        System.out.println("Árvore (TreeSet) criada, indexada por Matrícula.");


        cadastroAlunos.add(new Aluno(2005, "Gabriel Silva"));
        cadastroAlunos.add(new Aluno(2001, "Arthur França"));
        cadastroAlunos.add(new Aluno(2003, "Pedro Vitor"));
        System.out.println("\nAlunos adicionados.");

        System.out.println("\n--- Listando (Em Ordem por Matrícula) ---");
        for (Aluno aluno : cadastroAlunos) {
            System.out.println(aluno);
        }


        System.out.println("\n--- Buscando por Matrícula 2003 (O(log n)) ---");

        Aluno buscaMatricula = new Aluno(2003, "");


        if (cadastroAlunos.contains(buscaMatricula)) {
            System.out.println("Aluno 2003 ENCONTRADO.");
        } else {
            System.out.println("Aluno 2003 NÃO encontrado.");
        }


        System.out.println("\n--- Buscando por Nome 'Gabriel Silva' (O(n)) ---");


        Optional<Aluno> alunoNome = cadastroAlunos.stream()
                .filter(a -> a.getNome().equals("Gabriel Silva"))
                .findFirst();

        if (alunoNome.isPresent()) {
            System.out.println("Aluno 'Gabriel Silva' ENCONTRADO: " + alunoNome.get());
        } else {
            System.out.println("Aluno 'Gabriel Silva' NÃO encontrado.");
        }
    }
}