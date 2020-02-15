package lambdasinaction.chap5;

import lambdasinaction.chap5.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class PuttingIntoPractice{
    public static void main(String ...args){    
        List<Transaction> transactions = initTransactions();	
        List<Transaction> s1 = transactions.stream().filter(i -> i.getYear()==2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
//        s1.forEach(System.out::println);
        System.out.println(s1);
        
        List<String> s2 = transactions.stream().map(i -> i.getTrader().getCity()).distinct().collect(Collectors.toList());
//        s2.forEach(System.out::println);
        System.out.println(s2);
        
        Set<String> s2Set = transactions.stream().map(i -> i.getTrader().getCity()).collect(Collectors.toSet());
//        s2Set.forEach(System.out::println);
        System.out.println(s2Set);
        
        List<Trader> s3 = transactions.stream().map(Transaction::getTrader)
        		.filter(i -> "Cambridge".equals(i.getCity())).distinct().sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
//        s3.forEach(System.out::println);
        System.out.println(s3);
        
        String s4 = transactions.stream().map(i -> i.getTrader().getName()).distinct().sorted().collect(Collectors.joining(", "));
        System.out.println(s4);
        
        boolean s5 = transactions.stream().anyMatch(i -> "Milan".equals(i.getTrader().getCity()));
        System.out.println(s5);
        
        // s6
        transactions.stream().filter(i -> "Cambridge".equals(i.getTrader().getCity())).map(Transaction::getValue).forEach(System.out::println);
        
        Optional<Integer> s7 = transactions.stream().map(Transaction::getValue).reduce(Integer::max);
        System.out.println(s7);
        
        Optional<Integer> s8 = transactions.parallelStream().map(Transaction::getValue).reduce(Integer::min);
        System.out.println(s8);
        
        // official solutions. NB: Q6 and Q8 seem to be different from the book. 
        solve(transactions);      
    }
    

	/**
	 * @return
	 */
	private static List<Transaction> initTransactions() {
		Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
		
		List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300), 
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),	
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );
		return transactions;
	}

	/**
	 * @param transactions
	 */
	private static void solve(List<Transaction> transactions) {
		// Query 1: Find all transactions from year 2011 and sort them by value (small to high).
        List<Transaction> tr2011 = transactions.stream()
                                               .filter(transaction -> transaction.getYear() == 2011)
                                               .sorted(comparing(Transaction::getValue))
                                               .collect(toList());
        System.out.println(tr2011);
        
        // Query 2: What are all the unique cities where the traders work?
        List<String> cities = 
            transactions.stream()
                        .map(transaction -> transaction.getTrader().getCity())
                        .distinct()
                        .collect(toList());
        System.out.println(cities);

        // Query 3: Find all traders from Cambridge and sort them by name.
        
        List<Trader> traders = 
            transactions.stream()
                        .map(Transaction::getTrader)
                        .filter(trader -> trader.getCity().equals("Cambridge"))
                        .distinct()
                        .sorted(comparing(Trader::getName))
                        .collect(toList());
        System.out.println(traders);
        
        
        // Query 4: Return a string of all traders’ names sorted alphabetically.
        
        String traderStr = 
            transactions.stream()
                        .map(transaction -> transaction.getTrader().getName())
                        .distinct()
                        .sorted()
                        .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(traderStr);
        
        // Query 5: Are there any trader based in Milan?
        
        boolean milanBased =
            transactions.stream()
                        .anyMatch(transaction -> transaction.getTrader()
                                                            .getCity()
                                                            .equals("Milan")
                                 );
        System.out.println(milanBased);
        
        
        // Query 6: Update all transactions so that the traders from Milan are set to Cambridge.
        transactions.stream()
                    .map(Transaction::getTrader)
                    .filter(trader -> trader.getCity().equals("Milan"))
                    .forEach(trader -> trader.setCity("Cambridge"));
        System.out.println(transactions);
        
        
        // Query 7: What's the highest value in all the transactions?
        int highestValue = 
            transactions.stream()
                        .map(Transaction::getValue)
                        .reduce(0, Integer::max);
        System.out.println(highestValue);
	}

}