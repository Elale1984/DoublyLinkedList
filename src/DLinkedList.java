import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

/**
 * provided file for DLinkedList Assignment 
 *
 * @author lkfritz
 */
public class DLinkedList<T extends Comparable<T>> {

    public static void main(String[] args) throws FileNotFoundException {

        DLinkedList<String> lst1 = new DLinkedList<>();
        DLinkedList<String> lst2 = new DLinkedList<>();

        Scanner fin = new Scanner(new File("text1.in"));
        String str;

        while (fin.hasNext()) {
            str = fin.next();
            str = cleanUp(str);
            /* Test Print */ System.out.println(str);;
            lst1.insertOrderUnique(str);
        }
        fin.close();
        System.out.println();
        fin = new Scanner(new File("text2.in"));
        while (fin.hasNext()) {
            str = fin.next();
            str = cleanUp(str);
            /* Test Print */ System.out.println(str);

            lst2.insertOrderUnique(str);
        }

        System.out.println("List 1:  " + lst1);
        System.out.println("List 2:  " + lst2);


        DLinkedList combined = lst1.merge(lst2);

        System.out.println("\nAFTER MERGE");
        System.out.println("List 1:  " + lst1);
        System.out.println("List 2:  " + lst2);
        System.out.println("\n" + combined);
    }


    /**
     * ASSIGNED
     * @param str
     * @return str in all lower case with LEADING and TRAILING non-alpha
     * chars removed
     */
    public static String cleanUp(String str) {

        str = str.toLowerCase(Locale.ROOT);
        char[] delimiters = new char[] {'!','@','#','$','%','&','(',')','.',',','?','\"','\''};

        for (char delim:
                delimiters) {

            int index = str.lastIndexOf(delim);

            if (index != -1) {
                str = str.substring(0, index) + str.substring(index + 1);
            }
        }

        return str;
    }

    //inner DNode class:  PROVIDED
    private class DNode {

        private DNode next, prev;
        private T data;

        private DNode(T val) {
            this.data = val;
            next = prev = this;
        }
    }

    //DLinkedList fields:  PROVIDED
    private DNode header;

    //create an empty list:  PROVIDED
    public DLinkedList() {
        header = new DNode(null);
    }

    /**
     * PROVIDED add
     *
     * @param item return ref to newly inserted node
     */
    public DNode add(T item) {
        //make a new node
        DNode newNode = new DNode(item);
        //update newNode
        newNode.prev = header;
        newNode.next = header.next;
        //update surrounding nodes
        header.next.prev = newNode;
        header.next = newNode;
        return newNode;
    }

    //PROVIDED
    public String toString() {
        String str = "[";
        DNode curr = header.next;
        while (curr != header) {
            str += curr.data + " ";
            curr = curr.next;
        }
        str = str.substring(0, str.length() - 1);
        return str + "]";
    }

    /**
     * ASSIGNED
     * remove val from the list
     *
     * @param val
     * @return true if successful, false otherwise
     */
    public boolean remove(T val) {

        if(this.header == null)
            return false;
        else{

            DNode currentNode = this.header;


            while (currentNode != null){
                DNode nextNode = currentNode.next.prev;
                DNode prevNode = currentNode.prev;

                if(currentNode.data.compareTo(val) == 0){
                    nextNode.prev = currentNode.prev;
                    prevNode.next = nextNode;
                    return true;

                }
                else {
                    currentNode = currentNode.next;
                }

            }


        }

        return true;
    }

    /**
     * ASSIGNED
     *
     * @param item
     */
    public void insertOrder(T item) {




    }

    /**
     * ASSIGNED
     *
     * @param item
     */
    public boolean insertOrderUnique(T item) {

        DNode head = this.header;

        if (head.data == null){
            this.header = add(item);
        }
        else {

            while (head.data != null ){

                if(head.data.compareTo(item) < 0){
                    DNode newNode = add(item);

                    System.out.println(head.next.data);
                    System.out.println(newNode.data + " is the new node and " + head.data + " is the head");

                }
                System.out.println("focus node is " + head.data);
                head = head.next;

            }
        }

        System.out.println(this.header.data);
        System.out.println(this.header.next.data);
        System.out.println(this.header.prev.data);
        return true;
    }

    /**
     * ASSIGNED
     * PRE:  this and rhs are sorted lists
     * @param rhs
     * @return list that contains this and rhs merged into a sorted list
     * POST:  returned list will not contain duplicates
     */
    public DLinkedList merge(DLinkedList rhs) {
        DLinkedList result = new DLinkedList();
        return result;
    }

}