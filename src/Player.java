import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Player {

	private String Name;
	private List<Card> Cards;
	private double cardsValue = 0;
	
	Player(String Name)
	{
		this.Name = Name;
		Cards = new ArrayList<Card>();
	}
	
	public int TotalCards()
	{
		return Cards.size();
	}
	
	public void GiveCard(Card card)
	{
		Cards.add(card);
	}
	
	public List<Card> GetCards()
	{
		return Cards;
	}
	
	public void ClearHand()
	{
		Cards.clear();
	}
	
	public void SortHand()
	{
		// Sort cards with Ace being first Two being last
		Comparator<Card> compareCard = (c1, c2) -> c1.compareTo(c2);
		Collections.sort(Cards, Collections.reverseOrder(compareCard));
	}
	
	public void DisplayHand()
	{
		CalculateCardsValue();
		
		Renderer.Instance().WriteLine(Name + ": " + Cards.toString() + " (" + cardsValue + ")");

		Renderer.Instance().AddPlayer(this);
	}
	
	// Returns double= HandValue.HighCard
	private void CalculateCardsValue()
	{
		if(Cards.size() <= 0)
		{
			System.out.println("Player.GetHandValue invalid hand.");
			cardsValue = 0;
			return;
		}
		
		HandValue handValue = HandValue.HighCard;
		int highCard = CardValue.Two.ordinal();
		
		// Get all the information needed to determine hand value
		List valueMatches = GetValueMatches();
		boolean isMatchingSuit = IsMatchingSuit();
		boolean isStraight = IsStraight();
		
		
		// Determine if there are any HandValues that involves pairs
		int twoMatchingCount = 0;
		for(int i = 0; i < valueMatches.size(); i++)
		{
			int countFound = (int)valueMatches.get(i);
			if(countFound == 2)
			{
				twoMatchingCount++;
				
				if(handValue == HandValue.ThreeOfAKind)
					handValue = HandValue.FullHouse;
				else 
				{
					if(i > highCard)
						highCard = i;
					
					if(twoMatchingCount == 1)
						handValue = HandValue.Pair;
					else if(twoMatchingCount == 2)
						handValue = HandValue.TwoPairs;
				}
			}
			else if(countFound >= 3)
			{
				if(twoMatchingCount == 1)
					handValue = HandValue.FullHouse;
				else if(countFound == 3)
					handValue = HandValue.ThreeOfAKind;
				else if(countFound == 4)
					handValue = HandValue.FourOfAKind;
				
				highCard = i;
			}
		} // End of valueMatches for loop
		
		// Check for straight, flush and straight flush but only if current hand is lower than FullHouse
		if(handValue.ordinal() < HandValue.FullHouse.ordinal())
		{
			if(isMatchingSuit)
			{
				if(isStraight)
					handValue = HandValue.StraightFlush;
				else
					handValue = HandValue.Flush;
			}
			else if(isStraight)
				handValue = HandValue.Straight;
			
		}
		

		cardsValue = (double)handValue.ordinal() + (0.01 * (double)highCard) 
				+ (0.0001 * (double)Cards.get(0).value.ordinal()) //Calculate high card values
				+ (0.000001 * (double)Cards.get(1).value.ordinal())
				+ (0.00000001 * (double)Cards.get(2).value.ordinal())
				+ (0.0000000001 * (double)Cards.get(3).value.ordinal())
				+ (0.000000000001 * (double)Cards.get(4).value.ordinal());
	}
	
	// First value is CardValue, Second value is count
	private List GetValueMatches()
	{
		List returnValue = new ArrayList();
		for(int i = 0; i < CardValue.values().length; i++)
			returnValue.add(i, 0);
		
		// Count the number of each card
		for(int i = 0; i < Cards.size(); i++)
		{
			Card card = (Card)Cards.get(i);
			int index = card.value.ordinal();
			
			returnValue.set(index, (int)returnValue.get(index)+1);
		}
		
		return returnValue;
	}
	
	private boolean IsMatchingSuit()
	{
		int startCardSuit = ((Card)Cards.get(0)).suit.ordinal();
		
		// Count the number of each card
		for(int i = 1; i < Cards.size(); i++)
		{
			Card card = (Card)Cards.get(i);
			int cardSuit = card.suit.ordinal();
			
			if(startCardSuit != cardSuit)
				return false;
		}
		
		return true;
	}
	
	private boolean IsStraight()
	{
		// Check if lastCard is is one away from the current card
		// Cards should already be sorted in descending order
		for(int i = 1; i < Cards.size(); i++)
		{
			int lastCard = Cards.get(i-1).value.ordinal();
			int currentCard = Cards.get(i).value.ordinal();
			if(lastCard-1 != currentCard)
				return false;
		}
		
		return true;
	}
	
	public double GetCardsValue()
	{
		return cardsValue;
	}
	
	public String GetCardValueString()
	{
		return HandValue.values()[(int)cardsValue].toString();
	}
	
	public String GetName()
	{
		return Name;
	}
}