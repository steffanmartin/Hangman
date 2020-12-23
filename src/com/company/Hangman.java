package com.company;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hangman {
    static List<String> words = new ArrayList<>();
    static List<Character> chars = new ArrayList<>();
    static String word;
    static int count;
    static final int LIVES = 6;
    // Static method that prints Hangman game
    public static void print(){
        // Prints player's remaining lives
        System.out.println("Lives: " + (LIVES - count));
        // Displays list of chars guessed by the player
        chars.forEach(ch -> System.out.print(ch + " "));
        // Prints Hangman for different values of count
        System.out.println();
        System.out.println("+----------+");
        System.out.println("| /        |");
        System.out.print("|/");
        if (count > 0) System.out.print(String.format("%10s", "O"));
        System.out.println();
        System.out.print("|");
        if (count > 1) System.out.print(String.format("%10s", "/"));
        if (count > 2) System.out.print("|");
        if (count > 3) System.out.print("\\");
        System.out.println();
        System.out.print("|");
        if (count > 4) System.out.print(String.format("%10s", "/"));
        if (count > 5) System.out.print(" \\");
        System.out.println();
        System.out.println("|");
        System.out.println("==============");
        System.out.println();
        // Prints hint character if count is equal to 4 and less than
        // half of characters guessed.
        if (count == LIVES - 1 && rem() > word.length() / 2){
            for (int i = 0; i < word.length(); i++){
                if (!chars.contains(word.charAt(i))){
                    System.out.println("Hint: " + word.charAt(i));
                    break;
                }
            }
        }
        // Prints entire word if count is equal to 5
        if (count == LIVES){
            for (char ch : word.toCharArray()){
                System.out.print(ch + " ");
            }
        // Prints guessed characters in word if count is less than 5
        } else {
            for (char ch : word.toCharArray()) {
                if (chars.contains(ch)) {
                    System.out.print(ch + " ");
                } else {
                    String s = (ch == ' ') ? " " : "_ ";
                    System.out.print(s);
                }
            }
        }
        System.out.println();
    }
    // Static method that returns the remaining number of characters
    public static int rem(){
        int rem = word.length();
        for (int i = 0; i < word.length(); i++){
            if (chars.contains(word.charAt(i))) rem--;
        }
        return rem;
    }
    // Static method that checks if the word contains the guessed character
    public static void check(char ch){
        if (chars.contains(ch)) {
            return;
        } else if (!word.contains(String.valueOf(ch))){
            count++;
        }
        chars.add(ch);
    }
    // Loads words from different categories into list of Strings
    private static List<String> getWords(int n){
        List<String> _words = new ArrayList<String>();
        String fileName = "C:\\Projects\\Hangman\\src\\com\\company\\";
        if (n == 1)
            fileName += "celebrities";
        else if (n == 2)
            fileName += "countries";
        else if (n == 3)
            fileName += "movies";
        else if (n == 4)
            fileName += "tvshows";
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            _words = lines.collect(Collectors.toList());
        } catch (Exception ex){
            System.out.println("Please select a category");
        }
        return _words;
    }
    // Main method that simulates the Hangman game
    public static void main(String[] args) {
        System.out.println("+-------------+");
        System.out.println("|   Hangman   |");
        System.out.println("+-------------+");
        for(; ;) {
            words.clear();
            chars.clear();
            Scanner sc = new Scanner(System.in);
            System.out.println("Select category: \n1 Celebrities \n2 Countries \n3 Movies \n4 TV Shows " +
                    "\n(Press X to exit game)");
            do {
                try {
                    char n = Character.toUpperCase(sc.next().charAt(0));
                    if (n == 'X'){
                        System.exit(0);
                    } else {
                        words = getWords(Integer.parseInt(Character.toString(n)));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please select a category");
                    continue;
                }
            } while (words.isEmpty());
            Random random = new Random();
            word = (words.get(random.nextInt(words.size()))).toUpperCase();
            List<Character> meme = new ArrayList<>();
            for (char cs : word.toCharArray()) {
                meme.add(cs);
            }
            chars.add(' ');
            count = 0;
            while (count < LIVES) {
                print();
                Scanner sn = new Scanner(System.in);
                char ch = Character.toUpperCase(sc.next().charAt(0));
                check(ch);
                if (chars.containsAll(meme)) {
                    System.out.println("Well done!");
                    break;
                }
            }
            print();
            System.out.println("Play again? (Y/N)");
            char c = sc.next().charAt(0);
            if (c == 'N' | c == 'n'){
                System.exit(0);
            }
        }
    }
}
