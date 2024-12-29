/*
 * This code solves word Puzzles using
 * multiple threads.
 * This code also contains a handling and pushes
 * out result in a specefic manner. 
 * Check out Sample_Result folder for better visual.
 * Check out the Puzzles folder for sample test trials
 * 
 *  Acknowledgements:
 *  Craig Maloney from https://github.com/craigmaloney/wordsearch
 *  Profressor Rice for the vocabulary puzzle is also at prof-rice/P07/baseline/puzzle_vocab.txt. 
 *  https://github.com/prof-rice/cse1325-prof.git
 *  
 * 
 * NOTE: Code is under development and debugging 12/29/2024, Wilmer Soriano
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet; //to keep WordSeach.solutions sorted to get similar output
import java.util.TreeSet;   //It sort of work, just keep.

public class WordSearch {
    private static final String usage = "usage: java WordSearch [-h] [-v] [#threads] [#puzzles] [puzzleFile]...";

    public WordSearch(List<String> args) {
    
        // Validate the command line arguments
        if(args.size() > 0 && args.get(0).equals("-h")) {
            System.out.println(usage);
            System.exit(0);
        }
        if(args.size() > 0 && args.get(0).equals("-v")) {
             verbose = true;
             args.remove(0);
        } else {
             verbose = false;
        }
        int threads = 0;
        try {
            // Can't assign NUM_THREADS here because javac worries
            // it may not be assigned a value
            threads = Integer.parseInt(args.get(0));
            args.remove(0);
        } catch(Exception e) {
            System.err.println("Error: Invalid number of threads\n" + usage);
            System.exit(-1);
        }
        NUM_THREADS = threads;
        int numPuzzles = 0;
        try {
            // Can't assign NUM_THREADS here because javac worries
            // it may not be assigned a value
            numPuzzles = Integer.parseInt(args.get(0));
            args.remove(0);
        } catch(Exception e) {
            System.err.println("Error: Invalid number of puzzles\n" + usage);
            System.exit(-1);
        }

        // Load the puzzles!
        for(String puzzleName : args) {
            try(BufferedReader br = new BufferedReader(new FileReader(puzzleName))) {
                puzzles.add(new Puzzle(puzzleName, br));
            } catch(IOException e) {
                System.err.println("Unable to load puzzle " + puzzleName);
            }
        }
        
        // Verify all puzzles loaded successfully
        // No error is printed, as a message should be printed for each failed load above
        if(puzzles.size() != args.size()) System.exit(-3);
        
        // Delete or duplicate puzzles to get the right number
        if(numPuzzles < puzzles.size()) puzzles.subList(numPuzzles, puzzles.size()).clear();
        else if (numPuzzles > puzzles.size()) {
            for(int i=puzzles.size(); i<numPuzzles; ++i)
                puzzles.add(puzzles.get(i%puzzles.size()));
        }
        NUM_PUZZLES = puzzles.size();
        
        // -------- All Puzzles Loaded --------
    }
    
    // vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
    // Modify THIS method to divide up the puzzles among your threads!
    // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public void solve() {
        System.err.println ("\n" + NUM_PUZZLES + " puzzles with " 
            + NUM_THREADS + " threads"); // Show the # puzzles and threads
        // Solve all puzzles
        
        int multiplier =  NUM_PUZZLES / NUM_THREADS; //a fraction of the total number of puzzle, EX: 900 puzzle, 2 thread , 900/2 =450
        
        List<Thread> threads = new ArrayList<>();  //created a iterate for threadID
        
        for(int i=0; i<NUM_THREADS; ++i) //if i = 0 , 450*0 = 0 , 450*1 =450, etc..
        {
           final int numPuzzles = i*multiplier;
           final int lastAdded = numPuzzles + multiplier; 
           final int threadID = i;
           
           (new Thread(()-> solve(threadID, numPuzzles, lastAdded))).start();
        }
        try
        {
           for(Thread x : threads)
            {
              x.join();
            }
        }
        catch(InterruptedException e){System.err.println("didn't work");}
    }
    
   private static Object lock = new Object();
   
    public void solve( int threadID, int firstPuzzle, int lastPuzzlePlusOne) {
        System.err.println("Thread " + threadID + ": " + firstPuzzle + "-" + (lastPuzzlePlusOne-1));
        for(int i=firstPuzzle; i<lastPuzzlePlusOne; ++i) {
           
           
            Puzzle p ;
            synchronized(lock){p = puzzles.get(i);}//===============
            Solver solver = new Solver(p);
           
           
            for(String word : p.getWords()) {
                try {
                   
                    Solution s = solver.solve(word);//test4=====================
                   
                    if(s == null) System.err.println("#### Failed to solve " + p.name() + " for '" + word + "'");
                    
                    else synchronized(lock){solutions.add(s);} //another one ?
                    
                } catch (Exception e) {
                    System.err.println("#### Exception solving " + p.name() 
                        + " for " + word + ": " + e.getMessage());
                }
            }
        }
        
        // -------- All Puzzles Solved --------
    }
    public void printSolutions() {
        for(Solution s : solutions)
            System.out.println(s);
    }
    public static void main(String[] args) {
        WordSearch ws = new WordSearch(new LinkedList<>(Arrays.asList(args)));
        ws.solve();
        if(ws.verbose) ws.printSolutions();
    }

    public final int NUM_THREADS;
    public final int NUM_PUZZLES;
    public final boolean verbose;

    private List<Puzzle> puzzles = new ArrayList<>();;
    private SortedSet<Solution> solutions = new TreeSet<>(); //Tree Set?...Tree set is only saving the sorted output by SortedSet
}
