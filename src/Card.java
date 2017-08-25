public class Card {

	public CardSuit suit;
	public CardValue value;
	
	Card(CardSuit suit, CardValue value)
	{
		this.suit = suit;
		this.value = value;
	}
	
	public String toString()
	{
		return value.toString() + suit.shortString();
	}

	public int compareTo(Card card)
	{
        if (this.value.ordinal() < card.value.ordinal())
            return -1;
        else
            return 1;
    }
}