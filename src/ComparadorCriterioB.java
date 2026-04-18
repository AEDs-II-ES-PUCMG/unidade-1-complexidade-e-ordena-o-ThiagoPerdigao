import java.util.Comparator;

/**
 * Critério B - Volume Total de Itens (crescente).
 * Desempate 1: Data do Pedido.
 * Desempate 2: Código Identificador do pedido.
 */
public class ComparadorCriterioB implements Comparator<Pedido> {

    @Override  // volume -> data -> id
    public int compare(Pedido o1, Pedido o2) {
        if (o1.getQuantosProdutos() == o2.getQuantosProdutos()) {
			if (o1.getDataPedido().equals(o2.getDataPedido())) {
                return (o1.getIdPedido() - o2.getIdPedido());
			} else {
				return 1;
			}
    	} else {
    		return (((o1.getQuantosProdutos() - o2.getQuantosProdutos()) > 0) ? 1 : -1);
        }
    }
}
