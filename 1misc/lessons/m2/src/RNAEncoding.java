import java.util.Scanner;

public class RNAEncoding {
    //      * "RNA encoding"
//        * Write a method that when given a sequence of RNA bases represented as a string with characters
//          A, U, C, G, it returns the transcribed RNA strand
//          * A pairs with U
//          * C pairs with G
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        System.out.print("RNA Bases (1): ");
        String rnaSequence1 = console.nextLine();
        System.out.println("Transcribed (1): " + transcribeRNASequence(rnaSequence1));
        System.out.println();

        System.out.print("RNA Bases (2): ");
        String rnaSequence2 = console.nextLine();
        System.out.println("Transcribed (2): " + transcribeRNASequence(rnaSequence2));
        System.out.println();

        int equalityCount = 0;

        for (int i = 0; i < rnaSequence1.length(); i++) {
            switch (rnaSequence1.charAt(i)) {
                case 'A':
                case 'U':
                    if (rnaSequence2.charAt(i) == 'A' || rnaSequence2.charAt(i) == 'U') {
                        equalityCount++;
                    }
                    break;
                case 'C':
                case 'G':
                    if (rnaSequence2.charAt(i) == 'C' || rnaSequence2.charAt(i) == 'G') {
                        equalityCount++;
                    }
                    break;
                default:
                    break;
            }
        }

        System.out.print("There are " + equalityCount + " bases that are equal. ");
        if (equalityCount == rnaSequence1.length()) {
            System.out.println("The two sequences are equal.");
        } else {
            System.out.println("The two sequences are not equal.");
        }

    }

    private static String transcribeRNASequence(String rnaSequence) {
        char[] rnaCharArray = new char[rnaSequence.length()]; // character array

        // Fill array
        for (int i = 0; i < rnaCharArray.length; i++) {
            rnaCharArray[i] = rnaSequence.charAt(i);
        }

        // Transcribe
        for (int i = 0; i < rnaSequence.length(); i++) {
            switch (rnaSequence.charAt(i)) {
                case 'A':
                    rnaCharArray[i] = 'U';
                    break;
                case 'U':
                    rnaCharArray[i] = 'A';
                    break;
                case 'C':
                    rnaCharArray[i] = 'G';
                    break;
                case 'G' :
                    rnaCharArray[i] = 'C';
                    break;
                default:
                    rnaCharArray[i] = rnaSequence.charAt(i);
                    break;
            }
        }

        String transcribedRNASequence = String.valueOf(rnaCharArray);

        return transcribedRNASequence;
    }

    //* Examples
//          * "AACG" -> "UUGC"
//          * "GGAAUU" -> "CCUUAA"
//          * "GCUA" -> "CGAU"
//          * Call the method multiple times to ensure that it's working correctly
//            * Use the IntelliJ debugger to step through your code
//* Can you write a method that accepts two strings compares them and outputs a message
//    about the equality of the two strings to help facilitate testing?

}
