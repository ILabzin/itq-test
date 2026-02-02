import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateEPFiller {
    public static void main(String[] args) throws IOException {

        String content = Files.readString(Paths.get("count.txt")).trim();
        int count = Integer.parseInt(content);

        String url = "http://localhost:8080/api/v1/documents/create";

        System.out.println("Будем дергать эндпоинт " + count + " раз");

        HttpClient client = HttpClient.newHttpClient();

        String jsonBody = "{\"author\":\"Test Author\",\"title\":\"Test Title\"}";

        for (int i = 0; i < count; i++) {

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(java.net.URI.create(url))
                        .header("Content-Type", "application/json")
                        .POST(java.net.http.HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println("Статус: " + response.statusCode());
                System.out.println("Ответ: " + response.body());

            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}