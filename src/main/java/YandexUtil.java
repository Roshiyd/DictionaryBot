import com.google.gson.Gson;
import lombok.SneakyThrows;
import model.Result;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class YandexUtil {
    public static final String APIKEY="dict.1.1.20201120T210344Z.751b5ce72e1c0000.56d17cce25e21eaf117ca07fddd69c7fa5edb230";


    @SneakyThrows
    public static Result getDictionary(String text, String lang) throws IOException {

        HttpGet httpGet = new HttpGet("https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20201118T061118Z.4d9dc2eef1fdc7f8.f8bda1a86c28e80c2354fdd42391d65aebde3df0&lang=" + lang + "&text=" + text);
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(httpGet);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        Gson gson = new Gson();
        Result result = gson.fromJson(bufferedReader, Result.class);

        System.out.println(result);
        return result;
    }
}
