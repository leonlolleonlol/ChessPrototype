import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public class Rook extends Piece {

    public Rook(int column, int row) {
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
        Scanner scanner = new Scanner(System.in);
        char direction = ' ';
        while (direction < 97 || direction > 117) {
            System.out.println("Which direction do you want to go?");
            System.out.println("Enter the first letter of the direction : (up, down, left, right)");
            direction = scanner.nextLine().charAt(0);
        }
        return directionMovement(possibleAttacks, eat, direction, possibleObstacles);
    }

    public int[] occupiedZones() {
        return new int[]{lastColumn.peek(), lastRow.peek()};
    }

    public int value() {
        return 5;
    }

    public int[] threateningZones() {
        return new int[]{lastColumn.peek() - 1, lastRow.peek() + 1, lastColumn.peek() + 1, lastRow.peek() + 1};
    }

    public int directionMovement(ArrayList<int[]> possibleAttacks, boolean eat, Character z, ArrayList<int[]> possibleObstacles) {
        Scanner scanner = new Scanner(System.in);
        int victim = -1;
        int stepsToTake = -1;
        try {
            while (stepsToTake < 1 || stepsToTake > 7) {
                System.out.println("You want to go ... steps in that direction:");
                stepsToTake = scanner.nextInt();
                if (stepsToTake < 1 || stepsToTake > 7)
                    System.out.println("Enter a valid number from 1 to 7");
            }
        } catch (InputMismatchException e) {
            System.out.println("You didn't enter a valid number from 1 to 7");
        }
        switch (z) {
            case 'u' ->
                    victim = movingRookIfTrue(stepsToTake, possibleAttacks, lastRow, lastColumn, possibleObstacles, 1,1, true);
            case 'r' ->
                    victim = movingRookIfTrue(stepsToTake, possibleAttacks, lastColumn, lastRow, possibleObstacles, 1,1, true);
            case 'd' ->
                    victim = movingRookIfTrue(stepsToTake, possibleAttacks, lastRow, lastColumn, possibleObstacles, -1,-1, true);
            case 'l' ->
                    victim = movingRookIfTrue(stepsToTake, possibleAttacks, lastColumn, lastRow, possibleObstacles, -1,-1, true);
            default -> System.out.println("You didn't enter a valid character.");
        }
        return victim;
    }
    public void specialMoveRightSide(){
        lastColumn.add(lastColumn.peek()-2);
    }
    public void specialMoveLeftSide(){
        lastColumn.add(lastColumn.peek()+3);
    }

    @Override
    public String toString() {
        return "R";
    }
}
