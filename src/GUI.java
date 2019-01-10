import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class GUI extends JFrame{
    final char PLAYER = '@';
    final char EXIT_DOOR = '!';
    final char COIN = '$';
    final char TRAP = '0';
    final char FLOOR = ' ';
    final char WALL = '#';
    int time = 25;
    int level = 1;
    int lives = 3;
    JButton exit = new JButton("Exit");
    int playerXPos = 1;
    int playerYPos = 1;

    boolean isPause = false;
    boolean isRunning = true;

    int posX = 1;
    int posY = 1;
    char[][] maze = new char[21][21];
    boolean[][] isVisited = new boolean[21][21];
    Vector<Pair> stack = new Vector<>();

    PapanGame gamePanel = new PapanGame();

    JLabel titleLbl = new JLabel("Find the X-it");
    JLabel timeLeftlbl = new JLabel("Time Left : ");
    JLabel lifeLbl = new JLabel("Life : ");
    JLabel levelLbl = new JLabel("Level : ");
    JLabel timeValue = new JLabel("25");
    JLabel liveValue = new JLabel("3");
    JLabel levelValue = new JLabel("1");
    JLabel space = new JLabel("Press space to pause the game");
    JLabel hovThis = new JLabel("Hover ");
    JLabel thisOne = new JLabel("THIS" );
    JLabel toShow = new JLabel("to show exit button");
    Thread t;
    Runnable r;
    KeyListener keyListener;
    public GUI(){

        r = new Runnable() {
            @Override
            public void run() {
                while (time != 0) {
                    try {
                        Thread.sleep(1000);
                        time--;
                        timeValue.setText(String.valueOf(time));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                removeKeyListener(keyListener);
                JOptionPane.showMessageDialog(null, "You lose");
            }
        };

        t = new Thread(r);
        t.start();
        setLayout(new BorderLayout());
        initializeMaze();
        generateMaze(maze, posX, posY);

        randomizeItem();
        gamePanel.setLayout(null);
        gamePanel.setSize(21*20, 21*20);
        add(gamePanel, BorderLayout.CENTER);
        initializeGUI();
        addListener();
        setVisible(true);
        setSize(700, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void initializeGUI(){



        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(8, 1));
        Font titleFont = titleLbl.getFont();
        titleLbl.setFont(new Font(titleFont.getName(), Font.BOLD, 22));
//        titleLbl.setBounds(2, 5, 20, 20);
        rightPanel.add(titleLbl);

        JPanel timePanel = new JPanel(new FlowLayout());
        JPanel lifePanel = new JPanel(new FlowLayout());
        JPanel levelPanel = new JPanel(new FlowLayout());


        timePanel.add(timeLeftlbl);
        timePanel.add(timeValue);

        lifePanel.add(lifeLbl);
        lifePanel.add(liveValue);

        levelPanel.add(levelLbl);
        levelPanel.add(levelValue);


        Legend legend = new Legend();
        legend.setLayout(new GridLayout(4, 1));

        legend.add(new JLabel("         Your Goal"));
        legend.add(new JLabel("         Player"));
        legend.add(new JLabel("         Coin Extra Time"));
        legend.add(new JLabel("         Its a trap!"));


        JPanel pressSpace = new JPanel(new FlowLayout());

        pressSpace.add(space);


        JPanel hoverThis = new JPanel(new FlowLayout());
        hoverThis.add(hovThis);
        hoverThis.add(thisOne);
        hoverThis.add(toShow);


        rightPanel.add(timePanel);
        rightPanel.add(lifePanel);
        rightPanel.add(levelPanel);
        rightPanel.add(legend);
        exit.setVisible(false);
        rightPanel.add(pressSpace);
        rightPanel.add(hoverThis);
        rightPanel.add(exit);
        add(rightPanel, BorderLayout.EAST);



    }

    void randomizeItem(){
        int counterTrap = 3;
        int counterCoin = 5;
        maze[1][1] = FLOOR;
        maze[19][19] = EXIT_DOOR;
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

    class Legend extends JPanel{
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            g.setColor(Color.decode("#00C3E5"));
            g.fillRect(0, 0, 20, 20);
            g.setColor(Color.decode("#07F723"));
            g.fillRect(0, 20, 20, 20);
            g.setColor(Color.decode("#FBC02D"));
            g.fillRect(0, 40, 20, 20);
            g.setColor(Color.decode("#ED020A"));
            g.fillRect(0, 60, 20, 20);
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
                    }else if(j == playerYPos && i == playerXPos){
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

    void addListener(){
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        thisOne.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exit.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        addKeyListener(keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(!isPause) {
                    if (Character.toLowerCase(e.getKeyChar()) == 'a') {

                        if (maze[playerXPos - 1][playerYPos] != WALL) {
                            playerXPos--;
                            gamePanel.repaint();
                            System.out.println("Heeee");
                        }
                    } else if (Character.toLowerCase(e.getKeyChar()) == 's') {
                        if (maze[playerXPos][playerYPos + 1] != WALL) {
                            playerYPos++;
                            gamePanel.repaint();
                            System.out.println("Heeee");
                        }
                    } else if (Character.toLowerCase(e.getKeyChar()) == 'd') {
                        if (maze[playerXPos + 1][playerYPos] != WALL) {
                            playerXPos++;
                            gamePanel.repaint();
                        }
                    } else if (Character.toLowerCase(e.getKeyChar()) == 'w') {
                        if (maze[playerXPos][playerYPos - 1] != WALL) {
                            playerYPos--;
                            gamePanel.repaint();
                        }
                    }
                }
                if(e.getKeyChar() == ' '){
                    if(!isPause){
                        t.suspend();
                        isPause = true;
                    }else{
                        t.resume();
                        isPause = false;
                    }
                }

                if(maze[playerXPos][playerYPos] == COIN){
                    System.out.println("COIN");
                    time += 5;
                    maze[playerXPos][playerYPos] = FLOOR;
                }
                if(maze[playerXPos][playerYPos] == TRAP){
                    System.out.println("TRAP");

                    lives--;
                    liveValue.setText(String.valueOf(lives));
                    maze[playerXPos][playerYPos] = FLOOR;
                    JOptionPane.showMessageDialog(null, "You loses a life");
                    if(lives == 0){
                        JOptionPane.showMessageDialog(null, "You lose");

                        removeKeyListener(keyListener);
                    }
                }

                if(maze[playerXPos][playerYPos] == EXIT_DOOR){

                    JOptionPane.showMessageDialog(null, "You win!");

                    level++;
                    levelValue.setText(String.valueOf(level));
                    time -= 3;

                    resetMap();
                    if(level== 8){
                        JOptionPane.showMessageDialog(null, "You win! Seriously win");
                        dispose();
                    }


                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    void resetMap(){
        for(int i = 0 ; i <= 20 ;i++){
            for(int j = 0 ; j <= 20 ; j++){
                maze[i][j] = '#';
                isVisited[i][j] = false;
            }
        }

        posX = 1;
        posY = 1;

        generateMaze(maze, posX, posY);
        randomizeItem();

        playerXPos = 1;
        playerYPos = 1;

    }


}
