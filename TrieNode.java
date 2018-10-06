   public class TrieNode {
        int c;
        int phraseNum;
        TrieNode neighbour;
        TrieNode child;

        TrieNode(int character, int phrase) {
            neighbour = null;
            child = null;
            c = character;
            phraseNum = phrase;
        }
    }
