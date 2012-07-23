ruleset {
	ruleset('rulesets/basic.xml')
	ruleset('rulesets/braces.xml')
	ruleset('rulesets/concurrency.xml')
	ruleset('rulesets/convention.xml')
	ruleset('rulesets/design.xml') {
		AbstractClassWithoutAbstractMethod {
			doNotApplyToClassNames = 'DatastoreCleaningInterceptor, DatastoreStateAwareSpec'
		}
	}
	ruleset('rulesets/dry.xml') {
		['DuplicateStringLiteral', 'DuplicateNumberLiteral', 'DuplicateListLiteral'].each {
			"$it" {
				doNotApplyToClassNames = '*Spec'
			}
		}
	}
	ruleset('rulesets/exceptions.xml')
	ruleset('rulesets/formatting.xml')
	ruleset('rulesets/generic.xml')
	ruleset('rulesets/grails.xml')
	ruleset('rulesets/groovyism.xml') {
		UseCollectMany {
			doNotApplyToClassNames = 'LoginUrlProvider'
		}
	}
	ruleset('rulesets/imports.xml') {
		MisorderedStaticImports {
			comesBefore = false
		}
	}
	ruleset('rulesets/jdbc.xml')
	ruleset('rulesets/junit.xml')
	ruleset('rulesets/logging.xml')
	ruleset('rulesets/naming.xml') {
		MethodName {
			regex = /[a-z#].*/
		}
	}
	ruleset('rulesets/security.xml')
	ruleset('rulesets/serialization.xml')
	ruleset('rulesets/size.xml')
	ruleset('rulesets/unnecessary.xml')
	ruleset('rulesets/unused.xml')
}