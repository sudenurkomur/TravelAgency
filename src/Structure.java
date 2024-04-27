import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Structure {
    public static void main(String[] args) throws IOException {
        callFile callFile=new callFile();
        callFile.call();
    }
}

    class callFile{
        public static void call() throws IOException {

            String[] filePaths = {"/Users/sudenurkomur/IdeaProjects/TravelAgency/src/landmark_map_data.txt",
                "/Users/sudenurkomur/IdeaProjects/TravelAgency/src/personal_interest.txt",
                "/Users/sudenurkomur/IdeaProjects/TravelAgency/src/visitor_load.txt"
        };

            ProcessFile file =new ProcessFile();
            Score score =new Score();

            BufferedReader findLine = new BufferedReader(new FileReader(filePaths[0]));
            BufferedReader findLine_two = new BufferedReader(new FileReader(filePaths[1]));
            BufferedReader findLine_three = new BufferedReader(new FileReader(filePaths[2]));

            int lineCount = 0;
            while (findLine.readLine() != null) {
                lineCount++;
            }

            int lineCount_two = 0;
            while (findLine_two.readLine() != null) {
                lineCount_two++;
            }

            int lineCount_three = 0;
            while (findLine_three.readLine() != null) {
                lineCount_three++;
            }

            Object[][] rowData = new Object[lineCount-1][8];


            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.print("Please enter the total number of landmarks (including Hotel): ");
            int Size = myObj.nextInt();  // Read user input

            Point[][] map = new Point[Size][Size];

            for(int i=0;i<Size;i++){
                map[i][i]= new Point(-1,  "not-exist");
            }

            file.processLandmarks(filePaths[0], rowData ,lineCount);
            file.processPersonalInterest(filePaths[1], rowData,lineCount_two);
            file.processVisitorLoad(filePaths[2], rowData,lineCount_three);
            score.landmarkaAttractiveScore(rowData);
            PrintArray.printScreen(rowData , map);

        }

    }

class Point {
    float score;
    String landmarkName;

    public Point(float score, String landmarkName) {
        this.score = score;
        this.landmarkName = landmarkName;
    }
}

class ProcessFile {
        public static void processLandmarks(String filePath , Object[][] rowData , int lineCount) throws FileNotFoundException {
        BufferedReader objReader ;
        try {
            String strCurrentLine;

            objReader = new BufferedReader(new FileReader(filePath));
            int lineIncrease = 0;

            while ((strCurrentLine = objReader.readLine()) != null && lineIncrease != lineCount) {
                // Satırı boşluklara göre böler ve elde edilen parçaları bir diziye atar
                Object[] parts = strCurrentLine.split("\\s+");
                //System.out.println(lineCount);

                if(lineIncrease != 0){
                    //System.out.println(parts[0]);
                    // Elde edilen parçaları diziye atar
                        if (parts.length >= 2) {
                            rowData[lineIncrease-1][0] = parts[0];
                            rowData[lineIncrease-1][1] = parts[1];
                            //basescore
                            float load =Float.parseFloat((String) parts[2]);
                            rowData[lineIncrease-1][2] =load;
                            //traveltime
                            float loadTwo =Float.parseFloat((String) parts[3]);
                            rowData[lineIncrease-1][3] =loadTwo;

                        }
                }
                lineIncrease++;
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void processPersonalInterest(String filePath , Object[][] rowData , int lineCount ){
        BufferedReader objReader ;
        try {
            String strCurrentLine;

            objReader = new BufferedReader(new FileReader(filePath));
            int lineIncrease = 0;

            while ((strCurrentLine = objReader.readLine()) != null && lineIncrease != lineCount) {
                // Satırı boşluklara göre böler ve elde edilen parçaları bir diziye atar

                Object[] parts = strCurrentLine.split("\\s+");

                //System.out.println(lineCount);

                if(lineIncrease != 0){
                    //System.out.println(parts[0]);
                    // Elde edilen parçaları diziye atar
                    if (parts.length >= 2) {
                        for(int i=0; i<rowData.length; i++){
                            if(parts[0].equals(rowData[i][1])){
                                float load = Float.parseFloat((String) parts[1]);
                                rowData[i][4]=load;
                            }
                        }
                    }
                }
                lineIncrease++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public static void processVisitorLoad(String filePath , Object[][] rowData , int lineCount ){
            BufferedReader objReader ;
            try {
                String strCurrentLine;

                objReader = new BufferedReader(new FileReader(filePath));
                int lineIncrease = 0;

                while ((strCurrentLine = objReader.readLine()) != null && lineIncrease != lineCount) {
                    // Satırı boşluklara göre böler ve elde edilen parçaları bir diziye atar

                    Object[] parts = strCurrentLine.split("\\s+");
                    //System.out.println(lineCount);
                    if(lineIncrease != 0){
                        //System.out.println(parts[0]);
                        // Elde edilen parçaları diziye atar
                        if (parts.length >= 2) {
                            for(int i=0; i<rowData.length; i++){
                                if(parts[0].equals(rowData[i][1])){
                                    float load = 1.0f - Float.parseFloat((String) parts[1]);
                                    rowData[i][5]= load;
                                }
                            }
                        }
                    }
                    lineIncrease++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

class Score{
    public static void landmarkaAttractiveScore(Object[][] rowData){

        for (int i = 0; i < rowData.length; i++) {
            // Öğelerin uygun türde olduğundan emin olmak için kontrol edin
            if (rowData[i][2] instanceof Float && rowData[i][4] instanceof Float && rowData[i][5] instanceof Float) {
                // Üç özelliğin çarpımıyla çekicilik hesaplanır
                float attractiveness = (float)rowData[i][2] * (float)rowData[i][4] * (float)rowData[i][5];

                // Hesaplanan çekicilik değeri rowData dizisinde ilgili öğenin altıncı sütununa atanır
                rowData[i][6] = attractiveness;
            }
        }
    }
}

class FillMap{

    public static void fillMap(Point[][] map, Object[][] rowData, int Size){

    }
}
class PrintArray{
    public static void printScreen(Object[][] rowData , Point[][] map){

        for(int i=0;i< rowData.length;i++){
            for(int m=0;m<rowData[i].length;m++){
                System.out.print(rowData[i][m] + "  ");
            }
            System.out.println();
        }

        System.out.println();

        /*for(int i=0;i< map.length;i++){
            for(int m=0;m<map[i].length;m++){
                System.out.print(map[i][m] + " ");
            }
            System.out.println();
        }*/

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Point point = map[i][j];
                if (point != null) {
                    System.out.println(" Score: " + point.score + ", " + " Landmark Adı: " + point.landmarkName + " ");
                } else {
                    //System.out.println("Koordinatlar: (Boş), Landmark Adı: (Boş)");
                }
            }
        }
    }
}

