/*
 * S3 · NÍVEL 3: mini-problema (constructors + static da S1 + exceptions)
 *
 * Um banco precisa de uma classe Conta (escreva como "static class Conta"):
 *
 *  Fields: titular (String), numero (int), saldo (double)
 *
 *  1. NÚMERO AUTOMÁTICO: cada conta nova recebe o próximo número, começando em 1
 *     (1ª conta criada -> 1, 2ª -> 2, ...). Quem cria a conta NÃO passa o número.
 *     Dica: lembra do Contador do diagnóstico? (static vs instância)
 *
 *  2. Constructors:
 *       Conta(String titular, double saldoInicial)
 *       Conta(String titular)                       -> saldo inicial 0 (use this(...))
 *     Se saldoInicial for negativo, lance uma exception:
 *       throw new IllegalArgumentException("Saldo inicial não pode ser negativo");
 *     (Pense: a conta que falhou deve gastar um número? O certo é NÃO gastar.)
 *
 *  3. Métodos:
 *       void depositar(double valor)    -> soma ao saldo (ignore valores <= 0)
 *       boolean sacar(double valor)     -> se tiver saldo suficiente, tira e retorna true;
 *                                          senão não mexe em nada e retorna false
 *
 * Quando terminar, descomente o main e rode.
 */
public class Nivel3_Conta {

    public static class Conta {
        private static int ultimoNumero;

        private String titular;
        private int numero;
        private double saldo;

        Conta(String titular, double saldoInicial) {
            if (saldoInicial < 0) {
                throw new IllegalArgumentException("Saldo inicial não pode ser negativo");
            }
            this.titular = titular;
            this.saldo = saldoInicial;
            ultimoNumero++;
            numero = ultimoNumero;
        }
        Conta(String titular) {
            this(titular, 0);
        }

        void depositar(double valor) {
            if (valor > 0) {
                saldo += valor;
            }
        }
        boolean sacar(double valor) {
            if (saldo >= valor && valor > 0) {
                saldo -= valor;
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {

         Conta a = new Conta("Ana", 100);
         Conta b = new Conta("Bruno");
         checar("a.numero", 1, a.numero);
         checar("b.numero", 2, b.numero);
         checar("a.saldo", 100.0, a.saldo);
         checar("b.saldo", 0.0, b.saldo);

         checar("saldo negativo lança exception", true,
                 lancaExcecao(() -> new Conta("Carla", -50)));
         Conta d = new Conta("Diego", 10);
         checar("conta depois da falha recebe o número 3", 3, d.numero);

         a.depositar(50);
         checar("depositar 50", 150.0, a.saldo);
         a.depositar(-20);
         checar("depositar negativo é ignorado", 150.0, a.saldo);
         checar("sacar 30 (tem saldo)", true, a.sacar(30));
         checar("saldo depois do saque", 120.0, a.saldo);
         checar("sacar 500 (sem saldo)", false, a.sacar(500));
         checar("saldo não muda", 120.0, a.saldo);
         checar("b.saldo não foi afetado", 0.0, b.saldo);
    }

    static boolean lancaExcecao(Runnable codigo) {
        try {
            codigo.run();
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    static void checar(String teste, Object esperado, Object obtido) {
        boolean ok = (esperado instanceof Double e && obtido instanceof Double o)
                ? Math.abs(e - o) < 1e-9
                : esperado.equals(obtido);
        System.out.println((ok ? "OK      " : "FALHOU  ") + teste
                + (ok ? "" : "  -> esperado " + esperado + ", obtido " + obtido));
    }
}
