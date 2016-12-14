import java.util.HashMap;
import java.util.Map;

/**
 * @author yangxiaochen
 * @date 2016/11/30 19:03
 */
public class LFUCache {

    static class Entry {
        Integer value;
        Integer key;
        Entry next;
        Entry pre;
        Level level;

        @Override
        public String toString() {
            return "Entry{" +
                    "value=" + value +
                    ", key=" + key +
                    '}';
        }
    }

    static class Level {
        Level pre;
        Level next;
        Entry firstEntry;
        Entry lastEntry;
        int count;
    }

    Map<Integer, Entry> cache;
    Level head;
    int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
    }

    public int get(int key) {
        Entry entry = this.cache.get(key);
        if (entry == null) {
            return -1;
        } else {
            levelUp(entry);
            return entry.value;
        }
    }

    private void levelUp(Entry entry) {
        Level currentLevel = entry.level;
        Level nextLevel = currentLevel.next;

        if (nextLevel == null || nextLevel.count > currentLevel.count + 1) {
            // make new level
            Level level = new Level();
            level.count = currentLevel.count + 1;

            concatLevel(currentLevel, level);
            concatLevel(level, nextLevel);

            nextLevel = level;
        }

//        Entry pre = entry.pre;
//        Entry next = entry.next;
//        if (pre == null && next == null) {
//            currentLevel.firstEntry = null;
//            currentLevel.lastEntry = null;
//            if (currentLevel == head) {
//                head = nextLevel;
//                nextLevel.pre = null;
//            } else {
//                Level preLevel = currentLevel.pre;
//                preLevel.next = nextLevel;
//                nextLevel.pre = preLevel;
//            }
//        } else if (pre == null && next != null) {
//            currentLevel.firstEntry = next;
//            next.pre = pre;
//        } else if (pre != null && next == null) {
//            pre.next = null;
//            currentLevel.lastEntry = pre;
//        } else if (pre != null && next != null) {
//            pre.next = next;
//            next.pre = pre;
//        }

        Level level = removeEntryFromLevel(entry);
        if (level.firstEntry == null) {
            concatLevel(level.pre, level.next);
        }

        entry.next = null;
        entry.pre = null;
        entry.level = nextLevel;
        if (nextLevel.firstEntry == null) {
            nextLevel.firstEntry = entry;
            nextLevel.lastEntry = entry;
        } else {
            entry.next = nextLevel.firstEntry;
            nextLevel.firstEntry.pre = entry;
            nextLevel.firstEntry = entry;
        }
    }


    public void set(int key, int value) {

        if (get(key) == -1) {
            if (cache.size() >= capacity) {
                if (capacity == 0) {
                    return;
                }
                clearOne();
            }

            // insert to level 1
            Entry entry = new Entry();
            entry.key = key;
            entry.value = value;
            cache.put(key, entry);
            if (head == null || head.count > 1) {
                Level level = new Level();
                level.count = 1;
                level.next = head;
                if (head != null) {
                    head.pre = level;
                }
                head = level;
            }

            entry.level = head;
            if (head.firstEntry == null) {
                // head 下没有entry
                head.lastEntry = entry;
                head.firstEntry = entry;
            } else {
                entry.next = head.firstEntry;
                entry.next.pre = entry;
                entry.pre = null;
                head.firstEntry = entry;
            }

        } else {
            //replace
            Entry entry = this.cache.get(key);
            levelUp(entry);
            entry.value = value;
        }
    }

    private void clearOne() {
        Entry toDel = head.lastEntry;
        if (toDel.pre != null) {
            head.lastEntry = toDel.pre;
            head.lastEntry.next = null;
        } else {
            removeHead();
        }
        cache.remove(toDel.key);
        toDel = null;
    }


    private void removeHead() {
        if (head.next != null) {
            this.head = head.next;
            head.pre = null;
        } else {
            head = null;
        }
    }

    void printlevels() {
        Level node = head;
        while (node != null) {
            System.out.println(node.count);
            Entry e = node.firstEntry;
            while (e != null) {
                System.out.print(e);
                System.out.print("       ");
                e = e.next;
            }
            node = node.next;
            System.out.println("");
        }
        System.out.println("");
    }

    private void concatLevel(Level pre, Level next) {
        if (pre != null) {
            pre.next = next;
        } else {
            head = next;
        }
        if (next != null) {
            next.pre = pre;
        }
    }

    private Level removeEntryFromLevel(Entry entry) {
        Level level = entry.level;
        Entry pre = entry.pre;
        Entry next = entry.next;
        cleanEntry(entry);

        if (pre == null && next == null) {
            level.firstEntry = null;
            level.lastEntry = null;
        } else if (pre == null) {
            level.firstEntry = next;
            next.pre = null;
        } else if (next == null) {
            level.lastEntry = pre;
            pre.next = null;
        } else {
            pre.next = next;
            next.pre = pre;
        }

        return level;
    }


    private void cleanEntry(Entry entry) {
        entry.pre = null;
        entry.next = null;
        entry.level = null;
    }

    public int getLevel(int key) {
        Entry entry = cache.get(key);
        if (entry == null) {
            return -1;
        }

        return entry.level.count;
    }


    public static void main(String[] args) {
        LFUCache cache = new LFUCache(10 /* capacity */);
//
//        cache.set(1, 1);
//        cache.set(2, 2);
//        assert cache.get(1) == 1;       // returns 1
//        cache.set(3, 3);    // evicts key 2
//        cache.printlevels();
//        assert cache.get(2) == 2;       // returns -1 (not found)
//        assert cache.get(3) == 3;       // returns 3.
//        cache.printlevels();
//        cache.set(4, 4);    // evicts key 1.
//        assert cache.get(1) == 1;       // returns -1 (not found)
//        assert cache.get(3) == 3;       // returns 3
//        assert cache.get(4) == 4;       // returns 4
//
//
//        assert cache.getLevel(4) == 2;
//
//        cache.get(4);       // returns 4
//        cache.get(4);       // returns 4
//        cache.get(4);       // returns 4
//        cache.get(4);       // returns 4
//        cache.get(4);       // returns 4
//        cache.set(5, 5);
//        cache.get(5);
//        cache.get(5);
//        cache.get(5);
//        cache.get(5);
//        cache.get(5);
//        cache.get(5);
//        cache.set(6, 6);
//        cache.set(7, 7);
//        cache.set(8, 8);
//        cache.set(1, 10);
//
//        assert cache.getLevel(4) == 7;  // returns 4
//        cache.printlevels();

        cache = new LFUCache(0);
        cache.set(0,0);
        System.out.println(cache.get(0));
    }
}