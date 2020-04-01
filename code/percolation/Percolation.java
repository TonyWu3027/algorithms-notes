import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF grid;
    private final int n;
    private int openCount;
    private boolean[] openStatus;
    private boolean percolateStatus = false;
    private boolean[] bottomConnctivity;
    private int root = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }

        this.n = n;
        this.openCount = 0;
        this.openStatus = new boolean[n*n];
        this.bottomConnctivity = new boolean[n*n+1];

        // instantiate a WeightedQuickUnionUF type and block all sites
        this.grid = new WeightedQuickUnionUF(n*n + 1);

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException("row and col must be greater than or equals to 1 and less than or equals to "+this.n);
        }

        int index = this.indexFinder(row, col);

        if (!this.isOpen(row, col)) {

            this.openCount++;
            this.openStatus[index-1] = true;

            if (row == 1) {
                this.grid.union(0, index);
            }

            int upIndex = this.indexFinder(row - 1, col);
            int downIndex = this.indexFinder(row + 1, col);
            int leftIndex = this.indexFinder(row, col - 1);
            int rightIndex = this.indexFinder(row, col + 1);
            int upComponent, downComponent, leftComponent, rightComponent;

            // up
            if (row - 1 >= 1 && this.isOpen(row - 1, col)) {
                upComponent = this.grid.find(upIndex);
                this.grid.union(upIndex, index);
                this.checkBottomConnectivity(upComponent, row, col);
            }
            // left
            if (col - 1 >= 1 && this.isOpen(row, col - 1)) {
                leftComponent = this.grid.find(leftIndex);
                this.grid.union(leftIndex, index);
                this.checkBottomConnectivity(leftComponent, row, col);
            }
            // right
            if (col + 1 <= this.n && this.isOpen(row, col + 1)) {
                rightComponent = this.grid.find(rightIndex);
                this.grid.union(rightIndex, index);
                this.checkBottomConnectivity(rightComponent, row, col);
            }
            // down
            if (row + 1 <= this.n && this.isOpen(row + 1, col)) {
                downComponent = this.grid.find(downIndex);
                this.grid.union(downIndex, index);
                this.checkBottomConnectivity(downComponent, row, col);
            }
            // buttom
            if (row == this.n) {
                this.bottomConnctivity[this.grid.find(index)] = true;
            }

            int centre = this.grid.find(index);
            this.root = this.grid.find(0);

            if (this.bottomConnctivity[centre] && centre == this.root) {
                this.percolateStatus = true;
            }

        }

    }

    private void checkBottomConnectivity(int offset, int row, int col) {
        int centre = this.grid.find(this.indexFinder(row, col));
        if (this.bottomConnctivity[offset]) {
            this.bottomConnctivity[centre] = true;
        }
    }

    private int indexFinder(int row, int col) {
        return (row - 1)*this.n + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException("row and col must be greater than or equals to 1 and less than or equals to "+this.n);
        }

        int index = this.indexFinder(row, col);

        return (this.openStatus[index - 1]);

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException("row and col must be greater than or equals to 1 and less than or equals to "+this.n);
        }

        int index = this.indexFinder(row, col);
        return (this.grid.find(index) == this.root);

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openCount;
    }

    // does the system percolate?
    public boolean percolates() { return percolateStatus; }

    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system


        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            System.out.println("Union "+i+" "+j+"\n");
        }
    }

}
