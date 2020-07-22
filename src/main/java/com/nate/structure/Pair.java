package com.nate.structure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.gson.annotations.SerializedName;
import com.nate.model.enums.Card;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Pair<T> {

    @NotNull
    @SerializedName("first")
    private T first;

    @NotNull
    @SerializedName("second")
    private T second;

    @JsonCreator
    public Pair(T t1, T t2) {
        first = t1;
        second = t2;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return first + ", " + second;
    }

    private static Pair<Card> getHighestHand(Pair<Card> first, Pair<Card> second) {
        int secondHandMax = Math.max(second.getFirst().value, second.getSecond().value);
        int firstHandMax = Math.max(first.getFirst().value, first.getSecond().value);
        if (secondHandMax == firstHandMax) {
            int secondHandSecond= Math.min(second.getFirst().value, second.getSecond().value);
            int firstHandSecond = Math.min(first.getFirst().value, first.getSecond().value);

            if (secondHandSecond == firstHandSecond) {
                return null;
            } else if (secondHandSecond > firstHandSecond) {
                return second;
            } else {
                return first;
            }
        } else if (secondHandMax > firstHandMax) {
            return second;
        } else {
            return first;
        }
    }

    public Set<T> toSet() {
        Set<T> r = new HashSet<>();
        r.add(first);
        r.add(second);
        return r;
    }

    /*
        First < Second @return < 0
        First == Second @return 0
        First > Second @return > 0
    */
    public static int compare(Pair<Card> o1, Pair<Card> o2) {
        if (o1.equals(o2)) {
            return 0;
        }
        Pair<Card> first = ((Pair<Card>)o1);
        Pair<Card> second = ((Pair<Card>)o2);

        if (second.getFirst().value == second.getSecond().value && first.getFirst().value != first.getSecond().value) {
            return -1;

        } else if (second.getFirst().value != second.getSecond().value && first.getFirst().value == first.getSecond().value) {
            return 1;
        } else  {
            Pair<Card> better = getHighestHand(first, second);
            if (better == null) {
                return 0;
            } else if (better.equals(first)) {
                return 1;
            } else if (better.equals(second)) {
                return -1;
            }
            System.out.println("ERROR!!!!  Pair.java +80");
            return 200;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?> pair = (Pair<?>) o;
        if (!Objects.equals(first, pair.first) || !Objects.equals(second, pair.second)) {
            return Objects.equals(first, pair.second) &&
                    Objects.equals(second, pair.first);
        }
        return true;
    }

    @Override
    public int hashCode() {

        return Objects.hash(first, second);
    }

    public enum STRENGTH {
        WORSE(-1),
        SAME(0),
        BETTER(1);

        public int value;

        STRENGTH(int value) {
            this.value = value;
        }

        public static STRENGTH getStrength(int value) {
            if (value == -1 || value < -1) {
                return WORSE;
            } else if (value == 0) {
                return SAME;
            } else {
                return BETTER;
            }
        }
    }
}

