package ru.krasilova.otus.microservices.service;


import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.microservices.exeptions.IdempotenceKeyConflictException;
import ru.krasilova.otus.microservices.exeptions.IdempotenceKeyMissingException;

@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);
    private final VersionService versionService;

    @Autowired
    public TokenServiceImpl(VersionService versionService) {
        this.versionService = versionService;
    }

    @Override
    public boolean checkToken(HttpServletRequest request) {

        String versionOut = request.getHeader("x-request-id");
        if (versionOut == null) {
            throw new IdempotenceKeyMissingException();
        }
        var versionInOpt = versionService.find();
        String versionIn;
        if (versionInOpt.isPresent()){
            versionIn = String.valueOf(versionInOpt.get().getVersion());
        } else {
            versionIn = "0";
        }
        if (!versionOut.equals(versionIn)){
            throw new IdempotenceKeyConflictException();
        }

        return true;
    }
}
