package it.unibo.inner.test.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import it.unibo.inner.api.IterableWithPolicy;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

     private T[] elements;
    private Predicate <T>filter;

    public IterableWithPolicyImpl(T[]elements,Predicate<T> filter) {
        this.elements = elements;
        this.filter = filter;
    }
    
    public IterableWithPolicyImpl(T[]elements) {
        this(elements, t -> true);
    }
    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("[");
        for (var elem : this) {
            sb.append(elem + ", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
         return new FilterIterator();
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }

    private class FilterIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            while (currentIndex < elements.length) {
                T elem = elements[currentIndex];
                if (filter.test(elem)) {
                    return true;
                }
                currentIndex++;
            }
            return false;
        }

        @Override
        public T next() {
            if (hasNext()) {

                T element = elements[currentIndex];
                currentIndex++;
                return element;
            }
            throw new NoSuchElementException();
        }
    }
    
}
