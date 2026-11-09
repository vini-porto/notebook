/*
 * S1 · ENCONTRE OS BUGS
 *
 * Este código COMPILA e RODA, mas os três métodos dão resultado errado.
 * Rode, veja o que falha, descubra o porquê e conserte.
 * Mude o MÍNIMO possível: cada bug se resolve mexendo em uma linha.
 *
 * No chat, além de dizer "pronto", explique em uma frase o motivo de cada bug.
 */
public class Bug_EncontreOsErros {

    // Aplica um desconto percentual. Ex.: precoComDesconto(200, 15) -> 170.0
    static double precoComDesconto(double preco, int percentual) {
        return preco - preco * (percentual / 100.0); // Divisão inteiro cortada
    }

    // Média de três notas. Ex.: media(7, 8, 8) -> 7.666...
    static double media(int n1, int n2, int n3) {
        int soma = n1 + n2 + n3;
        return soma / 3.0; // a função retorna double mas a divisão era inteira. Oque tambem cortava o resultado da media
    }

    // Retorna true se n for múltiplo de 3. Ex.: 9 -> true, 10 -> false
    static boolean ehMultiploDe3(int n) {
        return n % 3 == 0; // esperado % (modulo) ao inves de divisão direta
    }

    // ---------- não precisa mexer daqui pra baixo ----------
    public static void main(String[] args) {
        checar("precoComDesconto(200, 15)", 170.0, precoComDesconto(200, 15));
        checar("precoComDesconto(80, 50)", 40.0, precoComDesconto(80, 50));
        checar("media(7, 8, 8)", 23 / 3.0, media(7, 8, 8));
        checar("media(10, 10, 10)", 10.0, media(10, 10, 10));
        checar("ehMultiploDe3(9)", true, ehMultiploDe3(9));
        checar("ehMultiploDe3(10)", false, ehMultiploDe3(10));
        checar("ehMultiploDe3(0)", true, ehMultiploDe3(0));
        checar("ehMultiploDe3(2)", false, ehMultiploDe3(2));
    }

    static void checar(String teste, Object esperado, Object obtido) {
        boolean ok = (esperado instanceof Double e && obtido instanceof Double o)
                ? Math.abs(e - o) < 1e-9
                : esperado.equals(obtido);
        System.out.println((ok ? "OK      " : "FALHOU  ") + teste
                + (ok ? "" : "  -> esperado " + esperado + ", obtido " + obtido));
    }
}
