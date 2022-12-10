package de.shermanns.aoc22.day07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import de.shermanns.aoc22.Base;
import de.shermanns.aoc22.util.Node;
import de.shermanns.aoc22.util.Util;

public class Day07 extends Base {
    private static final String INPUT_TXT = "input.txt";

    private static final long MAX_FILE_SIZE = 100000L;

    private static final long MAX_DISK_SPACE = 70000000L;

    private static final long FREE_DISK_SPACE_NEEDED = 30000000L;

    private final Node<FileTreeEntry> root = new Node<>(new FileTreeEntry("/", 0, true));

    private Node<FileTreeEntry> workingNode = this.root;

    private long usedSpace = 0L;

    public static void main(final String[] args) {
        final Day07 day07 = new Day07();

        day07.logger.info("Lade Datei " + Day07.INPUT_TXT);
        final List<String> zeilen = Util.loadResource(day07.getClass(), Day07.INPUT_TXT, day07.logger);

        day07.loeseTeil1(zeilen);
        day07.loeseTeil2();
    }

    private void loeseTeil2() {
        this.usedSpace = Day07.MAX_DISK_SPACE
                - this.root.getData()
                        .getSize();

        final List<FileTreeEntry> dirList = new ArrayList<>();
        findDirToBeDeleted(this.root, dirList);
        
        Collections.sort(dirList, Comparator.comparing(FileTreeEntry::getSize));
        
        final long ergebnisTeil2 = dirList.get(0).getSize();

        this.logger.log(Level.INFO, "Ergebnis Teil 2: {0}", ergebnisTeil2);
    }

    private void findDirToBeDeleted(final Node<FileTreeEntry> node, List<FileTreeEntry> foundChildren) {
        for (final Node<FileTreeEntry> child : node.getChildren()) {
            final FileTreeEntry fte = child.getData();
            final long size = fte.getSize();

            if (fte.isDirectory() && this.usedSpace + size >= Day07.FREE_DISK_SPACE_NEEDED) {
                System.out.println(size);
                foundChildren.add(fte);
            }

            findDirToBeDeleted(child, foundChildren);
        }
    }

    private void loeseTeil1(final List<String> zeilen) {
        createTree(zeilen);

        calculateDirSize(this.root);

        // TreeUtils.printTree(this.root, " ");

        final long ergebnisTeil1 = calculateSumDirWithSizeAlmost100k();

        this.logger.log(Level.INFO, "Ergebnis Teil 1: {0}", ergebnisTeil1);
    }

    private long calculateSumDirWithSizeAlmost100k() {
        final List<FileTreeEntry> dirList = new ArrayList<>();
        findDirsWithSizeAlmost100k(this.root, dirList);

        Collections.sort(dirList, Comparator.comparing(FileTreeEntry::getSize));

        return dirList.stream()
                .map(FileTreeEntry::getSize)
                .reduce(0L, Long::sum);
    }

    private void findDirsWithSizeAlmost100k(final Node<FileTreeEntry> node, final List<FileTreeEntry> foundChildren) {
        node.getChildren()
                .forEach(each -> {
                    final FileTreeEntry fte = each.getData();

                    if (fte.isDirectory() && fte.getSize() <= Day07.MAX_FILE_SIZE) {
                        foundChildren.add(fte);
                    }
                    findDirsWithSizeAlmost100k(each, foundChildren);
                });
    }

    private void calculateDirSize(final Node<FileTreeEntry> node) {
        node.getChildren()
                .stream()
                .forEach(this::calculateDirSize);

        long dirSize = 0L;
        for (final Node<FileTreeEntry> child : node.getChildren()) {
            dirSize += child.getData()
                    .getSize();
        }

        if (node.getData()
                .isDirectory()) {
            node.getData()
                    .setSize(dirSize);
        }
    }

    public void createTree(final List<String> zeilen) {
        for (final String zeile : zeilen) {
            if (zeile.startsWith("$")) {
                processCommand(zeile.substring(2));
            }
            else {
                processFileOrDirectory(zeile);
            }
        }
    }

    private void processCommand(final String zeile) {
        if (zeile.startsWith("cd")) {
            processCd(zeile);
        }
        else if (zeile.startsWith("ls")) {
            // Nothing to do
        }
    }

    private void processCd(final String zeile) {
        final String[] zerlegteZeile = zeile.split(" ");
        final String argument = zerlegteZeile[1];

        if (argument.equals("..")) {
            if (!this.workingNode.isRoot()) {
                this.workingNode = this.workingNode.getParent();
            }
        }
        else if (argument.equals("/")) {
            this.workingNode = this.root;
        }
        else {
            final Node<FileTreeEntry> directory = findDirectory(argument);
            if (directory != null) {
                this.workingNode = directory;
            }
            else {
                this.logger.log(Level.SEVERE, "Verzeichnis {0} nicht gefunden", argument);
            }
        }
    }

    private Node<FileTreeEntry> findDirectory(final String dirName) {
        for (final Node<FileTreeEntry> child : this.workingNode.getChildren()) {
            final FileTreeEntry childData = child.getData();

            if (childData.getName()
                    .equals(dirName)
                    && childData.isDirectory()) {
                return child;
            }
        }

        return null;
    }

    private void processFileOrDirectory(final String zeile) {
        final String[] zerlegteZeile = zeile.split(" ");

        if (zerlegteZeile[0].equals("dir")) {
            this.workingNode.addChild(new FileTreeEntry(zerlegteZeile[1], 0, true));
        }
        else {
            this.workingNode.addChild(new FileTreeEntry(zerlegteZeile[1], Long.valueOf(zerlegteZeile[0]), false));
        }
    }
}

class FileTreeEntry {
    private String name;
    private long size;
    private boolean directory;

    public FileTreeEntry(final String name, final long size, final boolean directory) {
        this.name = name;
        this.size = size;
        this.directory = directory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final FileTreeEntry other = (FileTreeEntry) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return this.name + " " + (isDirectory() ? "(dir) " : "") + this.size;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(final long size) {
        this.size = size;
    }

    public boolean isDirectory() {
        return this.directory;
    }

    public void setDirectory(final boolean directory) {
        this.directory = directory;
    }
}
