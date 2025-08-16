import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws Exception {
        Dictionary dict = new Dictionary();
        
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println("Welcome to the Dictionary main driver.");

        while (true) {
            System.out.println();

            System.out.println("1. Load a dictionary from a file");
            System.out.println("2. Check a word in the dictionary");
            System.out.println("3. Add a new word to the dictionary");
            System.out.println("4. Remove a word from the dictionary");
            System.out.println("5. Search for similar words in the dictionary");
            System.out.println("6. Save the dicitionary to a text file");
            System.out.println("7. Exit");
            System.out.println("-----------------------------------------------");
            System.out.println();

            System.out.print("Choose an action> ");
            int choose = input.nextInt();
            if (choose == 1) {
                try {
                    System.out.print("Enter the filename> ");
                    String file = input.next();

                    System.out.println("loading...");
                    dict = new Dictionary(new File(file));
                    System.out.println("dictionary loaded successfully");
                } catch (IOException e) {
                    System.out.println("Error: file does not found");
                }
            }else if (choose == 2) {
                System.out.print("Check word> ");
                String w = input.next();
                dict.findWord(w);
                
            }  else if (choose == 3) {
                ;
                System.out.print("Enter the word> ");
                String w = input.next();
                try {
                    dict.addWord(w);
                } catch (WordAlreadyExistsException e) {
                    e.printStackTrace();
                }

            } else if (choose == 4) {
                System.out.print("Enter word to remove> ");
                String removedWord = input.next();
                try {
                    dict.deleteWord(removedWord);
                } catch (WordNotFoundException e) {
                    e.printStackTrace();
                }

            }

            else if (choose == 5) {

                System.out.print("Enter a word> ");
                String w = input.next();
                String[] words = dict.findSimilar(w);

                if (words.length == 0) {
                    System.out.println("No similar words found");
                } else {
                    String s = "";
                    for (int i = 0; i < words.length - 1; i++) {
                        s += words[i] + ", ";
                    }
                    s += words[words.length - 1];
                    System.out.println(s);
                }

            } else if (choose == 6) {
                System.out.print("Enter the new file name> ");
                String f = input.next();
                dict.saveDict(f);
            } else if (choose == 7) {
                System.out.println("Good Bye");
                input.close();
                break;
            } else {
                System.out.println("Error: Wrong input. Please try again!");
            }
        }
    }
}
