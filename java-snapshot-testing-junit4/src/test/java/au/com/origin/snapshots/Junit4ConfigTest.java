package au.com.origin.snapshots;

import org.junit.After;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class Junit4ConfigTest {

  @After
  public void after() {
    System.clearProperty(SnapshotConfig.JVM_UPDATE_SNAPSHOTS_PARAMETER);
  }

  @Test
  public void shouldNotUpdateSnapshotNotPassed() {
    SnapshotConfig snapshotConfig = new JUnit4Config();
    assertThat(snapshotConfig.updateSnapshot().isPresent()).isFalse();
  }

  @Test
  public void shouldUpdateSnapshotPassed() {
    System.setProperty(SnapshotConfig.JVM_UPDATE_SNAPSHOTS_PARAMETER, "example");
    SnapshotConfig snapshotConfig = new JUnit4Config();
    assertThat(snapshotConfig.updateSnapshot().get()).isEqualTo("example");
  }

}
