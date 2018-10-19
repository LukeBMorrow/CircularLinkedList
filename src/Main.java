import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        try
        { Set.test("a2testdata.txt");}

        catch(FileNotFoundException e)
        { System.out.println(e); }

        System.out.println("End of program.");
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




class Set {
    private Node end;
    private static final int MAX_PER_LINE = 10;

    public Set() {
        end = new Node(null, Integer.MAX_VALUE);//make a dummy node
        end.setNext(end);
    }


    private void insert(int newData) {
        Node curr = end.getNext();

        //work
        while (curr.getData() < newData)
        {curr = curr.getNext(); }

        //use work
        if (curr.getData() != newData)
            curr.setNext(new Node(curr.getNext(), newData));
    }


    private void delete(int key) {
        Node curr = end.getNext();
        Node prev = end;

        //work
        while (curr.getData() < key) {
            prev = curr;
            curr = curr.getNext();
        }

        //use work
        if (curr.getData() == key) {
            prev.setNext(curr.getNext());
        }
    }


    private void union(Set a, Set b) {
        Set result = new Set();
        Node currA = a.end.getNext();
        Node currB = b.end.getNext();
        Node lastInsert = result.end;

        //work
        while (currA != a.end || currB != b.end) {
            if (currA.getData() < currB.getData())//if a is smaller
            {
                lastInsert.setNext(new Node(lastInsert.getNext(), currA.getData()));
                currA = currA.getNext();
            } else {//if b is smaller or the same size
                lastInsert.setNext(new Node(lastInsert.getNext(), currB.getData()));
                currB = currB.getNext();
            }
        }

        //use work
        end = result.end;
    }


    private void difference(Set a, Set b) {
        Set result = new Set();
        Node currA = a.end.getNext();
        Node currB = b.end.getNext();
        Node lastInsert = result.end;

        //work
        while (!(currA == a.end && currB == b.end)) {
            if (currA.getData() < currB.getData())         //if a is smaller
            {
                lastInsert.setNext(new Node(lastInsert.getNext(), currA.getData()));
                currA = currA.getNext();
            } else if (currA.getData() == currB.getData()) { //if a is b
                currB = currB.getNext();
                currA = currA.getNext();
            } else                                       //if b is smaller
                currB = currB.getNext();
        }

        //use work
        end = result.end;
    }


    private void print(int setNum) {
        String construct = "Set "+setNum+": \n { ";
        Node curr = end.getNext();
        int counter = 1;

        //work
        while (curr != end) {
            if (counter == 1)//if first item
            {
                construct += curr.getData();
            }//first item formatting correction
            else
                construct += ", " + curr.getData();

            if (counter % MAX_PER_LINE == 0)//make a new line if it is a long set
                construct += "\n";
            counter++;
            curr=curr.getNext();
        }
        construct += " }";

        //use work
        System.out.println(construct);

    }

    public static void test(String fileName) throws FileNotFoundException {
        File inputFile = new File(fileName);
        Scanner scanner = new Scanner(inputFile);
        int numOfSets = scanner.nextInt();
        Set array[] = new Set[numOfSets];
        for (int i = 0; i < numOfSets; i++) {
            array[i] = new Set();
        }

        while (scanner.hasNext()) {
            String k = scanner.next();
            switch (k) {
                case "I":
                    int arrayIndexI = Integer.parseInt(scanner.next());
                    int dataI = Integer.parseInt(scanner.next());
                    array[arrayIndexI].insert(dataI);
                    break;
                case "P":
                    int arrayIndexP = scanner.nextInt();
                    array[arrayIndexP].print(arrayIndexP);
                    break;
                case "U":
                    int unionIndex1 = scanner.nextInt();
                    int unionIndex2 = scanner.nextInt();
                    int arrayIndexU = scanner.nextInt();
                    array[arrayIndexU].union(array[unionIndex1], array[unionIndex2]);
                    break;
                case "D":
                    int arrayIndexD = Integer.parseInt(scanner.next());
                    int dataD = Integer.parseInt(scanner.next());
                    array[arrayIndexD].delete(dataD);
                    break;
                case "\\":
                    int differenceIndex1 = scanner.nextInt();
                    int differenceIndex2 = scanner.nextInt();
                    int arrayIndexDiff = scanner.nextInt();
                    array[arrayIndexDiff].difference(array[differenceIndex1], array[differenceIndex2]);
                    break;
            }
        }

    }
}