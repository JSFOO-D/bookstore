# Getting Started Bookstore demo

### Guides
The following guides illustrate how to use some features concretely:

* Import project as existing maven project.
* Right click on pom.xml and click Add as Maven project
* Click the play button on the toolbar where it says BookstoreApplication
* Import postman collections attached.
* * Note that in the postman collection the jwt session token will expire after 24hrs.


### APIs created

* localhost:8081/auth/signup
* POST
* * used for creating users

* localhost:8081/auth/login
* POST
* * used for logging in to get jwt session token
* * Super admin credentials pre created ("email" : "super.admin@email.com", "password" : "123456")

* localhost:8081/users/me
* GET
* * Retrieve current logged in user profile
* * only authenticated users and hit (Authenticated = jwt session verified)

* localhost:8081/users
* GET
* * Retrieve all user profiles 
* * only admin and super admin can use this function - login as super admin and attach the jwt session to respective api calls

* localhost:8081/books/create
* POST
* * Create book
* * only authenticated users and hit (Authenticated = jwt session verified)

* localhost:8081/books
* GET
* * list all books
* * only authenticated users and hit (Authenticated = jwt session verified)

* localhost:8081/books/update
* POST
* * update existing book records (excluding authors)
* * only authenticated users and hit (Authenticated = jwt session verified)

* localhost:8081/books/search
* GET
* * search existing book records by title or/and author name
* * only authenticated users and hit (Authenticated = jwt session verified)

* localhost:8081/books/delete
* POST
* * delete books by ISBN
* * only super admin can use this function - use this api localhost:8081/auth/login as super admin and attach the jwt session to respective api calls