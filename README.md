# Reference

1) Problems when calculating hashcode/toString using Lombok for circular dependencies([Issue](https://github.com/projectlombok/lombok/issues/1007)): 
```
@EqualsAndHashCode.Exclude
@ToString.Exclude
private Set<Order> orders;
```

2) [Bidirectional circular relationships](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)

3) JSON recursive dependency (circular dependency) [Reference](http://springquay.blogspot.com/2016/01/new-approach-to-solve-json-recursive.html)
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
4) Saving ManyToOne
```
Customer customer = customerRepository.findById(customerId).orElse(null);
order.setCustomer(customer);
Set<Order> orders = customer.getOrders();
orders.add(order);
customer.setOrders(orders);
//Cascade on customer, so we just need to save customer
return new ResponseEntity<>(customerRepository.save(customer),HttpStatus.OK);
```
5) Fetching strategy <br/>
By default, the JPA ```@ManyToOne```(e.g one order has one customer) and ```@OneToOne``` annotations are fetched EAGERly, while the ```@OneToMany``` and ```@ManyToMany``` relationships are considered LAZY.(e.g customer is loaded but orders are loaded only when needed)

6) Many to Many circular dependency </br>
If we are using List, [@JsonIgnoreProperties](https://stackoverflow.com/a/60176449/12021132) </br>
We can also use ```@JsonIgnore``` to just prevent serializing of that property.
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
