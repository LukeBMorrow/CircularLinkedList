/*

        COMP 2140 SECTION A01
        INSTRUCTOR   Cameron(A01)
        ASSIGNMENT   2
        @author      Luke Morrow, 7787696
        @version     2018-10-19

        PURPOSE: This class allows for the creation of sets like those in
        discrete math, and allows for manipulation of those sets from file inputs,
        these manipulations are:
        union, difference, insert, delete, and print.

        */

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class A2MorrowLuke {


    public static void main(String[] args) {
        try
        { Set.test("a2testdata.txt");}//runs all of the tests

        catch(FileNotFoundException e)//prints the error
        { System.out.println("The file could not be opened: \n"+e); }

        System.out.println("End of program.");
    }

}



/*A standard node class,
    with getter, setters, next, data, and a constructor.
 */
class Node{
    private Node next;
    private int data;

    public Node(Node givenNext, int givenData)
    {
        next = givenNext;
        data = givenData;
    }

    /*getters and setters
     */
    public Node getNext() { return next; }

    public void setNext(Node next) { this.next = next; }

    public int getData() { return data; }
}



/*most of the leg-work of the file,
    contains:
        insert,
        delete,
        union,
        difference,
        print,
        and a standard constructor, an end node, and
        one constant.
 */
class Set {
    private Node end;//end of the linked list
    private static final int MAX_PER_LINE = 10;//max num of elements per line on println

    public Set() {
        end = new Node(null, Integer.MAX_VALUE);//make a dummy node
        end.setNext(end);
    }

    /*Inserts new items into the linked list,
        in sorted order and with no repetitions.
     */
    private void insert(int newData) {
        Node curr = end;

        //work
        while (curr.getNext().getData()<newData)
        {curr = curr.getNext(); }

        //use work
        if (curr.getNext().getData() != newData)
            curr.setNext(new Node(curr.getNext(), newData));
    }

    /*deletes the item in the linked list with
        the matching data to the parameter key.
     */
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

    /*changes an -this- linked list to be all the items
        that set a and set b have. (no repetitions, naturally)
     */
    private void union(Set a, Set b) {
        Set result = new Set();
        Node currA = a.end.getNext();
        Node currB = b.end.getNext();
        Node lastInsert = result.end;

        //work
        while (currA != a.end || currB != b.end) {
            if (currA.getData() < currB.getData() )//if a is smaller
            {
                lastInsert.setNext(new Node(lastInsert.getNext(), currA.getData()));
                currA = currA.getNext();
                lastInsert=lastInsert.getNext();
            } else if(currB.getData() < currA.getData()) {//if b is smaller or the same size
                lastInsert.setNext(new Node(lastInsert.getNext(), currB.getData()));
                currB = currB.getNext();
                lastInsert=lastInsert.getNext();
            }
            else
            {
                currB=currB.getNext();
            }
        }

        //use work
        end = result.end;
    }

    /*changes an -this- linked list to be all the items
        that set a, and set b, do NOT have in common.
     */
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
                lastInsert=lastInsert.getNext();
            } else if (currA.getData() == currB.getData()) { //if a is b
                currB = currB.getNext();
                currA = currA.getNext();
            } else                                       //if b is smaller
                currB = currB.getNext();
        }

        //use work
        end = result.end;
    }

    /*prints all items in the linked list.
     */
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

    /*Test reads from a given local file and runs different tests depending
        on the contents of the file.
        USES all of the methods found in Set.
     */
    public static void test(String fileName) throws FileNotFoundException {
        //opening file and making a scanner for it
        File inputFile = new File(fileName);
        Scanner scanner = new Scanner(inputFile);

        //initializing scanner containers
        String k;
        int whereToInsert, whatToInsert;//variables for insert
        int arrayToPrint;//array to print
        int unionIndex1, unionIndex2 ,unionDestinationArray;//set indexes for union
        int itemToBeDeleted, whatToDeleteFrom;//key and array for delete
        int differenceIndex1,differenceIndex2,diffDestinationArray;//set indexes for difference

        //initializing sets in array
        int numOfSets = scanner.nextInt();
        Set array[] = new Set[numOfSets];
        for (int i = 0; i < numOfSets; i++) {
            array[i] = new Set();
        }

        //work
        while (scanner.hasNext()) {
            k = scanner.next();//mode selector
            switch (k) {
                case "I"://INSERT
                    whereToInsert = scanner.nextInt();
                    whatToInsert = scanner.nextInt();

                    array[whereToInsert].insert(whatToInsert);
                    break;


                case "P"://PRINT
                    arrayToPrint = scanner.nextInt();

                    array[arrayToPrint].print(arrayToPrint);
                    break;


                case "U"://UNION
                    unionIndex1 = scanner.nextInt();
                    unionIndex2 = scanner.nextInt();
                    unionDestinationArray = scanner.nextInt();

                    array[unionDestinationArray].union(array[unionIndex1], array[unionIndex2]);
                    break;


                case "D"://DELETE
                    whatToDeleteFrom = Integer.parseInt(scanner.next());
                    itemToBeDeleted = Integer.parseInt(scanner.next());

                    array[whatToDeleteFrom].delete(itemToBeDeleted);
                    break;


                case "\\"://DIFFERENCE
                    differenceIndex1 = scanner.nextInt();
                    differenceIndex2 = scanner.nextInt();
                    diffDestinationArray = scanner.nextInt();

                    array[diffDestinationArray].difference(array[differenceIndex1], array[differenceIndex2]);
                    break;
            }
        }

    }
}