package fida;

import fida.domain.task.TaskStatus;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class TaskResourceTest {
    @Test
    void testTaskStatusesEndpoint() {
        List<TaskStatus> statuses = List.of(TaskStatus.values());
        ArrayList body = given()
                .when().get("/tasks/statuses")
                .then()
                .statusCode(200).extract().body().as(ArrayList.class);
        assertThat(statuses.containsAll(body));
    }
}