import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class AppOficina {

    static Produto[] produtos;
    static Produto[] produtosOrdenadosPorCodigo;
    static Produto[] produtosOrdenadosPorDescricao;
    static int quantProdutos = 0;
    static String nomeArquivoDados = "produtos.txt";
    static IOrdenador<Produto> ordenador;
    static Scanner teclado;

    static <T extends Number> T lerNumero(String mensagem, Class<T> classe) {
        System.out.print(mensagem + ": ");
        T valor;
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("XULAMBS COMERCIO DE COISINHAS v0.3\n================");
    }

    static int exibirMenuPrincipal() {
        cabecalho();
        System.out.println("1 - Procurar produto");
        System.out.println("2 - Filtrar produtos por preco maximo");
        System.out.println("3 - Ordenar produtos");
        System.out.println("4 - Embaralhar produtos");
        System.out.println("5 - Listar produtos");
        System.out.println("0 - Finalizar");

        return lerNumero("Digite sua opcao", Integer.class);
    }

    static int exibirMenuOrdenadores() {
        cabecalho();
        System.out.println("1 - Bolha");
        System.out.println("2 - Insercao");
        System.out.println("3 - Selecao");
        System.out.println("4 - Mergesort");
        System.out.println("0 - Cancelar");

        return lerNumero("Digite sua opcao", Integer.class);
    }

    static int exibirMenuComparadores() {
        cabecalho();
        System.out.println("1 - Por descricao");
        System.out.println("2 - Por codigo");
        System.out.println("0 - Cancelar");

        return lerNumero("Digite sua opcao", Integer.class);
    }

    static int exibirMenuBusca() {
        cabecalho();
        System.out.println("1 - Buscar por codigo");
        System.out.println("2 - Buscar por descricao");
        System.out.println("0 - Cancelar");

        return lerNumero("Digite sua opcao", Integer.class);
    }

    static Produto[] carregarProdutos(String nomeArquivo){
        Scanner dados;
        Produto[] dadosCarregados;
        quantProdutos = 0;

        try{
            dados = new Scanner(new File(nomeArquivo));
            int tamanho = Integer.parseInt(dados.nextLine());

            dadosCarregados = new Produto[tamanho];
            while (dados.hasNextLine()) {
                Produto novoProduto = Produto.criarDoTexto(dados.nextLine());
                dadosCarregados[quantProdutos] = novoProduto;
                quantProdutos++;
            }
            dados.close();
        }catch (FileNotFoundException fex){
            System.out.println("Arquivo nao encontrado. Produtos nao carregados");
            dadosCarregados = null;
        }
        return dadosCarregados;
    }

    static void prepararIndicesDeBusca() {
        if (produtos == null) {
            produtosOrdenadosPorCodigo = null;
            produtosOrdenadosPorDescricao = null;
            return;
        }

        IOrdenador<Produto> mergesortCodigo = new MergeSort<>();
        IOrdenador<Produto> mergesortDescricao = new MergeSort<>();
        produtosOrdenadosPorCodigo = mergesortCodigo.ordenar(produtos, new ComparadorPorCodigo());
        produtosOrdenadosPorDescricao = mergesortDescricao.ordenar(produtos, new ComparadorPorDescricao());
    }

    static IOrdenador<Produto> criarOrdenador(int opcao) {
        return switch (opcao) {
            case 1 -> new BubbleSort<>();
            case 2 -> new InsertSort<>();
            case 3 -> new SelectionSort<>();
            case 4 -> new MergeSort<>();
            default -> null;
        };
    }

    static Comparator<Produto> criarComparador(int opcao) {
        return switch (opcao) {
            case 1 -> new ComparadorPorDescricao();
            case 2 -> new ComparadorPorCodigo();
            default -> null;
        };
    }

    static Produto buscaBinariaPorCodigo(int codigo) {
        int esquerda = 0;
        int direita = quantProdutos - 1;

        while (esquerda <= direita) {
            int meio = (esquerda + direita) / 2;
            int codigoAtual = produtosOrdenadosPorCodigo[meio].getIdProduto();

            if (codigoAtual == codigo) {
                return produtosOrdenadosPorCodigo[meio];
            }

            if (codigoAtual < codigo) {
                esquerda = meio + 1;
            } else {
                direita = meio - 1;
            }
        }

        return null;
    }

    static Produto buscaBinariaPorDescricao(String descricao) {
        int esquerda = 0;
        int direita = quantProdutos - 1;
        String descricaoBusca = descricao.trim();

        while (esquerda <= direita) {
            int meio = (esquerda + direita) / 2;
            Produto atual = produtosOrdenadosPorDescricao[meio];
            int comparacao = atual.getDescricao().compareToIgnoreCase(descricaoBusca);

            if (comparacao == 0) {
                return atual;
            }

            if (comparacao < 0) {
                esquerda = meio + 1;
            } else {
                direita = meio - 1;
            }
        }

        return null;
    }

    static Produto localizarProduto() {
        int criterio = exibirMenuBusca();

        if (criterio == 1) {
            Integer numero = lerNumero("Digite o identificador do produto", Integer.class);
            return numero == null ? null : buscaBinariaPorCodigo(numero);
        }

        if (criterio == 2) {
            System.out.print("Digite a descricao do produto: ");
            return buscaBinariaPorDescricao(teclado.nextLine());
        }

        return null;
    }

    private static void mostrarProduto(Produto produto) {
        cabecalho();
        String mensagem = "Dados invalidos";

        if(produto!=null){
            mensagem = String.format("Dados do produto:\n%s", produto);
        }

        System.out.println(mensagem);
    }

    private static void filtrarPorPrecoMaximo(){
        cabecalho();
        System.out.println("Filtrando por valor maximo:");
        Double valor = lerNumero("valor", Double.class);
        if (valor == null) {
            System.out.println("Valor invalido.");
            return;
        }

        StringBuilder relatorio = new StringBuilder();
        for (int i = 0; i < quantProdutos; i++) {
            if(produtos[i].valorDeVenda() < valor)
                relatorio.append(produtos[i]).append("\n");
        }
        System.out.println(relatorio.toString());
    }

    static void ordenarProdutos(){
        int opcaoOrdenador = exibirMenuOrdenadores();
        if (opcaoOrdenador == 0) {
            return;
        }

        int opcaoComparador = exibirMenuComparadores();
        if (opcaoComparador == 0) {
            return;
        }

        ordenador = criarOrdenador(opcaoOrdenador);
        Comparator<Produto> comparador = criarComparador(opcaoComparador);

        if (ordenador == null || comparador == null) {
            cabecalho();
            System.out.println("Opcao invalida.");
            return;
        }

        Produto[] ordenados = ordenador.ordenar(produtos, comparador);

        cabecalho();
        for (Produto produto : ordenados) {
            System.out.println(produto);
        }
        System.out.println();
        System.out.println("Comparacoes: " + ordenador.getComparacoes());
        System.out.println("Movimentacoes: " + ordenador.getMovimentacoes());
        System.out.println("Tempo de ordenacao (ms): " + ordenador.getTempoOrdenacao());

        produtos = verificarSubstituicao(produtos, ordenados);
    }

    static void embaralharProdutos(){
        Collections.shuffle(Arrays.asList(produtos));
    }

    static Produto[] verificarSubstituicao(Produto[] dadosOriginais, Produto[] copiaDados){
        System.out.print("Deseja sobrescrever os dados originais pelos ordenados (S/N)? ");
        String resposta = teclado.nextLine().toUpperCase();
        if(resposta.equals("S"))
            return Arrays.copyOf(copiaDados, copiaDados.length);

        return dadosOriginais;
    }

    static void listarProdutos(){
        cabecalho();
        for (int i = 0; i < quantProdutos; i++) {
            System.out.println(produtos[i]);
        }
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);

        produtos = carregarProdutos(nomeArquivoDados);
        prepararIndicesDeBusca();
        embaralharProdutos();

        int opcao = -1;

        do {
            opcao = exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> mostrarProduto(localizarProduto());
                case 2 -> filtrarPorPrecoMaximo();
                case 3 -> ordenarProdutos();
                case 4 -> embaralharProdutos();
                case 5 -> listarProdutos();
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
            }
            if (opcao != 0) {
                pausa();
            }
        }while (opcao != 0);
        teclado.close();
    }
}
