/*
 * S3 · ENCONTRE OS BUGS
 *
 * A classe Pedido COMPILA, mas os objetos nascem com valores errados.
 * Há 3 bugs, um em cada constructor. Rode, veja o que falha e conserte.
 * Deixe um comentário curto com o PORQUÊ de cada conserto.
 *
 * Regras do Pedido:
 *  - Pedido(cliente, produto, quantidade): guarda os três.
 *  - Pedido(cliente, produto): quantidade 1.
 *  - Pedido(cliente): produto "Café" e quantidade 1.
 */
public class Bug_Pedido {

    static class Pedido {
        static final String PRODUTO_PADRAO = "Café";
        static final int QUANTIDADE_PADRAO = 1;

        String cliente;
        String produto;
        int quantidade;

        Pedido(String cliente, String produto, int quantidade) {
            this.cliente = cliente; // O certo seria "this.cliente" para pegar o field do cliente e "cliente" para atribuir o paramentro dado ao criar o objeto
            this.produto = produto;
            this.quantidade = quantidade;
        }

        Pedido(String cliente, String produto) {
            this(cliente, produto, QUANTIDADE_PADRAO);  // produto e cliente estão fora de ordem
        }

        Pedido(String cliente) {
            this(cliente, PRODUTO_PADRAO, QUANTIDADE_PADRAO); // aqui acontece apenas uma atribuição simples esquecendo completamente dos outros preenchimento automatico dos outros parametros
        }
    }

    // ---------- não precisa mexer daqui pra baixo ----------
    public static void main(String[] args) {
        Pedido p1 = new Pedido("Ana", "Chá", 3);
        checar("p1.cliente", "Ana", p1.cliente);
        checar("p1.produto", "Chá", p1.produto);
        checar("p1.quantidade", 3, p1.quantidade);

        Pedido p2 = new Pedido("Bruno", "Suco");
        checar("p2.cliente", "Bruno", p2.cliente);
        checar("p2.produto", "Suco", p2.produto);
        checar("p2.quantidade", 1, p2.quantidade);

        Pedido p3 = new Pedido("Carla");
        checar("p3.cliente", "Carla", p3.cliente);
        checar("p3.produto", "Café", p3.produto);
        checar("p3.quantidade", 1, p3.quantidade);
    }

    static void checar(String teste, Object esperado, Object obtido) {
        boolean ok = java.util.Objects.equals(esperado, obtido);
        System.out.println((ok ? "OK      " : "FALHOU  ") + teste
                + (ok ? "" : "  -> esperado " + esperado + ", obtido " + obtido));
    }
}
