# Issues and fixes

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
private Set<Order> orders;
```
```
@JsonBackReference
@ManyToOne
@JoinColumn(name = "customer_id")
private Customer customer;
```
