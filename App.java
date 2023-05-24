public class App {
    public static void main(String[] args) {
        String expression = "sin(cos(ln(100)))";
        System.out.println(Calculator.calculate(expression));
    }
}

