package edu.cnm.deepdive.many_to_many.controller;


import edu.cnm.deepdive.many_to_many.model.dao.ProjectRepository;
import edu.cnm.deepdive.many_to_many.model.dao.StudentRepository;
import edu.cnm.deepdive.many_to_many.model.entity.Project;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController//we are not specifying the views somewhere else--a controller that doesn't have additional views that we need to pass through
@ExposesResourceFor(Project.class)
@RequestMapping("/projects")//begin with some base url and then "/projects", everything here will be relative to /projects
public class ProjectController {

  private ProjectRepository projectRepository;//the current way to do injection in spring is to let the constructor be autowired
  private StudentRepository studentRepository;

  @Autowired//autowired does not need to be explicit in constructors, it is assumed to be there in constructors in spring
  public ProjectController(ProjectRepository projectRepository, StudentRepository studentRepository){
    this.projectRepository = projectRepository;
    this.studentRepository = studentRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Project> list(){
    return projectRepository.findAllByOrderByNameAsc();//how does this get returned as a json? spring looks in its pathway and will find jackson, and jackson will turn it into a json
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Project> post(@RequestBody Project project){
    projectRepository.save(project);//this saves it into the db, because we extended the crudrepository and now we are invoking it
    return ResponseEntity.created(project.getHref()).body(project);//.created gives us a 201 response.  There needs to be a location header, and a body with the representation of that address. The save method puts this body into the project object.
  }

  @GetMapping(value = "{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)//will now automatically look at /projects/something (/projects from annotation above)
  public Project get(@PathVariable("projectId") Long projectId){//@PathVariable is what links into the value variable name above.  Spring parses the long for us here.
    return projectRepository.findById(projectId).get();//this could throw an exception if there is no element
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound(){
    //do nothing on purpose
  }


}
