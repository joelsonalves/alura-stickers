import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        
        // Fazer a conexão HTTP e buscar os top 250 filmes
        String url = "https://alura-imdb-api.herokuapp.com/movies";
        URI uri = URI.create(url);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
        String body = httpResponse.body();
        //System.out.println(body);

        // Extrair apenas os dados que interessam (título, poster, classificação)
        JsonParse jsonParse = new JsonParse();
        List<Map<String, String>> listaDeFilmes = jsonParse.parse(body);
        //System.out.println(listaDeFilmes.size());

        // Exibir e manipular os dados
        String imDbRating = null;
        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println("\u001b[1m\u001b[32m\u001b[47mTítulo:\u001b[m\t\t" + filme.get("title"));
            System.out.println("\u001b[1m\u001b[35m\u001b[47mPoster:\u001b[m\t\t" + filme.get("image"));
            imDbRating = filme.get("imDbRating");
            System.out.print("\u001b[1m\u001b[34m\u001b[47mClassificação:\u001b[m\t");
            for (int i = 1; i <= (int) (Float.parseFloat(imDbRating) + 0.5); i++) {
                System.out.print("⭐");
            }
            System.out.println(" (" + imDbRating + ")\n");
        }

    }
}
