# 🤖 Doctor Quarantino
Dr Quarantino is a slackbot that asks random conversations starter questions on a schedule. It was built as a remote team bonding tool in early quarantine, and has been much-loved by my team since them. If your team needs some help with breaking the ice or building social bonds remotely, I highly recommend adding Dr Quarantino to your slack.

## Usage Tips
Over the years, we've tried a few different things with Dr Quarantino. This is what I've learned
* Have a dedicated slack channel for it (and general social banter) rather than reusing a team's main channel lets people mute the channel if they wish, and lets others post freely without worrying they're being too "noisy"
* Have the right size of channel. For my team, this meant inviting our tribe our around 30 people. Too few people means there's not enough energy in the room to get consistent engagement, and too many means shyer people won't contribute as much, and it will become the same handful of people posting while everyone else watches. As an added bonus, having a tribe-level group helps build connections between people on different teams, which is usually harder to build than connections within teams.
* Give it a face and a name, and over time a personality will develop. Half the fun is everyone laughing about what the Doctor is planning when he asks something weird. There's a pack of emojis in the `emojis` directory that can be imported to slack to help with this

## Technical overview
Dr Quarantino is a very simple app. Based on a `@Schedudule(cron)` annotation, it will pick a random question from a PostgreSQL database, and submit it to a slack webhook using an HTTP call. After asking a question, the questions `last_asked` is updated so that when selecting a question, it can avoid asking the same question twice in a short window of time.

Dr Quarantino also has a very simple question submission frontend and a Spring API that lets anyone submit questions to the database. Dr Quarantino can be configured to prefer use-submitted questions over bulk-imported ones.


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
