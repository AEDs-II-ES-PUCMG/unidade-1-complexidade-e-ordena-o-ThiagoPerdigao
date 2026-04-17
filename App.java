import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random();
    static long operacoes;
    static double nanoToMilli = 1.0/1_000_000;

    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;
    }

    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }

    static void exibirResultado(String nomeMetodo, IOrdenador<Integer> ordenador, Integer[] vetorOriginal, Integer[] vetorOrdenado) {
        System.out.println("\nMetodo escolhido: " + nomeMetodo);
        System.out.println("Vetor original:");
        System.out.println(Arrays.toString(vetorOriginal));
        System.out.println("Vetor ordenado:");
        System.out.println(Arrays.toString(vetorOrdenado));
        System.out.println("Comparacoes: " + ordenador.getComparacoes());
        System.out.println("Movimentacoes: " + ordenador.getMovimentacoes());
        System.out.println("Tempo de ordenacao (ms): " + ordenador.getTempoOrdenacao());
    }

    static IOrdenador<Integer> criarOrdenador(int opcao) {
        return switch (opcao) {
            case 1 -> new BubbleSort<>();
            case 2 -> new InsertionSort<>();
            case 3 -> new SelectionSort<>();
            case 4 -> new MergeSort<>();
            default -> null;
        };
    }

    static String nomeMetodo(int opcao) {
        return switch (opcao) {
            case 1 -> "BubbleSort";
            case 2 -> "InsertionSort";
            case 3 -> "SelectionSort";
            case 4 -> "MergeSort";
            default -> "Invalido";
        };
    }

    static void exibirMenu() {
        System.out.println("\nMenu de ordenacao");
        System.out.println("1 - BubbleSort");
        System.out.println("2 - InsertionSort");
        System.out.println("3 - SelectionSort");
        System.out.println("4 - MergeSort");
        System.out.println("0 - Sair");
        System.out.print("Escolha o metodo desejado: ");
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int opcao;

        do {
            exibirMenu();
            opcao = Integer.parseInt(teclado.nextLine());

            if (opcao >= 1 && opcao <= 4) {
                System.out.print("Digite o tamanho do vetor: ");
                int tamanho = Integer.parseInt(teclado.nextLine());

                Integer[] vetorOriginal = gerarVetorObjetos(tamanho);
                IOrdenador<Integer> ordenador = criarOrdenador(opcao);
                Integer[] vetorOrdenado = ordenador.ordenar(vetorOriginal);

                exibirResultado(nomeMetodo(opcao), ordenador, vetorOriginal, vetorOrdenado);
            } else if (opcao != 0) {
                System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);

        teclado.close();
    }
}
