# Java Pact Example

Simple example project that uses pacts with Java.

There are 2 sub projects:
1. provider - A rest service with a CustomerService world endpoint.
2. consumer - A consumer of the CustomerService world endpoint

With this project you will be able to
1. Create a CustomerConsumer using the consumer
2. Publish it to a CustomerConsumer broker
3. Verify that the provider fulfills the CustomerConsumer


## Steps

### Start a CustomerConsumer broker

In the CustomerConsumer-broker folder there is a docker compose file. 
This manages starting up a CustomerConsumer broker and a postgres DB that it requires

```sh
cd CustomerConsumer-broker
docker-compose up
```

Try navigating to localhost:80 in a browser and you should see the CustomerConsumer broker UI but no pacts... yet.

### Publish a CustomerConsumer

Consumers are always responsible for creating pacts.

A json file describing the CustomerConsumer can be generated with out example consumer by simply running the test.
This occurs because the tests are annotated with annotations provided by the CustomerConsumer JVM plugin.

The test code is actually starting a mock endpoint for the test and recording the interactions.

To create a CustomerConsumer in the consumer/build/pacts simply run:

```sh
./gradlew :consumer:test
```

You can publish the CustomerConsumer using (Note that the test intermediate step is not necessary as pactPublish depends on test):

```sh
./gradlew :consumer:pactPublish
```

If all goes well you should now see a CustomerConsumer present in the CustomerConsumer broker UI.

### Start the provider service

Ideally this would be handled as part of the verify task itself and I'll probably make this happen at some point.
However for now you will need to start up the service. Do this in a new shell tab:

```sh
./gradlew :provider:build
java -jar provider/build/libs/gs-actuator-service-0.1.0.jar
```

### Verify the CustomerConsumer

Now you can verify the CustomerConsumer against the service you have just started

Just do this:

```sh
./gradlew :provider:pactVerify
```

You should see some output saying the CustomerConsumer was verified.

## Other points

I explicitly tag the CustomerConsumer with INTEGRATION. This is just experimenting.

When fetching pacts to verify it gets the latest version with this tag. In the current setup it also verifies the latest
version regardless of tag.

Don't seem to be able to verify against versions.

In the real world these would be in separate repositories and pacts would be published and verified on the CI server.

Thanks to 'The Creative Tester' as this is heavily based on their work:
- http://the-creative-tester.github.io/Java-Consumer-Driven-Contract-Testing/
- https://github.com/the-creative-tester/consumer-driven-contract-testing-example