package uz.pdp.appaviauz.feign;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;


@Service
public class TelegramFeign {


    public void sendMessageToUser(SendMessage sendMessage) {
        String chatId = sendMessage.getChatId();
        String text = sendMessage.getText();
        sendMessage(chatId,text+" "+ LocalDateTime.now());
    }

    private void sendMessage(@NonNull String chatId, @NonNull String text) {
        try {
            URL url = new URL("https://api.telegram.org/bot6159804786:AAHDtTrexOPOqclr7rhPz8SkEwiB6eC7Jx8/sendMessage");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            String requestBody = "{\"chat_id\": \""+chatId+"\",\"text\": \"" + text + "\"}";
            byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);
            connection.setRequestProperty("Content-Length", String.valueOf(requestBodyBytes.length));
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.write(requestBodyBytes);
            outputStream.flush();
            outputStream.close();
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            System.out.println("Response: " + response.toString());
        } catch (Exception ignored) {

        }
    }

}
