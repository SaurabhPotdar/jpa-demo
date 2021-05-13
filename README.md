# Reference

1) Problems when calculating hashcode/toString using Lombok for circular dependencies([Issue](https://github.com/projectlombok/lombok/issues/1007)): 
```
@EqualsAndHashCode.Exclude
@ToString.Exclude
private Set<Order> orders;
```

2) JSON recursive dependency (circular dependency) [Reference](http://springquay.blogspot.com/2016/01/new-approach-to-solve-json-recursive.html)
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
3) Saving ManyToOne
```
Customer customer = customerRepository.findById(customerId).orElse(null);
order.setCustomer(customer);
Set<Order> orders = customer.getOrders();
orders.add(order);
customer.setOrders(orders);
//Cascade on customer, so we just need to save customer
return new ResponseEntity<>(customerRepository.save(customer),HttpStatus.OK);
```
4) Fetching strategy <br/>
By default, the JPA ```@ManyToOne```(e.g one order has one customer) and ```@OneToOne``` annotations are fetched EAGERly, while the ```@OneToMany``` and ```@ManyToMany``` relationships are considered LAZY.(e.g customer is loaded but orders are loaded only when needed)

