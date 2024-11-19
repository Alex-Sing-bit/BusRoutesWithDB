package ru.vsu.cs.baklanova;

public class Pair<V, T> {
    V  first;
    T second;

    public Pair(V first, T second) {
        this.first = first;
        this.second = second;
    }

    public V getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }
}
