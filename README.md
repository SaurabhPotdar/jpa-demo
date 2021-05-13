# Issues and fixes

1) Problems when calculating hashcode for circular dependencies: Use  ```@EqualsAndHashCode.Exclude private Set<Order> orders;```
