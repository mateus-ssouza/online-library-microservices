package online.library.book.utils;

import java.util.Random;

public class CopyCodeGenerator {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 3;
    private static final int NUMBER_LENGTH = 4;
    private static final Random RANDOM = new Random();

    public static String generateCopyCode() {
        
        // Gera a parte com letras
        StringBuilder codePart = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            codePart.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
        }

        // Gera a parte numérica
        StringBuilder numberPart = new StringBuilder(NUMBER_LENGTH);
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            numberPart.append(RANDOM.nextInt(10)); // Adiciona números de 0 a 9
        }

        // Combina as duas partes no formato XXX0000
        return codePart.toString() + "." + numberPart.toString();
    }
}