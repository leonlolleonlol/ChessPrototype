import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<AllDaPieces> pieces = new ArrayList<>();
        ArrayList<AllDaPieces> pieceOpps = new ArrayList<>();
        ArrayList<Integer> lastPieceNumber = new ArrayList<>();
        ArrayList<Integer> turnCounterBlack = new ArrayList<>();
        ArrayList<Integer> turnCounterWhite = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(i, 1));
            pieceOpps.add(new PawnOpps(i, 6));
        }
        pieces.add(new Rook(0, 0));
        pieces.add(new Rook(7, 0));
        pieceOpps.add(new RookOpps(0, 7));
        pieceOpps.add(new RookOpps(7, 7));
        pieces.add(new Knight(1, 0));
        pieces.add(new Knight(6, 0));
        pieceOpps.add(new KnightOpps(1, 7));
        pieceOpps.add(new KnightOpps(6, 7));
        pieces.add(new Bishop(2, 0));
        pieces.add(new Bishop(5, 0));
        pieceOpps.add(new BishopOpps(2, 7));
        pieceOpps.add(new BishopOpps(5, 7));
        pieces.add(new Queen(3, 0));
        pieces.add(new King(4, 0));
        pieceOpps.add(new QueenOpp(4, 7));
        pieceOpps.add(new KingOpp(3, 7));
        final int INITIAL_NUMBER_OF_WHITE_PIECES = pieces.size();
        final int INITIAL_NUMBER_OF_BLACK_PIECES = pieceOpps.size();
        String[][] board = new String[8][8];
        drawBoard(board, pieces, pieceOpps, 1, true);
        Scanner scanner = new Scanner(System.in);
        int counter = 1;
        boolean check = false;
        boolean checkmate = false;
        int turnscounter = 0;
        while (!checkmate) {
            int pieceNum;
            if (!checkmate) {
                if (counter == 1) {
                    do {
                        System.out.println("Which piece do you want to move?");
                        pieceNum = scanner.nextInt();
                    }
                    while (pieceNum < 0 || pieceNum > pieces.size() - 1);
                } else {
                    do {
                        System.out.println("Which piece do you want to move?");
                        pieceNum = scanner.nextInt();
                    }
                    while (pieceNum < 0 || pieceNum > pieceOpps.size() - 1);
                }
                lastPieceNumber.add(pieceNum);
                if (counter > 0)
                    turnBased(pieceNum, pieces, pieceOpps);
                else
                    turnBased(pieceNum, pieceOpps, pieces);
                drawBoard(board, pieces, pieceOpps, counter, false);
                checkmate = checkProcedure(pieces, pieceOpps, lastPieceNumber, board, counter, check, turnscounter, turnCounterBlack,turnCounterWhite);
                if (pieces.size() < INITIAL_NUMBER_OF_WHITE_PIECES || pieceOpps.size() < INITIAL_NUMBER_OF_BLACK_PIECES) {
                    differenceCalculator(pieces, pieceOpps);
                }
                counter *= -1;
                turnscounter++;
            }
        }
        System.out.println("CHECKMATE!!!");
        if(counter==-1)
        {
            System.out.println("White's win!");
        }
        else
            System.out.println("Black's win!");
    }

    public static boolean checkProcedure(ArrayList<AllDaPieces> pieces, ArrayList<AllDaPieces> pieceOpps, ArrayList<Integer> lastPieceNumber, String[][] board, int counter, boolean check, int turnscounter, ArrayList<Integer> turnCounterBlack,ArrayList<Integer> turnCounterWhite) {
        boolean checkmate = false;
        if (turnscounter != 0) {
            if (counter == -1)
                check = verifyCheck(pieceOpps, pieces, lastPieceNumber.get(lastPieceNumber.size() - 1), board);
            else
                check = verifyCheck(pieces, pieceOpps, lastPieceNumber.get(lastPieceNumber.size() - 1), board);
        }
        if (check) {
            System.out.println("Check!");
            if(counter==-1)
                turnCounterWhite.add(turnscounter);
            else
                turnCounterBlack.add(turnscounter);
        }
        if (turnCounterWhite.size() > 1&&turnCounterBlack.size()>1) {
            if (turnCounterWhite.get(turnCounterWhite.size() - 1) - 2 == turnCounterWhite.get(turnCounterWhite.size() - 2)||turnCounterBlack.get(turnCounterBlack.size() - 1) - 2 == turnCounterBlack.get(turnCounterBlack.size() - 2))
                checkmate = true;
        }
        return checkmate;
    }

    public static boolean verifyCheck(ArrayList<AllDaPieces> attacker, ArrayList<AllDaPieces> defender, int pieceNum, String[][] board) {
        boolean check = false;
        AllDaPieces king = null;
        for (AllDaPieces p : defender) {
            if (p.value() == 0)
                king = p;
        }
        try {
            switch (attacker.get(pieceNum).toString()) {
                case "P":
                case "p":
                case "k":
                case "K":
                    for (int i = 0; i < attacker.get(pieceNum).threateningZones().length; i += 2) {
                        if (Arrays.equals(king.occupiedZones(), new int[]{attacker.get(pieceNum).threateningZones()[i], attacker.get(pieceNum).threateningZones()[i + 1]})) {
                            check = true;
                            break;
                        }
                    }
                    break;
                case "R":
                case "r":
                    check = calculateCheck(false, checkWithRook(king,attacker,pieceNum,board));
                    break;
                case "B":
                case "b":
                    check = calculateCheck(false, checkWithBishop(king,attacker,pieceNum,board));
                    break;
                case "Q":
                case "q":
                    check = calculateCheck(false, checkWithBishop(king,attacker,pieceNum,board));
                    if(!check)
                        check = calculateCheck(false, checkWithRook(king,attacker,pieceNum,board));
                    break;
            }
        }
        catch (NullPointerException e)
        {
            check=true;
        }
        return check;
    }
    public static String checkWithBishop(AllDaPieces king,ArrayList<AllDaPieces> attacker,  int pieceNum, String[][] board) {
        String stringToVerify = "";
        if(king.occupiedZones()[0] > attacker.get(pieceNum).lastColumn.peek())
        {
            if(king.occupiedZones()[1] > attacker.get(pieceNum).lastRow.peek())
            {
                for(int i=1;i<king.occupiedZones()[0]-attacker.get(pieceNum).lastColumn.peek();i++)
                {
                    stringToVerify += board[attacker.get(pieceNum).occupiedZones()[0]+i][ attacker.get(pieceNum).occupiedZones()[1]+i].charAt(0);
                }
            }
            else
                for(int i=1;i<king.occupiedZones()[0]-attacker.get(pieceNum).lastColumn.peek();i++)
                {
                    stringToVerify += board[attacker.get(pieceNum).occupiedZones()[0]+i][ attacker.get(pieceNum).occupiedZones()[1]-i].charAt(0);
                }
        }
        else
        {
            if(king.occupiedZones()[1] > attacker.get(pieceNum).lastColumn.peek())
            {
                for(int i=1;i<king.occupiedZones()[1]-attacker.get(pieceNum).lastRow.peek();i++)
                {
                    stringToVerify += board[attacker.get(pieceNum).occupiedZones()[0]-i][attacker.get(pieceNum).occupiedZones()[1]+i].charAt(0);
                }
            }
            else
                for(int i=1;i<king.occupiedZones()[1]-attacker.get(pieceNum).lastRow.peek();i++)
                {
                    stringToVerify += board[attacker.get(pieceNum).occupiedZones()[0]-i][attacker.get(pieceNum).occupiedZones()[1]-i].charAt(0);
                }
        }
        return stringToVerify;
    }

        public static String checkWithRook(AllDaPieces king,ArrayList<AllDaPieces> attacker,  int pieceNum, String[][] board) {
        String stringToVerify = "";
        if (king.occupiedZones()[0] == attacker.get(pieceNum).lastColumn.peek() || king.occupiedZones()[1] == attacker.get(pieceNum).lastRow.peek()) {
            if (king.occupiedZones()[0] == attacker.get(pieceNum).lastColumn.peek()) {
                if (king.occupiedZones()[1] > attacker.get(pieceNum).lastRow.peek()) {
                    for (int i = attacker.get(pieceNum).lastRow.peek() + 1; i < king.occupiedZones()[1]; i++) {
                        stringToVerify += board[king.occupiedZones()[0]][7 - i].charAt(0);
                    }
                } else {
                    for (int i = attacker.get(pieceNum).lastRow.peek() - 1; i > king.occupiedZones()[1]; i--) {
                        stringToVerify += board[king.occupiedZones()[0]][7 - i].charAt(0);
                    }
                }
            } else {
                if (king.occupiedZones()[0] > attacker.get(pieceNum).lastRow.peek()) {
                    for (int i = attacker.get(pieceNum).lastColumn.peek() + 1; i < king.occupiedZones()[0]; i++) {
                        stringToVerify += board[i][7 - king.occupiedZones()[1]].charAt(0);
                    }
                } else {
                    for (int i = attacker.get(pieceNum).lastColumn.peek() - 1; i > king.occupiedZones()[0]; i--) {
                        stringToVerify += board[i][7 - king.occupiedZones()[1]].charAt(0);
                    }
                }
            }
        }
            return stringToVerify;

    }
    public static boolean calculateCheck(boolean check, String stringToVerify) {
        int cpt = 0;
        if (stringToVerify.length() < 1) {
            check = true;
        } else {
            for (int i = 0; i < stringToVerify.length() - 1; i++) {
                if (stringToVerify.charAt(i) == stringToVerify.charAt(i + 1))
                    cpt++;
            }
            if (cpt == stringToVerify.length() - 1)
                check = true;
        }
        return check;
    }

    public static void differenceCalculator(ArrayList<AllDaPieces> pieces, ArrayList<AllDaPieces> pieceOpps) {
        int valueWhites = totalValue(pieces);
        int valueBlacks = totalValue(pieceOpps);
        if (valueBlacks > valueWhites) {
            System.out.println("Blacks got +" + (valueBlacks - valueWhites));
        } else if (valueBlacks < valueWhites) {
            System.out.println("Whites got +" + (valueWhites - valueBlacks));
        } else
            System.out.println("It's a tie!");
    }

    public static int totalValue(ArrayList<AllDaPieces> arrayList) {
        int totalValue = 0;
        for (AllDaPieces p : arrayList) {
            if (p.value()!=0)
                totalValue += p.value();
        }
        return totalValue;
    }

    public static void drawBoard(String[][] board, ArrayList<AllDaPieces> pieces, ArrayList<AllDaPieces> pieceOpps, int counter, boolean firstTime) {
        for (int j = 97; j < 8 + 97; j++) {
            System.out.print((char) (j) + "       ");
        }
        System.out.println();
        System.out.println();
        for (int h = 0; h < 8; h++) {
            for (int i = 0; i < 8; i++) {
                board[i][h] = "*  ";
            }
        }
        displayPieces(board, pieces);
        displayPieces(board, pieceOpps);
        if (counter == 1 && !firstTime)
            board = invertBoard(board);
        for (int h = 0; h < 8; h++) {
            for (int i = 0; i < 8; i++) {
                if (board[i][h].charAt(1) != 'i')
                    System.out.print(board[i][h] + "     ");
                else
                    System.out.print(board[i][h] + "    ");
            }
            System.out.print(h + 1);
            System.out.println();
            System.out.println();
        }
    }

    public static String[][] invertBoard(String[][] board) {
        String[][] temp = new String[8][8];
        for (int h = 7; h > -1; h--) {
            for (int i = 7; i > -1; i--) {
                temp[7 - i][7 - h] = board[i][h];
            }
        }
        return temp;
    }

    public static void displayPieces(String[][] board, ArrayList<AllDaPieces> arrayList) {
        for (int j = 0; j < arrayList.size(); j++) {
            for (int h = 0; h < 8; h++) {
                for (int i = 0; i < 8; i++) {
                    if (h == arrayList.get(j).occupiedZones()[0] && i == 7 - arrayList.get(j).occupiedZones()[1])
                        if (j < 10)
                            board[h][i] = arrayList.get(j) + "0" + j;
                        else
                            board[h][i] = arrayList.get(j) + "" + j;
                }
            }
        }
    }

    public static void turnBased(int pieceNum, ArrayList<AllDaPieces> attacker, ArrayList<AllDaPieces> defender) {
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        while (option < 1 || option > 4) {
            if (attacker.get(pieceNum).value() == 1) {
                System.out.println("1.move");
                System.out.println("2.eat");
                option = scanner.nextInt();
            } else {
                if (attacker.get(pieceNum).value() == 5) {
                    System.out.println("Castling? (y for yes n for no)");
                    String yesOrNo = scanner.nextLine();
                    if (Objects.equals(yesOrNo, "y")) {
                        System.out.println("Which side? (r for right l for left)");
                        String whichSide = scanner.nextLine();
                        if (Objects.equals(whichSide, "l"))
                            option = 3;
                        else if (Objects.equals(whichSide, "r"))
                            option = 4;
                    } else
                        option = 2;
                } else
                    option = 2;
            }
            ArrayList<int[]> possibleAttacks = new ArrayList<>();
            ArrayList<int[]> possibleObstacles = new ArrayList<>();
            for (AllDaPieces p : defender) {
                possibleAttacks.add(p.occupiedZones());
            }
            AllDaPieces king = null;
            for (AllDaPieces p : attacker) {
                if (p != attacker.get(pieceNum)) {
                    possibleObstacles.add(p.occupiedZones());
                    if (p.value() == 0)
                        king = p;
                }
            }
            boolean unable = false;
            int columnCastling = 0;
            if (Objects.equals(attacker.get(0).isWhite(), "black's"))
                columnCastling = 7;
            switch (option) {
                case 1:
                    attacker.get(pieceNum).movement(possibleAttacks, possibleObstacles, false);
                    break;
                case 2:
                    if (attacker.size() > 1) {
                        int dead = attacker.get(pieceNum).movement(possibleAttacks, possibleObstacles, true);
                        if (dead != -1)
                            defender.remove(dead);
                    } else
                        System.out.println("There's no piece to eat!");
                    break;
                case 3:
                    if (Objects.equals(attacker.get(0).isWhite(), "black's")) {
                        for (int[] p : possibleObstacles) {
                            if ((p[0] == 5 || p[0] == 6) && p[1] == columnCastling) {
                                unable = true;
                                break;
                            }
                        }
                    } else {
                        for (int[] p : possibleObstacles) {
                            if (p[0] > 0 && p[0] < 4 && p[1] == columnCastling) {
                                unable = true;
                                break;
                            }
                        }
                    }
                    if (!unable && attacker.get(pieceNum).lastColumn.size() + attacker.get(pieceNum).lastRow.size() + king.lastColumn.size() + king.lastRow.size() == 4) {
                        attacker.get(pieceNum).specialMoveLeftSide();
                        king.specialMoveLeftSide();
                    }
                    break;
                case 4:
                    if (Objects.equals(attacker.get(0).isWhite(), "black's")) {
                        for (int[] p : possibleObstacles) {
                            if (p[0] > 0 && p[0] < 3 && p[1] == columnCastling) {
                                unable = true;
                                break;
                            }
                        }
                    } else {
                        for (int[] p : possibleObstacles) {
                            if ((p[0] == 5 || p[0] == 6) && p[1] == columnCastling) {
                                unable = true;
                                break;
                            }
                        }
                    }
                    if (!unable && attacker.get(pieceNum).lastColumn.size() + attacker.get(pieceNum).lastRow.size() + king.lastColumn.size() + king.lastRow.size() == 4) {
                        attacker.get(pieceNum).specialMoveRightSide();
                        king.specialMoveRightSide();
                    }
                    break;
                default:
                    System.out.println("Enter 1 or 2");
            }
        }
        checkKingCollision(pieceNum, attacker, defender);
        if (attacker.get(pieceNum).lastRow.peek() == 0 && attacker.get(pieceNum).value() == 1) {
            attacker.add(pieceNum, new QueenOpp(attacker.get(pieceNum).lastColumn.peek(), 0));
            attacker.remove(attacker.get(pieceNum + 1));
        }
        if (attacker.get(pieceNum).lastRow.peek() == 0 && attacker.get(pieceNum).value() == 1) {
            attacker.add(pieceNum, new Queen(attacker.get(pieceNum).lastColumn.peek(), 7));
            attacker.remove(attacker.get(pieceNum + 1));
        }
        System.out.println("It is now " + defender.get(0).isWhite() + " turn");
    }

    public static void checkKingCollision(int pieceNum, ArrayList<AllDaPieces> attacker, ArrayList<AllDaPieces> defender) {
        AllDaPieces king = null;
        for (AllDaPieces p : defender) {
            if (p.value() == 0)
                king = p;
        }
        if (attacker.get(pieceNum).value() == 0) {
            AllDaPieces kingTwo = attacker.get(pieceNum);
            for (int i = 0; i < kingTwo.threateningZones().length; i += 2) {
                if (Arrays.equals(king.occupiedZones(), new int[]{kingTwo.threateningZones()[i], kingTwo.threateningZones()[i + 1]})) {
                    kingTwo.lastColumn.pop();
                    kingTwo.lastRow.pop();
                    break;
                }
            }
        }
    }
}