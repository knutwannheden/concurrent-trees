package com.googlecode.concurrenttrees.radix;

import com.googlecode.concurrenttrees.common.KeyValuePair;

public interface TreeSearchResult<O> {

    Iterable<CharSequence> asKeyIterable();

    Iterable<O> asValueIterable();

    Iterable<KeyValuePair<O>> asKeyValuePairIterable();

    // TODO
    // void accept(Visitor visitor);
}
