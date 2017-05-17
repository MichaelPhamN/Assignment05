import java.util.ArrayList;
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
	public String strOfData;
	/**
	 * Create a new bigram model based on the text given as a String argument.
	 * See the assignment for more details (and also check out the Wikipedia
	 * article on bigrams).
	 * 
	 * @param s
	 *            text
	 */
	public Bigram(String s) {
		// TODO: implement me!
		createSetBigram(s);
		createMapBigram(s);
//		for (Entry entry : gMap.entrySet()) {
//		    System.out.println(entry.getKey() + ", " + entry.getValue());
//		}
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
		// TODO: implement me!
		return true; // Fix this!
	}
	
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
		for(ArrayList<String> list : gMap.values()){
			list.sort(null);
		}
		
		return null; // Fix this! Your method should never return null!
	}
	
	public static void main(String[] args){
		Bigram b = new Bigram("The balloon was red. The balloon got bigger and bigger. The balloon popped.");
		if(b.check("Jane likes cats."))
		{
			System.out.println("OK");
		}else{
			System.out.println("Not OK");
		}
		b.generate("The", 3);
//		System.out.println(b.set);
//		ArrayList<String> listofcountries = new ArrayList<String>();
//		listofcountries.add("The");
//		listofcountries.add("balloon");
//		listofcountries.add("balloon");
//		listofcountries.add("was");
//		listofcountries.add("was");
//		listofcountries.add("red.");
//		listofcountries.add("red.");
//		listofcountries.add("The");
//		listofcountries.add("The");
//		listofcountries.add("balloon");
//		listofcountries.add("balloon");
//		listofcountries.add("got");
//		listofcountries.add("got");
//		listofcountries.add("bigger");
//		listofcountries.add("bigger");
//		listofcountries.add("and");
//		listofcountries.add("and");
//		listofcountries.add("bigger.");
//		listofcountries.add("bigger.");
//		listofcountries.add("The");
//		listofcountries.add("The");
//		listofcountries.add("balloon");
//		listofcountries.add("balloon");
//		listofcountries.add("popped.");
//		System.out.println("Before Sorting:");
//		   for(String counter: listofcountries){
//				System.out.println(counter);
//			}
//		   Collections.sort(listofcountries);
//		   System.out.println("After Sorting:");
//		   for(String counter: listofcountries){
//				System.out.println(counter);
//			}
//		TreeMap<String, Data> dataTreeMap = new TreeMap<>();
//		Data d = new Data();
//		d.m_value = "likes";()
//		dataTreeMap.put("Bob",d);
//		d.m_value = "dogs.";
//		dataTreeMap.put("likes",d);
//		d.m_value = "Bill";
//		dataTreeMap.put("dogs.",d);
//		d.m_value = "likes";
//		dataTreeMap.put("Bill",d);
//		d.m_value = "cats.";
//		dataTreeMap.put("likes",d);
//		d.m_value = "Jane";
//		dataTreeMap.put("cats.",d);
//		d.m_value = "hates";
//		dataTreeMap.put("Jane",d);
//		d.m_value = "dogs.";
//		dataTreeMap.put("hate",d);
////		String s = "Bob likes dogs. Bill likes cats. Jane hates dogs.";
////		Scanner sc = new Scanner(s);		
////		String key = sc.next();
////		while(sc.hasNext()){
////			String value = sc.next();
////			bigram.put(key, value);
//////			System.out.println(key + " " + value);
////			key = value;
////		}
//		for (String key1: dataTreeMap.keySet()) {
//		    System.out.println(key1 + " " + dataTreeMap.get(key1).toString());		    
//		}
	}
}
