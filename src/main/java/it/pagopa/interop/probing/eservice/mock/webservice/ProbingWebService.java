package it.pagopa.interop.probing.eservice.mock.webservice;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.addressing.server.annotation.Action;
import it.pagopa.interop.probing.eservice.mock.soap.probing.ProbingRequest;
import it.pagopa.interop.probing.eservice.mock.soap.probing.ProbingResponse;
import lombok.extern.slf4j.Slf4j;

@Endpoint
@Slf4j
public class ProbingWebService {

  private static final String NAMESPACE_URI = "http://it/pagopa/interop/probing";

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "probingRequest")
  @Action("soap/interop/probing")
  @ResponsePayload
  public ProbingResponse probing(@RequestPayload ProbingRequest probingRequest) {
    ProbingResponse resp = new ProbingResponse();

    log.info("SOAP call with OK result");
    resp.setDescription("OK");
    resp.setStatus("200");
    return resp;
  }

}

