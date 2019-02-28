import java.util.List;
import java.util.Set;

public class Photo {
    public int id;
    public int id2=-1;
    public boolean isHorizontal;
    public int tagsCount;
    public Set<String> tags;
    public boolean isUsed = false;

    public Photo(){
    }
    public void write(){
        System.out.print(id + " " + isHorizontal + " " + tagsCount + " ");
        tags.forEach(s -> System.out.print(s + " "));
        System.out.println();
    }

}
