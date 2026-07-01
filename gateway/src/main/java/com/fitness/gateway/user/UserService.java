package com.fitness.gateway.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WebClient webClient;

    public Mono<Boolean> validateUser(String userId) {
        return webClient.get()
                .uri("/user/{userId}/validate", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode().value() == 404) {
                        return Mono.error(new RuntimeException("User not found: " + userId));
                    } else if (e.getStatusCode().value() == 503) {
                        return Mono.error(new RuntimeException("User Service is unavailable"));
                    } else {
                        return Mono.error(new RuntimeException(
                                "Error while validating user: " + e.getMessage()));
                    }
                });
    }

    public Mono<UserResponse> registerUser(UserRequest registerRequest) {
        return webClient.post()
                .uri("/user/register")
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new RuntimeException("BAD_REQUEST:"));
                    } else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                        return Mono.error(new RuntimeException("INTERNAL_SERVER_ERROR"));
                    } else {
                        return Mono.error(new RuntimeException(
                                "Error while validating user: " + e.getMessage()));
                    }});
    }

}