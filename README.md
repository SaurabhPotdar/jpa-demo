# Reference

[Documentation](https://docs.jboss.org/hibernate/orm/5.1/userguide/html_single/Hibernate_User_Guide.html#associations-many-to-one)

[Multiple Datasources](https://stackoverflow.com/a/48140893/12021132)

1. Problems when calculating hashcode/toString using Lombok for circular dependencies([Issue](https://github.com/projectlombok/lombok/issues/1007)): 
```
@EqualsAndHashCode.Exclude
@ToString.Exclude
private Set<Order> orders;
```

2. [Bidirectional circular relationships](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)

3. Many to One recursive dependency (circular dependency) [Reference](http://springquay.blogspot.com/2016/01/new-approach-to-solve-json-recursive.html)
```
@JsonManagedReference //Parent
@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
@EqualsAndHashCode.Exclude
@ToString.Exclude
private Set<Order> orders;
```
```
@JsonBackReference
@ManyToOne
@JoinColumn(name = "customer_id")
private Customer customer;
```
Saving ManyToOne
```
Customer customer = customerRepository.findById(customerId).orElse(null);
order.setCustomer(customer);
Set<Order> orders = customer.getOrders();
orders.add(order);
customer.setOrders(orders);
//Cascade on customer, so we just need to save customer
return new ResponseEntity<>(customerRepository.save(customer),HttpStatus.OK);
```
4. Fetching strategy <br/>
By default, the JPA ```@ManyToOne```(e.g one order has one customer) and ```@OneToOne``` annotations are fetched EAGERly, while the ```@OneToMany``` and ```@ManyToMany``` relationships are considered LAZY.(e.g customer is loaded but orders are loaded only when needed)

5. Many to Many circular dependency </br>
Use [@JsonIgnoreProperties](https://stackoverflow.com/a/60176449/12021132) </br>
We can also use ```@JsonIgnore``` to just prevent serializing of that property. </br>
Make sure to use ```@EqualsAndHashCode.Exclude``` for Set
```
@JsonIgnoreProperties("students")
@ManyToMany(mappedBy = "students")
@EqualsAndHashCode.Exclude
@ToString.Exclude
private Set<Course> courses = new HashSet<>();
```
```
@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
@JsonIgnoreProperties("courses")
@EqualsAndHashCode.Exclude
@ToString.Exclude
//Create a new table "student_course" with one colm from Course and one from Student
//This table will keep track of many to many
@JoinTable(name="student_course", joinColumns = {@JoinColumn(name="course_id")}, inverseJoinColumns = {@JoinColumn(name="student_id")})
private Set<Student> students = new HashSet<>();
```
6. [JPA queries](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)</br>
findBy, getBy, queryBy, readBy all generate the same criteria query. removeBy, deleteBy is also same [Reference](https://stackoverflow.com/questions/39869707/what-is-the-difference-between-query-methods-find-by-read-by-query-by-and-get)

7. @Transactional</br>
 Changes are saved to the database only if all the instructions are executed in the method. Rollback any changes if an error occurs.</br>
 We can annotate individual methods or the entire class(all methods run with @Transactional).
```
@Transactional
public void addCourse(Course course) {
}
```
8. [getOne vs findById](https://www.javacodemonk.com/difference-between-getone-and-findbyid-in-spring-data-jpa-3a96c3ff)
9. [Pagination and Sorting](https://www.baeldung.com/spring-data-jpa-pagination-sorting)
