import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private WeightedQuickUnionUF grid;
    private boolean[]   state;
    private int     N;
	
	public Percolation (int N){
		// Init Grid
		int size = N*N;
		grid = new WeightedQuickUnionUF(size+2);
		this.N = N;
		if (N <= 0) {
            throw new java.lang.IllegalArgumentException(
            		"N must be larger than 0");
		}
		state   = new boolean[size + 2];
		state[0]=state[size+1]=true;
		
		for(int i = 1; i<+size; i ++) {
			state[i]=false;
		}
	}
	
	private int xyToIndex(int i, int j) {
        // Exception IndexOutOfBoundsException if i or j is not valid
        if (i < 0 || i > N) 
            throw new IndexOutOfBoundsException("row i out of bound"+i);
        if (j < 0 || j > N) 
            throw new IndexOutOfBoundsException("column j out of bound"+j);

        return (i - 1) * N + j;
    }
	
	public void open (int i , int j) {
		int xy= xyToIndex(i, j);
        state[xy] = true;
        //unify this new node to surrounding nodes
        
        int xyleft = xyToIndex(i-1, j);
        int xyright = xyToIndex(i+1, j);
        int xyup = xyToIndex(i, j+1);
        int xydown = xyToIndex(i, j-1);
        grid.union(xy, xyleft);
        grid.union(xy, xyright);
        grid.union(xy, xyup);
        grid.union(xy, xydown);
        
        if(isTopSite(xy)) grid.connected(0, xy);
        if(isBottomSite(xy)) grid.connected(N+1, xy);
	}
	
	private boolean isTopSite(int index) {
        return index <= N;
    }

    private boolean isBottomSite(int index) {
        return index >= (N - 1) * N + 1;
    }
	
	public boolean isOpen(int i, int j) {
		//check is position is open
		int xy = xyToIndex(i,j);
		return	state[xy];	
	
	}
	
	public boolean isFull(int i, int j) {
		int xy = xyToIndex(i, j);
		return grid.connected(0, xy);
		
	}
	
	public boolean percolates () {
		//check if the top aux node is connected to bottom aux node
		return grid.connected(0, state.length-1); 
		
	}
	
	public static void main(String[] args) {
	    Percolation perc = new Percolation(3);
	    perc.open(1, 2);
	    perc.open(2, 2);
	    perc.open(2, 3);
	    perc.open(3, 3);
	    boolean c = perc.isFull(1, 1);
	    //boolean c1 = perc.uf.connected(perc.ijTo1D(1, 1), perc.ijTo1D(2, 1));
	    //boolean c2 = perc.percolates();
	    StdOut.println(c);
	    //StdOut.println(c1);
	    //StdOut.println(c2);
	  }
	
//	public static void main(String[] args) {
//        final int TESTS = 2;
//        final int N = 20;
//         
//        System.out.println("\n***Percolation: Monte Carlo Simulation ***\n");
//         
//        Percolation perc = new Percolation(N);
//        System.out.println("Successfully created Percolation object.");
//        System.out.println("N: " + perc.getN());
//        System.out.println();
//         
//        System.out.println("Starting to open random sites...");
//        int row, col, ct;
//        double sum = 0.0;
//        for (int i = 0; i < TESTS; i++) {
//            ct = 0;
//            perc = new Percolation(N);
//            while (!perc.percolates()) {
//                row = StdRandom.uniform(N) + 1;
//                col = StdRandom.uniform(N) + 1;
//                if (perc.isFull(row, col)) {
//                    perc.open(row, col);
//                    ct++;
//                }
//                // System.out.println("Count: " + ct);
//            }
//            sum += ct;
//        }
//        System.out.println("After " + TESTS + " attempts, the average number of sites");
//        System.out.printf("opened was %.2f", sum/TESTS);
//        System.out.printf(" or %.2f", ((sum/TESTS)/(N * N)) * 100);
//        System.out.println("%.");
//        System.out.println("\nDone.\n");
//    }
}
