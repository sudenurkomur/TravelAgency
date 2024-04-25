import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String[] filePaths = {"/Users/sudenurkomur/IdeaProjects/TravelAgency/src/landmark_map_data.txt",
                //"/Users/sudenurkomur/IdeaProjects/TravelAgency/src/personal_interest.txt",
                //"/Users/sudenurkomur/IdeaProjects/TravelAgency/src/visitor_load.txt"
        };

        for (String filePath : filePaths) {
            ProcessFile.process(filePath);
        }
    }

    // Inner class for processing files
    static class ProcessFile {
        public static void process(String filePath) {
            int lineCount;
            BufferedReader objReader = null;
            try {
                String strCurrentLine;

                objReader = new BufferedReader(new FileReader(filePath));
                // Finding the number of lines in the file
                lineCount = 0;
                while (objReader.readLine() != null) {
                    lineCount++;
                }

                // Array to store first spaces
                Object[][] landmarkData = new Object[lineCount][7]; // Create an array of appropriate size

                // Reading the content of the file line by line and splitting by spaces
                String line;
                int lineIncrease = 0;
                while ((line= objReader.readLine())!= null && lineIncrease != lineCount) {
                    String[] words = line.split("\\s+"); // Split by spaces
                    System.out.println(words[1]);
                    if (words.length >= 2) {
                        landmarkData[lineIncrease][0] = words[0];
                        landmarkData[lineIncrease][1] = words[1];
                    }
                    lineIncrease++;
                }

                /*for (int i = 0; i < lineCount; i++) {
                    for(int m=0;m< 7;m++)
                    {
                        System.out.print(landmarkData[i][m] + " ");
                    }
                    System.out.println();
                }*/

                // Closing the used resources
                objReader.close();


            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
