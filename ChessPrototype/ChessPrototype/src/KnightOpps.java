import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public class KnightOpps extends Piece {
    public KnightOpps(int column, int row) {
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
        int victim=-1;
        Scanner scanner = new Scanner(System.in);
        int direction = 0;
        while (direction < 1 || direction > 8) {
            System.out.println("Which direction do you want to go?");
            showExampleKnight(true);
            System.out.println("Enter the number: 1 to 8");
            try {
                direction =scanner.nextInt();
                super.movingWhicheverKnight(direction);
                super.goBack(possibleObstacles);
                victim = super.eatIt(possibleAttacks);
            }
            catch (InputMismatchException e)
            {
                System.out.println("You didn't enter a number from 1 to 8");
            }
        }
        return victim;
    }

    public int[] occupiedZones() {
        return new int[]{lastColumn.peek(), lastRow.peek()};
    }

    public int value() {
        return 3;
    }

    public int[] threateningZones() {
        return new int[]{lastColumn.peek() - 2, lastRow.peek() + 1, lastColumn.peek() - 2, lastRow.peek() - 1,lastColumn.peek() + 2, lastRow.peek() + 1, lastColumn.peek() + 2, lastRow.peek() - 1,lastColumn.peek() + 1, lastRow.peek() + 2, lastColumn.peek() - 1, lastRow.peek() + 2,lastColumn.peek() + 1, lastRow.peek() -2, lastColumn.peek() - 1, lastRow.peek() -2};
    }
    public void specialMoveLeftSide(){}     public void specialMoveRightSide(){}

    @Override
    public String toString() {
        return "k";
    }
}


