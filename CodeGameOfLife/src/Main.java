import java.util.concurrent.TimeUnit;

public class Main {

    private static int MAX_SCREEN_MOVEMENT;
    private static int SPEED;

    private static int WIDTH;

    private static int LENGTH;

    private static int[][] GENERATION;

    public static void main(String[] args){
        try {
            String inPut = args[0];
            String[] firstInPut = inPut.split("=");
            String width = firstInPut[1];
            int numberWidth = Integer.parseInt(width);

            inPut = args[1];
            String[] secondInPut = inPut.split("=");
            String length = secondInPut[1];
            int numberLength = Integer.parseInt(length);

            inPut = args[2];
            String[] thirdInPut = inPut.split("=");
            String numberOfGenerations = thirdInPut[1];
            int numberNumberOfGenerations = Integer.parseInt(numberOfGenerations);

            inPut = args[3];
            String[] fourthInPut = inPut.split("=");
            String speed = fourthInPut[1];
            int numberSpeed = Integer.parseInt(speed);

            inPut = args[4];
            String[] fifthInPut = inPut.split("=");
            String generationOne = fifthInPut[1];

            validation(numberWidth,numberLength,numberNumberOfGenerations,numberSpeed,generationOne );

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect and/or insufficient values");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void validation(int numberWidth, int numberLength, int numberNumberOfGenerations, int numberSpeed,String generationOne) throws InterruptedException {
        if(validateData(numberWidth,numberLength,numberNumberOfGenerations,numberSpeed)){

            MAX_SCREEN_MOVEMENT = numberNumberOfGenerations;
            SPEED = numberSpeed;
            WIDTH = numberWidth;
            LENGTH = numberLength;

            if(generationOne.equals("rnd")){
                GENERATION = fillGeneration(numberWidth,numberLength);
                nextGeneration();

            } else{

            }
        }else{
            System.out.println("Incorrect and/or insufficient values");
            System.exit(0);
        }
    }
    public static void nextGeneration() throws InterruptedException {
        int repetitions = 1;
        int[][] nextGeneration = new int[LENGTH][WIDTH];

        while (repetitions <= MAX_SCREEN_MOVEMENT) {
            for (int x = 0; x < LENGTH; x++) {
                for (int y = 0; y < WIDTH; y++) {
                    int aliveNeighbour = 0;
                    for (int i = -1; i <= 1; i++){
                        for (int j = -1; j <= 1; j++){
                            if ((x + i >= 0 && x + i < LENGTH) && (y + j >= 0 && y + j < WIDTH)){
                                aliveNeighbour += GENERATION[x + i][y + j];
                            }
                        }
                    }
                    aliveNeighbour -= GENERATION[x][y];

                    if ((GENERATION[x][y] == 1) && (aliveNeighbour < 2)) { //Rule 01
                        nextGeneration[x][y] = 0;
                    } else if ((GENERATION[x][y] == 1) && (aliveNeighbour > 3)) { //Rule 02
                        nextGeneration[x][y] = 0;
                    } else if ((GENERATION[x][y] == 1) && ((aliveNeighbour == 2) || (aliveNeighbour == 3))) { //Rule 03
                        nextGeneration[x][y] = 1;
                    } else if((GENERATION[x][y] == 0) && (aliveNeighbour == 3)) { //Rule 04
                        nextGeneration[x][y] = 1;
                    }else{
                        nextGeneration[x][y] = GENERATION[x][y];
                    }
                }
            }
            nextGeneration = GENERATION;
            showBoardNews(nextGeneration, repetitions);
            repetitions += 1;
            TimeUnit.MILLISECONDS.sleep(SPEED);
            cleanScreenSimulation();
        }
    }
    public static void cleanScreenSimulation() throws InterruptedException {
        final int SCROLLBAR_SPEED = 1000 / 850;
        for (int i = 0; i < MAX_SCREEN_MOVEMENT; i++) {
            System.out.println();
            TimeUnit.MILLISECONDS.sleep(SCROLLBAR_SPEED);
        }
    }

    public static void showBoardNews(int [][] board, int num) {
        System.out.println("Generation number : " + num);

        for (int x = 0; x < board.length; x++) {
            System.out.print("|");
            for (int y=0; y < board[x].length; y++) {
                if (board[x][y] == 0)
                    System.out.print("⬜");
                else
                    System.out.print("⬛");
                if (y!=board[x].length-1) System.out.print("\t");
            }
            System.out.println("|");
        }
    }

    public static void showBoard(int [][] board) {
        System.out.println("First Generation");
        for (int x = 0; x < board.length; x++) {
            System.out.print("|");
            for (int y=0; y < board[x].length; y++) {
                if (board[x][y] == 0)
                    System.out.print("⬜");
                else
                    System.out.print("⬛");
                if (y!=board[x].length-1) System.out.print("\t");
            }
            System.out.println("|");
        }
    }

    public static int[][] fillGeneration(int width, int length){
        int [][] generationOne = new int[length][width];
        for (int row=0; row < generationOne.length; row++) {
            for (int column=0; column < generationOne[row].length; column++) {
                int numberRandom = (int) (Math.random()*(-2)+(2));
                generationOne[row][column] =numberRandom;
            }
        }
        showBoard(generationOne);
        return generationOne;
    }
    public static boolean validateData(int numberWidth,int numberLength, int numberNumberOfGenerations, int numberSpeed){
        return checkTheRangesWidth(numberWidth) &&
                checkTheRangesLength(numberLength) &&
                checkTheRangesNumberGenerations(numberNumberOfGenerations) &&
                checkTheRangesSpeed(numberSpeed);
    }
    public static boolean checkTheRangesWidth(int widthNumber){
        return widthNumber == 10 || widthNumber == 20 || widthNumber == 40 || widthNumber == 80;
    }
    public static boolean checkTheRangesLength(int lengthNumber){
        return lengthNumber == 10 || lengthNumber == 20 || lengthNumber == 40;
    }
    public static boolean checkTheRangesNumberGenerations(int numberOfGenerations){
        return numberOfGenerations >= 1;
    }
    public static boolean checkTheRangesSpeed(int numberSpeed){
        return numberSpeed >= 250 && numberSpeed <= 1000;
    }
}