# Beach Bar API
The beach bar API allows you to order your favorite beverages via SMS.

This example shows how to use [YaaS services](https://www.yaas.io/) to read SMS messages and send an automated reply using the [arvato SMS service](http://devportal.arvatosystems.io/sms/index.html). Additionally new orders are posted to the hybris Order service.

## Setup and configuration
If you are new to YaaS, [start here](https://devportal.yaas.io/gettingstarted/) to understand the basics of registering and setting up a project. If you plan to run and/or import the code to your IDE, it's probably helpful to also read about the [YaaS service SDK](https://devportal.yaas.io/tools/servicesdk/index.html) and its plugins.


### Your Builder project
To run this service you will need to setup a new YaaS project using the [Builder](https://builder.yaas.io/). Your project needs to subscribe to the following packages:
* arvato SMS service
* hybris Events (for PubSub)
* hybris Commerce service (for order service)

You also need to create a new application in order to receive `clientId` and `clientSecret`.

### SMS service
To be able to send and receive SMS messages, you need to first purchase a phone number. Please refer to the arvato [SMS service documentation](http://devportal.arvatosystems.io/sms/index.html) for further details.

When purchasing the number make sure that the `publishToQueue` flag is set to true. (Or use the Update API to do it at a later time). This will publish incoming SMS messages to the Pubsub service.

### Beach Bar config
Copy the template config from `/src/main/resources/default.sample.properties` to `/src/main/resources/default.properties`.

Insert the `TENAT` (id of your project), `CLIENT_ID` and `CLIENT_SECRET`.

### Build and Run
Build using the following command: `mvn install`. Note: As of now the YaaS SDK requires you to be online during the build. This is because it downloads the API specifications in order to generate the YaaS web service clients.

Run the web server with `mvn jetty:run`.

Start a conversation with the Beach Bar by sending a message to the number you've purchased.

## How it works
The [arvato SMS service](http://devportal.arvatosystems.io/sms/index.html) is used to receive incoming SMS messages from customers. It is configured to publish new messages to the [YaaS PubSub service](https://devportal.yaas.io/services/pubsub/latest/index.html) under the `sms_incoming` event topic.

The Beach Bar API polls the PubSub service to receive new incoming messages. A simple State Machine based on [EasyFlow](http://datasymphony.com.au/open-source/easyflow) is used to manage the state for each customer (based on their phone number). Depending on the state, the service either sends a welcome message, a message to confirm the order or the order confirmation.

The API has to be able to identify the customer's request and to select the right product to order. This is implemented via very basic String matching and a few rules using [Easy Rules](http://www.easyrules.org/).

For the future I plan to integrate the upcoming arvato Rules Service and perhaps a more sophisticated text recognition implementation.
