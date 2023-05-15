package it.pagopa.interop.probing.eservice.mock.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.pagopa.interop.probing.eservice.mock.dtos.Problem;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/rest")
@Slf4j
public class ProbingController {

  @GetMapping(value = "/probing/v1", produces = "application/json")
  public ResponseEntity probingOk() {
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
