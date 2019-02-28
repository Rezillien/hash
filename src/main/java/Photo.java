import java.util.List;

public class Photo {
    public int id;
    public boolean isHorizontal;
    public int tagsCount;
    public List<String> tags;

    public Photo(){
    }
    public void write(){
        System.out.print(id + " " + isHorizontal + " " + tagsCount + " ");
        tags.forEach(s -> System.out.print(s + " "));
        System.out.println();
    }

}
