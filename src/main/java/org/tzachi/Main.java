package org.tzachi;

import java.util.Scanner;
import org.tzachi.tokenizer.TokenizerException;

/**
 *
 * A simple arithmetic text based calculator.
 *
 * The calculator using Shunting yard algorithm to convert the infix expression to prefix.
 *
 *
 * i = 0
 * j = ++i
 * x = i++ + 5
 * y = 5 + 3 * 10
 * i += y
 *
 * (i=37,j=1,x=6,y=35)
 */
public class Main {

    public static void main(String[] args) {


        CalculatorManager calculator = new CalculatorManager();
        System.out.println("*******************************************************");
        System.out.println("******           Arithmetic Calculator           ******");
        System.out.println("*******************************************************");
        System.out.println("\nEnter expression or empty line to show results and quit");
        Scanner input = new Scanner(System.in);

        String line;
        while (!(line = input.nextLine()).isEmpty()) {
            try {
                calculator.calculate(line);
            } catch (TokenizerException | IllegalArgumentException e) {
                System.out.println("Failed to calculate: " + e.getMessage());
            }
        }
        System.out.println(calculator.getResult());
    }
}