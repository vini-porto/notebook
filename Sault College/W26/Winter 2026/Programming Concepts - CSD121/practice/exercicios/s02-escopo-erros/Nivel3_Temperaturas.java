/*
 * S2 · NÍVEL 3: mini-problema (escopo + import static + S1)
 *
 * Uma estação registra a temperatura máxima de cada dia num int[].
 * Escreva os 4 métodos. REGRA GERAL: se o array estiver vazio, todos retornam 0
 * (ou 0.0 para a média), sem quebrar.
 *
 *  1. maxima(int[] t)              -> a maior temperatura
 *  2. minima(int[] t)              -> a menor temperatura
 *  3. amplitude(int[] t)           -> máxima - mínima (REUSE os métodos 1 e 2)
 *  4. media(int[] t)               -> média com casas decimais (cuidado: S1!)
 *
 * Regras de código:
 *  - Use "import static" para usar max(a, b) e min(a, b) do Math SEM escrever "Math."
 *    (o import vai no topo do arquivo, antes de "public class").
 *  - Nada de números mágicos.
 */

import static java.lang.Math.*;

public class Nivel3_Temperaturas {

    public static int maxima(int[] t){
        if (t.length == 0){
            return 0;
        }
        int maxima = t[0];
        for (int temp : t){
            maxima = max(temp, maxima);
        }
        return maxima;
    }

    public static int minima(int[] t){
        if (t.length == 0){
            return 0;
        }
        int minima = t[0];
        for (int temp : t){
            minima = min(temp, minima);
        }
        return minima;
    }
    public static int amplitude(int[] t){
        int maior = maxima(t);
        int menor = minima(t);

        return maior - menor;
    }

    public static double media(int[] t){
        if (t.length == 0){
            return 0.0;
        }
        double soma = 0.0;
        for (int temp : t) {
            soma += temp;
        }
        return soma / t.length;
    }

    public static void main(String[] args) {
        int[] semana = {12, -3, 7, 21, 0, 15, 4};
        int[] vazio = {};
        int[] umDia = {-8};

        // Descomente depois de escrever os métodos (selecione e aperte Ctrl+/):
        checar("maxima semana", 21, maxima(semana));
        checar("minima semana", -3, minima(semana));
        checar("amplitude semana", 24, amplitude(semana));
        checar("media semana", 8.0, media(semana));
        checar("maxima umDia", -8, maxima(umDia));
        checar("minima umDia", -8, minima(umDia));
        checar("amplitude umDia", 0, amplitude(umDia));
        checar("media {1, 2}", 1.5, media(new int[]{1, 2}));
        checar("maxima vazio", 0, maxima(vazio));
        checar("amplitude vazio", 0, amplitude(vazio));
        checar("media vazio", 0.0, media(vazio));
    }

    static void checar(String teste, Object esperado, Object obtido) {
        boolean ok = (esperado instanceof Double e && obtido instanceof Double o)
                ? Math.abs(e - o) < 1e-9
                : esperado.equals(obtido);
        System.out.println((ok ? "OK      " : "FALHOU  ") + teste
                + (ok ? "" : "  -> esperado " + esperado + ", obtido " + obtido));
    }
}
