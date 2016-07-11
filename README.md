# Spark Test
[![Build Status](https://travis-ci.org/thywen/spark-test.svg?branch=master)](https://travis-ci.org/thywen/spark-test)
[![Coverage Status](https://coveralls.io/repos/github/thywen/spark-test/badge.svg?branch=master)](https://coveralls.io/github/thywen/spark-test?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8e532ea42a214edf80f1f93348dbf44d)](https://www.codacy.com/app/sven-kroell/spark-test?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=thywen/spark-test&amp;utm_campaign=Badge_Grade)

A small test repo for the usage of spark and the deployment with heroku

# Api
I set up the api with spark it uses a local Transaction service which takes the responsibility of handling the tasks. With more time and (a bit more knowledge I would do add the folowing)

* Add Dependenci injection with spring that I can inject the Service into other clases
* Extract the routes to controllers (I need DI for this) (see add transaction controller branch for examples)
* Decouple the Transaction service that it doesn't handle the data saving

The Api marshalling and unmarshalling is done by jackson mapper because I can create and exclude specific properties

The api is unit tested as much as possible in this state.

# ApiTest
For the Api tests I used Restassured because it offers an easy way to execute calls

# Deployment and Test
The deployment is to heroku where the integration tests will run against the sven-26-staging app and I can manually promote the app to the live app sven-26-test.