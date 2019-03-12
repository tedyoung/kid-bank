
## Step 1

From https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security-oauth2

Include the `spring-security-oauth2-client` starter

* Kid Money Manager is the Client
* Google is the Provider

For Google, the provider info is predefined and configured like this:

```
spring.security.oauth2.client.registration.my-client.client-id=abcd
spring.security.oauth2.client.registration.my-client.client-secret=password
spring.security.oauth2.client.registration.my-client.provider=google

-- Provider client credentials
spring.security.oauth2.client.registration.google.client-id=${com.google.clientId}
spring.security.oauth2.client.registration.google.client-secret=${com.google.clientSecret}
```

Tell Google our "authorized redirect URI" is something like http://localhost:8081/login/oauth2/client/google

## Questions

* What's a "Resource Server" as per https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security-oauth2-server


## Resources

* https://www.baeldung.com/spring-security-5-oauth2-login
