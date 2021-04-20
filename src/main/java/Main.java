import lombok.SneakyThrows;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;


public class Main {
    @SneakyThrows
    public static void main(String[] args) {

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        botsApi.registerBot(new YandexBot());
        System.out.println("Bot Ishga tushdi!");
    }
}
