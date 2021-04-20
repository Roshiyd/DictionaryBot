import com.oracle.xmlns.internal.webservices.jaxws_databinding.XmlWebParam;
import lombok.SneakyThrows;
import model.DefItem;
import model.Result;
import model.SynItem;
import model.TrItem;
import org.checkerframework.checker.units.qual.C;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YandexBot extends TelegramLongPollingBot {


    public static final String BOTTOKEN = "1470475069:AAGS_3Rgv1EI_bimmEwwy4iNWOXwx9lXG6o";
    public static final String BOTNAME = "dynamictranslaterbot";
    int step = 0;
    String language = null;
    String chatText;
    Long chatId;
    String wordList;
    List<String> favouriteList = new ArrayList<>();

    public String getBotUsername() {
        return BOTNAME;
    }

    public String getBotToken() {
        return BOTTOKEN;
    }

    @SneakyThrows
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        Message message = update.getMessage();
        chatId = message.getChatId();
        chatText = message.getText();
        sendMessage.setChatId(chatId);

        if (chatText.equalsIgnoreCase("/start")) {
            sendMessage.setText("Main menu");
            languageButtons(sendMessage, 1);
            step = 1;
        }
        switch (step) {
            case 1://main menu
                if (chatText.equalsIgnoreCase("\uD83D\uDD04 ᶜʰᵃⁿᵍᵉ ᵇᵒᵗ ˡᵃⁿᵍᵘᵃᵍᵉ :\uD83C\uDDF7\uD83C\uDDFA")) {
                    sendMessage.setText("Главное меню");
                    languageButtons(sendMessage, 2);
                } else if (chatText.equalsIgnoreCase("изменить язык бота:\uD83C\uDDEC\uD83C\uDDE7")) {
                    sendMessage.setText("Main menu");
                    languageButtons(sendMessage, 1);
                } else if (chatText.equalsIgnoreCase("\uD83C\uDDEC\uD83C\uDDE7 ₑₙgₗᵢₛₕ - ʳᵘˢˢⁱᵃᴺ \uD83C\uDDF7\uD83C\uDDFA")) {
                    sendMessage.setText("Enter the word you want search \uD83D\uDD0E");
                    language = "en-ru";
                    step = 2;
                } else if (chatText.equalsIgnoreCase("\uD83C\uDDEC\uD83C\uDDE7английский - русский\uD83C\uDDF7\uD83C\uDDFA")) {
                    sendMessage.setText("Введите словa\uD83D\uDD0E");
                    language = "en-ru";
                    step = 3;
                }else if (chatText.equalsIgnoreCase("\uD83C\uDDF7\uD83C\uDDFAрусский- английский\uD83C\uDDEC\uD83C\uDDE7")) {
                    sendMessage.setText("Введите словa\uD83D\uDD0E");
                    language = "en-ru";
                    step = 3;
                } else if (chatText.equalsIgnoreCase("⭐ ᶠᵃᵛᵒᵘʳⁱᵗᵉ ʷᵒʳᵈˢ ⭐")) {
                    sendMessage.setText("Choose options \uD83D\uDD18");
                    favouriteButton(sendMessage,1);
                    step = 4;
                } /*else if (chatText.equalsIgnoreCase("⭐избранное слова⭐")) {
                    sendMessage.setText("Bыберите операцию \uD83D\uDD18");
                    favouriteButton(sendMessage,2);
                    step = 4;
                }*/else if (chatText.equalsIgnoreCase("\uD83C\uDDF7\uD83C\uDDFA ʳᵘˢˢⁱᵃᴺ - ₑₙgₗᵢₛₕ \uD83C\uDDEC\uD83C\uDDE7")) {
                    sendMessage.setText("Enter the word you want search\uD83D\uDD0E");
                    language = "ru-en";
                    step = 2;
                }else if (chatText.equalsIgnoreCase("\uD83C\uDDF7\uD83C\uDDFApусский- aнглийский\uD83C\uDDEC\uD83C\uDDE7")) {
                    sendMessage.setText("Введите словa\uD83D\uDD0E\uD83D\uDD0E");
                    language = "ru-en";
                    step = 2;
                } else if (chatText.equalsIgnoreCase("\uD83D\uDCF2 ᵃᵇᵒᵘᵗ")){
                    sendMessage.setText("English - Russian, Russian - English word translater. Bot created by @Abdurroshidar in java programming language");
                }
                break;
            case 2://english-russian (en theme)
                StringBuilder stringBuilder = new StringBuilder();
                Result dictionary = YandexUtil.getDictionary(chatText, language);
                stringBuilder.append(" \uD83C\uDF10 "+chatText+"\n");
                List<DefItem> defItems = dictionary.getDef();
                int n = 1;
                for (DefItem defItem : defItems) {
                    List<TrItem> trItems = defItem.getTr();
                    for (TrItem trItem : trItems) {
                        stringBuilder.append((n++) + ") " + trItem.getPos() + " ➡ " + trItem.getText() + "\n\n");
                    }
                }
                wordList = chatText + "-" + defItems.get(0).getTr().get(0).getText();
                sendMessage.setText(stringBuilder.toString());
                translate(sendMessage, 1);
                step = 3;
                break;
            case 3:// search menu
                if (chatText.equalsIgnoreCase("⬅")) {
                    sendMessage.setText("⬅");
                    languageButtons(sendMessage, 1);
                    step = 1;
                } else if (chatText.equalsIgnoreCase("ᵃᵈᵈ ᵗᵒ ᶠᵃᵛᵒᵘʳⁱᵗᵉˢ ⭐")) {
                    favouriteList.add(wordList);
                    wordList = null;
                    sendMessage.setText("Word added to favourite list ✅");
                } else if (chatText.equalsIgnoreCase("ˢᵉᵃʳᶜʰ ᵃⁿᵒᵗʰᵉʳ ʷᵒʳᵈ \uD83D\uDD0E")) {
                    sendMessage.setText("Enter the word you want to search \uD83D\uDD0E");
                    step = 2;
                }
                break;
            case 4:
                if (chatText.equalsIgnoreCase("⬅")) {
                    sendMessage.setText("⬅");
                    languageButtons(sendMessage, 1);
                    step = 1;
                } else if (chatText.equalsIgnoreCase("ᵛⁱᵉʷ ⁱⁿ ᶜʰᵃᵗ \uD83D\uDCCB")) {
                    StringBuilder stringBuilder1 = new StringBuilder();
                    for (String s : favouriteList) {
                        stringBuilder1.append(s + "\n");
                    }
                    sendMessage.setText(stringBuilder1.toString());
                }/*else if (chatText.equalsIgnoreCase("показать в чате \uD83D\uDCCB")) {
                    StringBuilder stringBuilder1 = new StringBuilder();
                    for (String s : favouriteList) {
                        stringBuilder1.append(s + "\n");
                    }
                    sendMessage.setText(stringBuilder1.toString());
                }*/ else if (chatText.equalsIgnoreCase("ˢᵉⁿᵈ ᵈᵒᶜᵘᵐᵉⁿᵗ ᶠⁱˡᵉ \uD83D\uDDA8")) {
                    makeDoc();
                    sendMessage.setText("1");
                    SendDocument sendDocument=new SendDocument();
                    sendDocument.setChatId(chatId);
                    sendDocument.setDocument("src/main/resources/wordList.docx");
                    sendDocument.setCaption("caption");
                    execute(sendDocument);
                    System.out.println("file created");
                }
        }
        execute(sendMessage);
    }

    public void languageButtons(SendMessage sendMessage, int lanChoice) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();
        if (lanChoice == 1) {
            row1.add(new KeyboardButton("\uD83C\uDDEC\uD83C\uDDE7 ₑₙgₗᵢₛₕ - ʳᵘˢˢⁱᵃᴺ \uD83C\uDDF7\uD83C\uDDFA"));//en-ru
            row1.add(new KeyboardButton("\uD83C\uDDF7\uD83C\uDDFA ʳᵘˢˢⁱᵃᴺ - ₑₙgₗᵢₛₕ \uD83C\uDDEC\uD83C\uDDE7"));
            row2.add(new KeyboardButton("⭐ ᶠᵃᵛᵒᵘʳⁱᵗᵉ ʷᵒʳᵈˢ ⭐"));
            row3.add(new KeyboardButton("\uD83D\uDD04 ᶜʰᵃⁿᵍᵉ ᵇᵒᵗ ˡᵃⁿᵍᵘᵃᵍᵉ :\uD83C\uDDF7\uD83C\uDDFA"));
            row4.add(new KeyboardButton("\uD83D\uDCF2 ᵃᵇᵒᵘᵗ"));
        }
        if (lanChoice == 2) {
            row1.add(new KeyboardButton("\uD83C\uDDEC\uD83C\uDDE7английский - русский\uD83C\uDDF7\uD83C\uDDFA"));
            row2.add(new KeyboardButton("\uD83C\uDDF7\uD83C\uDDFAрусский- английский\uD83C\uDDEC\uD83C\uDDE7"));
            row3.add(new KeyboardButton("⭐избранное слова⭐"));
            row4.add(new KeyboardButton("изменить язык бота:\uD83C\uDDEC\uD83C\uDDE7"));
            row5.add(new KeyboardButton("\uD83D\uDCF2o боте"));

        }
        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);
        keyboardRows.add(row4);
        if (lanChoice==2){
            keyboardRows.add(row5);
        }
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

    public void translate(SendMessage sendMessage, int lanChoice) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        if (lanChoice == 1) {
            row1.add(new KeyboardButton("ᵃᵈᵈ ᵗᵒ ᶠᵃᵛᵒᵘʳⁱᵗᵉˢ ⭐"));
            row2.add(new KeyboardButton("ˢᵉᵃʳᶜʰ ᵃⁿᵒᵗʰᵉʳ ʷᵒʳᵈ \uD83D\uDD0E"));
            row3.add(new KeyboardButton("⬅"));
        }
        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

    public void favouriteButton(SendMessage sendMessage,int lanChoice) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        if (lanChoice==1) {
            row1.add(new KeyboardButton("ᵛⁱᵉʷ ⁱⁿ ᶜʰᵃᵗ \uD83D\uDCCB"));
            row1.add(new KeyboardButton("ˢᵉⁿᵈ ᵈᵒᶜᵘᵐᵉⁿᵗ ᶠⁱˡᵉ \uD83D\uDDA8"));
            row2.add(new KeyboardButton("⬅"));
        }/*
        if (lanChoice==1) {
            row1.add(new KeyboardButton("показать в чате \uD83D\uDCCB"));
            row1.add(new KeyboardButton("отправить документ \uD83D\uDDA8"));
            row2.add(new KeyboardButton("⬅"));
        }*/
        keyboardRows.add(row1);
        keyboardRows.add(row2);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

    @SneakyThrows
    public void makeDoc() {
        XWPFDocument document=new XWPFDocument();
        FileOutputStream outputStream=new FileOutputStream(new File("src/main/resources/wordList.docx"));
        for (String s : favouriteList) {
            XWPFParagraph paragraph=document.createParagraph();
            XWPFRun run=paragraph.createRun();
            run.setText(s);
        }
        document.write(outputStream);
        outputStream.close();
        System.out.println("created");

    }

}
