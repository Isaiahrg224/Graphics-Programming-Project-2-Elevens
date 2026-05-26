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


}
