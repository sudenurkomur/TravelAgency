import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
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
        Scanner myObj = new Scanner(System.in);  // O(1)
        System.out.print("Please enter the total number of landmarks (including Hotel): ");
        int Size = myObj.nextInt();  // O(1)
        ArrayList<Record> arrayList = new ArrayList<>(); // O(1)

        readLandMark(arrayList); // O(N * M), where N is the number of landmarks and M is the average line length in the file
        ArrayList<String> IndexOfVertices = new ArrayList<>(); // O(1)
        readInterestAndLoadAndModifyTheList(arrayList, IndexOfVertices); // O(N * M) + O(N * M) = O(2N)
        Double[][] graphscore_map = new Double[Size][Size]; // O(1)
        Double[][] graphtime_map = new Double[Size][Size]; // O(1)
        createGraphScoreArray(graphscore_map, arrayList); // O(N^2)
        createTimeArray(graphtime_map, arrayList); // O(N^2)
        int indexOfHotel = getIndexOfHotel(arrayList, Size); // O(N)

        List<Integer> IndexOfPath = new ArrayList<>();
        double[] array = tspMax(Size, indexOfHotel, graphscore_map, graphtime_map, IndexOfPath); // O(2^N * N^2)

        System.out.println("""
                Three input files are read.

                The tour planning is now processing…

                The visited landmarks:""");
        for(int i = 0; i<IndexOfPath.size(); i++){ // O(N)
            System.out.println((i+1)+ "." + IndexOfVertices.get(IndexOfPath.get(i)));
        }
        System.out.println();
        System.out.print("Total attractiveness score: ");
        System.out.println(array[0]);
        System.out.println();
        System.out.print("Total travel time: ");
        System.out.print(array[1]);
        System.out.println(" min.");
    }
    // Time complexity: O(N) - Reads each line of the file (N times) with an average line length of M
    public static void readLandMark(ArrayList<Record> arrayList){
        try{
            FileInputStream fStream = new FileInputStream("/Users/sudenurkomur/IdeaProjects/TravelAgency/src/landmark_map_data.txt");
            DataInputStream in = new DataInputStream(fStream);
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
    // Time complexity: O(N)
    public static void readInterestAndLoadAndModifyTheList(ArrayList<Record> arrayList, ArrayList<String> IndexOfVertices){
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
                IndexOfVertices.add(dest);
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
            for(int i =0; i<arrayList.size(); i++){
                Double temp = Load.get(arrayList.get(i).dest);
                arrayList.get(i).score *= temp;
            }
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
    // O(N^2)
    public static void createGraphScoreArray(Double[][] graph_map, ArrayList<Record> arrayList){
        int counter1 = 0;
        for(int i = 0; i<graph_map.length; i++){
            for(int j = 0; j<graph_map[i].length;j++){
                if(i!=j){
                    Record temp = arrayList.get(counter1);
                    graph_map[i][j] = arrayList.get(counter1).score;
                    counter1++;
                }else{
                    graph_map[i][j]= 0.0;
                }
            }

        }
    }
    // O(N^2)
    public static void createTimeArray(Double[][] graph_map, ArrayList<Record> arrayList){
        int counter1 = 0;
        for(int i = 0; i<graph_map.length; i++){
            for(int j = 0; j<graph_map[i].length;j++){
                if(i!=j){
                    Record temp = arrayList.get(counter1);
                    graph_map[i][j] = arrayList.get(counter1).time;
                    counter1++;
                }else{
                    graph_map[i][j]= 0.0;
                }
            }

        }
    }
    // O(N)
    public static int  getIndexOfHotel(ArrayList<Record> arrayList,int size){
        for(int i =0; i<arrayList.size(); i++){
            if(arrayList.get(i).source.equals("Hotel")){
                if(i == 0){
                    return 0;
                }else{
                    return i/(size-1);
                }
            }
        }
        return -1;
    }
    // O(2^N * N^2)
    public static double[] tspMax(int N, int start, Double[][] dist, Double[][] time, List<Integer> path) {
        int VISITED_ALL = (1 << N) - 1;

        // Handle empty or null distance/time matrices
        if (dist == null || time == null || dist.length != N || dist[0].length != N || time.length != N || time[0].length != N) {
            throw new IllegalArgumentException("Distance or time matrices are invalid.");
        }

        // Handle negative distances (initialize dp with -Double.MAX_VALUE)
        double[][] dp = new double[1 << N][N]; //O(N)
        for (double[] row : dp) {
            Arrays.fill(row, -Double.MAX_VALUE);
        }

        double[][] dpTime = new double[1 << N][N];
        int[][] parent = new int[1 << N][N];

        dp[1 << start][start] = 0.0;
        dpTime[1 << start][start] = 0.0;

        for (int mask = 1; mask < (1 << N); mask += 2) {
            for (int last = 0; last < N; last++) {
                if ((mask & (1 << last)) != 0) {
                    for (int current = 0; current < N; current++) {
                        if ((mask & (1 << current)) == 0) {
                            // Handle disconnected graphs (check if distance is infinite)
                            if (dist[last][current] == Double.POSITIVE_INFINITY) {
                                continue;
                            }
                            double newDist = dp[mask][last] + dist[last][current];
                            double newTime = dpTime[mask][last] + time[last][current];

                            // Alternative tie-breaking prioritizing shorter time (uncomment if desired)
                            // if (newDist > dp[mask | (1 << current)][current] ||
                            //         (newDist == dp[mask | (1 << current)][current] && newTime < dpTime[mask | (1 << current)][current])) {
                            if (newDist > dp[mask | (1 << current)][current]) {
                                dp[mask | (1 << current)][current] = newDist;
                                dpTime[mask | (1 << current)][current] = newTime;
                                parent[mask | (1 << current)][current] = last;
                            }
                        }
                    }
                }
            }
        }

        double maxCost = Double.MIN_VALUE;
        int last = -1;
        for (int i = 0; i < N; i++) {
            if (i != start && dp[VISITED_ALL][i] + dist[i][start] > maxCost) {
                maxCost = dp[VISITED_ALL][i] + dist[i][start];
                last = i;
            }
        }

        path.clear();
        if (maxCost == Double.MIN_VALUE) {
            return new double[]{Double.MIN_VALUE, Double.MIN_VALUE};
        }

        int mask = VISITED_ALL;
        int current = last;
        while (current != start) {
            path.add(current);
            int prev = parent[mask][current];
            mask &= ~(1 << current);
            current = prev;
        }
        path.add(start);
        Collections.reverse(path);
        path.add(0);// Reverse the path for correct order

        double totalTime = dpTime[VISITED_ALL][last] + time[last][start];

        return new double[]{maxCost, totalTime};
    }
}