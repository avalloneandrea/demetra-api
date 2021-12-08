package demetra.alexa;

import com.fasterxml.jackson.databind.ObjectMapper;
import demetra.domain.Account;
import demetra.domain.Authorization;
import demetra.list.ListService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AlexaService {

    @ConfigProperty(name = "demetra.export.skill-id")
    String skillId;

    @ConfigProperty(name = "demetra.export.skill-secret")
    String skillSecret;

    @Inject
    ListService listService;

    @Inject
    ObjectMapper objectMapper;

    @Transactional
    public Account register(String amazonId, String alexaId) {
        Account account = Account.<Account>findByIdOptional(amazonId).orElseGet(() -> {
            Account newAccount = new Account();
            newAccount.amazonId = amazonId;
            return newAccount;
        });
        account.alexaId = alexaId;
        account.persist();
        return account;
    }

    public Response addItems(String accountId, List<Long> recipes) throws IOException, InterruptedException {
        Account account = Account.findById(accountId);
        Authorization authorization = this.getAuthorization();
        List<String> items = recipesToItems(recipes);
        int status = this.sendItems(account, authorization, items);
        return Response.status(status).build();
    }

    private Authorization getAuthorization() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.amazon.com/auth/o2/token"))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
                .timeout(Duration.ofMinutes(1))
                .POST(HttpRequest.BodyPublishers.ofString(String.format(
                        "grant_type=%s&client_id=%s&client_secret=%s&scope=%s",
                        "client_credentials",
                        skillId,
                        skillSecret,
                        "alexa:skill_messaging")))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), Authorization.class);
    }

    private List<String> recipesToItems(List<Long> recipes) {
        return this.listService.getList(recipes).stream()
                .map(recipeIngredient -> String.format(
                        "%s (%s %s)",
                        recipeIngredient.ingredient.name,
                        recipeIngredient.quantity,
                        recipeIngredient.ingredient.unit))
                .collect(Collectors.toList());
    }

    private int sendItems(Account account, Authorization authorization, List<String> items) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://api.eu.amazonalexa.com/v1/skillmessages/users/%s", account.alexaId)))
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", authorization.accessToken))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .timeout(Duration.ofMinutes(1))
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"data\":{\"items\":%s}}", objectMapper.writeValueAsString(items))))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

}
