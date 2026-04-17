import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Produto implements Comparable<Produto> {
    private static final double MARGEM_PADRAO = 0.2;
    private static int ultimoID = 10_000;

    protected int idProduto;
    protected String descricao;
    protected double precoCusto;
    protected double margemLucro;

    private void init(String desc, double precoCusto, double margemLucro){

        if(desc.length() < 3 || precoCusto <= 0 || margemLucro <= 0)
            throw new IllegalArgumentException("Valores invalidos para o produto");
        descricao = desc;
        this.precoCusto = precoCusto;
        this.margemLucro = margemLucro;
        idProduto = ultimoID++;

    }

    protected Produto(String desc, double precoCusto, double margemLucro){
        init(desc, precoCusto, margemLucro);
    }

    protected Produto(String desc, double precoCusto){
        init(desc, precoCusto, MARGEM_PADRAO);
    }

    public abstract double valorDeVenda();

    @Override
    public int hashCode(){
        return idProduto;
    }

    @Override
    public String toString(){
        NumberFormat moeda = NumberFormat.getCurrencyInstance();

        return String.format("%04d - %s: %s", idProduto, descricao, moeda.format(valorDeVenda()));
    }

    @Override
    public int compareTo(Produto outro){
        return this.descricao.compareToIgnoreCase(outro.descricao);
    }

    @Override
    public boolean equals(Object obj){
        try{
            Produto outro = (Produto)obj;
            return this.hashCode() == outro.hashCode();
        }catch (ClassCastException ex){
            return false;
        }
    }

    static Produto criarDoTexto(String linha){
        Produto novoProduto = null;
        String[] detalhes = linha.split(";");
        String descr = detalhes[1];
        double precoCusto = Double.parseDouble(detalhes[2].replace(",", "."));
        double margem = Double.parseDouble(detalhes[3].replace(",", "."));
        if(detalhes[0].equals("1")){
            novoProduto = new ProdutoNaoPerecivel(descr, precoCusto, margem);
        }
        else{
            LocalDate dataValidade =
                LocalDate.parse(detalhes[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            novoProduto = new ProdutoPerecivel(descr, precoCusto, margem, dataValidade);
        }
        return novoProduto;
    }

    public abstract String gerarDadosTexto();

    public String getDescricao() {
        return descricao;
    }

    public int getIdProduto() {
        return idProduto;
    }
}
