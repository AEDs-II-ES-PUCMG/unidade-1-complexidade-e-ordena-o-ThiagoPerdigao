import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> implements IOrdenador<T> {

    private int comparacoes;
    private int movimentacoes;
    private double tempoOrdenacao;
    private double inicio;

    private double nanoToMilli = 1.0/1_000_000;

    @Override
    public int getComparacoes() {
        return comparacoes;
    }

    @Override
    public int getMovimentacoes() {
        return movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return tempoOrdenacao;
    }

    private void iniciar(){
        this.comparacoes = 0;
        this.movimentacoes = 0;
        this.inicio = System.nanoTime();
    }

    private void terminar(){
        this.tempoOrdenacao = (System.nanoTime() - this.inicio) * nanoToMilli;
    }

    @Override
    public T[] ordenar(T[] dados) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        iniciar();
        mergeSort(dadosOrdenados, 0, dadosOrdenados.length - 1);
        terminar();
        return dadosOrdenados;
    }

    private void mergeSort(T[] vetor, int esquerda, int direita) {
        if (esquerda < direita) {
            int meio = (esquerda + direita) / 2;
            mergeSort(vetor, esquerda, meio);
            mergeSort(vetor, meio + 1, direita);
            intercala(vetor, esquerda, meio, direita);
        }
    }

    private void intercala(T[] vetor, int esquerda, int meio, int direita) {
        int tamanhoEsquerda = meio - esquerda + 1;
        int tamanhoDireita = direita - meio;

        T[] metadeEsquerda = Arrays.copyOfRange(vetor, esquerda, meio + 1);
        T[] metadeDireita = Arrays.copyOfRange(vetor, meio + 1, direita + 1);

        int i = 0;
        int j = 0;
        int k = esquerda;

        while (i < tamanhoEsquerda && j < tamanhoDireita) {
            comparacoes++;
            if (metadeEsquerda[i].compareTo(metadeDireita[j]) <= 0) {
                vetor[k] = metadeEsquerda[i];
                i++;
            } else {
                vetor[k] = metadeDireita[j];
                j++;
            }
            movimentacoes++;
            k++;
        }

        while (i < tamanhoEsquerda) {
            vetor[k] = metadeEsquerda[i];
            movimentacoes++;
            i++;
            k++;
        }

        while (j < tamanhoDireita) {
            vetor[k] = metadeDireita[j];
            movimentacoes++;
            j++;
            k++;
        }
    }
}
