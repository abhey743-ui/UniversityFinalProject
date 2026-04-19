package ApiGatewayService.Controller;

import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    public Controller(ReactiveOAuth2AuthorizedClientService clientService) {
        this.clientService = clientService;
    }

    private final ReactiveOAuth2AuthorizedClientService clientService;

    @GetMapping("/testing/token")
    public Mono<Map<String, Object>> tokenInfo(
            @AuthenticationPrincipal OidcUser oidcUser,
            OAuth2AuthenticationToken authToken) {

        return clientService
                .loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName())
                .map(client -> {

                    Map<String, Object> tokens = new HashMap<>();

                    tokens.put("id_token", oidcUser.getIdToken().getTokenValue());
                    tokens.put("access_token", client.getAccessToken().getTokenValue());
                    tokens.put("expires_at", client.getAccessToken().getExpiresAt());
                    tokens.put("scopes", client.getAccessToken().getScopes());
                    tokens.put("claims", oidcUser.getClaims());

                    return tokens;
                });
    }
    @GetMapping("/omg")
    public String test(){
        return "test";
    }


}
