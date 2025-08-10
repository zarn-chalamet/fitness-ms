package com.fitness.activity_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final WebClient userServiceWebClient;
    private final DiscoveryClient discoveryClient;

    //example using restClient
    public boolean validateUserWithRestClient(Long userId) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        URI uri = instances.get(0).getUri();

        Boolean isUserValid = restTemplate.getForObject(uri + "/api/users/" + userId +"/validate",Boolean.class);
        System.out.println("******************************************");
        System.out.println("isUserValid : "+ isUserValid);
        System.out.println("888888888888888888888888888888888888888888");

        return isUserValid;
    }


    //has error
    public boolean validateUser(Long userId) {

        try{
            System.out.println("++++++++++++++++++++++++++");
            System.out.println("blah blah");
            System.out.println("===========================");
            return userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();


        } catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new RuntimeException("User not found: "+ userId);
            else if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                throw new RuntimeException("Invalid Request ");

        } catch (Exception e){
            throw new RuntimeException("Error while validating user", e);
        }

        return false;
    }
}
