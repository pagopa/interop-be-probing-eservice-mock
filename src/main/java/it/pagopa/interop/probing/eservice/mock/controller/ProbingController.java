package it.pagopa.interop.probing.eservice.mock.controller;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/interop/probing/{mode}/status")
    public ResponseEntity<?> probingStatus(
        @PathVariable String mode,
        @RequestHeader("Authorization") String authorizationHeader) {

    if (!SecurityUtil.checkJwtValid(authorizationHeader, pKey)) {
      log.info("Unauthorized to access");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    log.info("Access Authorized with REST mode={}", mode);

    boolean success;

    switch (mode) {
        case "ok":
            success = true;
            break;
        case "error":
            success = false;
            break;
        case "random":
            success = new Random().nextBoolean();
            break;
        default:
            return ResponseEntity.badRequest().build();
    }

    if (success) {
        log.info("REST call OK result");
        return ResponseEntity.ok().build();
    } else {
      log.info("REST call ERROR result");
      Problem problem = Problem.builder()
              .type("about:blank")
              .title("Internal Server Error")
              .status(500)
              .detail("Internal Server Error")
              .traceId(UUID.randomUUID().toString())
              .build();

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }
  }

}
