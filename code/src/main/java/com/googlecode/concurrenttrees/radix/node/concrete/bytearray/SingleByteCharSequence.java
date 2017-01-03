/**
 * Copyright 2012-2013 Niall Gallagher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.concurrenttrees.radix.node.concrete.bytearray;

/**
 * A {@link CharSequence} <i>view</i> onto a single byte representing any character that can be encoded with a single byte
 * using the UTF-8 encoding. This uses Java's built-in casting from UTF-8 to char primitives.
 *
 * @author Knut Wannheden
 */
public class SingleByteCharSequence implements CharSequence {

    private final byte b;

    SingleByteCharSequence(byte b) {
        this.b = b;
    }

    private static class Cache {
        private Cache() {}

        static final SingleByteCharSequence[] cache = new SingleByteCharSequence[-(-128) + 127 + 1];

        static {
            for(int i = 0; i < cache.length; i++)
                cache[i] = new SingleByteCharSequence((byte)(i - 128));
        }
    }

    public static SingleByteCharSequence of(byte b) {
        final int offset = 128;
        return Cache.cache[(int) b + offset];
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public char charAt(int index) {
        return (char) (b & 0xFF);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0) throw new IllegalArgumentException("start " + start + " < 0");
        if (end > 1) throw new IllegalArgumentException("end " + end + " > length " + length());
        if (end < start) throw new IllegalArgumentException("end " + end + " < start " + start);
        return end > start ? this : "";
    }

    @Override
    public String toString() {
        return Character.toString(charAt(0));
    }

    /**
     * Encodes a given {@code char} into a {@code byte} in UTF-8 encoding, with the requirement that
     * it must be possible to represent the character as a single byte in UTF-8. Otherwise an exception will be
     * thrown.
     *
     * @param input The {@link char} to encode
     * @return The character cast to a {@code byte} in UTF-8
     * @throws IllegalStateException If the character cannot be encoded as described
     */
    public static final byte toSingleByteUtf8Encoding(char input) {
        if (input > 255) {
            throw new ByteArrayCharSequence.IncompatibleCharacterException("Input cannot be represented as a single byte in UTF-8: " + input);
        }
        return (byte) input;
    }

    public static class IncompatibleCharacterException extends IllegalStateException {
        public IncompatibleCharacterException(String s) {
            super(s);
        }
    }

}
