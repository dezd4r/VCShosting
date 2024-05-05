package com.example.vcshosting;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;


@Service
public class VcsHostingService {
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Repository> getRepositories(String orgLink, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String apiUrl = orgLink + "/repos";
        ResponseEntity<Repository[]> response = restTemplate.exchange(
                apiUrl, HttpMethod.GET, entity, Repository[].class);

        if (response.getBody() != null) {
            return processRepositories(response.getBody(), accessToken);
        } else {
            return new ArrayList<>();
        }
    }

    private List<Repository> processRepositories(Repository[] repositories, String accessToken) {
        List<Repository> highlightedRepos = new ArrayList<>();
        for (Repository repo : repositories) {
            String readmeUrl = repo.getUrl() + "/readme"; // Assumes GitHub API v3
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + accessToken);
            headers.set("Accept", "application/vnd.github.VERSION.raw");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<String> response = restTemplate.exchange(
                        readmeUrl, HttpMethod.GET, entity, String.class);
                String readmeContents = response.getBody();
                if (readmeContents != null && readmeContents.contains("Hello")) {
                    repo.setHighlight(true);
                }
            } catch (HttpClientErrorException.NotFound e) {
                System.out.println("README.md not found for repository: " + repo.getName());
            } catch (RestClientException e) {
                System.out.println("Failed to fetch README.md for repository: " + repo.getName() + " due to: " + e.getMessage());
            }
            highlightedRepos.add(repo);
        }
        return highlightedRepos;
    }
}