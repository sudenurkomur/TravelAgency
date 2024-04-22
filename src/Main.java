// Java Program to Create
// HashMap in Java
import java.util.HashMap;

// Driver Class
public class Main {
    // main function
    public static void main(String[] args) {

        // İzmir'in bilgilerini içeren bir obje oluşturalım
        LandMarkInformations izmir = new LandMarkInformations("Hotel", 25.0 ,4);
        LandMarkInformations sude = new LandMarkInformations("uzay", 24.0 ,4);


        // Tek bir anahtar altında iki değeri saklayacak bir HashMap oluşturalım
        HashMap<String, LandMarkInformations> izmirBilgileri = new HashMap<>();
        izmirBilgileri.put("Izmir", izmir);
        izmirBilgileri.put("Sude", sude);

        // Anahtarı kullanarak İzmir'in bilgilerine erişelim
        LandMarkInformations izmirBilgi = izmirBilgileri.get("Izmir");
        System.out.println("İzmir'in yüzölçümü: " + izmirBilgi.getName() );
        System.out.println("İzmir'in sıcaklığı: " + izmirBilgi.getLoad() );

        LandMarkInformations sudeBilgi = izmirBilgileri.get("Sude");
        System.out.println("İzmir'in yüzölçümü: " + sudeBilgi.getName() );
        System.out.println("İzmir'in sıcaklığı: " + sudeBilgi.getLoad() );
        System.out.println("İzmir'in sıcaklığı: " + sudeBilgi.getInterest() );
    }
}

 class LandMarkInformations {
    private String name;
    private double load;
    private double interest;

    public LandMarkInformations(String name, double load ,double interest) {
        this.name = name;
        this.load = load;
        this.interest=interest;
    }

    public String getName() {
        return name;
    }

    public double getLoad() {
        return load;
    }

     public double getInterest() {
         return interest;
     }
}

class OtherLandMarkInformations {
    private String name;
    private double destination;
    private double attractiveness;

    public OtherLandMarkInformations(String name, double destination,double attractiveness) {
        this.name = name;
        this.destination = destination;
        this.attractiveness=attractiveness;
    }

    public String getName() {
        return name;
    }

    public double getDestination() {
        return destination;
    }

    public double getAttractiveness() {
        return attractiveness;
    }
}