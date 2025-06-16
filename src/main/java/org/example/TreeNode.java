package org.example;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TreeNode<T extends Comparable<T>> implements Comparator<TreeNode<T>> {

    public final T value;
    public final int depth;
    public final List<TreeNode<T>> childNodes;

    public TreeNode(T value, int depth) {
        this.value = value;
        this.depth = depth;
        this.childNodes = new LinkedList<>();
    }

    public void addChild(TreeNode<T> childNode) {
        this.childNodes.add(childNode);
    }

    public TreeNode<T> addChild(T childNodeValue, int depth) {
        final TreeNode<T> childNode = new TreeNode<T>(childNodeValue, depth);
        this.childNodes.add(childNode);
        return childNode;
    }

    public void showTreeNodes() {
        //BreathFirstSearchPrintTreeNodes.printNodes(this);
    }

    public T getValue() {
        return value;
    }

    public List<TreeNode<T>> getChildNodes() {
        return childNodes;
    }

    @Override
    public int compare(TreeNode<T> o1, TreeNode<T> o2) {
        return o1.value.compareTo(o2.value);
    }

    @Override
    public String toString() {
        final String spacing = IntStream.range(0, depth).mapToObj(i -> "  ").collect(Collectors.joining());
        return "\r\n" + spacing + "TreeNode{" +
                "value=" + value +
                ", depth=" + depth +
                ", childNodes=" + (childNodes.isEmpty() ? "[EMPTY]" : childNodes.stream().map(Object::toString).collect(Collectors.joining(","))) +
                spacing + "}\r\n";
    }
}

