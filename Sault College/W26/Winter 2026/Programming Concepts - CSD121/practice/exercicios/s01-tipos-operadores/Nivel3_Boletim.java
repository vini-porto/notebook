/*
 * S1 · NÍVEL 3: mini-problema (combina com laços e métodos)
 *
 * Um professor tem as notas de uma turma (de 0 a 100) num array de int.
 * A turma sempre tem pelo menos 1 aluno. Escreva os 3 métodos:
 *
 *  1. calcularMedia(int[] notas)       -> double  (média com casas decimais)
 *  2. contarAprovados(int[] notas)     -> int     (aprovado = nota >= 50)
 *  3. percentualAprovados(int[] notas) -> double  (ex.: 3 de 5 -> 60.0)
 *
 * Regras:
 *  - A nota mínima (50) deve ser uma constante, não um número solto no código.
 *  - percentualAprovados deve REUSAR contarAprovados, sem repetir o loop.
 *  - Cuidado com a divisão inteira nos métodos 1 e 3.
 */
public class Nivel3_Boletim {

    static final int NOTA_MINIMA = 50;

    static double calcularMedia(int[] notas) {
        double total = 0;
        int tamanho = notas.length;
        for (int i = 0; i < tamanho; i++){
            total += notas[i];
        }
        return total/tamanho;
    }

    static int contarAprovados(int[] notas) {
        int aprovados = 0;
        for (int nota : notas) {
            if (nota >= NOTA_MINIMA) { aprovados++; }
        }
        return aprovados;
    }

    static double percentualAprovados(int[] notas) {
        int totalAprovados = contarAprovados(notas);
        return (totalAprovados/ (double) notas.length) * 100;
    }

    // ---------- não precisa mexer daqui pra baixo ----------
    public static void main(String[] args) {
        int[] turmaA = {80, 45, 50, 30, 95};
        int[] turmaB = {49, 51};
        int[] turmaC = {100};

        checar("media A", 60.0, calcularMedia(turmaA));
        checar("aprovados A", 3, contarAprovados(turmaA));
        checar("percentual A", 60.0, percentualAprovados(turmaA));

        checar("media B", 50.0, calcularMedia(turmaB));
        checar("aprovados B", 1, contarAprovados(turmaB));
        checar("percentual B", 50.0, percentualAprovados(turmaB));

        checar("media C", 100.0, calcularMedia(turmaC));
        checar("aprovados C", 1, contarAprovados(turmaC));
        checar("percentual C", 100.0, percentualAprovados(turmaC));

        checar("media {7, 8, 8}", 23 / 3.0, calcularMedia(new int[]{7, 8, 8}));
    }

    static void checar(String teste, Object esperado, Object obtido) {
        boolean ok = (esperado instanceof Double e && obtido instanceof Double o)
                ? Math.abs(e - o) < 1e-9
                : esperado.equals(obtido);
        System.out.println((ok ? "OK      " : "FALHOU  ") + teste
                + (ok ? "" : "  -> esperado " + esperado + ", obtido " + obtido));
    }
}
