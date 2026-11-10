
/*
 * S2 · NÍVEL 2: detetive de erros de run-time
 *
 * Os 5 métodos abaixo COMPILAM, mas quebram (exception) ou erram com certas entradas.
 * Rode o main. Para cada FALHOU, a saída mostra:
 *     o tipo da exception · a mensagem · (Arquivo.java:LINHA onde quebrou)
 *
 * Para cada caso:
 *  1. Leia a saída e vá até a linha indicada.
 *  2. Descubra a causa (índice? null? divisão por zero? texto vazio?).
 *  3. Conserte para que o método siga a REGRA escrita no comentário dele.
 *  4. Deixe um comentário curto explicando o PORQUÊ do conserto.
 */
public class Nivel2_Detetive {

    // Retorna o último elemento. REGRA: array vazio -> retorna -1.
    static int ultimoElemento(int[] numeros) {
        if (numeros.length == 0){
            return -1;
        }
        return numeros[numeros.length - 1]; // indice começa em 0 e por isso o ultimo elemento é sempre o tamanho -1
    }

    // Soma todos os elementos. REGRA: array vazio -> 0.
    static int somaTodos(int[] numeros) {
        int soma = 0;
        for (int i = 0; i < numeros.length; i++) { // Como o indice começa em 0 e ia até igual o tamanho do array acabava sempre buscando um a mais doque o array comportava
            soma += numeros[i];
        }
        return soma;
    }

    // Quantidade de letras do nome. REGRA: nome null -> 0.
    static int tamanhoDoNome(String nome) {
        if (nome == null) { // sem checagem de null o codigo tem a chance de quebrar por conta do usuario.
            return 0;
        }
        return nome.length();
    }

    // Quanto cada aluno paga, dividindo o total igualmente (com centavos).
    // REGRA: 0 alunos -> 0.0. Ex.: mediaPorAluno(100, 3) -> 33.333...
    static double mediaPorAluno(int total, int alunos) {
        if (alunos == 0) { // divisão invalida por 0
            return 0.0;
        }
        return (double) total / alunos;
    }

    // Primeira letra do texto. REGRA: texto vazio "" -> '?'.
    static char primeiraLetra(String texto) {
        if (texto.isEmpty()){ // checar se a String esta vazia antes extrair character
            return '?';
        }
        return texto.charAt(0);
    }

    // ---------- não precisa mexer daqui pra baixo ----------
    public static void main(String[] args) {
        caso("ultimoElemento({4, 8, 15})", 15, () -> ultimoElemento(new int[]{4, 8, 15}));
        caso("ultimoElemento({})", -1, () -> ultimoElemento(new int[]{}));
        caso("somaTodos({1, 2, 3})", 6, () -> somaTodos(new int[]{1, 2, 3}));
        caso("somaTodos({})", 0, () -> somaTodos(new int[]{}));
        caso("tamanhoDoNome(\"Ana\")", 3, () -> tamanhoDoNome("Ana"));
        caso("tamanhoDoNome(null)", 0, () -> tamanhoDoNome(null));
        caso("mediaPorAluno(100, 4)", 25.0, () -> mediaPorAluno(100, 4));
        caso("mediaPorAluno(100, 3)", 100 / 3.0, () -> mediaPorAluno(100, 3));
        caso("mediaPorAluno(100, 0)", 0.0, () -> mediaPorAluno(100, 0));
        caso("primeiraLetra(\"Java\")", 'J', () -> primeiraLetra("Java"));
        caso("primeiraLetra(\"\")", '?', () -> primeiraLetra(""));
    }

    // Roda um caso e, se der exception, mostra o tipo, a mensagem e a linha.
    static void caso(String teste, Object esperado, java.util.function.Supplier<Object> codigo) {
        try {
            Object obtido = codigo.get();
            boolean ok = (esperado instanceof Double e && obtido instanceof Double o)
                    ? Math.abs(e - o) < 1e-9
                    : esperado.equals(obtido);
            System.out.println((ok ? "OK      " : "FALHOU  ") + teste
                    + (ok ? "" : "  -> esperado " + esperado + ", obtido " + obtido));
        } catch (RuntimeException ex) {
            // Mostra a primeira linha do stack trace que está no SEU arquivo
            StackTraceElement onde = ex.getStackTrace()[0];
            for (StackTraceElement linha : ex.getStackTrace()) {
                if ("Nivel2_Detetive.java".equals(linha.getFileName())) {
                    onde = linha;
                    break;
                }
            }
            System.out.println("FALHOU  " + teste + "  -> " + ex.getClass().getSimpleName()
                    + ": " + ex.getMessage() + "  (" + onde.getFileName() + ":" + onde.getLineNumber() + ")");
        }
    }
}
