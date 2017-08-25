import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
	
	private static Deck instance = null;
	public static Deck Instance() {
      if(instance == null) {
         instance = new Deck();
      }
      return instance;
   }
	
	private List Cards;
	
	public void Initialize(int shuffleAmount)
	{
		Cards = new ArrayList();
		
		for (CardSuit suit : CardSuit.values()) {
			for (CardValue value : CardValue.values()) {
				Card card = new Card(suit, value);
				Cards.add(card);
			} 
		}
		
		Shuffle(shuffleAmount);
	}
	
	private void Shuffle(int shuffleAmount)
	{
		Random random = new Random();
		
		for(int x = 0; x < shuffleAmount; x++)
		{
			int firstCardIndex = random.nextInt(Cards.size());
			int secondCardIndex = random.nextInt(Cards.size());
			
			// Swap two random card
			Card firstCard = (Card)Cards.get(firstCardIndex);
			Cards.set(firstCardIndex, (Card)Cards.get(secondCardIndex));
			Cards.set(secondCardIndex, firstCard);
		}
		
		System.out.println(Cards);
	}
	
	public Card DrawCard()
	{
		int endPos = Cards.size() - 1;
		
		//System.out.println("Remaining: " + endPos);
		
		if(endPos >= 0)
		{
			// Draw and remove card from end of list
			Card card = (Card)Cards.get(endPos);
			Cards.remove(endPos);
			return card;
		}
		else
		{
			// Get a new deck if no cards are left
			Initialize(1000);
			return DrawCard();
		}
	}
}