import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class Dictionary {
    private AVLTree<String> tree;

    public Dictionary(String s) {
        tree = new AVLTree<String>(new BTNode<>(s));
    }

    public Dictionary() {
        tree = new AVLTree<String>();
    }

    public Dictionary(File f) throws FileNotFoundException {
        tree = new AVLTree<String>();
        Scanner inputFileProcess = new Scanner(f);
        while (inputFileProcess.hasNext()) {
            String word = inputFileProcess.next();
            if (!tree.search(word)) {
                tree.insertAVL(word);
            }
        }
        inputFileProcess.close();
    }

    public void addWord(String s) throws WordAlreadyExistsException {
        if (tree.search(s))
            throw new WordAlreadyExistsException();
        else
            tree.insertAVL(s);
        System.out.println("word added successfully.");

    }

    public boolean findWord(String s) {
        if (tree.search(s)) {
            System.out.println("Word found");
            return true;
        } else {
            System.out.println("Word not found");
            return false;
        }

    }

    public void deleteWord(String s) throws WordNotFoundException {
        if (tree.search(s)) {
            tree.deleteAVL(s);
            System.out.println("Word deleted");
        } else
            throw new WordNotFoundException();
    }
    public String[] findSimilar(String word) {
        return findSimilarWordHelper(tree.root, word);
    }
    public String[] findSimilarWordHelper(BTNode<String> node, String word) {
        SLL<String> similar = new SLL<>();
        if (node != null) {
            String currentWord = node.data;
            if (!currentWord.equals(word)) {
                if (currentWord.length() == word.length()) {
                    int count = 0;
                    for (int i = 0; i < currentWord.length(); i++) {
                        if (currentWord.charAt(i) != word.charAt(i)) {
                            count++;
                        }
                        if (count > 1) {
                            break;
                        }
                    }
                    if (count == 1) {
                        similar.addToTail(currentWord);
                    }
                } else if (currentWord.length() == word.length() + 1) {
                    int count = 0;
                    for (int i = 0; i < currentWord.length() - 1; i++) {
                        if (currentWord.charAt(i) != word.charAt(i)) {
                            count++;
                        }
                        if (count > 0) {
                            break;
                        }
                    }
                    if (count == 0) {
                        similar.addToTail(currentWord);
                    }
                } else if (currentWord.length() == word.length() - 1) {
                    int count = 0;
                    for (int i = 0; i < currentWord.length(); i++) {
                        if (currentWord.charAt(i) != word.charAt(i)) {
                            count++;
                        }
                        if (count > 0) {
                            break;
                        }
                    }
                    if (count == 0) {
                        similar.addToTail(currentWord);
                    }
                }
            }
            // recursively find similar words in the whole tree
            String[] left = findSimilarWordHelper(node.left, word);
            String[] right = findSimilarWordHelper(node.right, word);
            for (String w : left) {
                similar.addToTail(w);
            }
            for (String w : right) {
                similar.addToTail(w);
            }
        }
        return similar.toArray(similar.size()); }
   

   
    public void saveDict(String dictName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(dictName + ".txt"));
        BTNode<String> p = tree.root;
        Stack<BTNode<String>> travStack = new Stack<>();
        while (p != null || !travStack.isEmpty()) {
            while (p != null) {
                travStack.push(p);
                p = p.left;
            }
            p = travStack.pop();
            writer.write(p.data + "\n");
            p = p.right;
        }
        System.out.println("dictionary saved in "+dictName+" successfully");

        writer.close();
    }
}
