
/*
 * S1 · NÍVEL 2: escreva o método do zero
 *
 * Escreva o método converterSegundos(int totalSegundos), que recebe um total de
 * segundos (>= 0) e retorna uma String no formato "H h M min S s".
 *
 *   converterSegundos(3725) -> "1 h 2 min 5 s"
 *   converterSegundos(59)   -> "0 h 0 min 59 s"
 *
 * Regras:
 *  - Use / e % (nada de loops). A divisão inteira aqui é sua AMIGA.
 *  - Nada de números mágicos: crie constantes para 60 e 3600
 *    (ex.: static final int SEGUNDOS_POR_MINUTO = 60;).
 *
 * Quando escrever o método, apague o comentário do main e rode.
 */
public class Nivel2_Segundos {

    static final int SEGUNDOS_POR_MINUTO = 60;
    static final int SEGUNDOS_POR_HORA = 3600;

    public static String converterSegundos(int totalSeg) {
        int segundos = totalSeg % SEGUNDOS_POR_MINUTO;
        int horas = totalSeg / SEGUNDOS_POR_HORA;
        int minutos = (totalSeg % SEGUNDOS_POR_HORA) / SEGUNDOS_POR_MINUTO;

        return horas + " h "+ minutos + " min " + segundos + " s";
    }

    public static void main(String[] args) {
        // Descomente depois de escrever o método:
        checar("3725", "1 h 2 min 5 s", converterSegundos(3725));
        checar("59", "0 h 0 min 59 s", converterSegundos(59));
        checar("3600", "1 h 0 min 0 s", converterSegundos(3600));
        checar("7384", "2 h 3 min 4 s", converterSegundos(7384));
        checar("0", "0 h 0 min 0 s", converterSegundos(0));
    }

    static void checar(String teste, Object esperado, Object obtido) {
        boolean ok = (esperado instanceof Double e && obtido instanceof Double o)
                ? Math.abs(e - o) < 1e-9
                : esperado.equals(obtido);
        System.out.println((ok ? "OK      " : "FALHOU  ") + teste
                + (ok ? "" : "  -> esperado \"" + esperado + "\", obtido \"" + obtido + "\""));
    }
}
