import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class Words {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Set<String> dictionary = new TreeSet<String>();
		Set<String> wordCount = new TreeSet<String>();
		List<List<String>> results = new ArrayList<>();
		readWordListFromFile(dictionary);
		searchWords(dictionary, results);
		writeWordCombinationsInFile(wordCount, results);
		printTotalCountOfWords(wordCount);
	}

	private static void printTotalCountOfWords(Set<String> wordCount) {
		System.out.println("Total number of words which are part of wordlist file " + wordCount.size());
	}

	private static void writeWordCombinationsInFile(Set<String> wordCount, List<List<String>> results)throws FileNotFoundException {
		try (PrintWriter out = new PrintWriter("WordCombinations.txt")) {
			for (List<String> result : results) {
				if (result.size() > 1) {
					String actualWord = "";
					for (String word : result) {
						actualWord = actualWord + word;
					}
					out.printf("(\'" + actualWord + "\' ,");
					wordCount.add(actualWord);
					for (String word : result) {
						if (word.equalsIgnoreCase(result.get(result.size() - 1))) {
							out.printf("\'" + word + "\' )");
						} else {
							out.printf("\'" + word + "\' ,");
						}
					}
					out.println("(" + result.size() + " words)");
				}
			}
		}
	}

	private static void searchWords(Set<String> dictionary, List<List<String>> results) {
		Iterator<String> it = dictionary.iterator();
		while (it.hasNext()) {
			search(it.next(), dictionary, new Stack<String>(), results);
		}
	}

	private static void readWordListFromFile(Set<String> dictionary) throws FileNotFoundException {
		Scanner filescan = new Scanner(new File("wordlist.txt"));
		while (filescan.hasNext()) {
			dictionary.add(filescan.nextLine().toLowerCase());
		}
	}

	public static void search(String input, Set<String> dictionary, Stack<String> words, List<List<String>> results) {
		for (int i = 0; i < input.length(); i++) {
			String substring = input.substring(0, i + 1);
			if (dictionary.contains(substring)) {
				words.push(substring);
				if (i == input.length() - 1) {
					results.add(new ArrayList<String>(words));
				} else {
					search(input.substring(i + 1), dictionary, words, results);
				}
				words.pop();
			}
		}
	}
}
