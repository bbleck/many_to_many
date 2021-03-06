package edu.cnm.deepdive.many_to_many.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.cnm.deepdive.many_to_many.view.BaseProject;
import edu.cnm.deepdive.many_to_many.view.BaseStudent;
import java.net.URI;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Entity
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements BaseProject {

  private static EntityLinks entityLinks;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "project_id", nullable = false, updatable = false)
  private long id;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @NonNull
  @Column(nullable = false)
  private String name;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects",
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @OrderBy("name ASC")
  private List<Student> students = new LinkedList<>();

  @PostConstruct
  private void initEntityLinks() {
    String ignore = entityLinks.toString();
  }

  @Autowired
  private void setEntityLinks(EntityLinks entityLinks) {
    Project.entityLinks = entityLinks;
  }

  public long getId() {
    return id;
  }

  public Date getCreated() {
    return created;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonSerialize(contentAs = BaseStudent.class)
  public List<Student> getStudents() {
    return students;
  }

  public URI getHref() {
    return entityLinks.linkForSingleResource(Project.class, id).toUri();
  }

}



//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import edu.cnm.deepdive.many_to_many.view.BaseProject;
//import edu.cnm.deepdive.many_to_many.view.BaseStudent;
//import java.net.URI;
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;
//import javax.annotation.PostConstruct;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToMany;
//import javax.persistence.OrderBy;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import org.hibernate.annotations.CreationTimestamp;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.EntityLinks;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Component;
//
//@Entity
//@Component//this allows us to inject things into the class related to html
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class Project implements BaseProject {
//
//  private static EntityLinks entityLinks;//the only things that are eligible to be columns saved in the db are non static members
//
//  @Id//this tells jpa and hibernate this is the primary key
//  @GeneratedValue(strategy = GenerationType.AUTO)
//  @Column(name = "project_id", nullable = false, updatable = false)
//  private long id;
//
//  @CreationTimestamp
//  @Temporal(TemporalType.TIMESTAMP)
//  @Column(nullable = false, updatable = false)
//  private Date created;//hibernate will auto convert this to "created_date" so no need to add a name in the @column
//
//  @NonNull
//  @Column(nullable = false)
//  private String name;
//
//  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects",
//      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//  @OrderBy("name ASC")
//  private List<Student> students = new LinkedList<>();
//
//  @PostConstruct
//  private void initEntityLinks(){
//    String ignore = entityLinks.toString();
//  }
//
//  @Autowired
//  private void setEntityLinks(EntityLinks entityLinks){
//    Project.entityLinks = entityLinks;
//  }
//
//  //hibernate uses reflection to access things it needs, it doesn't require getters and setters like Room does
//
//  @JsonSerialize(contentAs = BaseStudent.class)
//  public List<Student> getStudents() {
//    return students;
//  }
//
//  public long getId() {
//    return id;
//  }
//
//  @Override
//  public Date getCreated() {
//    return created;
//  }
//
//  public String getName() {
//    return name;
//  }
//
//  public void setName(String name) {
//    this.name = name;
//  }
//
//  public URI getHref(){
//    return entityLinks.linkForSingleResource(Project.class, id).toUri();
//  }
//}
