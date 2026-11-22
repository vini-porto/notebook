/*
 * S3 · NÍVEL 1: complete os constructors
 *
 * A classe Contato fica DENTRO da classe do exercício ("static class"),
 * só para organizar um exercício por arquivo. Funciona igual a uma classe normal.
 *
 * Complete os 3 constructors (TODOs). Regras:
 *  - Só o constructor COMPLETO faz atribuições com this.campo = ...
 *  - Os outros dois só chamam outro constructor com this(...), completando o que falta:
 *      telefone que falta -> "sem telefone"
 *      email que falta    -> "sem email"
 *  - Nada de textos repetidos: use as constantes que já estão declaradas.
 */
public class Nivel1_Contato {

    static class Contato {
        static final String SEM_TELEFONE = "sem telefone";
        static final String SEM_EMAIL = "sem email";

        String nome;
        String telefone;
        String email;

        // Constructor completo
        Contato(String nome, String telefone, String email) {
            this.nome = nome;
            this.telefone = telefone;
            this.email = email;
        }

        // Só nome e telefone
        Contato(String nome, String telefone) {
            this(nome, telefone, SEM_EMAIL);
        }

        // Só nome
        Contato(String nome) {
            this(nome, SEM_TELEFONE);
        }
    }

    // ---------- não precisa mexer daqui pra baixo ----------
    public static void main(String[] args) {
        Contato c1 = new Contato("Ana", "705-555-0101", "ana@mail.com");
        checar("c1.nome", "Ana", c1.nome);
        checar("c1.telefone", "705-555-0101", c1.telefone);
        checar("c1.email", "ana@mail.com", c1.email);

        Contato c2 = new Contato("Bruno", "705-555-0202");
        checar("c2.nome", "Bruno", c2.nome);
        checar("c2.telefone", "705-555-0202", c2.telefone);
        checar("c2.email", "sem email", c2.email);

        Contato c3 = new Contato("Carla");
        checar("c3.nome", "Carla", c3.nome);
        checar("c3.telefone", "sem telefone", c3.telefone);
        checar("c3.email", "sem email", c3.email);
    }

    static void checar(String teste, Object esperado, Object obtido) {
        boolean ok = esperado.equals(obtido);
        System.out.println((ok ? "OK      " : "FALHOU  ") + teste
                + (ok ? "" : "  -> esperado " + esperado + ", obtido " + obtido));
    }
}
