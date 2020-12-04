package observers;

import javafx.scene.layout.VBox;

public abstract class Observer<T>  extends VBox {
    public abstract void onUpdate(T t);
}
