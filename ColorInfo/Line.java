public class Line //holds the coordinates and finds the length
{
   private double x1;
   private double y1;
   private double x2; 
   private double y2;
   private Color color;

   public Line(double x1, double y1,double x2, double y2, Color color)
   {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      this.color = color;
   }
   
   public double length()
   {
      double TotalFor_y;
      double TotalFor_x;
      
      TotalFor_x = Math.pow(x1 - x2,2); //to solve (x1-x2)^2
      TotalFor_y = Math.pow(y1 - y2,2);

      return Math.sqrt(TotalFor_x + TotalFor_y);
   }
   
   @Override
   public String toString()
   {
      return String.format("%10s", color)+ " ("  // Tested if color is a string, yes use %s
            +String.format("%.3f", x1)+ "," 
            +String.format("%.3f", y1)+ ") - (" 
            +String.format("%.3f", x2)+ "," 
            +String.format("%.3f", y2)+ ") has length " 
            + length(); //prints out, added alignment
   }
}
