package ru.shashki.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 12.05.15
 * Time: 20:42
 */
public class StreamUtil {

    public static final int BUFFER_SIZE = 8192;

    public static long copy(final InputStream input, final OutputStream output)
            throws IOException {

        return copy(input, output, BUFFER_SIZE);

    }

    public static long copy(final InputStream input, final OutputStream output,
                            int bufferSize) throws IOException {

        final byte[] buffer = new byte[bufferSize];
        int n = 0;
        long count = 0;
        while ((n = input.read(buffer)) > 0) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}