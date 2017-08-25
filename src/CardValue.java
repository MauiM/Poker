public enum CardValue {
	Two(0, "2"),
	Three(1, "3"),
	Four(2, "4"),
	Five(3, "5"),
	Six(4, "6"),
	Seven(5, "7"),
	Eight(6, "8"),
	Nine(7, "9"),
	Ten(8, "10"),
	Jack(9, "J"),
	Queen(10, "Q"),
	King(11, "K"),
	Ace(12, "A");
	
	private int _index;
	private String _string;
	CardValue(int Index, String s) {
        this._index = Index;
        this._string = s;
    }
	
	public int getIndex() {
        return _index;
	}
	
	public String toString()
	{
		return _string;
	}
}