import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class GUI extends JFrame {
    final char PLAYER = '@';
    final char EXIT_DOOR = '!';
    final char COIN = '$';
    final char TRAP = '0';
    final char FLOOR = ' ';
    final char WALL = '#';

    int posX = 1;
    int posY = 1;
    char[][] maze = new char[21][21];
    boolean[][] isVisited = new boolean[21][21];
    Vector<Pair> stack = new Vector<>();

    PapanGame gamePanel = new PapanGame();


    public GUI(){
        initializeMaze();
        generateMaze(maze, posX, posY);
        maze[1][1] = PLAYER;
        maze[19][19] = EXIT_DOOR;
        randomizeItem();
        gamePanel.setLayout(null);
        gamePanel.setSize(21*20, 21*20);



        add(gamePanel);

        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    void initializeGUI(){

    }

    void randomizeItem(){
        int counterTrap = 3;
        int counterCoin = 5;

        while(counterCoin != 0)
        {
            int x = new Random().nextInt(21);
            int y = new Random().nextInt(21);

            if(maze[x][y] == FLOOR){
                counterCoin--;
                maze[x][y] = COIN;
            }
        }

        while(counterTrap != 0){
            int x = new Random().nextInt(21);
            int y = new Random().nextInt(21);

            if(maze[x][y] == FLOOR){
                counterTrap--;
                maze[x][y] = TRAP;
            }
        }

    }


    class PapanGame extends JPanel{

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            for(int i = 0 ; i < 21 ; i++){
                for(int j = 0 ; j < 21 ; j++){
                    if(maze[i][j] == WALL){
                        g.setColor(Color.BLACK);
                    }else if(maze[i][j] == PLAYER){
//                        g.setColor(Color.decode("#07F723"));
                        g.setColor(Color.decode("#07F723"));
                    }else if(maze[i][j] == EXIT_DOOR){
                        g.setColor(Color.decode("#00C3E5"));
                    }else if(maze[i][j] == COIN){
                        g.setColor(Color.decode("#FBC02D"));
                    }else if(maze[i][j] == TRAP){
                        g.setColor(Color.decode("#ED020A"));
                    }else if(maze[i][j] == FLOOR){
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(i*20, j*20, 20, 20);
                }
            }

        }
    }

    void initializeMaze(){
        for(int i = 0 ; i <= 20 ;i++){
            for(int j = 0 ; j <= 20 ; j++){
                maze[i][j] = '#';
                isVisited[i][j] = false;
            }
        }
    }
    void generateMaze(char[][] maze, int posX, int posY){
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

    void printMaze(){
        for(int i = 0 ; i <= 20 ;i++){
            for(int j = 0 ; j <= 20 ; j++){
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }



}
