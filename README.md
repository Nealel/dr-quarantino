# Doctor Quarantino



## Setting up

1. Install and create a postgresdatabase, and run the setup from `db-setup.sql` file
2. Load the database with questions. You can use the questions.txt file from this repo, or your own.
3. Optionally, load up some recurring questions. These will be asked at the same time each week. Use the recurringQuestions.txt file, or create your own. The 'slot' value assumes the bot will ask questions Mon-Fri, AM and PM, with Monday morning being slot 1 and Friday Afternoon being slot 10.
4. set the values in src/main/resources/application.yml for your desired setup. For example

```
app:
    schedule: 0 30 8,13 * * MON-FRI   ## a cron that defines when questions should be asked. In this example, Mon to Fri at 8.30 and 13.30

slack:
    webhook.url: https://hooks.slack.com/services/FOOBAR....   ## a webhook URL provided by your slack workspace. Info on how to get this found on google

spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:postgresql://localhost:5432/doctordb    ## URL of your db
    username: doc    ## your db's username
    password: pwd   ## your db's password
    driver-class-name: org.postgresql.Driver
```

5. to run the app: `cd ~/path/to/dr-quarantino/ && mvn spring-boot:run &> ~/path/to/dr-quarantino/doc.log &`
