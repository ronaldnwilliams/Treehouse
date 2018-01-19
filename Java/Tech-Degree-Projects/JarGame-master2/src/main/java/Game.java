import java.io.Console;
import java.util.Random;

public class Game {
 public static void main(String[] args) {
   Console c = System.console();
   c.printf("=====ADMIN SETUP=====\n");
   boolean adminNotTroll = false;
   String item = c.readLine("Enter an item to place in the jar: ");
   while (! adminNotTroll) {    
    String maxNumber = c.readLine("Enter the maximum number of %s you want in the jar: ", item);
    int convertedNumber = Integer.parseInt(maxNumber);
    if (convertedNumber <= 0 || convertedNumber > 2147483647) {
      adminNotTroll = false;
      System.out.println("Please pick a number: 1 through 2,147,483,647");
    } else {
      adminNotTroll = true;
      Jar jar = new Jar(item, convertedNumber);
      Prompter prompter = new Prompter(jar);
      prompter.play();
    }
   }     
 }
}