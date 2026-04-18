import java.util.Comparator;

/**
 * Critério A - Valor Final do Pedido (crescente).
 * Desempate 1: Volume Total de Itens (quantProdutos).
 * Desempate 2: Código Identificador do primeiro item do pedido.
 */
public class ComparadorCriterioA implements Comparator<Pedido> {

    @Override // valor -> qtd -> id
    public int compare(Pedido o1, Pedido o2) {
        if (o1.valorFinal() == o2.valorFinal()) {
			if (o1.getQuantosProdutos() == o2.getQuantosProdutos()) {
                return (o1.getIdPedido() - o2.getIdPedido());
			} else {
                return (o1.getQuantosProdutos() - o2.getQuantosProdutos());
            }
    	} else {
    		return (((o1.valorFinal() - o2.valorFinal()) > 0) ? 1 : -1);
        }
    }
}
