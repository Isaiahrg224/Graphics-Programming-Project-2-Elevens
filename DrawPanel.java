import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private Deck deck;
    private Card[][] currentCards;
    private boolean[][] hitboxesOn;
    private Rectangle reset;
    private Rectangle replace;
    int amountOfhitboxes;


    public DrawPanel() {

        deck = new Deck();
        currentCards = new Card[3][3];
        hitboxesOn = new boolean[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                currentCards[r][c] = deck.getRandomCard();
                hitboxesOn[r][c] = false;
            }
        }
        replace = new Rectangle(250, 300, 200, 80);
        reset = new Rectangle(50, 300, 200, 80);
        amountOfhitboxes = 0;
        this.addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawRect(50, 300, 150, 80);
        g.drawString("Reset", 90, 350);

        g.drawRect(250, 300, 150, 80);
        g.drawString("Replace Cards", 300, 350);

        int x = 50;
        int y = 10;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                g.drawImage(currentCards[r][c].getImage(), x, y, null);
                Rectangle h = new Rectangle(x, y, (int) currentCards[r][c].getImage().getWidth(),
                        (int) currentCards[r][c].getImage().getHeight());
                currentCards[r][c].setHitbox(h);
                if (hitboxesOn[r][c]) {
                    g.drawRect(x, y, (int) currentCards[r][c].getImage().getWidth(), (int) currentCards[r][c].getImage().getHeight());
                }
                x += 70;

            }
            y += 100;
            x -= 210;
        }

        g.drawString(("There are " + deck.deckList.size() + " cards left"), 100, 410);
        g.drawString(("There are " + possibleMoves(currentCards)), 100, 440);
    }

    public static String possibleMoves(Card[][] array) {
        String[][] cards = new String[3][3];
        for(int r = 0; r < array.length; r++){
            for(int c = 0; c < array[0].length; c++){
                cards[r][c] = array[r][c].getValue();
            }
        }

        boolean moves = false;
        boolean k = false;
        boolean q = false;
        boolean j = false;
        for (String[] r : cards) {
            for (String card : r) {
                if (card.equals("K")) {
                    k = true;
                } else if (card.equals("Q")) {
                    q = true;
                } else if (card.equals("J")) {
                    j = true;
                }
            }
        }
        if (j && q && k) {
            moves = true;
        }
        int total1 = 0;
        int total2 = 0;
        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards[0].length; c++) {
                total1 = 0;
                if (cards[r][c].equals("A")) {
                    total1++;
                } else if (cards[r][c].equals("J") || cards[r][c].equals("Q") || cards[r][c].equals("K")) {
                    total1 = 0;
                } else {
                    total1 += Integer.parseInt(cards[r][c]);
                }
                for (int y = 0; y < cards.length; y++) {
                    for (int x = 0; x < cards[0].length; x++) {
                        if (cards[y][x].equals("A")) {
                            total2++;
                        } else if (cards[y][x].equals("J") || cards[y][x].equals("Q") || cards[y][x].equals("K")) {
                            total2 = 0;
                        } else {
                            total2 += Integer.parseInt(cards[y][x]);
                        }
                        if (total1 + total2 == 11) {
                            moves = true;
                        }
                        total2 = 0;
                    }
                }
            }


        }
     if(!moves){
         return "no possible moves left";
     }
     else {
         return "there are possible moves left";
     }
}



    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        int button = e.getButton();
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (currentCards[r][c].getHitbox().contains(p)) {

                            if(hitboxesOn[r][c] && amountOfhitboxes <= 3){
                                hitboxesOn[r][c] = false;
                                amountOfhitboxes--;
                            }
                            else if (amountOfhitboxes < 3){
                                hitboxesOn[r][c] = true;
                                amountOfhitboxes++;
                        }

                    }
                }
            }
        if(reset.contains(p)){
            deck = new Deck();
            for(int r = 0; r < 3; r++){
                for(int c = 0; c < 3; c++){
                    currentCards[r][c] = deck.getRandomCard();
                    hitboxesOn[r][c] = false;
                }
            }
            amountOfhitboxes = 0;
        }
        if(replace.contains(p)){
            ArrayList<String> listCards = new ArrayList<>();
            ArrayList<Integer> position = new ArrayList<>();
            for(int r = 0; r < hitboxesOn.length; r++){
               for(int c = 0; c < hitboxesOn[r].length; c++)
                if(hitboxesOn[r][c]){
                    listCards.add(currentCards[r][c].getValue());
                    position.add((r * 3) + c);
                }
            }
            if(amountOfhitboxes == 3){
            boolean k = false;
            boolean q = false;
            boolean j = false;
            for(String card : listCards) {
                if (card.equals("K")) {
                    k = true;
                } else if (card.equals("Q")) {
                    q = true;
                } else if (card.equals("J")) {
                    j = true;
                }
            }
                if(j && q && k){
                    currentCards[(position.get(0) - position.get(0) % 3) / 3][position.get(0) % 3] = deck.getRandomCard();
                    currentCards[(position.get(1) - position.get(1) % 3) / 3][position.get(1) % 3] = deck.getRandomCard();
                    currentCards[(position.get(2) - position.get(2) % 3) / 3][position.get(2) % 3] = deck.getRandomCard();
                    hitboxesOn[(position.get(0) - position.get(0) % 3) / 3][position.get(0) % 3] = false;
                    hitboxesOn[(position.get(1) - position.get(1) % 3) / 3][position.get(1) % 3] = false;
                    hitboxesOn[(position.get(2) - position.get(2) % 3) / 3][position.get(2) % 3] = false;
                    amountOfhitboxes = 0;
            }

            } else if (amountOfhitboxes == 2) {
                int total = 0;
                for(String card : listCards){
                   if(card.equals("A")){
                       total++;
                   }
                   else if (card.equals("J") || card.equals("Q") ||card.equals("K")){
                       break;
                   }
                   else{
                       total += Integer.parseInt(card);
                   }
                }
                if(total == 11){
                    currentCards[(position.get(0) - position.get(0) % 3) / 3][position.get(0) % 3] = deck.getRandomCard();
                    currentCards[(position.get(1) - position.get(1) % 3) / 3][position.get(1) % 3] = deck.getRandomCard();
                    hitboxesOn[(position.get(0) - position.get(0) % 3) / 3][position.get(0) % 3] = false;
                    hitboxesOn[(position.get(1) - position.get(1) % 3) / 3][position.get(1) % 3] = false;
                    amountOfhitboxes = 0;
                }
            }
        }



    }


    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}