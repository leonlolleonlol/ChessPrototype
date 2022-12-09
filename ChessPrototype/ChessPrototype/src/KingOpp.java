import java.util.*;

public class KingOpp extends PieceOpps {

    public KingOpp(int column, int row) {
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
        int victim = -1;
        System.out.println("Move in x or + ?");
        char movementShape = scanner.nextLine().charAt(0);
        String direction = " ";
        if (movementShape != 'x' && movementShape != '+' && movementShape != 'X') {
            System.out.println("You didn't enter a valid or answer.");
        } else {
            if (movementShape == '+') {
                while (direction.charAt(0) < 97 || direction.charAt(0) > 117 || direction.length() != 1) {
                    System.out.println("Which direction do you want to go?");
                    System.out.println("Enter the first letter of the direction : (up, down, left, right)");
                    direction = scanner.nextLine();
                }
            } else {
                while (!Objects.equals(direction, "ne") && !Objects.equals(direction, "nw") && !Objects.equals(direction, "se") && !Objects.equals(direction, "sw")) {
                    System.out.println("Which direction do you want to go?");
                    showExampleBishop(true);
                    System.out.println("Enter the first two letters of the direction : (ne, nw, se, sw)");
                    direction = scanner.nextLine();
                }
            }
            switch (direction) {
                case "d" ->
                        victim = movingRookIfTrue(1, possibleAttacks, lastRow, lastColumn, possibleObstacles, 1, 1, true);
                case "l" ->
                        victim = movingRookIfTrue(1, possibleAttacks, lastColumn, lastRow, possibleObstacles, 1, 1, true);
                case "u" ->
                        victim = movingRookIfTrue(1, possibleAttacks, lastRow, lastColumn, possibleObstacles, -1, -1, true);
                case "r" ->
                        victim = movingRookIfTrue(1, possibleAttacks, lastColumn, lastRow, possibleObstacles, -1, -1, true);
                case "ne" ->
                        victim = movingRookIfTrue(1, possibleAttacks, lastRow, lastColumn, possibleObstacles, 1, 1, false);
                case "nw" ->
                        victim = movingRookIfTrue(1, possibleAttacks, lastRow, lastColumn, possibleObstacles, 1, -1, false);
                case "se" ->
                        victim = movingRookIfTrue(1, possibleAttacks, lastRow, lastColumn, possibleObstacles, -1, 1, false);
                case "sw" ->
                        victim = movingRookIfTrue(1, possibleAttacks, lastRow, lastColumn, possibleObstacles, -1, -1, false);
                default -> System.out.println("You didn't enter a valid character.");
            }
        }
        return victim;
    }
    public void specialMoveRightSide(){
        lastColumn.add(lastColumn.peek()-2);
    }
    public void specialMoveLeftSide(){
        lastColumn.add(lastColumn.peek()+2);
    }
    public int[] occupiedZones() {
        return new int[]{lastColumn.peek(), lastRow.peek()};
    }

    public int value() {
        return 0;
    }

    public int[] threateningZones() {
        int []zones=new int[16];
        int cpt=0;
        for(int i=-1;i<2;i++)
        {
            for(int j=-1;j<2;j++)
            {
                if(i!=0||j!=0)
                {
                    zones[cpt]=lastColumn.peek()+j;
                    cpt++;
                    zones[cpt]=lastRow.peek()+i;
                    cpt++;
                }
            }
        }
        return zones;
    }

    @Override
    public String toString() {
        return "ki";
    }
}

