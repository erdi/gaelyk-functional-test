# Cleaning up datastore after tests execution

If your tests need to setup some data before running or the actions performed in a test modify the datastore you obviously want to clear the changes after the test has run so that next test can start with a clean datastore. There is a `@ModifiesDatastore` [Spock][spock] extension available as a part of the library that comes to your rescue in such situations.

## Examples

### Annotating a feature

If you annotate a feature method then datastore will be cleaned up every time after your feature has been executed. See [`ModifiesDatastoreOnFeatureSpec`][modifies-datastore-on-feature] for an example.

### Annotating a non-stepwise specification

If you annotate a non-stepwise specification then datastore will be cleaned up after every feature execution in that specification. See [`ModifiesDatastoreOnSpecSpec`][modifies-datastore-on-spec] for an example.

### Annotating a stepwise specification
If you annotate a stepwise specification then datastore will be cleaned up only after the execution of the last feature in that specification. See [`ModifiesDatastoreOnStepwiseSpecSpec`][modifies-datastore-on-stepwise-spec] for an example.