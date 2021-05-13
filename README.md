# Issues and fixes

1) Problems when calculating hashcode for circular dependencies([reference](https://github.com/projectlombok/lombok/issues/1007)): Use  ```@EqualsAndHashCode.Exclude private Set<Order> orders;```
