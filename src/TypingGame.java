import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TypingGame {
    private String paragraph;
    private ArrayList<String> words;
    private int currentWordIndex;
    private double progress;
    private int timeLimit; // in seconds
    private boolean timeUp;
    private int timeRemaining;

    public TypingGame(List<String> paragraphs, int timeLimit) {
        this.paragraph = getRandomParagraph(paragraphs);
        this.words = new ArrayList<>();
        for (String word : paragraph.split(" ")) {
            words.add(word);
        }
        this.currentWordIndex = 0;
        this.progress = 0;
        this.timeLimit = timeLimit;
        this.timeUp = false;
        this.timeRemaining = timeLimit;
    }

    private String getRandomParagraph(List<String> paragraphs) {
        Random random = new Random();
        int index = random.nextInt(paragraphs.size());
        return paragraphs.get(index);
    }

    public void start(Scanner scanner) {
        System.out.println("Start typing the following paragraph:");
        System.out.println(paragraph);
        System.out.println();

        // Start the timer in a separate thread
        Thread timerThread = new Thread(() -> {
            try {
                int currentTimeRemaining = timeLimit;
                while (currentTimeRemaining > 0 && !timeUp) {
                    Thread.sleep(1000);
                    currentTimeRemaining--;
                }
                if (!timeUp) {
                    System.out.println("\nTime's up!");
                    timeUp = true;
                }
            } catch (InterruptedException e) {
                System.out.println("Timer interrupted.");
            }
        });
        timerThread.start();

        int totalWordsAttempted = 0;
        int correctWords = 0;
        while (!timeUp && scanner.hasNextLine()) {
            String inputLine = scanner.nextLine().trim();

            // If the input line is not empty, split it into words
            String[] inputs = !inputLine.isEmpty() ? inputLine.split("\\s+") : new String[0];

            for (String input : inputs) {
                totalWordsAttempted++;
                if (checkWord(input)) {
                    correctWords++;
                }
                updateProgress();
                if (timeUp) {
                    break;
                }
            }

            // If the user entered no input or time is up, break the loop
            if (inputs.length == 0 || timeUp) {
                break;
            }

            // Check if the user has completed typing the paragraph
            if (currentWordIndex == words.size()) {
                System.out.println("Congratulations! You've completed the paragraph.");
                break;
            }
        }

        // Calculate and print accuracy only if words were attempted
        double accuracy = totalWordsAttempted > 0 ? (double) correctWords / totalWordsAttempted * 100 : 100;
        int incorrectWords = totalWordsAttempted - correctWords;
        System.out.printf("Accuracy: %.2f%%\n", accuracy);
        System.out.printf("Correct words: %d/%d\n", correctWords, totalWordsAttempted);
        System.out.printf("Incorrect words: %d/%d\n", incorrectWords, totalWordsAttempted);
        System.out.println(); // Add some space after accuracy for readability

        // Ensure timer thread stops when the game ends
        timeUp = true; // Set timeUp to true to stop the timer thread
        try {
            timerThread.join(); // Wait for the timer thread to complete
        } catch (InterruptedException e) {
            System.out.println("Timer thread interrupted while joining.");
        }
    }

    private boolean checkWord(String input) {
        return input.equals(words.get(currentWordIndex));
    }

    private void updateProgress() {
        currentWordIndex++;
        progress = (double) currentWordIndex / words.size() * 100;
    }

    public static void main(String[] args) {
        List<String> paragraphs = new ArrayList<>();
        paragraphs.add("Stop and take a breath. Say 'I don't know' when you don't know.");
        paragraphs.add("Never take decisions when angry, and don't make promises when you are happy.");
        paragraphs.add("Progress is more important than perfection.");
        paragraphs.add("Practice makes perfect, so keep trying and you'll get better over time.");
        paragraphs.add("The quick brown fox jumps over the lazy dog.");
        paragraphs.add("A journey of a thousand miles begins with a single step.");
        paragraphs.add("To be or not to be, that is the question.");
        paragraphs.add("All that glitters is not gold.");
        paragraphs.add("The early bird catches the worm.");
        paragraphs.add("A picture is worth a thousand words.");
        paragraphs.add("When in Rome, do as the Romans do.");
        paragraphs.add("Actions speak louder than words.");
        paragraphs.add("Beauty is in the eye of the beholder.");
        paragraphs.add("Better late than never.");
        paragraphs.add("Every cloud has a silver lining.");
        paragraphs.add("A watched pot never boils.");
        paragraphs.add("Fortune favors the brave.");
        paragraphs.add("Good things come to those who wait.");
        paragraphs.add("If it ain't broke, don't fix it.");
        paragraphs.add("Laughter is the best medicine.");
        paragraphs.add("You can't judge a book by its cover.");
        paragraphs.add("The pen is mightier than the sword.");
        paragraphs.add("Time flies when you're having fun.");
        paragraphs.add("Two wrongs don't make a right.");
        paragraphs.add("You can lead a horse to water, but you can't make it drink.");
        paragraphs.add("A penny saved is a penny earned.");
        paragraphs.add("Birds of a feather flock together.");
        paragraphs.add("Don't count your chickens before they hatch.");
        paragraphs.add("Honesty is the best policy.");
        paragraphs.add("The grass is always greener on the other side.");
        paragraphs.add("Don't bite the hand that feeds you.");
        paragraphs.add("A rolling stone gathers no moss.");
        paragraphs.add("The best way to predict the future is to create it.");
        paragraphs.add("Don't put all your eggs in one basket.");

        Scanner scanner = new Scanner(System.in); // Create a single Scanner object
        boolean playAgain = true;
        while (playAgain) {
            // Initial prompt to ask if user wants to play
            System.out.print("Hey! Do you want to play? (yes/no): ");
            String playChoice = scanner.nextLine().trim().toLowerCase();

            if (playChoice.equals("yes")) {
                int timeLimit = 0;
                while (true) {
                    // Prompt for time limit selection
                    System.out.println("Choose a time limit:");
                    System.out.println("1. 10 seconds");
                    System.out.println("2. 20 seconds");
                    System.out.println("3. 30 seconds");
                    System.out.print("Enter your choice (1/2/3): ");
                    int timeChoice;
                    try {
                        timeChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline after reading int

                        // Validate and set time limit
                        switch (timeChoice) {
                            case 1:
                                timeLimit = 10;
                                break;
                            case 2:
                                timeLimit = 20;
                                break;
                            case 3:
                                timeLimit = 30;
                                break;
                            default:
                                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                                continue; // Restart the loop to prompt again
                        }
                        break; // Exit the loop if a valid choice is made
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.nextLine(); // Clear the invalid input from the scanner
                    }
                }

                TypingGame game = new TypingGame(paragraphs, timeLimit);
                game.start(scanner);
            } else if (playChoice.equals("no")) {
                playAgain = false; // Exit the loop if user chooses not to play
            } else {
                System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
            }
        }
        scanner.close(); // Close the scanner after the loop ends
    }
}
