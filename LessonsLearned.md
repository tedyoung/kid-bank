# Sept. 23, 2019 (Monday)

## Kid Money Manager, S2 E23

* Added a surround Live Template for assertThatThrownBy

* 

# Sept. 20, 2019 (Friday)

## Kid Money Manager, S2 E22

* Adding a column with Flyway migrations using ALTER TABLE ... ADD COLUMN

* Be careful about object relationships in DTOs: for integration tests, can't just instantiate other DTOs,
  they need to come from the database (e.g., TransactionDtoJpaAdapterTest)

* Not having a test for the requirement that Transactions in the database may have `null` creators

# Sept. 19, 2019 (Thursday)

## Kid Money Manager, S2 E21

* Don't write code without a failing test!

* Don't stay out on the unstable plank (being in the state of things not compiling) too long

* Predicting failing tests and how they fail is super important to ensure your mental model matches
  the actual codebase

* Equals/HashCode for domain entities!! Should just compare IDs.

# Sept. 18, 2019 (Wednesday)

## Kid Money Manager, S2 E20

* Appropriate use of debugger

* Created Live Template for autowiring component with constructor

  * File issue with JetBrains regarding the description for postfix completion items
  * Why doesn't Postfix completion have variables?

* Extracting the Principal during the OAuth2 authn process (see UserProfilePrincipalExtractor)

  * Write a blog post on this (as well as the Google Authorities)
  
  * See org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerTokenServicesConfiguration.RemoteTokenServicesConfiguration.UserInfoTokenServicesConfiguration.UserInfoTokenServicesConfiguration

* Testing the @AuthenticationPrincipal by using the `with(authentication())` and
  the `TestingAuthenticationToken`

* Use Errors instead of BindingResult where possible (thanks to hydrsd)
