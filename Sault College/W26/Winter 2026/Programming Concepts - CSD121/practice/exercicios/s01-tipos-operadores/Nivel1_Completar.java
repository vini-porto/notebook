/*
 * S1 · NÍVEL 1: complete os TODOs
 *
 * Cada método tem um TODO. Substitua o "return" provisório pelo código certo.
 * Rode o main: cada linha deve mostrar OK. Quando tudo der OK, diga "pronto" no chat.
 */
public class Nivel1_Completar {

    // Retorna a média de dois inteiros, SEM perder a parte decimal.
    // Ex.: mediaDeDois(7, 8) -> 7.5
    static double mediaDeDois(int a, int b) {
        return (a + b) / 2.0;
    }

    // Retorna true se n for par. Use o operador %.
    static boolean ehPar(int n) {
        return n % 2 == 0;
    }

    // Retorna o último dígito de um número positivo. Use o operador %.
    // Ex.: ultimoDigito(1234) -> 4
    static int ultimoDigito(int n) {
        return n % 10;
    }

    // ---------- não precisa mexer daqui pra baixo ----------
    public static void main(String[] args) {
        checar("mediaDeDois(7, 8)", 7.5, mediaDeDois(7, 8));
        checar("mediaDeDois(10, 4)", 7.0, mediaDeDois(10, 4));
        checar("mediaDeDois(3, 4)", 3.5, mediaDeDois(3, 4));
        checar("ehPar(4)", true, ehPar(4));
        checar("ehPar(7)", false, ehPar(7));
        checar("ehPar(0)", true, ehPar(0));
        checar("ultimoDigito(1234)", 4, ultimoDigito(1234));
        checar("ultimoDigito(70)", 0, ultimoDigito(70));
        checar("ultimoDigito(5)", 5, ultimoDigito(5));
    }

    static void checar(String teste, Object esperado, Object obtido) {
        boolean ok = (esperado instanceof Double e && obtido instanceof Double o)
                ? Math.abs(e - o) < 1e-9
                : esperado.equals(obtido);
        System.out.println((ok ? "OK      " : "FALHOU  ") + teste
                + (ok ? "" : "  -> esperado " + esperado + ", obtido " + obtido));
    }
}
