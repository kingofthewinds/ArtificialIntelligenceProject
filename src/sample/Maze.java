package sample;

import java.util.Stack;
import java.util.*;


public class Maze {
    int size;
    boolean north[][];
    boolean south[][];
    boolean east[][];
    boolean west[][];
    boolean visited[][];

    public Maze(int size) {
        this.size = size;
    }

    public ArrayList<int[]> getCircuit() {
        initialize();
        generate();
        int[] path = solve();
        for (int i=0; i<path.length; i++) {
            path[i] += 1;
        }
        int[] boucle = closeCircuit();
        int[] circuit = concatenate(path, boucle);

        //Redefinition of x and y due to JavaFx convention (see in controller
        ArrayList<int[]> finalCircuit = new ArrayList<>();
        for (int i=0; i<circuit.length; i+=2) {
            finalCircuit.add(new int[] {circuit[i+1],circuit[i]});
        }
        return finalCircuit;
    }

    private void initialize(){
        // initialze all walls as present
        north = new boolean[size][size];
        east  = new boolean[size][size];
        south = new boolean[size][size];
        west  = new boolean[size][size];

        visited = new boolean[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                north[x][y] = true;
                east[x][y]  = true;
                south[x][y] = true;
                west[x][y]  = true;
                visited[x][y] = false;
            }
        }
    }

    private void generate(){
        Stack<int[]> stack = new Stack<int[]>();
        int x = 0;
        int y = 0;
        stack.push(new int[]{x,y});
        while (!stack.isEmpty()) {
            visited[x][y] = true;
            ArrayList<Integer> check = new ArrayList<>();
            if (x>0 && !visited[x-1][y]) {
                check.add(1);					//1 = move left;
            }
            if (y>0 && !visited[x][y-1]) {
                check.add(2);					//2 = move up;
            }
            if (x<size-1 && !visited[x+1][y]) {
                check.add(3);					//3 = move right;
            }
            if (y<size-1 && !visited[x][y+1]) {
                check.add(4);					//4 = move down;
            }

            if (check.size()>0) {
                stack.push(new int[] {x,y});
                Random r = new Random();
                int value = r.nextInt(check.size());
                int direction = check.get(value);
                if (direction == 1) {
                    //1 = move left;
                    west[x][y] = false;
                    x = x-1;
                    east[x][y] = false;
                }
                if (direction == 2) {
                    //2 = move up;
                    north[x][y] = false;
                    y = y-1;
                    south[x][y] = false;
                }
                if (direction == 3) {
                    //3 = move right;
                    east[x][y] = false;
                    x = x+1;
                    west[x][y] = false;
                }
                if (direction == 4) {
                    //4 = move down;
                    south[x][y] = false;
                    y = y+1;
                    north[x][y] = false;
                }

            }
            else {
                //If there are no valid cells to move to.
                //retrace one step back in history if no move is possible
                int[] v = stack.pop();
                x = v[0];
                y = v[1];
            }
        }
        //Open the walls at the start and finish
        north[0][0] = false;
        east[size-1][size-1] = false;
    }

    private int[] solve(){
        reinitializeVisitedGrid();
        int x = 0;
        int y = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] {x,y});
		/*queue.add(new int[]{1,2});
		queue.add(new int[]{3,4});
		queue.add(new int[]{5,6});
		queue.add(new int[]{7,8});
		int[] v = queue.poll();
		System.out.println(v[0] + " "+ v[1]);
		v = queue.poll();
		v = concatenate(v , new int[] {2,3});
		System.out.println(v[0] + " "+ v[1]+ " " + v[2] + " "+ v[3]);
		*/
        while (!queue.isEmpty()) {
            int[] path = queue.poll();
            int sizePath = path.length;
            x = path[sizePath-2];
            y = path[sizePath-1];

            if (x == this.size-1 && y == this.size-1) {
                return path;
            }
            visited[x][y] = true;
            ArrayList<Integer> check = new ArrayList<>();
            if (x>0 && !visited[x-1][y] && !west[x][y]) {
                check.add(1);					//1 = can move left;
            }
            if (y>0 && !visited[x][y-1] && !north[x][y]) {
                check.add(2);					//2 = can move up;
            }
            if (x<size-1 && !visited[x+1][y] && !east[x][y]) {
                check.add(3);					//3 = can move right;
            }
            if (y<size-1 && !visited[x][y+1] && !south[x][y]) {
                check.add(4);					//4 = can move down;
            }
            for (Integer i : check) {
                if (i == 1) {
                    queue.add(concatenate(path, new int[] {x-1,y}));
                }
                else if (i == 2) {
                    queue.add(concatenate(path, new int[] {x,y-1}));
                }
                else if (i == 3) {
                    queue.add(concatenate(path, new int[] {x+1,y}));
                }
                else if (i == 4) {
                    queue.add(concatenate(path, new int[] {x,y+1}));
                }

            }
        }
        return null;
    }

    private int[] concatenate(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int i = 0;
        for (int x : a) { c[i] = x; i ++; }
        for (int x : b) { c[i] = x; i ++; }
        return c;
    }

    private void reinitializeVisitedGrid() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                visited[x][y] = false;
            }
        }
    }


    private int[] closeCircuit() {
        int close[] = new int[4*size + 2];
        int n = 0;
        for (int i = size; i>=0; i--) {
            close[n] = i;
            close[n+1] = this.size+1;
            n+=2;
        }
        for (int i = size; i>=1; i--) {
            close[n] = 0;
            close[n+1] =i;
            n+=2;
        }
        return close;
    }


}
