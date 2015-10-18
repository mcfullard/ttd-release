package za.ttd.mapgen;

import java.util.Random;

/**
 * Class to support returning enums with a cache to avoid creating superfluous arrays of enums
 *
 * @author minnaar
 * @since 2015/07/27.
 */
public class RandomEnum<E extends Enum> {
    private Random random;
    private final E[] values;

    public RandomEnum(Class<E> token, Random random) {
        values = token.getEnumConstants();
        this.random = random;
    }

    public E random() {
        return values[random.nextInt(values.length)];
    }
}
