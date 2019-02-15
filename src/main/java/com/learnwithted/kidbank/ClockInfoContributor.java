package com.learnwithted.kidbank;

import com.google.common.collect.ImmutableMap;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class ClockInfoContributor implements InfoContributor {

  private final Clock clock = Clock.systemDefaultZone();

  @Override
  public void contribute(Info.Builder builder) {
    ImmutableMap<Object, Object> infoMap = ImmutableMap.of(
        "ZoneID", clock.getZone(),
        "Date/Time", clock.instant()
    );
    builder.withDetail("Clock Info", infoMap);
  }
}
