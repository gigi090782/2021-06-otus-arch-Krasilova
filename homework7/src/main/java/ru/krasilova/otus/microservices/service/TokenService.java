package ru.krasilova.otus.microservices.service;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    boolean checkToken(HttpServletRequest request) throws Exception;
}
