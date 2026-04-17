import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class InsertSort<T extends Comparable<T>> implements IOrdenador<T>{

    private long comparacoes;
    private long movimentacoes;
    private LocalDateTime inicio;
    private LocalDateTime termino;

    public InsertSort() {
        zerarContadores();
    }

    private void zerarContadores() {
        comparacoes = 0;
        movimentacoes = 0;
    }

    @Override
    public T[] ordenar(T[] dados) {
        return ordenar(dados, T::compareTo);
    }

    @Override
    public T[] ordenar(T[] dados, Comparator<T> comparador) {
        zerarContadores();
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        int tamanho = dadosOrdenados.length;

        inicio = LocalDateTime.now();

        for (int posReferencia = 1; posReferencia <= tamanho - 1; posReferencia++) {
            T valor = dadosOrdenados[posReferencia];
            movimentacoes++;
            int j = posReferencia - 1;

            while(j >= 0){
                comparacoes++;
                if(comparador.compare(valor, dadosOrdenados[j]) < 0){
                    dadosOrdenados[j + 1] = dadosOrdenados[j];
                    movimentacoes++;
                    j--;
                } else {
                    break;
                }
            }

            dadosOrdenados[j + 1] = valor;
            movimentacoes++;
        }
        termino = LocalDateTime.now();

        return dadosOrdenados;
    }

    public long getComparacoes() {
        return comparacoes;
    }

    public long getMovimentacoes() {
        return movimentacoes;
    }

    public double getTempoOrdenacao() {
        return Duration.between(inicio, termino).toNanos() / 1_000_000.0;
    }
}
