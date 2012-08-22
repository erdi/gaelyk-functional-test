# @ModifiesDatastore

If your tests need to setup some data before running or the actions performed in a test modify the datastore you obviously want to clear the changes after the test has run so that next test can start with a clean datastore. There is a `@ModifiesDatastore` [Spock][spock] extension available as a part of the library that comes to your rescue in such situations.