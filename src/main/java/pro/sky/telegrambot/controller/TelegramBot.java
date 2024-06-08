package pro.sky.telegrambot.controller;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


//public class TelegramBot extends TelegramLongPollingBot  {


//    @Override
//    public String getBotUsername()  {
//        return "Message2_bot";
//    }

//    @Override
//    public String getBotToken() {
//        return "7375650867:AAEwur4ryaHd2KfPC0IjVwJo8Wcxf_5XPWE";
//    }

//    @Override
//    public void onUpdateReceived(Update update) {
//        String chatId = update.getMessage().getChatId().toString();
//        String text = update.getMessage().getText();
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId);
//        sendMessage.setText(text);
//        try {
//            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        switch (text) {
            case "/start":
                try {
                    startCommandReceived(Long.parseLong(chatId), update.getMessage().getChat().getFirstName());
                }catch (TelegramApiException e){
                    throw new RuntimeException(e);
                }
            default:
                try {
                    sendMessage(Long.parseLong(chatId), "Sorry, please try again");
                }catch (TelegramApiException e){
                    throw new RuntimeException(e);
                }
        }


    }

    private void startCommandReceived(long chatId, String name) throws TelegramApiException {
        String answer = "Hi, " + name + " nice to meet you";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
    }
}
