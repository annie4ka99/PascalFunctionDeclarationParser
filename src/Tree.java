import java.util.Arrays;
import java.util.List;

public class Tree {
    private final String node;

    private void show (String start, boolean lastChild) {
        if (lastChild) System.out.println(start + "⤷---→" + node);
        else System.out.println(start + "|---→" + node);
        if (children != null) {
            for (int i = 0; i < children.size(); ++i) {
                if (i != children.size() - 1) {
                    if (lastChild) {children.get(i).show(start + "     ", false);}
                    else {children.get(i).show(start + "|    ", false);}
                } else {
                    if (lastChild) {children.get(i).show(start + "     ", true);}
                    else {children.get(i).show(start + "|    ", true);}
                }
            }
        }
    }

    public void show(){
        this.show("", true);
    }

    private List<Tree> children;

    Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    Tree(String node){
        this.node = node;
    }

}


