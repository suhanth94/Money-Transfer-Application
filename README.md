# Money Transfer Application

### Instructions to Build & Run:

Steps to run the application from IDE:

1. Import project into any IDE like Intellij using pom.xml
2. Run App from Intellij Configuration straight away
3. (Or) Go to MoneyTransferApplication File -> Right Click -> Run

From command line:

1. Build the project using pom.xml - > mvn clean install
2. Run the application -> java -jar target/revolut-1.0-SNAPSHOT.jar

### Tech/Tools:

1. Java 8
2. Spark Java  (for REST)
3. GSON (for JSON handling)
4. Junit 5 unit tests
5. Postman Client (for API test suite)

### API Endpoints:

| Command | HTTP Method | Description
| --- | --- | --- 
| `/accounts` | POST | Creates New Account (Initial Balance should be provided as input)
| `/accounts/:id` | GET | Fetches an account of the ID passed
| `/accounts` | GET | Fetches all the accounts
| `/accounts/:id/credits` | GET | Fetches all the credits of an account of the ID passed
| `/accounts/:id/debits` | GET | Fetches all the debits of an account of the ID passed
| `/transactions` | POST | Creates New Transaction (From Account ID, To Account ID, Transaction Amount are to be provided in the input payload)
| `/transactions/:id` | GET | Fetches a transaction of the ID passed
| `/transactions` | GET | Fetches all the transactions

### Sample Account Creation Request: 

```json
{
	"balance":200.45
}
```
### Sample Account Creation Response:
```json
{
    "id": 3,
    "balance": 200.45,
    "credits": [],
    "debits": []
}
```
### Sample Transaction Creation Request: 

```json
{
	"fromAccountId" :1,
	"toAccountId" :2,
	"amount": 124.25
}
```
### Sample Transaction Creation Response:
```json
{
    "id": 1,
    "amount": 124.25,
    "fromAccountId": 1,
    "toAccountId": 2,
    "timestamp": "Jan 11, 2020, 12:58:21 PM"
}
```

PS: API test suite is checked into this repo inside resources folder which can be imported onto POSTMAN.
