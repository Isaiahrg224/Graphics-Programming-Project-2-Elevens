import java.util.ArrayList;

public class Deck {

    public ArrayList<Card> deckList;

    public Deck(){
        deckList = new ArrayList<Card>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] numbers = {"A", "02", "03", "04", "05", "06", "07", "08", "09", "10", "J", "Q", "K"};
        for(String suit : suits){
            for (String number : numbers){
                Card currentCard = new Card(suit, number);
                deckList.add(currentCard);
            }
        }
    }
     public Card getRandomCard(){
        int random = (int) (Math.random() * deckList.size());
        Card tempCard = deckList.get(random);
        deckList.remove(random);
        return tempCard;
     }

    public Card getRandomCardNonRemove(){
        if(deckList.size() > 0) {
            int random = (int) (Math.random() * deckList.size());
            Card tempCard = deckList.get(random);
            return tempCard;
        }else{
            Card back = new Card("back");
            return back;
        }
    }

    public Card getRandomCardEasy(Card[][] array, int selectedCard){
        if(deckList.size() > 0) {
            Card[][] remainingCards = new Card[3][3];
            for (int r = 0; r < array.length; r++) {
                for (int c = 0; c < array[0].length; c++) {
                    boolean unselectedCard = true;
                    if (r * 3 + c == selectedCard) {
                        unselectedCard = false;

                    }
                    if (unselectedCard) {
                        remainingCards[r][c] = array[r][c];
                    }
                }
            }

            int random = (int) (Math.random() * deckList.size());
            Card tempCard = deckList.get(random);
            remainingCards[(selectedCard - selectedCard % 3) / 3][selectedCard % 3] = tempCard;
            while (!DrawPanel.possibleMoves(remainingCards)) {
                random = (int) (Math.random() * deckList.size());
                tempCard = deckList.get(random);
                remainingCards[(selectedCard - selectedCard % 3) / 3][selectedCard % 3] = tempCard;
            }
            deckList.remove(random);
            return tempCard;
        }
        else{
            Card back = new Card("back");
            return back;
        }
    }


}
