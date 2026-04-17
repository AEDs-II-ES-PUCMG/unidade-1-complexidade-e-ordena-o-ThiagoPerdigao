import java.util.Arrays;
import java.util.Random;

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

    static void exibirResultado(String nomeMetodo, IOrdenador<Integer> ordenador, Integer[] vetorOrdenado) {
        System.out.println("\nVetor ordenado metodo " + nomeMetodo + ":");
        System.out.println(Arrays.toString(vetorOrdenado));
        System.out.println("Comparacoes: " + ordenador.getComparacoes());
        System.out.println("Movimentacoes: " + ordenador.getMovimentacoes());
        System.out.println("Tempo de ordenacao (ms): " + ordenador.getTempoOrdenacao());
    }

    static void testarOrdenador(String nomeMetodo, IOrdenador<Integer> ordenador, Integer[] vetorBase) {
        Integer[] vetorOrdenado = ordenador.ordenar(vetorBase);
        exibirResultado(nomeMetodo, ordenador, vetorOrdenado);
    }

    static void compararAlgoritmos(int[] tamanhos) {
        for (int tamanho : tamanhos) {
            Integer[] vetorBase = gerarVetorObjetos(tamanho);

            BubbleSort<Integer> bolha = new BubbleSort<>();
            InsertionSort<Integer> insercao = new InsertionSort<>();
            SelectionSort<Integer> selecao = new SelectionSort<>();

            bolha.ordenar(vetorBase);
            insercao.ordenar(vetorBase);
            selecao.ordenar(vetorBase);

            System.out.println("\n==============================");
            System.out.println("Tamanho do vetor: " + tamanho);
            System.out.println("==============================");
            System.out.println("BubbleSort");
            System.out.println("Comparacoes: " + bolha.getComparacoes());
            System.out.println("Movimentacoes: " + bolha.getMovimentacoes());
            System.out.println("Tempo de ordenacao (ms): " + bolha.getTempoOrdenacao());

            System.out.println("InsertionSort");
            System.out.println("Comparacoes: " + insercao.getComparacoes());
            System.out.println("Movimentacoes: " + insercao.getMovimentacoes());
            System.out.println("Tempo de ordenacao (ms): " + insercao.getTempoOrdenacao());

            System.out.println("SelectionSort");
            System.out.println("Comparacoes: " + selecao.getComparacoes());
            System.out.println("Movimentacoes: " + selecao.getMovimentacoes());
            System.out.println("Tempo de ordenacao (ms): " + selecao.getTempoOrdenacao());
        }
    }

    public static void main(String[] args) {
        int tam = 20;
        Integer[] vetor = gerarVetorObjetos(tam);

        System.out.println("Vetor original:");
        System.out.println(Arrays.toString(vetor));

        testarOrdenador("Bolha", new BubbleSort<>(), vetor);
        testarOrdenador("Insercao", new InsertionSort<>(), vetor);
        testarOrdenador("Selecao", new SelectionSort<>(), vetor);

        compararAlgoritmos(new int[] {20, 100, 500, 1000});
    }
}
