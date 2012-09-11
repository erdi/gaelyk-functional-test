# Testing applications using the default GAE authentication mechanism with LoginCategory

For applications using the [default GAE authentication mechanism][gae-authentication] you might want to test if your application behaves as expected for logged in users and sometimes you might need to log in into the application to access certain parts of it, i.e. administration pages. This is where `LoginCategory` which provides `loginTo` method comes in handy.

## Examples

To use `loginTo` method you have to first annotate your specification with `@Mixin(LoginCategory)`

    @Mixin(LoginCategory)
	class NeedsToLoginSpec extends GebSpec {
	}

After that you can use the `loginTo` method just like [`to` method from Geb][to-method] with the exception that first too parameters are `username` of the logged in user and `isAdmin`, a switch to speify if the logged in user should have admin rights for the application.