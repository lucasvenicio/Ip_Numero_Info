package mashapelucas;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class IpInfo {

    public static void main(String[] args) throws UnirestException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o IP: ");
        String ip = scan.nextLine();

        Scanner scan2 = new Scanner(System.in);
        System.out.println("Digite o Código do País: ");
        String code = scan2.nextLine();

        Scanner scan3 = new Scanner(System.in);
        System.out.println("Digite o número: ");
        String numero = scan3.nextLine();

        HttpResponse<JsonNode> response = Unirest.post("https://community-neutrino-ip-info.p.mashape.com/ip-info")
                .header("X-Mashape-Key", "qYW0rfQcBRmshAgPIxuJUPrVfgT1p19FOIkjsnruP8KNZmqfdj")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .field("ip", ip)
                .field("reverse-lookup", false)
                .asJson();

        HttpResponse<JsonNode> response2 = Unirest.post("https://neutrinoapi-phone-validate.p.mashape.com/phone-validate")
                .header("X-Mashape-Key", "qYW0rfQcBRmshAgPIxuJUPrVfgT1p19FOIkjsnruP8KNZmqfdj")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .field("country-code", code)
                .field("number", numero)
                .asJson();

        //System.out.println(response.getBody());
        //System.out.println(response2.getBody());
        JSONArray array = response.getBody().getArray();
        JSONArray array2 = response2.getBody().getArray();
        
        String countrycode1 = null;
        String countrycode2 = null;

        for (int i = 0; i < array.length(); i++) {
            JSONObject match = array.getJSONObject(i);

            String country = match.getString("country");
            countrycode1 = match.getString("country-code");
            String city = match.getString("city");
            String region = match.getString("region");
            System.out.printf("\nResultado IP: " + "\n" + "País: %s" + "\nCódido do País: %s" + "\nRegião: %s" + "\nCidade: %s",
                    country, countrycode1, region, city);
        }
        for (int i = 0; i < array2.length(); i++) {
            JSONObject match2 = array2.getJSONObject(i);

            countrycode2 = match2.getString("country-code");
            String location = match2.getString("location");
            System.out.printf("\n\nResultado Número: " + "\nCódido do País: %s" + "\nRegião: %s",
                    countrycode2, location);
        }
        if(countrycode1.equalsIgnoreCase(countrycode2)){
            System.out.println("\nO IP e o número informados estão localizados no mesmo País.");
        }
        else
            System.out.println("\n\nO IP e o número informados não estão localizados no mesmo País.");
    }

}
