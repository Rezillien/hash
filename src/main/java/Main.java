import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static int R,C,F,N,B,T;
    public static List<Photo> list = new ArrayList<>();

    public static void main(String args[]) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/d_pet_pictures.txt"));
        try {
//            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
//            sb.append(line);
//            sb.append("\n");
            N=Integer.parseInt(line);

            for(int i=0;i<N;i++){
//                sb.append(line);
//                sb.append("\n");
                line = br.readLine();
                if(line==null)
                    break;
                String[] partss= line.split(" ");
                var photo = new Photo();
                if (partss[0].equals("H")){
                    photo.isHorizontal = true;
                } else{
                    photo.isHorizontal = false;
                }
                photo.tagsCount = Integer.parseInt(partss[1]);
                List<String> tags = new ArrayList<>();
                for(var j=2;j<photo.tagsCount+2;j++){
                    tags.add(partss[j]);
                }
                photo.tags = tags;
                photo.id = i;
                list.add(photo);
            }
        } finally {
            br.close();
        }
//        System.out.println(N);
//
//        for (Photo ride:list
//        ) {
//            ride.write();
//        }
        calculate();





    }

    public static void calculate(){
        var veriticalList = new ArrayList<Photo>();
        var horizontalList  = new ArrayList<Photo>();
        for(Photo photo:list){
            if(photo.isHorizontal){
                horizontalList.add(photo);
            } else {
                veriticalList.add(photo);
            }
        }

        System.out.println(horizontalList.size());
        System.out.println(veriticalList.size());

        System.out.println(calculatePoints(horizontalList.get(0),horizontalList.get(1)));

    }

    public static int calculatePoints(Photo a, Photo b){

        List<String> elements = new ArrayList<String>(a.tags);
        elements.retainAll(b.tags);
        System.out.println(a.tags);
        System.out.println(b.tags);
        System.out.println(elements);
        return elements.size();
    }
}
