public class Main {
    public static void main(String[] args) {

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

            if(checkTheRangesWidth(numberWidth) && checkTheRangesLength(numberLength) && checkTheRangesNumberGenerations(numberNumberOfGenerations) && checkTheRangesSpeed(numberSpeed)){
                if(generationOne.equals("rnd")){
                    fillGeneration(numberWidth,numberLength);
                } else{
                    //Cehcar que este correcto el formato
                    checkStructure(generationOne);
                    //Pasarlo a una matriz
                    //Llenar los espacios faltantes 0
                    //Mostrarlo
                }
            }else{
                System.out.println("Incorrect and/or insufficient values");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect and/or insufficient values");
        }
    }

    public static boolean checkTheRangesWidth(int widthNumber){
        if(widthNumber==10 || widthNumber==20 || widthNumber== 40 || widthNumber== 80){
            return true;
        }else{
            return false;
        }
    }
    public static boolean checkTheRangesLength(int lengthNumber){
        if(lengthNumber ==10 || lengthNumber ==20 || lengthNumber == 40){
            return true;
        }else{
            return false;
        }
    }
    public static boolean checkTheRangesNumberGenerations(int numberOfGenerations){
        if(numberOfGenerations>= 1){
            return true;
        }else{
            return false;
        }
    }

    public static boolean checkTheRangesSpeed(int numberSpeed){
        if(numberSpeed>= 250 && numberSpeed<= 1000){
            return true;
        }else{
            return false;
        }
    }

    public static void checkStructure(String generation){

    }

    public static void fillGeneration(int width, int length){
        String [][] generationOne = new String[length][width];
        for (int row=0; row < generationOne.length; row++) {
            for (int column=0; column < generationOne[row].length; column++) {
                int numberRandom = (int) (Math.random()*(-2)+(2));
                generationOne[row][column] = String.valueOf(numberRandom);
            }
        }
        showBoard(generationOne);
    }
    public static void showBoard(String [][] board) {
        for (int x=0; x < board.length; x++) {
            System.out.print("|");
            for (int y=0; y < board[x].length; y++) {
                System.out.print (board[x][y]);
                if (y!=board[x].length-1) System.out.print("\t");
            }
            System.out.println("|");
        }
    }
}