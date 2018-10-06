# IsTheApp
Open-source android spyware

Contains two apps, the child app is the client that will be installed on the victim device, the app parent is the one that will be installed on the observer device.

# Feacture
- Real-time location.
- Recording calls.
- SMS.
- Environment recording.
- Take pictures.
- Keylogger.
- Phishing network social.

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

```java 
Copyright [2018] [Rafael Mercado]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
