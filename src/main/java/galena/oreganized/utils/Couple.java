package galena.oreganized.utils;

import com.mojang.datafixers.util.Pair;

public class Couple<T> extends Pair<T, T> {
    public Couple(T first, T second) {
        super(first, second);
    }

    public static <T> Couple<T> create(T left, T right) {
        return new Couple<>(left, right);
    }
}
