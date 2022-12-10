package de.shermanns.aoc22.util;

import org.apache.commons.lang3.StringUtils;

public class TreeUtils {
    private TreeUtils() {
    }

    public static <T> void printTree(final Node<T> node, final String appender) {
        final StringBuilder builder = new StringBuilder() //
                .append(StringUtils.repeat(appender, node.getHeight()))
                .append((node.hasChildren() ? "+ " : "- ")) //
                .append(node.getData());
        System.out.println(builder.toString());

        node.getChildren()
                .forEach(each -> TreeUtils.printTree(each, appender));
    }
}
