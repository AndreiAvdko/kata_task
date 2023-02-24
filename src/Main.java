import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main
{
    public static void main(String[] args) throws Exception {
        var romNum = new String[]{"I", "V", "X", "L", "C", "D", "M"};
        int[] arabNum;
        arabNum = new int[]{1, 5, 10, 50, 100, 500, 1000};
        // Строка вводимая пользователем
        String str = " ";

        // Считывание из строки
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите вычисляемое выражение: ");
        try
        {
            str = br.readLine();
            str = str.replaceAll("\\s", "");
            System.out.println(calc(str));
        }
        catch(IOException e)
        {
            System.out.println("Ошибка ввода");
        }
    }

    // функция вычисления выражения
    public static double calcExp(String firstNum, String secondNum, char signBetwNum)
    {
        double fN = Double.parseDouble(firstNum);
        double sN = Double.parseDouble(secondNum);
        switch (signBetwNum)
        {
            case '+': return fN + sN;
            case '-': return fN - sN;
            case  '/': return fN / sN;
            case '*': return fN * sN;
            default: return fN;
        }
    }

    // Функция перевода из Римских цифр в арабские
    public static double convertRomInArab (String romNum)
    {
        String romNumber = "IVXLCDM";
        var arabNumber = new int[]{1, 5, 10, 50, 100, 500, 1000};
        double arabNum = 0;
        int currentNum = 0;
        int nextNum = 0;
        for (int i = romNum.toCharArray().length - 1; i >= 0; i--)
        {
            currentNum = arabNumber[romNumber.indexOf(romNum.toCharArray()[i])];
            if (i < romNum.toCharArray().length - 1)
            {
                nextNum = arabNumber[romNumber.indexOf(romNum.toCharArray()[i+1])];
            }

            if (currentNum < nextNum)
            {
                arabNum = arabNum - currentNum;
            }
            else
            {
                arabNum = arabNum + currentNum;
            }
        }
        return arabNum;
    }

    // функция перевода из арабских цифр в римские
    public static String convertArabInRom (int num)
    {
        String romNum = "";
        String romNumber = "IVXLCDM";
        var arabNumber = new int[]{1, 5, 10, 50, 100, 500, 1000};
        for (int i = arabNumber.length - 1; i >= 0; i--)
        {
            while (num/arabNumber[i] > 0)
            {
                num = num - arabNumber[i];
                romNum = romNum + romNumber.toCharArray()[i];
            }
            switch (num)
            {
                case 9: num = num - 9;
                    romNum = romNum + "IX";
                    break;
                case 4: num = num - 4;
                    romNum = romNum + "IV";
                    break;
                default: break;
            }

        }
        return romNum;
    }

    // Функция вычисления выражения, требуется по условию задачи
    public static String calc(String input) throws Exception
    {
        // Строка, представляющая первое число
        String firstNum = "";
        // Строка, представляющая второе число
        String secondNum = "";
        // Индекс знака между числами
        int indexOfNum = 0;
        // массив из символов между знаками
        var mathSign = new String[]{"+", "-", "/", "*"};
        //     Регулярка для арифметического выражения с числами из арабских цифр
        //     ^[0-9]+(-|\+|\*|\/){1}[0-9]+$
        String regexArabNum = "^[0-9]+(-|\\+|\\*|\\/){1}[0-9]+$";
        // Регулярка для арифметического выражения с римскими числами
        // ^M{0,3}(CM|CD|D?C{0,3})?(XC|XL|L?X{0,3})?(IX|IV|V?I{0,3})?(-|\+|\*|\/){1}M{0,3}(CM|CD|D?C{0,3})?(XC|XL|L?X{0,3})?(IX|IV|V?I{0,3})?$
        // Непосредственно для римского числа
        // M{0,3}(CM|CD|D?C{0,3})?(XC|XL|L?X{0,3})?(IX|IV|V?I{0,3})?
        String regexRomNum = "^M{0,3}(CM|CD|D?C{0,3})?(XC|XL|L?X{0,3})?(IX|IV|V?I{0,3})?(-|\\+|\\*|\\/){1}M{0,3}(CM|CD|D?C{0,3})?(XC|XL|L?X{0,3})?(IX|IV|V?I{0,3})?$";

        if (input.matches(regexArabNum))
        {
            // Получаем индекс знака между числами в выражении
            for (String sign : mathSign)
            {
                if (input.contains(sign))
                {
                    // индекс знака между числами
                    indexOfNum = input.indexOf(sign);
                }
            }
            for (int i = 0; i < indexOfNum; i++)
            {
                firstNum += input.toCharArray()[i];
            }
            for (int i = indexOfNum + 1; i < input.toCharArray().length; i++)
            {
                secondNum += input.toCharArray()[i];
            }

            return new String(String.valueOf((int) Math.floor(calcExp(firstNum, secondNum, input.toCharArray()[indexOfNum]))));
        }
        else
        {
            // Проверка на соответствие выражению с римскими числами
            if (input.matches(regexRomNum))
            {
                // Получаем индекс знака между числами в выражении
                for (String sign : mathSign)
                {
                    if (input.contains(sign))
                    {
                        // индекс знака между числами
                        indexOfNum = input.indexOf(sign);
                    }
                }
                for (int i = 0; i < indexOfNum; i++)
                {
                    firstNum += input.toCharArray()[i];
                }
                for (int i = indexOfNum + 1; i < input.toCharArray().length; i++)
                {
                    secondNum += input.toCharArray()[i];
                }
                firstNum = String.valueOf(convertRomInArab(firstNum));
                secondNum = String.valueOf(convertRomInArab(secondNum));

                double result = calcExp(firstNum, secondNum, input.toCharArray()[indexOfNum]);

                if (result <= 0)
                {
                    throw new IOException("Результат оказался меньше нуля.");
                }
                else
                {
                    return convertArabInRom((int)Math.round(result));
                }
            }
            else
            {
                throw new IOException("Неправильный ввод");

            }
        }
    }
}
