import java.io.Console;

public class Prompter {
  private Jar mJar;
  
  public Prompter(Jar jar) {
    mJar = jar;
  }
  
  public void play() {
    System.out.println("=====PLAYER=====");
    System.out.printf("Hello, we have a jar here filled with %s.\n", mJar.getItem());
    while (!mJar.isMatched()) {
      promptForGuess();
    }
    if (mJar.isMatched()) {
      System.out.printf("Congratulations! You won! The number was %d and you got in %d tries.", 
                        mJar.getWinningNumber(), mJar.getTries());
    }
  }
  
  public boolean promptForGuess() {
    Console console = System.console();
    boolean isValidGuess = false;
    while (! isValidGuess) {
      String guessAsString = console.readLine("Guess how many are in the jar (HINT could be 1 - %d): ", 
                                              mJar.getMaxItem());
      int convertedGuess = Integer.parseInt(guessAsString);
      try {
        int validGuess = isValidGuess(convertedGuess);
        isValidGuess = true;
        return mJar.applyGuess(validGuess);
      } catch (IllegalArgumentException iae) {
        console.printf("%s  Please try again.\n", iae.getMessage());
      }
    }
    return true;
  }
  
  private int isValidGuess(int checkGuess) {
   if (checkGuess > mJar.getMaxItem())  {
     throw new IllegalArgumentException("Whoops! That guess is higher than the max number.");
   }
   if (checkGuess <= 0) {
     throw new IllegalArgumentException("Whoops! That number is not possible.");
   }
   return checkGuess;
  }
}