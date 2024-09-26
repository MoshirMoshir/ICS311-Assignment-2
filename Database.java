import java.util.Scanner;

// Proverb class for saying
class Proverb {
    String japanese;
    String english;
    String meaning;

    public Proverb(String japanese, String english, String meaning) {
        this.japanese = japanese;
        this.english = english;
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "Japanese: " + japanese + "\nEnglish: " + english + "\nMeaning: " + meaning;
    }
}

// AVLNode class for AVL Tree
class AVLNode {
    Proverb data;
    AVLNode left, right;
    int height;

    AVLNode(Proverb data) {
        this.data = data;
        this.height = 1;
    }
}

// AVLTree class for balanced tree operations
class AVLTree {
    private AVLNode root;
    // debugMode has not been implemented yet
    private boolean debugMode;

    AVLTree(boolean debugMode) {
        this.root = null;
        this.debugMode = debugMode;
    }

    // Method to get the height of the node
    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    // Method to right rotate subtree rooted with y
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Method to left rotate subtree rooted with x
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Get balance factor of node
    private int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Insert a new Proverb in the AVLTree
    public void insert(Proverb proverb) {
        root = insert(root, proverb);
    }

    // Recursive function to insert a new Proverb in the subtree rooted with node
    private AVLNode insert(AVLNode node, Proverb proverb) {
        if (node == null)
            return new AVLNode(proverb);

        // Compare based on Japanese saying
        int cmp = proverb.japanese.compareTo(node.data.japanese);
        if (cmp < 0)
            node.left = insert(node.left, proverb);
        else if (cmp > 0)
            node.right = insert(node.right, proverb);
        else
            return node;

        // Update height of the node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get balance factor of the node
        int balance = getBalance(node);

        // If the node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && proverb.japanese.compareTo(node.left.data.japanese) < 0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && proverb.japanese.compareTo(node.right.data.japanese) > 0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && proverb.japanese.compareTo(node.left.data.japanese) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && proverb.japanese.compareTo(node.right.data.japanese) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Find and return the node with the smallest key value (first saying in order)
    public AVLNode findMin() {
        return findMin(root);
    }

    private AVLNode findMin(AVLNode node) {
        if (node == null || node.left == null)
            return node;
        return findMin(node.left);
    }

    // Find and return the node with the largest key value (last saying in order)
    public AVLNode findMax() {
        return findMax(root);
    }

    private AVLNode findMax(AVLNode node) {
        if (node == null || node.right == null)
            return node;
        return findMax(node.right);
    }

    // Search for a specific Japanese saying in the tree
    public AVLNode search(String japanese) {
        return search(root, japanese);
    }

    private AVLNode search(AVLNode node, String japanese) {
        if (node == null)
            return null;

        int cmp = japanese.compareTo(node.data.japanese);

        if (cmp < 0)
            return search(node.left, japanese);
        else if (cmp > 0)
            return search(node.right, japanese);
        else
            return node; // Saying found
    }

    // Get the first proverb (smallest key in order)
    public Proverb first() {
        AVLNode node = findMin(root);
        return node != null ? node.data : null;
    }

    // Get the last proverb (largest key in order)
    public Proverb last() {
        AVLNode node = findMax(root);
        return node != null ? node.data : null;
    }

    // Get the predecessor of the given Japanese saying
    public Proverb predecessor(String japanese) {
        AVLNode node = root;
        AVLNode predecessor = null;
        while (node != null) {
            int cmp = japanese.compareTo(node.data.japanese);
            if (cmp > 0) {
                predecessor = node;
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return predecessor != null ? predecessor.data : null;
    }

    // Get the successor of the given Japanese saying
    public Proverb successor(String japanese) {
        AVLNode node = root;
        AVLNode successor = null;
        while (node != null) {
            int cmp = japanese.compareTo(node.data.japanese);
            if (cmp < 0) {
                successor = node;
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return successor != null ? successor.data : null;
    }

    // Get all proverbs containing the given non-English word
    public void meHua(String word) {
        System.out.println("Sayings containing \"" + word + "\" in Japanese:");
        meHua(root, word);
    }

    private void meHua(AVLNode node, String word) {
        if (node != null) {
            if (node.data.japanese.contains(word)) {
                System.out.println(node.data);
                System.out.println();
            }
            meHua(node.left, word);
            meHua(node.right, word);
        }
    }

    // Get all proverbs containing the given English word
    public void withWord(String word) {
        System.out.println("Sayings containing \"" + word + "\" in English:");
        withWord(root, word);
    }

    private void withWord(AVLNode node, String word) {
        if (node != null) {
            if (node.data.english.toLowerCase().contains(word.toLowerCase())) {
                System.out.println(node.data);
                System.out.println();
            }
            withWord(node.left, word);
            withWord(node.right, word);
        }
    }

    // Method to print the tree structure
    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(AVLNode node, String indent, boolean last) {
        if (node != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }
            System.out.println(node.data.japanese);

            printTree(node.left, indent, false);
            printTree(node.right, indent, true);
        }
    }
}

// Main Class with ui
public class Database {
    private static AVLTree tree;

    public static void main(String[] args) {
        // Change to true to enable debug mode (outputs tree layout after running any command)
        boolean debugMode = false;

        // Check if debug mode is enabled from command-line arguments
        if (args.length > 0 && args[0].equals("debug")) {
            debugMode = false; 
        }

        tree = new AVLTree(debugMode);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Proverb Database initialized.");
        if (debugMode) {
            System.out.println("Debug mode enabled.");
        }

        // 10 Japanese Proverbs pre-packaged in database
        tree.insert(new Proverb("相手のない喧嘩はできない", 
        "You can’t fight without an opponent.", 
        "You cannot have an argument or fight without someone to argue or fight with. It is used to describe a situation where two people are not fighting and are often used to warn someone about doing something reckless. A lot of Japanese people avoid conflicts, so this Japanese saying is often used to prevent arguments from happening."
        ));

        tree.insert(new Proverb("七転び八起き", 
        "When you fall seven times, you get up at eight.", 
        "This is the Japanese equivalent of “Don’t give up, don’t give in.” This Japanese saying is often recited to children to encourage them never to give up and always try their best. It encourages people to keep going, even when the going gets tough."
        ));

        tree.insert(new Proverb("井の中の蛙大海を知らず", 
        "A frog in the well does not know the ocean.", 
        "This Japanese proverb means you should not look at situations from their face value. Go beyond that and widen your imagination. If you use a narrow-minded approach, you will not experience the world beyond your cocoon. It applies to people who do not want to focus on other opportunities and venture out to the unknown."
        ));

        tree.insert(new Proverb("言わぬが花", 
        "Silence is golden.", 
        "You don’t always have to talk or give your opinions. Maintain your silence when you have nothing significant to contribute. Being too mouthy could lead you into trouble."
        ));

        tree.insert(new Proverb("能ある鷹は爪を隠す", 
        "The adept hawk hides its claws.", 
        "It means a hawk that wants to pounce does not display its prowess to the targeted prey. It will wait for the right moment to make a move. The Japanese use the expression to remind them of the importance of remaining humble."
        ));

        tree.insert(new Proverb("猿も木から落ちる", 
        "Even monkeys fall from trees.", 
        "Monkeys are known for their deft excellence in climbing trees. They can climb even the tallest of trees, no matter how daunting it might look. However, even a monkey makes mistakes. Even monkeys fall from trees but that’s a normal occurrence."
        ));

        tree.insert(new Proverb("二兎を追う者は一兎をも得ず", 
        "He who runs after two hares will catch neither.", 
        "This expression is used to warn people who are greedy or want to focus on too many things simultaneously. It is often difficult to achieve success if you are not focused on what you are trying to do."
        ));

        tree.insert(new Proverb("悪銭身に付かず", 
        "Easy come, easy go.", 
        "The Japanese are honest and like transacting in the right way. They avoid shoddy deals. If you don’t be careful, you might end up losing everything that you have earned. The Japanese proverb means that it is easy to make money, but it is also easy to lose it."
        ));

        tree.insert(new Proverb("負けるが勝ち", 
        "There is victory in losing.", 
        "This may sound quite paradoxical, but it simply means you do not have to win every battle. Walking away from a fight is smart and a wise decision."
        ));

        tree.insert(new Proverb("自業自得", 
        "What goes around comes around.", 
        "Relating to people in the right manner is an essential component of peaceful coexistence. Treat people in the same manner you would wish them to treat you."
        ));

        // UI implementation
        while (true) {
            System.out.print("Enter command (Insert, Member, First, Last, Predecessor, Successor, MeHua, WithWord, exit): ");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the program.");
                break;
            }

            switch (command.toLowerCase()) {
                case "insert":
                    System.out.print("Enter Japanese saying: ");
                    String japanese = scanner.nextLine();

                    System.out.print("Enter English translation: ");
                    String english = scanner.nextLine();

                    System.out.print("Enter Meaning: ");
                    String meaning = scanner.nextLine();

                    Proverb newProverb = new Proverb(japanese, english, meaning);

                    System.out.println("Confirm to add the saying? (yes/no): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("yes")) {
                        tree.insert(newProverb);
                        System.out.println("Saying added.");
                    } else {
                        System.out.println("Operation cancelled.");
                    }
                    break;

                case "member":
                    System.out.print("Enter Japanese saying to search: ");
                    japanese = scanner.nextLine();
                    AVLNode result = tree.search(japanese);
                    if (result != null) {
                        System.out.println("Found: " + result.data);
                    } else {
                        System.out.println("Saying not found.");
                    }
                    break;

                case "first":
                    Proverb firstProverb = tree.first();
                    if (firstProverb != null) {
                        System.out.println("First proverb: ");
                        System.out.println(firstProverb);
                    } else {
                        System.out.println("Tree is empty.");
                    }
                    break;

                case "last":
                    Proverb lastProverb = tree.last();
                    if (lastProverb != null) {
                        System.out.println("Last proverb: ");
                        System.out.println(lastProverb);
                    } else {
                        System.out.println("Tree is empty.");
                    }
                    break;

                case "predecessor":
                    System.out.print("Enter Japanese saying to find predecessor: ");
                    japanese = scanner.nextLine();
                    Proverb predecessor = tree.predecessor(japanese);
                    if (predecessor != null) {
                        System.out.println("Predecessor proverb: ");
                        System.out.println(predecessor);
                    } else {
                        System.out.println("No predecessor found.");
                    }
                    break;

                case "successor":
                    System.out.print("Enter Japanese saying to find successor: ");
                    japanese = scanner.nextLine();
                    Proverb successor = tree.successor(japanese);
                    if (successor != null) {
                        System.out.println("Successor proverb: ");
                        System.out.println(successor);
                    } else {
                        System.out.println("No successor found.");
                    }
                    break;

                case "mehua":
                    System.out.print("Enter Japanese word to search in sayings: ");
                    String japaneseWord = scanner.nextLine();
                    tree.meHua(japaneseWord);
                    break;

                case "withword":
                    System.out.print("Enter English word to search in translations: ");
                    String englishWord = scanner.nextLine();
                    tree.withWord(englishWord);
                    break;

                default:
                    System.out.println("Unknown command.");
                    break;
            }

            // Prints AVLTree if debugMode
            if (debugMode) {
                System.out.println("Tree structure:");
                tree.printTree();
            }
        }

        scanner.close();
    }
}
