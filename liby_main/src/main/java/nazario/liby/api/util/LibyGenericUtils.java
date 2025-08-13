package nazario.liby.api.util;

import java.util.stream.Stream;

public class LibyGenericUtils {
    public static String streamToString(Stream<String> stream, String suffix) {
        StringBuilder builder = new StringBuilder();

        stream.forEach(str -> {
            builder.append(str + suffix);
        });

        return builder.toString();
    }
}
