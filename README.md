# IsTheApp v2.0.0
Open-source android spyware

The application is installed on the child's device as well as on the parent's device, in the login view the type of user is chosen.

# Donate
The download of the IsTheApp app is 100% free. However, developing and supporting this project is hard work and costs real money. Please help support the development of this project for future features!

<a href="https://www.paypal.com/paypalme2/midrosapps">
  <img src="https://raw.githubusercontent.com/M1Dr05/IsTheApp/master/art/paypal.png">
</a>

# Feacture
- Multiple Child clients
- Hidden app icon (stealth mode)
- Real-time location.
- Recording calls: incoming/outgoing.
- SMS: received/sent.
- Environment recording.
- Take pictures.
- Keylogger.
- Phishing social network.
- Notifications received: Whatsapp, Instagram, Messenger.

# Build this project
the application work with the api of firebase with which you will have to create a project in firebase and synchronize the application with such project.
[Firebase API](https://firebase.google.com/)

Enable the following development platforms on firebase:
`Authentication`, `realtime database` and `storage`.

- in authentication/sign-in method enable the `email` access provider

- in firebase real-time database assign the following rules:
```java
{
  "rules": {
    ".read": "auth != null",
      ".write": "auth != null"
  }
}
```

- in firebase storage assign the following rules:
```java
service firebase.storage {
  match /b/{bucket}/o {
    match /{allPaths=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

- In the `build.gradle` assign the social network package of your preference.
also you will have to recreate the view in xml of the social network
```java
ext {
       PACKAGE_CHECK_SOCIAL = "\"PHISHING-SOCIAL_NETWORK\""
}
```

In the `res/values/string.xml` assign your `APY_KEY_MAPS`

- Get the GOOGLE MAPS API KEY [here](https://developers.google.com/maps/documentation/android-api/signup)
```java
<string name="APY_KEY_MAPS">YOU_API_KEY_MAPS</string>
```

note: it is very important that accept all the necessary permissions for the application to work properly

# Pictures
<img src="https://raw.githubusercontent.com/M1Dr05/IsTheApp/master/art/login.jpeg" width="203" height="360"> <img src="https://raw.githubusercontent.com/M1Dr05/IsTheApp/master/art/maps.jpeg" width="203" height="360"> <img src="https://raw.githubusercontent.com/M1Dr05/IsTheApp/master/art/call.jpeg" width="203" height="360"> <img src="https://raw.githubusercontent.com/M1Dr05/IsTheApp/master/art/sms.jpeg" width="203" height="360"> <img src="https://raw.githubusercontent.com/M1Dr05/IsTheApp/master/art/recording.jpeg" width="203" height="360"> <img src="https://raw.githubusercontent.com/M1Dr05/IsTheApp/master/art/photo.jpeg" width="203" height="360"> <img src="https://raw.githubusercontent.com/M1Dr05/IsTheApp/master/art/keylog.jpeg" width="203" height="360"> <img src="https://raw.githubusercontent.com/M1Dr05/IsTheApp/master/art/notification.jpeg" width="203" height="360"> <img src="https://raw.githubusercontent.com/M1Dr05/IsTheApp/master/art/social.jpeg" width="203" height="360"> <img src="https://raw.githubusercontent.com/M1Dr05/IsTheApp/master/art/setting.jpeg" width="203" height="360"> 

# Disclaimer
The IsTheApp application is intended for legal and educational purposes ONLY. It is a violation of the law to install surveillance software on a mobile phone that you have no right to monitor.

IsTheApp is not responsible if the user does not follow the laws of the country and goes against it. If it is found that the user violates any law or spy in secret, he will be subject to sanctions that govern the legislation of the country.


# License

```java 
Copyright [2019] [Rafael Mercado]

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
