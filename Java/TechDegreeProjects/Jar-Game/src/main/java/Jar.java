import java.util.Random;

public class Jar {
  private String mItem;
  private int mMaxItem;
  private int mTries;
  private int mWinningNumber;
  private int mGuess;
  
  public Jar(String item, int maxItem) {
    mItem = item;
    mMaxItem = maxItem;
    mTries = 0;
    mWinningNumber = makeRandomNumber(maxItem);
    mGuess = 0;
  }
  
  public String getItem() {
    return mItem;
  }
  
  public int getMaxItem() {
    return mMaxItem;
  }
  
  public int getTries() {
    return mTries;
  }
  
  public int getWinningNumber() {
    return mWinningNumber;
  }
  
  private int makeRandomNumber(int someNumber) {
    Random random = new Random();
    int randomNumber = random.nextInt((someNumber) + 1);
    return randomNumber;
  }
  
  public boolean isMatched() {
    return mGuess == mWinningNumber;
  }
  
  public boolean applyGuess(int guess) {
    mGuess = guess;    
    mTries++;
    if (mWinningNumber == guess) {
      return isMatched();
    } else {
      if (guess < mWinningNumber) {
      System.out.println("Try guessing higher.");
      } else {
        System.out.println("Try guessing lower.");
      }
    }
    return false;
  }  
}