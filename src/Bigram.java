import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * CS 143 Assignment 5
 * 
 * Your Name Here
 *
 */
public class Bigram {
	// TODO: add member fields! You may have more than one.
	// You will probably want to use some kind of Map!
	public Set<String> set = new HashSet<>();
	public Map<String, ArrayList<String>> gMap = new HashMap<>();	
	
	/**
	 * Create a new bigram model based on the text given as a String argument.
	 * See the assignment for more details (and also check out the Wikipedia
	 * article on bigrams).
	 * 
	 * @param s
	 *            text
	 */
	public Bigram(String s) {
		// Create a Set of Bigram of a String
		createSetBigram(s);
		
		// Create a Map of Bigram of a String
		createMapBigram(s);
	}

	/**
	 * Check to see whether the sentence is possible according to the bigram
	 * model. A sentence is possible if each bigram in the sentence was seen in
	 * the text that was passed to the constructor.
	 * 
	 * @param s
	 *            Sentence
	 * @return true if possible, false if not possible (some transition does not
	 *         exist in the model as constructed)
	 */
	public boolean check(String s) {		
		Scanner sc = new Scanner(s);
		String previous = sc.next();
		while(sc.hasNext()){
			String next = sc.next();
			String checkInSet = previous + " " + next;			
			if(set.contains(checkInSet)){
				previous = next;
			}else{					
				return false;			
			}
		}
		return true;
	}
	
	/**
	 * Create a Set of Bigram of a String
	 * @param s
	 */
	public void createSetBigram(String s){		
		Scanner sc = new Scanner(s);		
		String previous = sc.next();
		while(sc.hasNext()){
			String next = sc.next();
			String cat = previous + " " + next;
			this.set.add(cat);
			previous = next;
		}
	}
	
	/**
	 * Create a Map of Bigram of a String
	 * @param s
	 */
	public void createMapBigram(String s){		
		Scanner sc = new Scanner(s);		
		String previous = sc.next();
		while(sc.hasNext()){
			String next = sc.next();			
			if(gMap.containsKey(previous)){				
				ArrayList<String> arr = new ArrayList<>();
				arr = gMap.get(previous);
				arr.add(next);
				gMap.remove(previous);
				gMap.put(previous, arr);
			}else{
				ArrayList<String> arr = new ArrayList<>();
				arr.add(next);
				gMap.put(previous, arr);
			}			
			previous = next;
		}
		
		if(gMap.containsKey(previous)){
			ArrayList<String> arr = new ArrayList<>();
			arr = gMap.get(previous);
			arr.add("");
			gMap.remove(previous);
			gMap.put(previous, arr);
		}else{
			ArrayList<String> arr = new ArrayList<>();
			arr.add("");
			gMap.put(previous, arr);
		}
		
		for(ArrayList<String> list : gMap.values()){
			list.sort(null);
		}
	}

	/**
	 * Generate an array of strings based on the model, start word, and count.
	 * You are given the start word to begin with. Each successive word should
	 * be generated as the most likely or common word after the preceding word
	 * according to the bigram model derived from the text passed to the
	 * constructor. If more than one word is most likely, pick the smallest one
	 * according to the natural String comparison order (compareTo order). Fewer
	 * than count words may be generated if a dead end is reached with no
	 * possibilities. If the start word never appears in the input text, only
	 * that word will be generated.
	 * 
	 * @param start
	 *            Start word
	 * @param count
	 *            Number of words to generate (you may assume it's at least 1)
	 * @return Array of generated words which begins with the start word and
	 *         will usually have the length of the count argument (less if there
	 *         is a dead end)
	 */
	public String[] generate(String start, int count) {
		String[] genString = new String[count];
		String mostCommonString = null;
		mostCommonString = start;		
		for(int i = 0; i < count; i++){
			ArrayList<String> list = new ArrayList<>();			
			list = gMap.get(mostCommonString);
			genString[i] = mostCommonString;			
			if((list.size() == 1) && (list.get(0).length() ==0)){
				break;
			}else{				
				int mostCommonCount = 0;
				int countAppearance = 0;					
				String strCompare = list.get(0);				
				for (int j = 0; j < list.size(); j++){			
					if(strCompare.equals(list.get(j))){					
						countAppearance = countAppearance + 1;
					}else{
						if(mostCommonCount < countAppearance){
							mostCommonCount = countAppearance;
							mostCommonString = strCompare;
						}
						strCompare = list.get(j);		//Loi cho nay			
						countAppearance = 0;
					}
				}
				
				if(countAppearance == list.size()){
					mostCommonString = strCompare;
				}			
			}
		}	
		
		int notNull = 0;
		for(int i = 0; i < genString.length; i++){
			if(genString[i] != null){
				notNull = notNull + 1;
			}
		}
		
		String[] returnString = new String[notNull];
		int totalWord = 0;
		for(int i = 0; i < genString.length; i++){
			if(genString[i] != null){
				returnString[totalWord++] = genString[i];
			}
		}
		
		return returnString;
		
	}
	
	public static void main(String[] args){
		int checkScore = 0, genScore = 0;
		Bigram x = new Bigram("The balloon was red. The balloon got bigger and bigger. The balloon popped.");
		System.out.println(Arrays.toString(x.generate("The", 3)));
//		if(x.check("Bob likes cats.")){
//			System.out.println("OKE");
//		}
//		if (x.check("Bob likes cats.")) {
//			checkScore += 10;
//		} else {
//			System.out.println("First check failed.");
//		}
//		if (!x.check("Jane likes cats.")) {
//			checkScore += 10;
//		} else {
//			System.out.println("Second check failed.");
//		}
//		System.out.println("Check: " + checkScore + " / 50");
	}
}
