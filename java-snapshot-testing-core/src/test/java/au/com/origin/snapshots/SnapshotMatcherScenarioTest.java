package au.com.origin.snapshots;

import au.com.origin.snapshots.config.BaseSnapshotConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SnapshotMatcherScenarioTest {

  private static final String FILE_PATH =
      "src/test/java/au/com/origin/snapshots/__snapshots__/SnapshotMatcherScenarioTest.snap";

  @BeforeAll
  static void beforeAll() {
    SnapshotMatcher.start(new BaseSnapshotConfig());
  }

  @AfterAll
  static void afterAll() throws IOException {
    SnapshotMatcher.validateSnapshots();
    File f = new File(FILE_PATH);
    assertThat(String.join("\n", Files.readAllLines(f.toPath())))
        .isEqualTo(
            "au.com.origin.snapshots.SnapshotMatcherScenarioTest.should1ShowSnapshotSuccessfully[Scenario A]=[\n"
                + "any type of object\n"
                + "]\n\n\n"
                + "au.com.origin.snapshots.SnapshotMatcherScenarioTest.should2SecondSnapshotExecutionSuccessfully[Scenario B]=[\n"
                + "any second type of object\n"
                + "any third type of object\n"
                + "]");
    Files.delete(Paths.get(FILE_PATH));
  }

  @Test
  void should1ShowSnapshotSuccessfully() throws IOException {
    SnapshotMatcher.expect("any type of object").scenario("Scenario A").toMatchSnapshot();
    File f = new File(FILE_PATH);
    if (!f.exists() || f.isDirectory()) {
      throw new RuntimeException("File should exist here");
    }
  }

  @Test
  void should2SecondSnapshotExecutionSuccessfully() throws IOException {
    SnapshotMatcher.expect("any second type of object", "any third type of object")
            .scenario("Scenario B")
            .toMatchSnapshot();
    File f = new File(FILE_PATH);
    if (!f.exists() || f.isDirectory()) {
      throw new RuntimeException("File should exist here");
    }
  }
}
