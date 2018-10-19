import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        try
        { Set.test("a2testdata.txt");}

        catch(FileNotFoundException e)
        { System.out.println(e); }

    }

}




class Node{
    private Node next;
    private int data;

    public Node(Node givenNext, int givenData)
    {
        next = givenNext;
        data = givenData;
    }

    public Node getNext() { return next; }

    public void setNext(Node next) { this.next = next; }

    public int getData() { return data; }
}




class Set{
    private Node end;
    private static final int MAX_PER_LINE = 10;

    public Set() {
        end = new Node(null, Integer.MAX_VALUE);//make a dummy node
    }


    private void insert(int newData)
    {
        Node curr=end.getNext();

        //work
        while(curr.getData()<newData)//&& curr!=end removed due to redundancy
        { curr=curr.getNext();}

        //use work
        if(curr.getData()!=newData)
            curr.setNext(new Node(curr.getNext(),newData));
    }


    private void delete(int key)
    {
        Node curr = end.getNext();
        Node prev=end;

        //work
        while(curr.getData()<key) {
            prev=curr;
            curr=curr.getNext();
        }

        //use work
        if(curr.getData()==key)
        { prev.setNext(curr.getNext()); }
    }


    private void union(Set a, Set b)
    {
        Set result = new Set();
        Node currA = a.end.getNext();
        Node currB = b.end.getNext();
        Node lastInsert = result.end;

        //work
        while(!(currA==end && currB==end))
        {
            if(currA.getData()<currB.getData())//if a is smaller
            {
                lastInsert.setNext(new Node(lastInsert.getNext(),currA.getData()));
                currA=currA.getNext();
            }else {//if b is smaller or the same size
                lastInsert.setNext(new Node(lastInsert.getNext(), currB.getData()));
                currB=currB.getNext();
            }
        }

        //use work
        end = result.end;
    }


    private void difference(Set a, Set b)
    {
        Set result = new Set();
        Node currA = a.end.getNext();
        Node currB = b.end.getNext();
        Node lastInsert = result.end;

        //work
        while(!(currA==end && currB==end))
        {
            if(currA.getData()<currB.getData())         //if a is smaller
            {
                lastInsert.setNext(new Node(lastInsert.getNext(),currA.getData()));
                currA=currA.getNext();
            }else if(currA.getData()==currB.getData()){ //if a is b
                currB=currB.getNext();
                currA=currA.getNext();
            }else                                       //if b is smaller
                currB=currB.getNext();
        }

        //use work
        end=result.end;
    }


    private void print()
    {
        String construct = "Set: \n { ";
        Node curr = end.getNext();
        int counter=0;

        //work
        while(curr!=end)
        {
            if(counter==0)//if first item
            { construct+=curr.getData();}//first item formatting correction
            else
                construct+=", "+curr.getData();

            if(counter % MAX_PER_LINE == 0)//make a new line if it is a long set
                construct+="\n";

            counter++;
        }

        //use work
        System.out.println(construct);

    }

    public static void test(String fileName) throws FileNotFoundException
    {
        File inputFile = new File(fileName);
        Scanner scanner = new Scanner(inputFile);
        int numOfSets = scanner.nextInt();
        Set array[] = new Set[numOfSets];
        for(int i = 0; i < numOfSets;i++)
        { array[i]=new Set(); }

        while(scanner.hasNext())
        {
            switch(scanner.next()){
                case "I": array[scanner.nextInt()].insert(scanner.nextInt());//good
                break;
                case "P": array[scanner.nextInt()].print();//good
                break;
                case "U":
                    int a = scanner.nextInt();
                    int b = scanner.nextInt();
                    int c = scanner.nextInt();
                    array[c].union(array[a],array[b]);//experimental
                break;
                case "D": array[scanner.nextInt()].delete(scanner.nextInt());//good
                break;
                case "\\":
                    int d = scanner.nextInt();
                    int e = scanner.nextInt();
                    int f = scanner.nextInt();
                    array[f].difference(array[d],array[e]);//experimental
                break;
            }
        }

    }
}