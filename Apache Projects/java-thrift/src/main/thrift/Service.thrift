namespace java com.sapient.learning
 
exception HelloException {
    1: i32 code,
    2: string description
}
 
struct HelloResource {
    1: i32 id,
    2: string name,
    3: optional string salutation
}
 
service HelloService {
 
    HelloResource get(1:i32 id) throws (1:HelloException e),
 
    void save(1:HelloResource resource) throws (1:HelloException e),
 
    list <HelloResource> getList() throws (1:HelloException e),
 
    bool ping() throws (1:HelloException e)
}