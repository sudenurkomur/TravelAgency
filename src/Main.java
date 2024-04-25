import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

class Record
{
    public String source;
    public String dest;
    public Double score;
    public Double time;
    public Record(String source, String dest, Double score, Double time){
        this.source = source;
        this.dest = dest;
        this.score = score;
        this.time = time;
    }

    public static void main(String[] args){
        ArrayList<Record> arrayList = new ArrayList<>();
        readLandMark(arrayList);
        readInterestAndLoadAndModifyTheList(arrayList);

        System.out.println();

    }
    public static void readLandMark(ArrayList<Record> arrayList){
        try{
            FileInputStream fstream = new FileInputStream("/Users/sudenurkomur/IdeaProjects/TravelAgency/src/landmark_map_data.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            strLine = br.readLine();
            while ((strLine = br.readLine()) != null)   {
                String[] tokens = strLine.split("\t");
                Record record = new Record(tokens[0].trim(),tokens[1].trim(),Double.parseDouble(tokens[2].trim()),Double.parseDouble(tokens[3].trim()));//process record , etc
                arrayList.add(record);
            }
            in.close();
            System.out.println();
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println();
    }

    public static void readInterestAndLoadAndModifyTheList(ArrayList<Record> arrayList){
        try{
            FileInputStream fstream = new FileInputStream("/Users/sudenurkomur/IdeaProjects/TravelAgency/src/visitor_load.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            HashMap<String,Double> Load = new HashMap<>();
            strLine = br.readLine();
            while ((strLine = br.readLine()) != null)   {
                String[] tokens = strLine.split("\t");
                String dest = tokens[0].trim();
                Double load = (1- Double.parseDouble(tokens[1].trim()));
                Load.put(dest,load);
            }
            fstream = new FileInputStream("/Users/sudenurkomur/IdeaProjects/TravelAgency/src/personal_interest.txt");
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            strLine = br.readLine();
            while ((strLine = br.readLine()) != null)   {
                String[] tokens = strLine.split("\t");
                String dest = tokens[0].trim();
                Double interest = Double.parseDouble(tokens[1].trim());
                Load.put(dest, Load.get(dest)*interest);
            }
            in.close();
            System.out.println();
            for(int i =0; i<arrayList.size(); i++){
                Double temp = Load.get(arrayList.get(i).dest);
                arrayList.get(i).score *= temp;
            }
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println();
    }

}