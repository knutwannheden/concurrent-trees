package com.googlecode.concurrenttrees.radix.node.concrete.bytearray;

import com.googlecode.concurrenttrees.radix.node.Node;
import com.googlecode.concurrenttrees.radix.node.concrete.voidvalue.VoidValue;

import java.util.Collections;
import java.util.List;

/**
 * Similar to {@link ByteArrayNodeLeafVoidValue} but with an incoming length of a single character only.
 * <p/>
 * Supports only characters which can be represented as a single byte in UTF-8. Throws an exception if characters
 * are encountered which cannot be represented as a single byte.
 *
 * @author Knut Wannheden
 */
public class SingleByteNodeLeafVoidValue implements Node {

    // Characters in the edge arriving at this node from a parent node.
    // Once assigned, we never modify this...
    private final byte incomingEdgeChar;

    public SingleByteNodeLeafVoidValue(char edgeChar) {
        this.incomingEdgeChar = SingleByteCharSequence.toSingleByteUtf8Encoding(edgeChar);
    }

    @Override
    public CharSequence getIncomingEdge() {
        return SingleByteCharSequence.of(incomingEdgeChar);
    }

    @Override
    public Character getIncomingEdgeFirstCharacter() {
        return (char) (incomingEdgeChar & 0xFF);
    }

    @Override
    public Object getValue() {
        return VoidValue.SINGLETON;
    }

    @Override
    public Node getOutgoingEdge(Character edgeFirstCharacter) {
        return null;
    }

    @Override
    public void updateOutgoingEdge(Node childNode) {
        throw new IllegalStateException("Cannot update the reference to the following child node for the edge starting with '" + childNode.getIncomingEdgeFirstCharacter() +"', no such edge already exists: " + childNode);
    }

    @Override
    public List<Node> getOutgoingEdges() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node{");
        sb.append("edge=").append(getIncomingEdge());
        sb.append(", value=").append(VoidValue.SINGLETON);
        sb.append(", edges=[]");
        sb.append("}");
        return sb.toString();
    }

}
