package hk.org.ha.kcc.project.controller;

import java.lang.invoke.MethodHandles;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "sample", description = "Sample API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@RequestMapping(SampleApiController.BASE_URL)
public class SampleApiController {

  private static final AlsXLogger log =
      AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

  public static final String BASE_URL = "/api/v1/samples";
}
