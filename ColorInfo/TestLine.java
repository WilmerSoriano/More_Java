/*
 * The code generates random coordinates within the range [1, 10).
 * NOTE: the color are of type ENUM.
 * To Compile use the Apache Ant
 * Ant build (creates the class)
 * Run java TestLine
 * to Clean use Ant clean (clean the class)
 */

public class TestLine
{
   public static void main(String[] args)
   {
      double x1,y1,x2,y2;
   
      for(Color color : Color.values()) //used the new method of looping inside of an array
      {                                //must use .values()
         x1 = 1+Math.random()*9;
         y1 = 1+Math.random()*9;// Lecture 2 provided on using random
         x2 = 1+Math.random()*9;// dont include 10... messes up my alignment
         y2 = 1+Math.random()*9;
         
         Line line = new Line(x1, y1, x2, y2 , color);// new line to use new input for colors
         System.out.println(line);    // after all calculation was made printed a color info.
      }
      
   }
}