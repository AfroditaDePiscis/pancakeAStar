package pancakeAStar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class PancakeSortingAStar {
    public static void main(String[] args) {
        String input = "cgehdfba";
        pancakeSort(input);
    }

    public static void pancakeSort(String input) {
        char[] pancakes = input.toCharArray();
        int n = pancakes.length;
        int nodes = 0;

        Queue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.flips + node.heuristic));
        Node start = new Node(pancakes, new ArrayList<>(), heuristic(pancakes));
        queue.add(start);
        Set<String> visited = new HashSet<>();
        visited.add(new String(start.state));
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            nodes++;
            for (int i = 2; i <= n; i++) {
                char[] childState = flip(current.state, i);
                String childStateString = new String(childState);
                if (!visited.contains(childStateString)) {
                    List<Integer> childFlips = new ArrayList<>(current.flipIndices);
                    childFlips.add(i);
                    int heuristic = heuristic(childState);
                    Node child = new Node(childState, childFlips, heuristic);
                    queue.add(child);
                    visited.add(childStateString);
                }
            }
            if (isSorted(current.state)) {
                System.out.println("Pasos: " + current.flips);
                System.out.println("Volteos: " + current.flipIndices);
                System.out.println("Nodos visitados: " + nodes);
                return;
            }
        }

    }

    private static boolean isSorted(char[] pancakes) {
        for (int i = 0; i < pancakes.length - 1; i++) {
            if (pancakes[i] > pancakes[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private static char[] flip(char[] pancakes, int k) {
        char[] result = new char[pancakes.length];
        int count = 1;
        for (int i = 0; i <= pancakes.length - k; i++) {
            result[i] = pancakes[i];
        }
        for (int i = pancakes.length - k; i < pancakes.length; i++) {
            result[i] = pancakes[pancakes.length - count];
            count++;
        }
        return result;
    }



    private static class Node {
        public char[] state;
        public List<Integer> flipIndices;
        public int flips;
        public int heuristic;

        public Node(char[] state, List<Integer> flipIndices, int remainingFlips) {
            this.state = state;
            this.flipIndices = flipIndices;
            this.flips = flipIndices.size();
            this.heuristic = heuristic(state);
        }
    }
    
         private static int heuristic(char[] state) {
        int heuristic = 0;
        for (int i = 0; i < state.length - 1; i++) {
            if (Math.abs(state[i] - state[i+1]) > 1) {
                heuristic++;
            }
        }
        if (state[0] != '1') {
            heuristic++;
        }
        return heuristic;
    }
}
