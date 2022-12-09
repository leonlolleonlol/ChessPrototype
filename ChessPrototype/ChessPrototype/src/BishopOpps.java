import java.util.*;

public class BishopOpps extends Piece {

    public BishopOpps(int column, int row) {
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
        String direction = "";
        while (!Objects.equals(direction, "ne") && !Objects.equals(direction, "nw") && !Objects.equals(direction, "se") && !Objects.equals(direction, "sw")) {
            System.out.println("Which direction do you want to go?");
            showExampleBishop(true);
            System.out.println("Enter the first two letters of the direction : (ne, nw, se, sw)");
            direction = scanner.nextLine();
        }
        return directionMovement(possibleAttacks, eat, direction, possibleObstacles);
    }

    public int[] occupiedZones() {
        return new int[]{lastColumn.peek(), lastRow.peek()};
    }

    public int value() {
        return 3;
    }

    public int[] threateningZones() {
        return new int[]{lastColumn.peek() - 1, lastRow.peek() + 1, lastColumn.peek() + 1, lastRow.peek() + 1};
    }

    public int directionMovement(ArrayList<int[]> possibleAttacks, boolean eat, String direction, ArrayList<int[]> possibleObstacles) {
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
        switch (direction) {
            case "ne" ->
                    victim = movingRookIfTrue(stepsToTake, possibleAttacks, lastRow, lastColumn, possibleObstacles, 1, 1, false);
            case "nw" ->
                    victim = movingRookIfTrue(stepsToTake, possibleAttacks, lastRow, lastColumn, possibleObstacles, 1, -1, false);
            case "se" ->
                    victim = movingRookIfTrue(stepsToTake, possibleAttacks, lastRow, lastColumn, possibleObstacles, -1, 1, false);
            case "sw" ->
                    victim = movingRookIfTrue(stepsToTake, possibleAttacks, lastRow, lastColumn, possibleObstacles, -1, -1, false);
            default -> System.out.println("You didn't enter a valid character.");
        }
        return victim;
    }
    public void specialMoveLeftSide(){}     public void specialMoveRightSide(){}

    @Override
    public String toString() {
        return "b";
    }
}
