import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public class Pawn extends Piece {
    public Pawn(int column, int row) {
        lastColumn.add(column);
        lastRow.add(row);
    }

    public Stack<Integer> getLastColumn() {
        return lastColumn;
    }

    public void setLastColumn(Stack<Integer> lastColumn) {
        this.lastColumn = lastColumn;
    }

    public Stack<Integer> getLastRow() {
        return lastRow;
    }

    public void setLastRow(Stack<Integer> lastRow) {
        this.lastRow = lastRow;
    }

    public int movement(ArrayList<int[]> possibleAttacks, ArrayList<int[]> possibleObstacles, boolean eat) {
        int dead = -1;
        if (!eat) {
            if (lastRow.peek() < 7) {
                if (lastRow.peek() == 1) {
                    Scanner scanner = new Scanner(System.in);
                    int answer = -1;
                    try {
                        while (answer < 1 || answer > 2) {
                            System.out.println("Advance 1 block or two blocks?");
                            answer = scanner.nextInt();
                            switch (answer) {
                                case 1:
                                    lastRow.add(lastRow.peek() + 1);
                                    break;
                                case 2:
                                    lastRow.add(lastRow.peek() + 2);
                                    break;
                                default:
                                    System.out.println("Write 1 or two:");
                            }
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("You didn't enter a number from 1 to 2");
                    }
                } else
                    lastRow.add(lastRow.peek() + 1);
                for (int i = 0; i < possibleAttacks.size(); i++) {
                    if (lastColumn.peek() == possibleAttacks.get(i)[0] && lastRow.peek() == possibleAttacks.get(i)[1]) {
                        lastRow.pop();
                        System.out.println("I can't!");
                    }
                }
            } else
                System.out.println("I can't go further!");
            super.goBack(possibleObstacles);
        } else {
            Scanner scanner = new Scanner(System.in);
            int choice = -1;
            try {
                while (choice < 0 || choice > 1) {
                    System.out.println("Do you wanna eat right (0) or left (1)?");
                    choice = scanner.nextInt();
                    if (choice == 1) {
                        if (lastRow.peek() < 7 && lastColumn.peek() > 0) {
                            lastRow.add(lastRow.peek() + 1);
                            lastColumn.add(lastColumn.peek() - 1);
                            dead = confirmingEating(possibleAttacks);
                        } else
                            System.out.println("I can't!");
                    } else if (choice == 0) {
                        if (lastRow.peek() < 7 && lastColumn.peek() < 7) {
                            lastRow.add(lastRow.peek() + 1);
                            lastColumn.add(lastColumn.peek() + 1);
                            dead = confirmingEating(possibleAttacks);
                        } else
                            System.out.println("I can't!");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("You didn't enter a number from 0 to 1");
            }
        }
        return dead;
    }

    public int[] occupiedZones() {
        return new int[]{lastColumn.peek(), lastRow.peek()};
    }

    public int value() {
        return 1;
    }

    public int[] threateningZones() {
        return new int[]{lastColumn.peek() - 1, lastRow.peek() + 1, lastColumn.peek() + 1, lastRow.peek() + 1};
    }

    public int confirmingEating(ArrayList<int[]> possibleAttacks) {
        int victim = eatIt(possibleAttacks);
        if (victim == -1) {
            lastRow.pop();
            lastColumn.pop();
            System.out.println("I can't!");
        }
        return victim;
    }
    public void specialMoveLeftSide(){}     public void specialMoveRightSide(){}

    @Override
    public String toString() {
        return "P";
    }
}
