import java.util.concurrent.TimeUnit;

public class GameOfLife {
    private static final int[] VALID_WIDTHS = { 10, 20, 40, 80 };
    private static final int[] VALID_HEIGHTS = { 10, 20, 40 };
    private static final int MAX_GENERATIONS = 1000;
    private static final int MIN_SPEED = 250;
    private static final int MAX_SPEED = 1000;
    private static final int INFINITE_GENERATIONS = 0;

    private static int MAX_SCREEN_MOVEMENT;
    private static int SPEED;

    private static int WIDTH;
    private static int HEIGHT;

    private static int[][] GENERATION;

    private static int[][] NEW_GENERATION;

    public static void main(String[] args) throws InterruptedException {
        try {
            int numberWidth = 0;
            int numberHeight = 0;
            int numberNumberOfGenerations = 0;
            int numberSpeed = 0;
            String generationOne = "";

            for (String arg : args) {
                if (arg.startsWith("w=")) {
                    String width = arg.substring(2);
                    numberWidth = Integer.parseInt(width);
                    if (!isValidWidth(numberWidth)) {
                        return;
                    }
                } else if (arg.startsWith("h=")) {
                    String height = arg.substring(2);
                    numberHeight = Integer.parseInt(height);
                    if (!isValidHeight(numberHeight)) {
                        return;
                    }
                } else if (arg.startsWith("g=")) {
                    String numberOfGenerations = arg.substring(2);
                    numberNumberOfGenerations = Integer.parseInt(numberOfGenerations);
                    if (!isValidNumberOfGenerations(numberNumberOfGenerations)) {
                        return;
                    }
                } else if (arg.startsWith("s=")) {
                    String speed = arg.substring(2);
                    numberSpeed = Integer.parseInt(speed);
                    if (!isValidSpeed(numberSpeed)) {
                        return;
                    }
                } else if (arg.startsWith("p=")) {
                    generationOne = arg.substring(2);
                    if (!isValidGenerationOne(generationOne, numberWidth, numberHeight)) {
                        return;
                    }
                }
            }

            MAX_SCREEN_MOVEMENT = numberNumberOfGenerations == INFINITE_GENERATIONS ? MAX_GENERATIONS
                    : numberNumberOfGenerations;
            SPEED = numberSpeed;
            WIDTH = numberWidth;
            HEIGHT = numberHeight;

            GENERATION = goToMatrix(generationOne, numberWidth, numberHeight);
            NEW_GENERATION = new int[HEIGHT][WIDTH];

            for (int i = 0; i < MAX_SCREEN_MOVEMENT || MAX_SCREEN_MOVEMENT == INFINITE_GENERATIONS; i++) {
                display(GENERATION);
                nextGeneration();
                TimeUnit.MILLISECONDS.sleep(SPEED);
            }

        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Incorrect and/or insufficient values");
        }
    }

    public static int[][] goToMatrix(String generation, int width, int height) {
        int[][] matrix = new int[height][width];
        String[] rows = generation.split("#");
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            for (int j = 0; j < row.length(); j++) {
                char cell = row.charAt(j);
                if (cell == '1') {
                    matrix[i][j] = 1;
                } else if (cell == '0') {
                    matrix[i][j] = 0;
                }
            }
        }
        return matrix;
    }
    public static void display(int[][] generation) {
        for (int x = 0; x < generation.length; x++) {
            System.out.print("|");
            for (int y = 0; y < generation[x].length; y++) {
                if (generation[x][y] == 0)
                    System.out.print("⬜");
                else
                    System.out.print("⬛");
                if (y != generation[x].length - 1) System.out.print("\t");
            }
            System.out.println("|");
        }
    }

    public static void nextGeneration() {
        for (int i = 0; i < GENERATION.length; i++) {
            for (int j = 0; j < GENERATION[i].length; j++) {
                int aliveNeighbours = countAliveNeighbours(i, j);
                if (GENERATION[i][j] == 1) {
                    if (aliveNeighbours < 2 || aliveNeighbours > 3) {
                        NEW_GENERATION[i][j] = 0;
                    } else {
                        NEW_GENERATION[i][j] = 1;
                    }
                } else if (GENERATION[i][j] == 0) {
                    if (aliveNeighbours == 3) {
                        NEW_GENERATION[i][j] = 1;
                    } else {
                        NEW_GENERATION[i][j] = 0;
                    }
                }
            }
        }
        GENERATION = NEW_GENERATION;
        NEW_GENERATION = new int[HEIGHT][WIDTH];
    }

    public static int countAliveNeighbours(int row, int column) {
        int aliveNeighbours = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < GENERATION.length && j >= 0 && j < GENERATION[i].length) {
                    if (GENERATION[i][j] == 1) {
                        aliveNeighbours++;
                    }
                }
            }
        }
        if (GENERATION[row][column] == 1) {
            aliveNeighbours--;
        }
        return aliveNeighbours;
    }

    public static boolean isValidWidth(int width) {
        for (int validWidth : VALID_WIDTHS) {
            if (width == validWidth) {
                return true;
            }
        }
        System.out.println("Invalid width");
        return false;
    }

    public static boolean isValidHeight(int height) {
        for (int validHeight : VALID_HEIGHTS) {
            if (height == validHeight) {
                return true;
            }
        }
        System.out.println("Invalid height");
        return false;
    }

    public static boolean isValidNumberOfGenerations(int numberOfGenerations) {
        if (numberOfGenerations >= 0 && numberOfGenerations <= MAX_GENERATIONS) {
            return true;
        }
        System.out.println("Invalid number of generations");
        return false;
    }

    public static boolean isValidSpeed(int speed) {
        if (speed >= MIN_SPEED && speed <= MAX_SPEED) {
            return true;
        }
        System.out.println("Invalid speed");
        return false;
    }

    public static boolean isValidGenerationOne(String generationOne, int width, int height) {
        if (generationOne.length() == width * height + height - 1) {
            return true;
        }
        System.out.println("Invalid generation");
        return false;
    }

}