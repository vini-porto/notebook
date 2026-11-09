/*
 * S2 · NÍVEL 1: onde declarar a variável?
 *
 * Nos três métodos, o segredo é DECLARAR cada variável no escopo certo:
 *  - Fora do loop: o que precisa "lembrar" de uma volta pra outra e ser usado depois.
 *  - Dentro do loop: o que é temporário, de uma volta só.
 *
 * Complete os TODOs, rode e deixe tudo OK.
 */
public class Nivel1_Escopo {

    // Soma de 1 até n. Ex.: somaAte(4) -> 1 + 2 + 3 + 4 = 10
    static int somaAte(int n) {
        int soma = 0;
        for (int i = 1; i <= n; i++){
            soma += i;
        }
        return soma;
    }

    // Quantos números negativos tem no array. Ex.: {3, -1, 0, -7} -> 2
    static int contarNegativos(int[] numeros) {
        int acumulador = 0;
        for (int numero : numeros) {
            if (numero < 0){
                acumulador++;
            }
        }
        return acumulador;
    }

    // Maior valor do array (o array tem pelo menos 1 elemento).
    // Ex.: {4, 9, 2} -> 9   |   {-5, -2, -8} -> -2
    // Dica: com que valor a variável "maior" deve começar? (pense no segundo exemplo)
    static int maiorValor(int[] numeros) {
        int maior = numeros[0];
        for (int numero : numeros) {
            if (numero > maior){
                maior = numero;
            }
        }
        return maior;
    }

    // ---------- não precisa mexer daqui pra baixo ----------
    public static void main(String[] args) {
        checar("somaAte(4)", 10, somaAte(4));
        checar("somaAte(1)", 1, somaAte(1));
        checar("somaAte(0)", 0, somaAte(0));
        checar("contarNegativos({3, -1, 0, -7})", 2, contarNegativos(new int[]{3, -1, 0, -7}));
        checar("contarNegativos({1, 2})", 0, contarNegativos(new int[]{1, 2}));
        checar("maiorValor({4, 9, 2})", 9, maiorValor(new int[]{4, 9, 2}));
        checar("maiorValor({-5, -2, -8})", -2, maiorValor(new int[]{-5, -2, -8}));
        checar("maiorValor({7})", 7, maiorValor(new int[]{7}));
        // Casos adicionados depois do bug do "maior == 0":
        checar("maiorValor({-3, 0, -5})", 0, maiorValor(new int[]{-3, 0, -5}));
        checar("maiorValor({0, -5})", 0, maiorValor(new int[]{0, -5}));
        checar("maiorValor({-1, -1})", -1, maiorValor(new int[]{-1, -1}));
        checar("maiorValor({-3, 0, -5})", 0, maiorValor(new int[]{-3, 0, -5}));
    }

    static void checar(String teste, Object esperado, Object obtido) {
        boolean ok = esperado.equals(obtido);
        System.out.println((ok ? "OK      " : "FALHOU  ") + teste
                + (ok ? "" : "  -> esperado " + esperado + ", obtido " + obtido));
    }
}
