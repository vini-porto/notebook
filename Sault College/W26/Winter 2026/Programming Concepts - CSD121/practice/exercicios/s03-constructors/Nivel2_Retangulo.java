/*
 * S3 · NÍVEL 2: escreva a classe do zero
 *
 * Escreva a classe Retangulo (dentro de Nivel2_Retangulo, como "static class Retangulo")
 * com:
 *
 *  Fields:   largura e altura (double)
 *
 *  Constructors (use this(...) para não repetir atribuições):
 *    Retangulo(double largura, double altura)  -> retângulo normal
 *    Retangulo(double lado)                    -> um QUADRADO com esse lado
 *    Retangulo()                               -> um quadrado de lado 1 (use uma constante LADO_PADRAO)
 *
 *  Métodos (de instância, SEM static):
 *    double area()          -> largura * altura
 *    double perimetro()     -> 2 * (largura + altura)
 *    boolean ehQuadrado()   -> true se largura e altura forem iguais
 *
 * Quando terminar, descomente o main e rode.
 */
public class Nivel2_Retangulo {

    public static class Retangulo {
        static final int LADO_PADRAO = 1;

        private final double largura;
        private final double altura;

        Retangulo(double largura, double altura){
            this.largura = largura;
            this.altura = altura;
        }
        Retangulo(double lado) {
            this(lado, lado);
        }
        Retangulo() {
            this(LADO_PADRAO);
        }

        double area() {
            return largura * altura;
        }
        double perimetro(){
            return 2 * (largura + altura);
        }
        boolean ehQuadrado(){
            return largura == altura;
        }
    }

    public static void main(String[] args) {
         Retangulo r = new Retangulo(3, 4);
         checar("r.area()", 12.0, r.area());
         checar("r.perimetro()", 14.0, r.perimetro());
         checar("r.ehQuadrado()", false, r.ehQuadrado());

         Retangulo q = new Retangulo(5);
         checar("q.largura", 5.0, q.largura);
         checar("q.altura", 5.0, q.altura);
         checar("q.area()", 25.0, q.area());
         checar("q.ehQuadrado()", true, q.ehQuadrado());

         Retangulo p = new Retangulo();
         checar("p.area()", 1.0, p.area());
         checar("p.perimetro()", 4.0, p.perimetro());
         checar("p.ehQuadrado()", true, p.ehQuadrado());
    }

    static void checar(String teste, Object esperado, Object obtido) {
        boolean ok = (esperado instanceof Double e && obtido instanceof Double o)
                ? Math.abs(e - o) < 1e-9
                : esperado.equals(obtido);
        System.out.println((ok ? "OK      " : "FALHOU  ") + teste
                + (ok ? "" : "  -> esperado " + esperado + ", obtido " + obtido));
    }
}
