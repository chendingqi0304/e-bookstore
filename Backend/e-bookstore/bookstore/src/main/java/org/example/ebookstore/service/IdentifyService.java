package org.example.ebookstore.service;

public interface IdentifyService {
    Integer newCode(String emailAddress);
    Integer getCode(String emailAddress);
}
