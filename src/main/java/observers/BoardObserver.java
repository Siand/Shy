package observers;

import items.Board;
import items.Tile;
import items.TileCard;
import moves.Move;
import moves.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardObserver {

    private static BoardObserver instance;
    private static Map<Class<? extends Observer<Board>>,Observer<Board>> observers;
    private final Board board;

    private BoardObserver() {
        board = new Board();
    }

    public static BoardObserver Instance() {
        if(instance == null) {
            instance = new BoardObserver();
            observers = new HashMap<>();
        }
        return instance;
    }

    public void subscribe(Class<? extends Observer<Board>> observerClass, Observer<Board> observer) {
        observers.put(observerClass, observer);
        observer.onUpdate(board);
    }


    public void createPawnAt(int x, int y) {
        board.createPawnAt(x,y);
    }

    public Tile get(int x, int y) {
        return board.get(x, y);
    }

    public Position getPawnPos() {
        return board.getPawnPos();
    }

    public void reveal(int x ,int y) {
        board.reveal(x, y);
        notifyObservers();
    }
    public void remove(int x, int y) {
        board.remove(x, y);
        notifyObservers();
    }

    public void play(Move m) {
        board.play(m);
        notifyObservers();
    }

    public ArrayList<Position> getSurroundings(int x, int y) {
        return board.getSurroundings(x, y);
    }

    public boolean isShy(int x, int y) {
        return board.isShy(x, y);
    }

    public boolean isSteppable(int x, int y) {
        return board.isSteppable(x, y);
    }

    public String toJSONString() {
        return board.toJSONString();
    }

    public boolean isRevealed(int x, int y) {
        return board.isRevealed(x, y);
    }

    public void tagAll() {
        board.tagAll();
        notifyObservers();
    }

    public void reset() {
        board.reset();
        notifyObservers();
    }

    public boolean add(int x, int y, TileCard t) {
        boolean added =  board.add(x, y, t);
        if(added) {
            notifyObservers();
        }
        return added;
    }

    private void notifyObservers() {
        System.out.printf("Notifying %s observers.%n", observers.values().size());
        observers.values().forEach( o -> o.onUpdate(board));
    }
}
