import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class Main {

    static int posX = 1;
    static int posY = 1;
    static char[][] maze = new char[21][21];
    static boolean[][] isVisited = new boolean[21][21];
    static Vector<Pair> stack = new Vector<>();

    public static void main(String[] args) {


        new GUI();

    }


    /*

        #######################
        ## ####################
        #   ##################
        ##  #################
        #  ####################
        ########################

     */


    private static void generateMaze(char[][] maze, int posX, int posY){

        int count = 0;
        Vector<Pair>temp = new Vector<>();

        if(posY-2 > 0 && !isVisited[posX][posY-2]){
            //Atas
            maze[posX][posY-1] = ' ';
            maze[posX][posY-2] = ' ';
            isVisited[posX][posY-2] = true;

            temp.add(new Pair(posX, posY-2));
        }
        if(posX-2 > 0 && !isVisited[posX-2][posY]){
            //Kiri

            maze[posX-1][posY] = ' ';
            maze[posX-2][posY] = ' ';
            isVisited[posX-2][posY] = true;

            temp.add(new Pair(posX-2, posY));
        }

        if(posY+2 <= 19 && !isVisited[posX][posY+2] ){
            //Bawah

            maze[posX][posY+1] = ' ';
            maze[posX][posY+2] = ' ';
            isVisited[posX][posY+2] = true;

            temp.add(new Pair(posX, posY+2));
        }

        if(posX+2 <= 19 && !isVisited[posX+2][posY]){
            //Kanan

            maze[posX+1][posY] = ' ';
            maze[posX+2][posY] = ' ';
            isVisited[posX+2][posY] = true;
            temp.add(new Pair(posX+2, posY));
        }


        Collections.shuffle(temp);

        for(int i = 0 ; i < temp.size() ; i++){
            stack.add(0, temp.get(i));
            System.out.println(stack.get(0).getX() + " " + stack.get(0).getY());
        }




        if(stack.size() > 0){
            int x = stack.get(0).getX();
            int y = stack.get(0).getY();
            stack.remove(0);
            generateMaze(maze, x, y);
        }






    }



}
