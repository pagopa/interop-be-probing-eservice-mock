package it.pagopa.interop.probing.eservice.mock.webservice;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;
import org.springframework.beans.factory.annotation.Value;
import it.pagopa.interop.probing.eservice.mock.soap.probing.ProbingRequest;
import it.pagopa.interop.probing.eservice.mock.soap.probing.ProbingResponse;
import it.pagopa.interop.probing.eservice.mock.webservice.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Endpoint
@Slf4j
public class ProbingWebService {

     @Value("${aws.kms.public.key}")
    private String PUBLIC_KEY;

    private static final String NAMESPACE_URI = "http://it/pagopa/interop/probing";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "probingRequest")
    @ResponsePayload
    public ProbingResponse handleProbing(@RequestPayload ProbingRequest request) {

        ProbingResponse resp = new ProbingResponse();

        HttpServletRequest httpRequest = ((HttpServletConnection) TransportContextHolder
                .getTransportContext()
                .getConnection())
                .getHttpServletRequest();

        String uri = httpRequest.getRequestURI();
        log.info("SOAP call received on URI: {}", uri);

        String authHeader = httpRequest.getHeader("Authorization");
        if (!SecurityUtil.checkJwtValid(authHeader, PUBLIC_KEY)) {
            log.warn("Unauthorized SOAP call");
            throw new RuntimeException("Unauthorized");
        }

        log.info("Access Authorized with SOAP");

        switch (uri) {
            case "/soap/interop/probing/ok/status":
                resp.setDescription("OK");
                resp.setStatus("200");
                log.info("SOAP call OK result");
                return resp;

            case "/soap/interop/probing/error/status":
                log.info("SOAP call ERROR result");
                throw new RuntimeException("Internal server error");

            case "/soap/interop/probing/random/status":
                boolean success = new Random().nextBoolean();
                if (success) {
                    resp.setDescription("OK");
                    resp.setStatus("200");
                    log.info("SOAP call OK result");
                    return resp;
                } else {
                    log.info("SOAP call ERROR result");
                    throw new RuntimeException("Internal server error");
                }

            default:
                log.warn("SOAP call unknown endpoint: {}", uri);
                throw new IllegalArgumentException("Unknown endpoint");
        }
    }
}
