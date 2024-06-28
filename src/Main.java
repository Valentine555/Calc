import java.util.Scanner;

public class Main {
    private static final String[] romanNumerals = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    private static final String[] romanNumeralsToArabic = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Введите выражение (или 'exit' для выхода): ");
            String x = scanner.nextLine().trim();

            if (x.equalsIgnoreCase("exit")) {
                running = false;
                System.out.println("Программа завершена.");
            } else {
                try {
                    String result = calc(x);
                    System.out.println(result);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        scanner.close();
    }

    public static String calc(String x) {
            if (!x.contains("+") && !x.contains("-") && !x.contains("*") && !x.contains("/")) {
                throw new IllegalArgumentException("Строка не является математической операцией");
            }

            String[] parts = x.split("[\\+\\-\\*/]");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Формат математической операции не удовлетворяет заданию");
            }

            boolean Roman = isRoman(parts[0]) && isRoman(parts[1]);
            int operand1, operand2;
          if (Roman) {
            operand1 = romanToArabic(parts[0].trim());
            operand2 = romanToArabic(parts[1].trim());
        } else {
            try {
                operand1 = Integer.parseInt(parts[0].trim());
                operand2 = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                if (isRoman(parts[0].trim()) && isArabic(parts[1].trim()) || isArabic(parts[0].trim()) && isRoman(parts[1].trim())){
                    throw new IllegalArgumentException("Используются одновременно разные системы исчисления");
                }
                throw new IllegalArgumentException("Операнды должны быть числами");
            }
        }


            if (operand1 < 1 || operand1 > 10 || operand2 < 1 || operand2 > 10) {
                throw new IllegalArgumentException("Операнды должны быть числами от 1 до 10");
            }

            char operator = x.charAt(parts[0].length());
            int result = P(operand1, operand2, operator);
         if (Roman) {
            if (result <= 0) {
                throw new IllegalArgumentException("В римской системе нет отрицательных чисел");
            }
            return arabicToRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    private static int P(int operand1, int operand2, char operator) {
        int result = 0;
        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                result = operand1 / operand2;
                break;
            default:
                throw new IllegalArgumentException("Неизвестный оператор");
        }
        return result;
    }


private static boolean isRoman (String s) {
    for (String roman : romanNumerals) {
        if (roman.equals(s)) {
            return true;
        }
    }
    return false;
}
    private static boolean isArabic (String s) {
        for (String roman : romanNumeralsToArabic) {
            if (roman.equals(s)) {
                return true;
            }
        }
        return false;
    }

private static int romanToArabic(String s) {
    for (int i = 0; i < romanNumerals.length; i++) {
        if (romanNumerals[i].equals(s)) {
            return i + 1;
        }
    }
    throw new IllegalArgumentException("Неверное римское число");
}

private static String arabicToRoman(int number) {
    if (number < 1 || number > 10) {
        throw new IllegalArgumentException("Римские числа должны быть от 1 до 10");
    }
    return romanNumerals[number - 1];
}


}