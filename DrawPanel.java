import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;

class DrawPanel extends JPanel implements MouseListener {

    private Deck deck;
    private Card[][] currentCards;
    private boolean[][] hitboxesOn;
    private Rectangle reset;
    private Rectangle replace;
    private int amountOfhitboxes;
    private boolean[][] flippedCards;
    private int backroundIndex;
    long originalTime = System.currentTimeMillis();
    private boolean gameModeEasy;
    boolean initilization;
    private Rectangle easy;
    private Rectangle hard;


    public DrawPanel() {
        initilization = true;
        easy = new Rectangle(90, 50, 300, 150);
        hard = new Rectangle(90, 250, 300, 150);
//        gameModeEasy = false;
        deck = new Deck();
        currentCards = new Card[3][3];
        hitboxesOn = new boolean[3][3];
        flippedCards = new boolean[3][3];
        backroundIndex = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3 && !(r + c == 4); c++) {
                currentCards[r][c] = deck.getRandomCard();
                hitboxesOn[r][c] = false;
                flippedCards[r][c] = false;
            }
        }
        currentCards[2][2] = deck.getRandomCardNonRemove();
        currentCards[2][2] = deck.getRandomCardEasy(currentCards, 8);
        replace = new Rectangle(300, 140, 150, 80);
        reset = new Rectangle(300, 10, 150, 80);
        amountOfhitboxes = 0;
        this.addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        if (initilization) {
            g.drawRect(90, 50, 300, 150);
            g.drawRect(90, 250, 300, 150);
            g.drawString("Easy", 225, 130);
            g.drawString("Hard", 225, 330);
        } else {
            long time = System.currentTimeMillis();
            if ((time - originalTime) > 200) {
                backroundIndex++;
                originalTime = System.currentTimeMillis();
            }
            super.paintComponent(g);
            int x1 = 0;
            int y1 = 0;
            String[][] backgroundColor = new String[50][50];
            for (int r = 0; r < backgroundColor.length; r++) {
                for (int c = 0; c < backgroundColor[0].length; c++) {
                    if ((r + c + backroundIndex) % 30 < 10) {
                        if(gameModeEasy){
                            g.setColor(Color.blue);
                        }
                        if(!gameModeEasy){
                        g.setColor(Color.red);
                        }
                    } else if ((r + c + backroundIndex) % 20 < 10) {
                        if(gameModeEasy){
                            g.setColor(Color.green);
                        }
                       if(!gameModeEasy){
                            g.setColor(Color.orange);
                        }
                    } else if ((r + c + backroundIndex) % 10 < 10) {
                        g.setColor(Color.yellow);
                    }
                    g.drawRect(x1, y1, 10, 10);
                    if (x1 >= 300 && x1 <= 440 && y1 >= 10 && y1 <= 80 && (r + c) % 2 == 0) {
                        g.fillRect(x1, y1, 10, 10);
                    }
                    if (x1 >= 300 && x1 <= 440 && y1 >= 140 && y1 <= 210 && (r + c) % 2 == 0) {
                        g.fillRect(x1, y1, 10, 10);
                    }
                    x1 += 10;
                }
                y1 += 10;
                x1 = 0;
            }
            g.setColor(Color.white);
            g.fillRect(345, 40, 60, 20);
            g.setColor(Color.darkGray);
            g.drawString("Reset", 360, 55);
            g.setColor(Color.white);
            g.fillRect(330, 170, 90, 20);
            g.setColor(Color.darkGray);
            g.drawString("Replace Cards", 335, 185);

            int x = 40;
            int y = 10;
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    Rectangle h = new Rectangle(x, y, (int) currentCards[r][c].getImage().getWidth(),
                            (int) currentCards[r][c].getImage().getHeight());
                    currentCards[r][c].setHitbox(h);
                    if (hitboxesOn[r][c]) {
                        g.drawRect(x, y - 5, (int) currentCards[r][c].getImage().getWidth(), (int) currentCards[r][c].getImage().getHeight() + 10);
                        g.setColor(Color.white);
                        g.fillRect(x, y - 5, (int) currentCards[r][c].getImage().getWidth(), (int) currentCards[r][c].getImage().getHeight() + 10);
                    }
                    g.drawImage(currentCards[r][c].getImage(), x, y, null);

                    x += 80;

                }
                y += 100;
                x -= 240;
            }
            g.setColor(Color.black);
            g.drawString(("Cards Left: " + deck.deckList.size()), 100, 410);
            g.drawString(("Possible Moves Left:  " + possibleMoves(currentCards)), 100, 440);
        }
    }

    public static boolean possibleMoves(Card[][] array) {

            String[][] cards = new String[3][3];
            for (int r = 0; r < array.length; r++) {
                for (int c = 0; c < array[0].length; c++) {
                    cards[r][c] = array[r][c].getValue();
                }
            }

            boolean moves = false;
            boolean k = false;
            boolean q = false;
            boolean j = false;
            for (String[] r : cards) {
                for (String card : r) {
                    if (card == null) {
                    } else if (card.equals("K")) {
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
                    if (cards[r][c] == null) {
                        total1 = 0;
                    } else if (cards[r][c].equals("A")) {
                        total1++;
                    } else if (cards[r][c].equals("J") || cards[r][c].equals("Q") || cards[r][c].equals("K") || cards[r][c] == null) {
                        total1 = 0;
                    } else {
                        total1 += Integer.parseInt(cards[r][c]);
                    }
                    for (int y = 0; y < cards.length; y++) {
                        for (int x = 0; x < cards[0].length; x++) {
                            if (cards[y][x] == null) {
                                total2 = 0;
                            } else if (cards[y][x].equals("A")) {
                                total2++;
                            } else if (cards[y][x].equals("J") || cards[y][x].equals("Q") || cards[y][x].equals("K") || cards[y][x] == null) {
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
            return moves;
        }


        public void mousePressed (MouseEvent e){
        Point p = e.getPoint();
        if(initilization){
                if(easy.contains(p)){
                    gameModeEasy = true;
                }
                if(hard.contains(p)){
                    gameModeEasy = false;
                }
                initilization = false;
            }
            else {
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        if (currentCards[r][c].getHitbox().contains(p)) {

                            if (hitboxesOn[r][c] && amountOfhitboxes <= 3) {
                                hitboxesOn[r][c] = false;
                                amountOfhitboxes--;
                            } else if (amountOfhitboxes < 3 && !flippedCards[r][c]) {
                                hitboxesOn[r][c] = true;
                                amountOfhitboxes++;
                            }

                        }
                    }
                }
                if (reset.contains(p)) {
                    deck = new Deck();
                    for (int r = 0; r < 3; r++) {
                        for (int c = 0; c < 3; c++) {
                            currentCards[r][c] = deck.getRandomCard();
                            hitboxesOn[r][c] = false;
                        }
                    }
                    amountOfhitboxes = 0;
                    initilization = false;
                }
                if (replace.contains(p)) {
                    ArrayList<String> listCards = new ArrayList<>();
                    ArrayList<Integer> position = new ArrayList<>();
                    for (int r = 0; r < hitboxesOn.length; r++) {
                        for (int c = 0; c < hitboxesOn[r].length; c++)
                            if (hitboxesOn[r][c]) {
                                listCards.add(currentCards[r][c].getValue());
                                position.add((r * 3) + c);
                            }
                    }
                    if (amountOfhitboxes == 3) {
                        boolean k = false;
                        boolean q = false;
                        boolean j = false;
                        for (String card : listCards) {
                            if (card.equals("K")) {
                                k = true;
                            } else if (card.equals("Q")) {
                                q = true;
                            } else if (card.equals("J")) {
                                j = true;
                            }
                        }
                        if (j && q && k) {
                            if (gameModeEasy) {
                                currentCards[(position.get(0) - position.get(0) % 3) / 3][position.get(0) % 3] = deck.getRandomCardEasy(currentCards, position.get(0));
                                currentCards[(position.get(1) - position.get(1) % 3) / 3][position.get(1) % 3] = deck.getRandomCardEasy(currentCards, position.get(1));
                                currentCards[(position.get(2) - position.get(2) % 3) / 3][position.get(2) % 3] = deck.getRandomCardEasy(currentCards, position.get(2));
                            } else {
                                currentCards[(position.get(0) - position.get(0) % 3) / 3][position.get(0) % 3] = deck.getRandomCard();
                                currentCards[(position.get(1) - position.get(1) % 3) / 3][position.get(1) % 3] = deck.getRandomCard();
                                currentCards[(position.get(2) - position.get(2) % 3) / 3][position.get(2) % 3] = deck.getRandomCard();
                            }
                            hitboxesOn[(position.get(0) - position.get(0) % 3) / 3][position.get(0) % 3] = false;
                            hitboxesOn[(position.get(1) - position.get(1) % 3) / 3][position.get(1) % 3] = false;
                            hitboxesOn[(position.get(2) - position.get(2) % 3) / 3][position.get(2) % 3] = false;
                            amountOfhitboxes = 0;
                        }

                    } else if (amountOfhitboxes == 2) {
                        int total = 0;
                        for (String card : listCards) {
                            if (card.equals("A")) {
                                total++;
                            } else if (card.equals("J") || card.equals("Q") || card.equals("K")) {
                                break;
                            } else {
                                total += Integer.parseInt(card);
                            }
                        }
                        if (total == 11) {
                            if (gameModeEasy) {
                                currentCards[(position.get(0) - position.get(0) % 3) / 3][position.get(0) % 3] = deck.getRandomCardEasy(currentCards, position.get(0));
                                currentCards[(position.get(1) - position.get(1) % 3) / 3][position.get(1) % 3] = deck.getRandomCardEasy(currentCards, position.get(1));
                            } else {
                                currentCards[(position.get(0) - position.get(0) % 3) / 3][position.get(0) % 3] = deck.getRandomCard();
                                currentCards[(position.get(1) - position.get(1) % 3) / 3][position.get(1) % 3] = deck.getRandomCard();
                            }
                            hitboxesOn[(position.get(0) - position.get(0) % 3) / 3][position.get(0) % 3] = false;
                            hitboxesOn[(position.get(1) - position.get(1) % 3) / 3][position.get(1) % 3] = false;
                            amountOfhitboxes = 0;
                        }
                    }
                }
            }

        }



        public void mouseReleased (MouseEvent e){
        }
        public void mouseEntered (MouseEvent e){
        }
        public void mouseExited (MouseEvent e){
        }
        public void mouseClicked (MouseEvent e){
        }
    }
