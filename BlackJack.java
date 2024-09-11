public class BlackJack 
{ 

    public static class Deck
    {
        private int[] cards;
        private int top;

        public Deck() 
        {
            cards = new int[60];
            for(int i = 1; i <= 52; i++)
            {
                cards[i] = i;
            }
            top = 1;
        }

        public void reset() 
        {
            for(int i = 1; i <= 52; i++)
            {
                cards[i] = i;
            }
            top = 1;
        }

        public void shuffle() 
        {
            for (int i = 1; i <= 52; i++) 
            {
                int rng_id = (int) (Math.random() * 51)+1;
                int temp = cards[i];
                cards[i] = cards[rng_id];
                cards[rng_id] = temp;
            }
        }

        public int draw() 
        {
            return cards[top++];
        }
    }

    public static class Player
    {
        public int[] hand;
        private int cnt_card;
        public String role="Player";

        // public int[] public_hand; remember to secure the information later

        private String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
        private String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

        public Player() 
        {
            hand = new int[12];
            // public_hand = new int[12];
            cnt_card = 0;
        }

        public String card_String(int card)
        {
            int value = (card-1) % 13 + 1;
            int suit = (card-1) / 13;
            return values[value-1] + " of " + suits[suit];
        }

        void announce_draw(int card) 
        {
            System.err.println(role + " draws " + card_String(card));
        }

        public void Hit(int card, boolean announce) 
        {
            // public_hand[cnt_card] = card;
            hand[cnt_card++] = card;
            
            if(announce) announce_draw(card);
        }

        public void Show_full_hand()
        {
            String statement = role + "'s current hand: ";
            for(int i = 0; i < cnt_card; i++)
            {
                statement+= card_String(hand[i]);
                if(i < cnt_card-1) statement+=", ";
                else statement+=".";
            }
            System.out.println(statement);
            System.out.println(role + "'s current hand value: " + hand_value());
        }

        public int hand_value() 
        {
            int sum = 0, cnt_ace=0;
            for (int i = 0; i < cnt_card; i++) {
                int value = (hand[i]-1) % 13 + 1;
                if(value == 1)
                {
                    cnt_ace++;
                    continue;
                }
                if(value > 10) value = 10;
                sum+=value;
            }
            if(cnt_ace > 0)
            {
                if(sum + 11 + (cnt_ace-1) <= 21) sum += 11 + (cnt_ace-1);
                else sum += cnt_ace;
            }
            return sum;
        }

        public boolean busted() 
        {
            return hand_value() > 21;
        }
    }

    public static class Dealer extends Player
    {
        // private int[] hand;

        public Dealer()
        {
            role = "Dealer";
        }

        public boolean hand_check() 
        {
            return hand_value() < 17;
        }

        public void Show_one_card()
        {
            // hand=public_hand;
            String statement = role + "'s current hand: ";
            statement += card_String(hand[0]) + ", ?.";
            System.out.println(statement);
        }
    }

    static class Game 
    {
        private Deck deck;
        private Player player;
        private Dealer dealer;
    
        public Game() 
        {
            deck = new Deck();
            player = new Player();
            dealer = new Dealer();
        }
    
        public void start() 
        {
            deck.shuffle();
            dealer.Hit(deck.draw(), true);
            dealer.Hit(deck.draw(), false);
            player.Hit(deck.draw(), true);
            player.Hit(deck.draw(), true);
    
            player.Show_full_hand();
            dealer.Show_one_card();
        }

        public void player_turn()
        {
            while(player.hand_value() < 21)
            {
                System.out.println("Do you want to hit or stand?");
                String choice = System.console().readLine();
                if(choice.equals("hit"))
                {
                    player.Hit(deck.draw(), true);
                    player.Show_full_hand();
                }
                else if(choice.equals("stand"))
                {
                    System.out.println("Player stands.");
                    System.out.println("Player's final hand value: " + player.hand_value());
                    break;
                }
            }
            if(player.busted())
            {
                System.out.println("Player busted. Dealer wins. Round end.");
            }
        }

        public void dealer_turn()
        {
            if(player.busted())
            {
                return;
            }
            dealer.Show_full_hand();
            while(dealer.hand_check())
            {
                dealer.Hit(deck.draw(), true);
                dealer.Show_full_hand();
            }

            if(dealer.busted())
            {
                System.out.println("Dealer busted. Player wins. Round end.");
            }
            else
            {
                System.out.println("Dealer's final hand value: " + dealer.hand_value());
            }
        }

        public void compare()
        {
            if(dealer.busted() || player.busted())
            {
                return;
            }
            if(player.hand_value() > dealer.hand_value())
            {
                System.out.println("Player wins. Round end.");
            }
            else if(player.hand_value() < dealer.hand_value())
            {
                System.out.println("Dealer wins. Round end.");
            }
            else
            {
                System.out.println("It's a tie. Round end.");
            }
        }
    }

    public static void main(String[] args) 
    {
        while(true)
        {
            System.out.println("Do you want to play a game of Blackjack? (yes/no)");
            String choice = System.console().readLine();
            if(choice.equals("no"))
            {
                break;
            }
            Game game = new Game();
            game.start();
            game.player_turn();
            game.dealer_turn();
            game.compare();
        }
    }
}