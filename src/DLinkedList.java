import org.w3c.dom.Node;

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
            /* Test Print */
            System.out.println(str);
            //lst1.insertOrder(str);
            lst1.insertOrderUnique(str);
        }
        fin.close();
        System.out.println();
        fin = new Scanner(new File("text2.in"));
        while (fin.hasNext()) {
            str = fin.next();
            str = cleanUp(str);
            /* Test Print */
            System.out.println(str);
            //lst2.insertOrder(str);
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
     *
     * @param str
     * @return str in all lower case with LEADING and TRAILING non-alpha
     * chars removed
     */

    /* Time complexity is O(n) */
    public static String cleanUp(String str) {

        str = str.toLowerCase(Locale.ROOT);

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isAlphabetic(str.charAt(i))) {
                str = str.replace(String.valueOf(str.charAt(i)), "");
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

        if (this.header == null)
            return false;
        else {

            DNode currentNode = this.header;


            while (currentNode != null) {
                DNode nextNode = currentNode.next.prev;
                DNode prevNode = currentNode.prev;

                if (currentNode.data.compareTo(val) == 0) {
                    nextNode.prev = currentNode.prev;
                    prevNode.next = nextNode;
                    return true;

                } else {
                    currentNode = currentNode.next;
                }

            }


        }

        return true;
    }


    // The insertOrder has a time complexity of O(n) - Linear Time, where n is the number of
    // nodes in the DLL because we only iterate through one full time in worst case. Everything else is
    // O(1) constant time which is factored out.

    /**
     * ASSIGNED
     *
     * @param item
     */
    public void insertOrder(T item) {
        DNode newNode = new DNode(item);
        DNode currentNode = this.header.next;
        while (currentNode != header) {
            // insert in the middle somewhere and then end
            if (item.compareTo(currentNode.data) < 0) {
                newNode.next = currentNode;
                newNode.prev = currentNode.prev;
                currentNode.prev.next = newNode;
                currentNode.prev = newNode;
                return;
            }
            // traverse to the next node
            currentNode = currentNode.next;
        }
        //  placed at the end
        newNode.next = currentNode;
        newNode.prev = currentNode.prev;
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
    }

    // The insertOrderUnique has a time complexity of O(n) - Linear Time, where n is the number of
    // nodes in the DLL because we only iterate through one full time in worst case. Everything else is
    // O(1) constant time which is factored out.
    /**
     * ASSIGNED
     *
     * @param item
     */
    public boolean insertOrderUnique(T item) {
        DNode newNode = new DNode(item);
        DNode currentNode = this.header.next;
        while (currentNode != header) {
            // if the node already exists, just return false
            if (item.equals(currentNode.data))
                return false;
            // otherwise, if it goes in the middle somewhere
            if (item.compareTo(currentNode.data) < 0) {
                newNode.next = currentNode;
                newNode.prev = currentNode.prev;
                currentNode.prev.next = newNode;
                currentNode.prev = newNode;
                return true;
            }
            // traverse to next node
            currentNode = currentNode.next;
        }
        // placed at the end
        newNode.next = currentNode;
        newNode.prev = currentNode.prev;
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        return true;

    }


    /**
     * ASSIGNED
     * PRE:  this and rhs are sorted lists
     *
     * @param rhs
     * @return list that contains this and rhs merged into a sorted list
     * POST:  returned list will not contain duplicates
     */

    // This merge method has a time complexity of O(n + n) because we iterate throughout 2 full times in the
    // worst case scenario.
    // Can be simplified to be O(n)
    public DLinkedList merge(DLinkedList rhs) {
        DLinkedList result = new DLinkedList();
        DNode currentNode = this.header.next;
        // first list
        while(!currentNode.equals(this.header)){
            // Remove the current node from the list by resetting the pointers.
            currentNode.next.prev = currentNode.prev;
            currentNode.prev.next = currentNode.next;
            // use the insertOrder because there shouldn't be any duplicates of the first list.
            result.insertOrder(currentNode.data);
            // advance to the next node
            currentNode = this.header.next;
        }
        // focus now switches to rhs list
        currentNode = rhs.header.next;
        while(!currentNode.equals(rhs.header)){
            // remove the currentNode from its place from the list by resetting the pointers.
            currentNode.next.prev = currentNode.prev;
            currentNode.prev.next = currentNode.next;
            // using insertOrderUnique because the second list could contain some of the same words as the first list.
            result.insertOrderUnique(currentNode.data);
            // advancing to the next node
            currentNode = rhs.header.next;
        }

        return result;
    }
}