# Setting up datastore state using the DSL provided by DataSetupCategory

The library provides a very simple DSL for setting up data in a datastore if your test need the datastore to be in a certain state to execute. The DSL is supposed to be a simplified version of [inline fixtures feature of Grails Fixtures plugin][grails-fixtures].

## Examples

To use the DSL you have to annotate your specification with `@Mixin(DataSetupCategory)` which provides the `setupData` method. Please refer to [`DataSetupCategorySpec`][data-setup-category-spec] for examples.