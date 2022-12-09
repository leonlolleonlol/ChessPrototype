import java.util.ArrayList;
import java.util.Stack;

public abstract class AllDaPieces {
    protected Stack<Integer> lastColumn = new Stack<>();
    protected Stack<Integer> lastRow = new Stack<>();
    abstract int[] occupiedZones() ;
    abstract int value();
    abstract int[] threateningZones() ;
    abstract int movement(ArrayList<int[]> possibleAttacks,ArrayList<int[]> possibleObstacles,boolean eat);
    abstract String isWhite();
    abstract void specialMoveRightSide();
    abstract void specialMoveLeftSide();
    protected void movingWhicheverKnight(int direction) {
        switch (direction) {
            case 1 : lastRow.add(lastRow.peek()+2);lastColumn.add(lastColumn.peek()-1);
                break;
            case 2 : lastRow.add(lastRow.peek()+2);lastColumn.add(lastColumn.peek()+1);
                break;
            case 3 : lastRow.add(lastRow.peek()+1);lastColumn.add(lastColumn.peek()+2);
                break;
            case 4 : lastRow.add(lastRow.peek()-1);lastColumn.add(lastColumn.peek()+2);
                break;
            case 5 : lastRow.add(lastRow.peek()-2);lastColumn.add(lastColumn.peek()+1);
                break;
            case 6 : lastRow.add(lastRow.peek()-2);lastColumn.add(lastColumn.peek()-1);
                break;
            case 7 : lastRow.add(lastRow.peek()-1);lastColumn.add(lastColumn.peek()-2);
                break;
            case 8 : lastRow.add(lastRow.peek()+1);lastColumn.add(lastColumn.peek()-2);
                break;
        }
    }
    public int movingRookIfTrue(int stepsToTake, ArrayList<int[]> possibleAttacks, Stack<Integer> firstStack,Stack<Integer> secondStack,ArrayList<int[]> possibleObstacles,int firstSign,int secondSign, boolean rookIfTrue) {
        int victim=-1;
        for(int i=0;i<stepsToTake;i++) {
            if(victim==-1) {
                int initialNum = firstStack.peek();
                int initialNumTwo = secondStack.peek();
                firstStack.add(firstStack.peek() + firstSign);
                if(rookIfTrue)
                    secondStack.add(secondStack.peek());
                else
                    secondStack.add(secondStack.peek()+secondSign);
                victim = eatIt(possibleAttacks);
                goBack(possibleObstacles);
                if (initialNum == firstStack.peek() && initialNumTwo == secondStack.peek())
                    break;
            }
        }
        return victim;
    }
    protected void goBack(ArrayList<int[]> possibleObstacles)
    {
        for (int[] p:possibleObstacles)
        {
            if(p[0]==lastColumn.peek()&&p[1]==lastRow.peek()||(lastColumn.peek()<0||lastColumn.peek()>7||lastRow.peek()<0||lastRow.peek()>7))
            {
                lastColumn.pop();
                lastRow.pop();
            }
        }
    }
    protected int eatIt(ArrayList<int[]> possibleAttacks)
    {
        int victim=-1;
        for (int i=0;i<possibleAttacks.size();i++)
        {
            if(possibleAttacks.get(i)[0]==lastColumn.peek()&&possibleAttacks.get(i)[1]==lastRow.peek())
            {
                victim=i;
                break;
            }
        }
        return victim;
    }
    public void showExampleKnight(boolean inversion)
    {
        String [][]example;
        if(!inversion)
            example= new String[][]{{"*", "1", "*", "2", "*"}, {"8", "*", "*", "*", "3"}, {"*", "*", "k", "*", "*"}, {"7", "*", "*", "*", "4"}, {"*", "6", "*", "5", "*"}};
        else
            example= new String[][]{{"*", "5", "*", "6", "*"}, {"4", "*", "*", "*", "7"}, {"*", "*", "k", "*", "*"}, {"3", "*", "*", "*", "8"}, {"*", "2", "*", "1", "*"}};
        for(int h=0;h< example.length;h++)
        {
            for (int i=0;i< example[h].length;i++)
            {
                System.out.print(example[h][i]+"  ");
            }
            System.out.println();
        }
    }
    public void showExampleBishop(boolean inversion)
    {
        String [][] example;
            if(!inversion)
                example=new String[][]{ {"nw","*","ne"},{"*"," b","*"},{"sw","*","se"}};
            else
                example=new String[][]{ {"se","*","sw"},{"*"," b","*"},{"ne","*","nw"}};
        for(int h=0;h< example.length;h++)
        {
            for (int i=0;i< example[h].length;i++)
            {
                System.out.print(example[h][i]+"  ");
            }
            System.out.println();
        }
    }
}
