public class Pair {

    private int x;
    private int y;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean isVisited(int x, int y){
        if(this.x == x && this.y == y){
            return true;
        }
        return false;
    }

}
