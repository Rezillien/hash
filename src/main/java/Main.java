import java.io.*;
import java.util.*;

public class Main {
    public static int R,C,F,N,B,T;
    public static List<Photo> list = new ArrayList<>();
    public static List<Photo> horizontalList = new ArrayList<>();
    public static List<Photo> veriticalList = new ArrayList<>();


    public static void main(String args[]) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/c_memorable_moments.txt"));
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
                Set<String> tags = new HashSet<>();
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

    public static void calculate() throws FileNotFoundException, UnsupportedEncodingException {
        var asd = new LinkedList<String>();
        for(Photo photo:list){
            if(photo.isHorizontal){
                horizontalList.add(photo);
            } else {
                veriticalList.add(photo);
            }
        }

        System.out.println(horizontalList.size());
        System.out.println(veriticalList.size());

        System.out.println(horizontalList.get(0).tags);
        System.out.println(calculateMaxHorizontal(horizontalList.get(0)).tags);
        System.out.println(calculatePoints(horizontalList.get(0),calculateMaxHorizontal(horizontalList.get(0))));
        joinList();
        System.out.println(horizontalList.size());
//        for(var i=0; i<horizontalList.size(); i++){
//            var photo = horizontalList.get(i);
//            Photo maxH = calculateMaxHorizontal(photo);
//            Photo[] maxV = calculateMaxVeritical(photo);
//            System.out.print(photo.id+": ");
//            System.out.println(photo.tags);
//            System.out.print(maxH.id+"; " + calculatePoints(photo, maxH) + "; ");
//            System.out.println(maxH.tags);
//            System.out.print(maxV[0].id+ " " + maxV[1].id +"; " + calculatePoints(photo, maxV[0], maxV[1]) + "; ");
//            System.out.println(maxV[0].tags + " " + maxV[1].tags);
//        }
        var endList = new LinkedList<Photo>();
        Photo photoLeft;
        Photo photoRight;
        photoLeft = horizontalList.get(0);
        endList.add(photoLeft);
        photoRight = calculateMaxHorizontal(photoLeft);
        endList.add(photoRight);
        photoLeft.isUsed = true;
        photoRight.isUsed = true;
        int usedCount = 2;
        while(usedCount < horizontalList.size()){
            if(usedCount%100==0){
                System.out.println(usedCount);
            }
            Photo photoLeftN = calculateMaxHorizontal(photoLeft);
            Photo photoRightN = calculateMaxHorizontal(photoRight);
            if(photoLeftN == null || photoRightN == null){
                System.out.println("asd");
            }
            if(calculatePoints(photoLeft, photoLeftN) > calculatePoints(photoRight, photoRightN)){
                endList.add(0,photoLeftN);
                photoLeft = photoLeftN;
                photoLeftN.isUsed = true;
            } else {
                endList.add(photoRightN);
                photoRight = photoRightN;
                photoRightN.isUsed = true;
            }
            usedCount++;
        }
        PrintWriter writer = new PrintWriter("c.txt", "UTF-8");
        writer.println(endList.size());
        for(var i=0; i<endList.size();i++){
            if(endList.get(i).id2 != -1){
                writer.println(endList.get(i).id + " " + endList.get(i).id2);
            } else {
                writer.println(endList.get(i).id);
            }
        }
        writer.close();

    }

    public static Photo calculateMaxHorizontal(Photo a){
        int maxScore = -1;
        Photo max = null;
        for(Photo photo:horizontalList){
            if(photo.equals(a) || photo.isUsed ){
                continue;
            }
            int score = calculatePoints(a, photo);
            if(score>maxScore){
                max = photo;
                maxScore = score;
            }

        }
        return max;
    }

    public static Photo[] calculateMaxVeritical(Photo a){
        int maxScore = 0;
        Photo max = null;
        Photo max2 = null;

        for(Photo photo1: veriticalList){
            for(Photo photo2: veriticalList){
                if(photo1.equals(photo2)){
                    continue;
                }
                int score = calculatePoints(a, photo1, photo2);
                if(score>maxScore){
                    max = photo1;
                    max2 = photo2;
                    maxScore = score;
                }

            }
        }
        Photo[] ret = new Photo[2];
        ret[0] = max;
        ret[1] = max2;
        return ret;
    }

    public static Photo calculateMaxHorizontal(Photo a, Photo b){
        int maxScore = 0;
        Photo max = null;
        for(Photo photo:horizontalList){
            if(photo.equals(a)){
                continue;
            }
            int score = calculatePoints(photo, a, b);
            if(score>maxScore){
                max = photo;
                maxScore = score;
            }

        }
        return max;
    }

    public static Photo[] calculateMaxVeritical(Photo a, Photo b){
        int maxScore = 0;
        Photo max = null;
        Photo max2 = null;

        for(Photo photo1: veriticalList){
            for(Photo photo2: veriticalList){
                if(photo1.equals(photo2)){
                    continue;
                }
                int score = calculatePoints(a, b, photo1, photo2);
                if(score>maxScore){
                    max = photo1;
                    max2 = photo2;
                    maxScore = score;
                }

            }
        }
        Photo[] ret = new Photo[2];
        ret[0] = max;
        ret[1] = max2;
        return ret;
    }


    public static int calculatePoints(Photo a, Photo b){

        List<String> elements = new ArrayList<String>(a.tags);
        if(elements == null || b==null){
            return 0;
//            System.out.println("asd");
        }
        elements.retainAll(b.tags);
//        System.out.println(a.tags);
//        System.out.println(b.tags);
//        System.out.println(elements);
        return Math.min(Math.min(elements.size(),a.tags.size()-elements.size()),b.tags.size()-elements.size());
    }

    public static int calculatePoints(Photo a, Photo b, Photo c){
        var e = new ArrayList<String>(b.tags);
        e.addAll(c.tags);
        List<String> elements = new ArrayList<String>(a.tags);
        elements.retainAll(e);
//        System.out.println(a.tags);
//        System.out.println(e);
//        System.out.println(elements);
        return Math.min(Math.min(elements.size(),a.tags.size()-elements.size()),e.size()-elements.size());
    }

    public static int calculatePoints(Photo a, Photo b, Photo c, Photo d){
        var e = new ArrayList<String>(c.tags);
        e.addAll(d.tags);
        var f = new ArrayList<String>(a.tags);
        e.addAll(b.tags);
        List<String> elements = new ArrayList<String>(e);
        elements.retainAll(f);
//        System.out.println(e);
//        System.out.println(f);
//        System.out.println(elements);
        return Math.min(Math.min(elements.size(),e.size()-elements.size()),f.size()-elements.size());
    }

    public static void joinList(){

        for(Photo photo: veriticalList){
//            System.out.println(photo.id);
            if(photo.id%100 == 0){
                System.out.println(photo.id);
            }
            if(photo.isUsed){
                continue;
            }
            int min = 2000;
            Photo photo2= null;
            for(Photo photonew: veriticalList){
                if(photonew.equals(photo)){
                    continue;
                }
                if(photonew.isUsed){
                    continue;
                }
                List<String> elements = new ArrayList<String>(photo.tags);
                elements.retainAll(photonew.tags);
                if(elements.size()<min){
                    min = elements.size();
                    photo2 = photonew;
                }
            }
            photo.id2 = photo2.id;
            photo.tags.addAll(photo2.tags);
            horizontalList.add(photo);
            photo.isUsed = true;
            photo2.isUsed = true;
//            veriticalList.remove(photo.id);
//            veriticalList.remove(photo2.id);
        }
        for(Photo photo: horizontalList){
            photo.isUsed = false;
        }
    }
}
