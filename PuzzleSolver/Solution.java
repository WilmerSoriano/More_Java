public class Solution implements Comparable<Solution>{ 
    public Solution(String name, String word, int x, int y, Direction direction) {
        this.name = name;
        this.word = word;
        this.x = x;
        this.y = y;;
        this.direction = direction;
    }
    
    @Override
    public int compareTo(Solution version)
    {
      int result = this.name.compareTo(version.name); //returns the result of "this name" field compareTo another solution object field
      
      if(result != 0)//less than or equal return them
      {
         return result;         
      }
      else//both are zero! continue looping for word
      {
         return this.word.compareTo(version.word);
      }
    }
    
    @Override
    public String toString() {
        return String.format("In %s: %s found at (%d,%d,%s)", name, word, x, y, direction);
    }

    private String name;
    private String word;
    private int x;
    private int y;
    private Direction direction;
}
