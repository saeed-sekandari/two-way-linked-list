
// Exercise24_03 - TwoWayLinkedList implementation and test
// Student Name: Saeed Sekandari
// Class and Section: COMP 182 - Tues/Thurs
// Date: April 2025

import java.util.*;

public class Exercise24_03 {
    public static void main(String[] args) {
        // Create a new TwoWayLinkedList of Strings
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();

        // Add elements to the list
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");

        // Print original list elements
        System.out.println("Original list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i)); // Print each element
        }

        // Remove element at index 1 ("Banana")
        list.remove(1);

        // Print list after removal
        System.out.println("\nAfter removing element at index 1:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // Use list iterator starting from index 1 and go backward
        System.out.println("\nUsing list iterator from index 1:");
        var iterator = list.listIterator(1);
        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous()); // Print elements in reverse order
        }
    }
}

// Implementation of a Two-Way (Doubly) Linked List
class TwoWayLinkedList<E> implements java.util.List<E> {
    private Node<E> head, tail; // Head and tail of the list
    private int size = 0; // Number of elements in the list

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(E e) {
            element = e;
        }
    }

    public TwoWayLinkedList() {}

    @Override
    public void add(int index, E e) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node<E> newNode = new Node<>(e);
        if (index == 0) {
            newNode.next = head;
            if (head != null) head.previous = newNode;
            head = newNode;
            if (tail == null) tail = head;
        } else if (index == size) {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        } else {
            Node<E> current = head;
            for (int i = 0; i < index; i++) current = current.next;
            newNode.next = current;
            newNode.previous = current.previous;
            if (current.previous != null) current.previous.next = newNode;
            current.previous = newNode;
        }
        size++;
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<E> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current.element;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<E> current = head;
        if (index == 0) {
            head = head.next;
            if (head != null) head.previous = null;
            if (head == null) tail = null;
        } else if (index == size - 1) {
            current = tail;
            tail = tail.previous;
            if (tail != null) tail.next = null;
        } else {
            for (int i = 0; i < index; i++) current = current.next;
            current.previous.next = current.next;
            current.next.previous = current.previous;
        }
        size--;
        return current.element;
    }

    @Override public int size() { return size; }
    @Override public boolean isEmpty() { return size == 0; }
    @Override public boolean contains(Object o) { throw new UnsupportedOperationException(); }
    @Override public Iterator<E> iterator() { throw new UnsupportedOperationException(); }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
    @Override public boolean remove(Object o) { throw new UnsupportedOperationException(); }
    @Override public boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(int index, Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public void clear() { head = tail = null; size = 0; }
    @Override public E set(int index, E element) { throw new UnsupportedOperationException(); }
    @Override public int indexOf(Object o) { throw new UnsupportedOperationException(); }
    @Override public int lastIndexOf(Object o) { throw new UnsupportedOperationException(); }
    @Override public List<E> subList(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIterator<>() {
            private Node<E> current = nodeAt(index);
            private Node<E> lastReturned = null;
            private int nextIndex = index;

            private Node<E> nodeAt(int i) {
                Node<E> temp = head;
                for (int j = 0; j < i; j++) temp = temp.next;
                return temp;
            }

            public boolean hasNext() { return nextIndex < size; }

            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                lastReturned = current;
                current = current.next;
                nextIndex++;
                return lastReturned.element;
            }

            public boolean hasPrevious() { return nextIndex > 0; }

            public E previous() {
                if (!hasPrevious()) throw new NoSuchElementException();
                current = (current == null) ? tail : current.previous;
                lastReturned = current;
                nextIndex--;
                return current.element;
            }

            public int nextIndex() { return nextIndex; }
            public int previousIndex() { return nextIndex - 1; }
            public void remove() { throw new UnsupportedOperationException(); }
            public void set(E e) {
                if (lastReturned == null) throw new IllegalStateException();
                lastReturned.element = e;
            }
            public void add(E e) { throw new UnsupportedOperationException(); }
        };
    }
}
