# IsTheApp
Open-source android spyware

Contains two apps, the child app is the client that will be installed on the victim device, the app parent is the one that will be installed on the observer device.

# Build this project
the applications work with the api of firebase with which you will have to create a project in firebase and synchronize the applications with such project.
[Firebase API](https://firebase.google.com/)

In the `build.gradle` of the app `CHILD` assign the social network package of your preference.
also you will have to recreate the view in xml of the social network

```java
ext {
       PACKAGE_CHECK_SOCIAL = "\"YOU_NETWORK_SOCIAL\""
}
```
note: it is very important that in the app child accept all the necessary permissions for the application to work properly

In the `res/values/string.xml` of the app `PARENT` assign your `APY_KEY_MAPS`

- Get the GOOGLE MAPS API KEY [here](https://developers.google.com/maps/documentation/android-api/signup)

```java
<string name="APY_KEY_MAPS">YOU_API_KEY_MAPS</string>
```

# License

Released under [Apache License 2.0](https://github.com/M1Dr05/IsTheApp/blob/master/LICENSE)

