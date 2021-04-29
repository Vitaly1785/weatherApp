package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button buttonFind;

    @FXML
    private Text temperature;

    @FXML
    private Text feelItLike;

    @FXML
    private Text max;

    @FXML
    private Text min;

    @FXML
    private Text pressure;

    @FXML
    private Text precipitation;

    @FXML
    void initialize() {
        city.setOnAction(event -> {
          getContent();
        });
    }

    @FXML
    public void pressButton(ActionEvent actionEvent) {
        buttonFind.setOnAction(event -> {
            getContent();
        });
    }

    private static String getUrlContent(String urlAddress){
        StringBuffer content = new StringBuffer();

        try{
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bfReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bfReader.readLine()) != null){
                content.append(line + "\n");
            }
            bfReader.close();
        }catch(Exception e){
            System.out.println("City not found");
        }
        return content.toString();
    }

    public void getContent(){
        String getUserCity = city.getText().trim();
        if (!getUserCity.equals("")) {
            String outPut = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=51a4daa37802666b623cb09993470bb2&units=metric");

            if (!outPut.isEmpty()) {
                JSONObject jsonObject = new JSONObject(outPut);
                temperature.setText("temperature:  " + jsonObject.getJSONObject("main").getDouble("temp") +"°");
                feelItLike.setText("feels it like: " + jsonObject.getJSONObject("main").getDouble("feels_like")+"°");
                max.setText("maximum: " + jsonObject.getJSONObject("main").getDouble("temp_max")+"°");
                min.setText("minimum: " + jsonObject.getJSONObject("main").getDouble("temp_min")+"°");
                pressure.setText("pressure: " + jsonObject.getJSONObject("main").getDouble("pressure"));
                precipitation.setText("precipitation: " + jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
            }
            System.out.println(outPut);
        }
    }


}
