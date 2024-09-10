# Blackjack real
## How the game works
The rules are simple: 
- The closest one that has a score of less than or equal to 21 wins. 
- The ace card can be 1, 10 or 11 points. 
- The Jack, Queen, and King card worth 10 points
## How the AI works
The dealer AI works as follow:
- Every turn, it will calculate the maximum number of cards that he can pick to guarantee a score that is less than or equal to 21. This number will be calculate from the dealer's hand and his deduction on your hand. Lets call the number $k$.
- Then the dealer will randomize a number from 1 to the number of remaining cards and compare it to $k$. If $k$ is larger than or equal to the randomize number, the dealer will call a draw, else he will call a stay.
- If its hand has an ace (or two aces) and the score with the ace = 10 or 11 points is less than or equal to 21, its score will be the highest possible score. Then it will do a randomization like above.
- Another addition to the AI to make it more human, if its score is higher than or equal to 19, it will guarantee a stay, or if its score is lower than or equal to 16, it will guarantee a draw.
## The game phases
### The initial phase
This phase will occur once the game starts. You and the dealer both will be given 2 cards from the initial deck.
### The draw phase
In this phase, you and the dealer will take turn to draw a card or stay. If in a turn, you and the dealer both call stay, this phase ends.
### The compare phase
In this phase, the computer will compare your best score with the dealer best score. The result will be:
- Draw if both exceeds 21 or both have the same score and the same cards on hand.
- You win if your score does not exceed 21 and the dealer exceeds 21 or you have the higher score or you have the same score as the dealer but more cards on hand.
- The dealer wins in other cases.
