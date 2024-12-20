package org.baylist.controller.todoist;

import lombok.AllArgsConstructor;
import org.baylist.dto.todoist.api.Label;
import org.baylist.dto.todoist.api.Project;
import org.baylist.dto.todoist.api.Section;
import org.baylist.dto.todoist.api.Task;
import org.baylist.exception.TodoistApiException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.baylist.util.convert.InputStreamConverter.inputStreamToString;
import static org.baylist.util.convert.ToJson.fromJsonList;
import static org.baylist.util.convert.ToJson.toJson;

@Controller
@AllArgsConstructor
public class TodoistController implements Todoist {

    //todo механизм ретраев неудачных запросов
    private static final String PROJECT_METHOD = "projects";
    private static final String TASK_METHOD = "tasks";
    private static final String SECTION_METHOD = "sections";
    private static final String LABEL_METHOD = "labels";

    private static final String PROJECT_ID_PARAM = "project_id";
    private static final String SECTION_ID_PARAM = "section_id";
    private static final String LABEL_PARAM = "label";

    private final RestClient restClient;

    //region GET

    @Override
    public List<Project> getProjects() { // done
        return restClient
                .get()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(PROJECT_METHOD)
                        .build()
                        .toUriString())
                .exchange((request, response) -> {
                    if (response.getStatusCode().equals(HttpStatus.OK)) {
                        return fromJsonList(inputStreamToString(response.getBody()), Project.class);
                    } else {
                        throw new TodoistApiException(response);
                    }
                });
    }

    @Override
    public Project getProject(Long index) { //done
        return restClient
                .get()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(PROJECT_METHOD)
                        .pathSegment(String.valueOf(index))
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .body(Project.class);
    }

    @Override
    public List<Task> getTasks() { //done - check
        return restClient
                .get()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(TASK_METHOD)
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public List<Task> getTasksByProject(Long index) {
        return restClient
                .get()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(TASK_METHOD)
                        .queryParam(PROJECT_ID_PARAM, index)
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public List<Task> getTasksBySection(Long index) {
        return restClient
                .get()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(TASK_METHOD)
                        .queryParam(SECTION_ID_PARAM, index)
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public List<Task> getTasksByLabel(String label) {
        return restClient
                .get()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(TASK_METHOD)
                        .queryParam(LABEL_PARAM, label)
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public List<Section> getSections() {
        return restClient
                .get()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(SECTION_METHOD)
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public List<Section> getSectionsByProject(Long index) {
        return restClient
                .get()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(SECTION_METHOD)
                        .queryParam(PROJECT_ID_PARAM, index)
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public List<Label> getLabels() {
        return restClient
                .get()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(LABEL_METHOD)
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .body(new ParameterizedTypeReference<>() {
                });
    }

    //endregion GET

    //region CREATE

    @Override
    public Project createProject(Project project) {
        return restClient
                .post()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(PROJECT_METHOD)
                        .build()
                        .toUriString())
                .body(toJson(project))
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .toEntity(Project.class)
                .getBody();
    }


    @Override
    public Section createSection(Section section) {
        return restClient
                .post()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(SECTION_METHOD)
                        .build()
                        .toUriString())
                .body(toJson(section))
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .toEntity(Section.class)
                .getBody();
    }


    @Override
    public Task createTask(Task task) {
        return restClient
                .post()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(TASK_METHOD)
                        .build()
                        .toUriString())
                .body(toJson(task))
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .toEntity(Task.class)
                .getBody();
    }

    //endregion CREATE

    //region DELETE

    @Override
    public void deleteProject(Long projectId) {
        restClient
                .delete()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(PROJECT_METHOD)
                        .pathSegment(String.valueOf(projectId))
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .toBodilessEntity();
    }

    @Override
    public void deleteSection(Long sectionId) {
        restClient
                .delete()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(SECTION_METHOD)
                        .pathSegment(String.valueOf(sectionId))
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .toBodilessEntity();
    }


    @Override
    public void deleteTask(Long taskId) {
        restClient
                .delete()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(TASK_METHOD)
                        .pathSegment(String.valueOf(taskId))
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .toBodilessEntity();
    }

    //endregion DELETE

    //region UPDATE

    @Override
    public void updateProject(Project project) {
        restClient
                .post()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(PROJECT_METHOD)
                        .pathSegment(String.valueOf(project.getId()))
                        .build()
                        .toUriString())
                .body(toJson(project))
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .toBodilessEntity();
    }

    @Override
    public void updateSection(Section section) {
        restClient
                .post()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(SECTION_METHOD)
                        .pathSegment(String.valueOf(section.getId()))
                        .build()
                        .toUriString())
                .body(toJson(section))
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .toBodilessEntity();
    }

    @Override
    public void updateTask(Task task) {
        restClient
                .post()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(TASK_METHOD)
                        .pathSegment(String.valueOf(task.getId()))
                        .build()
                        .toUriString())
                .body(toJson(task))
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .toBodilessEntity();

    }

    //endregion UPDATE

    @Override
    public void closeTask(Long taskId) {
        restClient
                .post()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(TASK_METHOD)
                        .pathSegment(String.valueOf(taskId))
                        .pathSegment("close")
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .toBodilessEntity();
    }

    @Override
    public void reopenTask(Long taskId) {
        restClient
                .post()
                .uri(UriComponentsBuilder
                        .fromPath("")
                        .pathSegment(TASK_METHOD)
                        .pathSegment(String.valueOf(taskId))
                        .pathSegment("reopen")
                        .build()
                        .toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new TodoistApiException(response);
                }))
                .toBodilessEntity();
    }

}
