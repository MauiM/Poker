import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Canvas;
import java.awt.Label;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Poker extends JFrame {

	private static Poker instance = null;
	public static Poker Instance() {
	      if(instance == null) {
	         instance = new Poker();
	      }
	      return instance;
	   }
	
	List Players;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Poker frame = new Poker();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Poker() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 790, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelTop = new JPanel();
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            	super.paintComponent(g);
            	Renderer.Instance().paint(g);
            }
        };
		canvas.setBackground(Color.GRAY);
		contentPane.add(canvas, BorderLayout.CENTER);
		
		Renderer.Instance().Initialize(canvas);	
		
		JButton btnNewHand = new JButton("New Hand");
		btnNewHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PlayHand(true);
			}
		});
		panelTop.add(btnNewHand);
		
		/*
		JButton btnTest = new JButton("Test");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset hands
				for(int i = 0; i < Players.size(); i++)
				{
					Player player = (Player)Players.get(i);
					player.ClearHand();
				}
				
				Player player1 = (Player)Players.get(0);
				player1.GiveCard( new Card(CardSuit.Hearts, CardValue.Ace) );
				player1.GiveCard( new Card(CardSuit.Spades, CardValue.Ace) );
				player1.GiveCard( new Card(CardSuit.Hearts, CardValue.Jack) );
				player1.GiveCard( new Card(CardSuit.Hearts, CardValue.Jack) );
				player1.GiveCard( new Card(CardSuit.Hearts, CardValue.Ten) );
				
				Player player2 = (Player)Players.get(1);
				player2.GiveCard( new Card(CardSuit.Spades, CardValue.Ten) );
				player2.GiveCard( new Card(CardSuit.Spades, CardValue.Nine) );
				player2.GiveCard( new Card(CardSuit.Spades, CardValue.Four) );
				player2.GiveCard( new Card(CardSuit.Clubs, CardValue.Three) );
				player2.GiveCard( new Card(CardSuit.Diamonds, CardValue.Two) );
				
				PlayHand(false);
			}
		});
		panelTop.add(btnTest);
		*/
		
		// Initialize game
		Deck.Instance().Initialize(1000);
		Players = new ArrayList();
		Players.add( new Player("Bob") );
		Players.add( new Player("Beth") );
		
		int delay = 200; //milliseconds
		  ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
	    		  canvas.repaint();  
		      }
		  };
		  Timer timer = new Timer(delay, taskPerformer);
		  timer.start();
	}
	
	private void PlayHand(boolean clearLastHand)
	{
		Renderer.Instance().WriteLine("");
		
		if(clearLastHand)
		{
			// Reset hands
			for(int i = 0; i < Players.size(); i++)
			{
				Player player = (Player)Players.get(i);
				player.ClearHand();
			}
			
			// Draw cards
			boolean bContinue;
			do
			{
				int totalCards = 5;
				
				Renderer.Instance().ClearHands();
				bContinue = false;
				for(int i = 0; i < Players.size(); i++)
				{
					Player player = (Player)Players.get(i);
					if(player.TotalCards() < totalCards)
					{
						bContinue = true;
						player.GiveCard( Deck.Instance().DrawCard() );
					}
					else
					{
						player.SortHand();
						player.DisplayHand();
					}
				}
			} while(bContinue);
		}
		else
		{
			for(int i = 0; i < Players.size(); i++)
			{
				Player player = (Player)Players.get(i);
				player.SortHand();
				player.DisplayHand();
			}
		}
		
		//Determine winner
		double p1 = ((Player)Players.get(0)).GetCardsValue();
		double p2 = ((Player)Players.get(1)).GetCardsValue();
		
		if(p1 == p2)
		{
			
		}else
		{
			int winnerIndex = p1 > p2 ? 0 : 1;
			String winnerName = ((Player)Players.get(winnerIndex)).GetName();
			String winningHand = ((Player)Players.get(winnerIndex)).GetCardValueString(); 
			Renderer.Instance().WriteLine(winnerName + " won the hand with a " + winningHand);
		}
	}

	public void ShowMessage(String message)
	{
		//contentPane..();
	}
}
