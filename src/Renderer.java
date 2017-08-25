import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Renderer {

	private static Renderer instance = null;
	public static Renderer Instance() {
      if(instance == null) {
         instance = new Renderer();
      }
      return instance;
   }
	
	private BufferedImage[][] cardImages; // [Suit][Value]
	private List<List<Card>> hands;
	private List<Player> players;
	
	private JPanel canvas;
	
	public void Initialize(JPanel canvas)
	{
		this.canvas = canvas;
		
		cardImages = new BufferedImage[4][13];
		hands = new ArrayList<List<Card>>();
		players = new ArrayList<Player>();
		LoadCardImages(cardImages);
	}
	
	private void LoadCardImages(BufferedImage[][] cardImages2) {
		try
		{
			for (CardSuit suit : CardSuit.values()) {
				for (CardValue value : CardValue.values()) {
					String fileName = System.getProperty("user.dir") + "\\Resources\\Cards\\card" + suit.toString() + value.toString() + ".png";
					BufferedImage image = ImageIO.read(new File(fileName));
					cardImages[suit.getIndex()][value.getIndex()] = image; 
				} 
			}
		} catch (Exception e) {
            e.printStackTrace();
            System.out.println("Image could not be read");
        }
	}

	public boolean IsInitialized()
	{
		if(canvas == null)
		{
			Poker.Instance().ShowMessage("Renderer not initialized.");
			return false;
		}
		
		return true;
	}
	
	public void paint(Graphics g)
	{
		int padding = 10;
		
		// Image position
		int x = padding;
		int y = padding;
		
		// Image size
		int width = 140;
		int height = 190;
		
		double winningHandValue = 0;
		boolean tie = false;
		for(int playerNum = 0; playerNum < players.size(); playerNum++)
		{
			Player player = players.get(playerNum);
			if(player.GetCardsValue() > winningHandValue)
			{
				winningHandValue = player.GetCardsValue();
				tie = false;
			}
			else if(player.GetCardsValue() == winningHandValue)
				tie = true;
		}
		
		for(int playerNum = 0; playerNum < players.size(); playerNum++)
		{
			Player player = players.get(playerNum);
			
			String extraText = "";
			if(player.GetCardsValue() == winningHandValue)
			{
				g.setColor(Color.yellow);
				extraText = " - ";
				extraText += tie ? "Tied with a " : "Won with a ";
				extraText += player.GetCardValueString();
			}
			else
			{
				g.setColor(Color.white);
				extraText = " - Lost with a ";
				extraText += player.GetCardValueString();
			}
			
			// Draw player's name and if they won
			g.setFont(new Font("Helvetica", Font.PLAIN, 24));
			y += (padding * 2);
			g.drawString(player.GetName() + extraText, x, y);
			y += padding;

			// Draw player's cards
			List<Card> hand = player.GetCards();
			for(int cardNum = 0; cardNum < hand.size(); cardNum++)
			{
				Card card = hand.get(cardNum);
				g.drawImage(cardImages[card.suit.ordinal()][card.value.ordinal()], x, y, null);
				
				x += width + padding;
			}
			
			x = padding;
			y += height + (padding * 4);
		}

		g.dispose();
	}
	 
	public void WriteLine(String text)
	{
		if(!IsInitialized())
			return;
		
		//String s = textArea.getText();
		//s = text + "\n" + s;
		//textArea.setText(s);
		System.out.println(text);
	}
	
	public void ClearHands()
	{
		hands.clear();
		players.clear();
	}
	
	public void AddHand(String name, List<Card> hand, double handValue)
	{
		hands.add(hand);
	}
	
	public void AddPlayer(Player player)
	{
		players.add(player);
	}
}
