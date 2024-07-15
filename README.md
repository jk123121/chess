# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

## Sequence Diagram Link
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaaHQnU6M4ks8qRMA+W4wABmPI4fI6guFMFF4plzPUUsKYJU5TQPgQCBxIhUbINJNUpRAkLkKH5cJujIZ2llRtOxlKCg4Go9jJtVDx0t9hqdLpQboUPjAqVhwGTqW9mAdLLZgeDGqTKfDpsMuVOgIuZQiUKRUEiqktWErwONRWolxg1w6d0m5VWT3TKfqEAA1ug+1N9hHKG3suZygAmJxObrdoZisYwfuPKZD1Ij8doSerA7oDimLy+fwBaDsckwAAyEGiSQCaQyWWQ5jZR071TqJpWgMdQEjQNc9RQWYXjeDgDj-EFChbTseimSCVi+GClgwmAAXOVtQVxM0YAQF8eVhZ9X1RdFYmxUt7RjR0yQpD1aR7TcfSJWM8w5GBuV5MNtCFEV2PFYw2xzUka0VZU1QErUhJ1SDs0Y3NyxNIiUHNCBmAtK0IyjAlVKk8kwCqfRXiWNiN1GTimUdHjyn4jUsPeYTdVEsRxPUmRjKdaSlSSOSXIs2D3OUyTWR80tyg9GBXI1ABeRL4tCpYDLtaMuKYmAeQ6MA0wzQ90Dsv0osKQMYAASTQfKYDQFBkhgfdiqSJTPIyssK3wztKJ5Bsm0wZCoGi4oULQzyMMHIqxwnCZJngsaQVOecMCXFcYDXCabPuPovha2bj3m3CzE4S9vD8QIvBQdAnxfXxmHfdJMkwVbmEIjtqwqaQAFFHx++ofuaFoQNUMDugOo8Z0Qs4gU7fdoCQAAveJEnKAAeSH0HyIaephmKSPu5MKKJsBqIxOjNIY7K5RgUzWKxtBSu4gNeOczUBUUsJGYlCS-PlClAtVdUOagbVuZmqHIrbAm0B0+rLWteisvs2mOBQbhMkK4dDuZhzWfKaQNYpQxGfCjrle6uHq0oh6BoQZs8ZlparkWz652-NaYGXJxNqSHozzOzwLpvSENUfaEYAAcU3Vkns-V7PfejTPvKCoo4B4H7E3CHJfQaG2WG8oEagZHUbQDHGZx4bf008pkFiGPRlUCjoSbtRydozrqdVkyWJpRm9bUiq2ZFwT5HcnnvJVsqBZkoKx4UiedR56XRttLT6vlvSlapmfY3rtvY9hIf-RHpyRYb5gIBVOnY95nzIrnoXgpIyOb7v5uVJp-0PsjYir4wA-tnL+lskJ40Po3WO9tHbW2dqnLsUwQFqHGJUfoyCqrSFQQARkXAAZgACzTG6FMD8mQPS9nmrMbofQdAIFAKOChm4pp+z6MggAcpueaewYCNDdscHyb11q+y2mw2OqCKjoM3JgnB+CiEkL6GQ90k0qGsLoQwphowWE0I4VwvaPC+GnQvMHa8gRsBKmwNweArpMjR03CkZ6X4cjJ3bCUNOtQGhZxzsEPO4Fei6NGPw2cVsqzFwzIjFGBA0YwExr4-IoiAm7QWrjOBf9wQwHjG6dusI4A2JQO3TuWJu77xyvTAevjT7lXZBfeSnNl4Sx1lDaeRkf7+QVC-RedTgCT18d-XuUU0nETlrpRWxSWn9OLiGbJiTZjIMqY5GABZP6GB1Ik4pITgTlFyQmTIBS1CDRruvNxiCxGjBkeUXBhCToIQ9s44RCTpFYIuXIk655zqmICJYDWpEmoACkIA8jsaMQI6iQCjkTs42uCDqiUiAi0ZBudGnoDXJY4AXyoBwAgKRKAszHlBJhkXGAABIEuZcokVxiVXURqL0WYuxSsAA6iwKqgMWgACFHwKDgAAaS+Bgp5MBLkEJOoclO-9N4ACsAVoGyf8nkey0QUzGb5VpzEqTlKRUzPpZUFns3Ht0levTmkqomQFWSnSxZc2ar0tegzN7DIVvpZW4zZ7C04LCfl8yDZ8RFsg9y-KH4lNpu081vI-WrMedqlmdrtIjKdXvF1B8YAGHVTtD1jzZiQS9efRZIYPI7X9ZGsBsNQkwDlTKzcMCUlVngccgOBdBFJ3ub0A4Rj3mXQCF4NF8BuB4GatgSxhBy4OITm9KFxzvp-QBkDVoxgG3gOtq7atBExXpJAL2uE66Ez1hQIqruzqTWuq3XgayUBs3VJkMbWxwArSixLAmw9Sbj1wkHlG-WOajaa1NreosB5DrKqfhkjd6bbJvuHhez9Jtmq3vbvejebZCXPobCgA5TsjkoXxbchc3sNprkDheIAA
