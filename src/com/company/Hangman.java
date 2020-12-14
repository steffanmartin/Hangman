package com.company;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hangman {
    static List<String> words = new ArrayList<>();
    static List<Character> chars = new ArrayList<>();
    static String word;
    static int count;
    static final int LIVES = 5;

    public static void print(){
        System.out.println("Lives: " + (LIVES - count));
        chars.forEach(ch -> System.out.print(ch + " "));
        System.out.println();
        System.out.println("+--------+");
        System.out.println("|        |");
        System.out.print("|");
        if (count > 0) System.out.print(String.format("%9s", "o"));
        System.out.println();
        System.out.print("|");
        if (count > 1) System.out.print(String.format("%10s", "/|\\"));
        System.out.println();
        System.out.print("|");
        if (count > 2) System.out.print(String.format("%9s", "|"));
        System.out.println();
        System.out.print("|");
        if (count > 3) System.out.print(String.format("%9s", "/ "));
        if (count > 4) System.out.print("\\");
        System.out.println();
        System.out.println("|");
        System.out.println("============");
        System.out.println();
        if (count >= 5){
            for (char ch : word.toCharArray()){
                System.out.print(ch + " ");
            }
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
        // Prints hint character if count is equal to 5
        if (count == 5){
            for (int i = 0; i < word.length(); i++){
                if (!chars.contains(word.charAt(i))){
                    System.out.println("Hint: " + word.charAt(i));
                    break;
                }
            }
        }
    }

    public static void check(char ch){
        if (chars.contains(ch)) {
            return;
        } else if (!word.contains(String.valueOf(ch))){
            count++;
        }
        chars.add(ch);
    }

    private static List<String> getWords(int n){
        List<String> _words = new ArrayList<String>();
        String fileName = "C:\\Projects\\Hangman\\src\\com\\company\\";
        //String fileName = (n == 1)? "C:\\Projects\\src\\com\\company\\movies" : "com/company/celebrities";
        if (n == 1)
            fileName += "celebrities";
        else if (n == 2)
            fileName += "countries";
        else if (n == 3)
            fileName += "movies";
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            _words = lines.collect(Collectors.toList());
        } catch (Exception ex){
            System.out.println("Please select a category");
        }
        return _words;
    }

    public static void main(String[] args) {
        System.out.println("Hangman");
        for(; ;) {
            chars.clear();
            Scanner sc = new Scanner(System.in);
            System.out.println("Select category: \n1 Celebrities \n2 Countries \n3 Movies");
            do {
                int n = sc.nextInt();
                words = getWords(n);
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
