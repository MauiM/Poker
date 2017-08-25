public enum CardSuit {
	
	Clubs(0, "C"),
	Diamonds(1, "D"),
	Hearts(2, "H"),
	Spades(3, "S");
	
	private int _index;
	private String _string;
	
	CardSuit(int Index, String s) {
        this._index = Index;
        this._string = s;
    }
	
	public int getIndex() {
        return _index;
	}
	
	public String shortString()
	{
		return _string;
	}
}