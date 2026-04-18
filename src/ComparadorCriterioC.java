import java.util.Comparator;

/**
 * Critério C - Índice de Economia (decrescente).
 * O índice de economia é a diferença entre o valor de catálogo atual e o valor efetivamente pago.
 * Desempate 1: Valor Final do Pedido (crescente).
 * Desempate 2: Código Identificador do pedido (crescente).
 */
public class ComparadorCriterioC implements Comparator<Pedido> {

    @Override //economia -> valor -> id
    public int compare(Pedido o1, Pedido o2) {
         if (o1.indiceEconomia() == o2.indiceEconomia()) {
			if (o1.valorFinal() == o2.valorFinal()) {
                return (o1.getIdPedido() - o2.getIdPedido());
			} else {
				return (((o1.valorFinal() - o2.valorFinal()) > 0) ? 1 : -1);
			}
    	} else {
    		return (((o1.indiceEconomia() - o2.indiceEconomia()) > 0) ? 1 : -1);
        }
    }
}
