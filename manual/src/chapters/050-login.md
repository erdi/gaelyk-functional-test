# Testing applications that use the default GAE authentication mechanism

For applications using the [default GAE authentication mechanism][gae-authentication] you might want to test if your application behaves as expected for logged in users and sometimes you might need to log in into the application to access certain parts of it, i.e. administration pages. This is where `LoginCategory` which provides `loginTo` method comes in handy.

## Examples

To use `loginTo` method you have to first annotate your specification with `@Use(LoginCategory)`

	@Use(LoginCategory)
	class NeedsToLoginSpec extends GebSpec {
	}

After that you can use the `loginTo` method just like [`to` method from Geb][to-method] with the exception that first too parameters are `username` of the logged in user and `isAdmin`, a switch to specify if the logged in user should have admin rights for the application.

	loginTo('foo@example.com', false, MyPage) //login a non-admin user foo@example.com and go to MyPage
	loginTo('admin@example.com', true, MyPage) //login an admin user admin@example.com and go to MyPage
	loginTo('foo@example.com', false, MyPage, 'custom parameter') //login a non-admin user foo@example.com and go to MyPage passing 'custom parameter' to convertToPath() of the page
	loginTo('foo@example.com', false, MyPage, arg: 'value') //login a non-admin user foo@example.com and go to MyPage with 'arg' appended to the query string