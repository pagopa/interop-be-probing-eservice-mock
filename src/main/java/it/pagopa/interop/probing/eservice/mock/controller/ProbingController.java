package it.pagopa.interop.probing.eservice.mock.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.pagopa.interop.probing.eservice.mock.dtos.Problem;
import it.pagopa.interop.probing.eservice.mock.webservice.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/rest")
@Slf4j
public class ProbingController {

  @Value("${aws.kms.public.key}")
  private String pKey;

  @GetMapping(value = "/probing/v1", produces = "application/json")
  public ResponseEntity probingOk(@RequestHeader("Authorization") String authorizationHeader) {
    if (!SecurityUtil.checkJwtValid(authorizationHeader, pKey)) {
      log.info("Unauthorized to access");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    log.info("Access Authorized");
    log.info("REST call with OK result ");
    return ResponseEntity.ok().build();
  }

  @GetMapping(value = "/probingError/v1", produces = "application/json")
  public ResponseEntity probingWithError() {
    log.info("REST call with Error result ");
    Problem problem = Problem.builder().status(500L).detail("Internal server error").build();

    return ResponseEntity.internalServerError().body(problem);
  }

}
