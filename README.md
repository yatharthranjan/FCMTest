# FCM Test: Firebase Cloud Messaging Android App

This is a sample Android project to showcase the Firebase Cloud Messaging (FCM) to manage upstream and downstream messages. To be precise, it is the client project for my [XMPP Connection Server for FCM](https://github.com/carlosCharz/fcmxmppserver). The objective is to test the complete flow from the android - FCM CCS - XMPP Client Server - Android.

##What this project does

**Android**

 * Send upstream messages to XMPP Server. (It is a echo to the same device)
 * Show push notifications.


**PHP**

 * Register tokens from device clients into DB.
 * Get the list of tokens in the DB.
 * Send push notifications to registered device clients.

**Firebase**

 * Send push notifications through the Firebase console.


##Documentation
For more information must read the following documentation: 
 
* [Create new project in Firebase](https://console.firebase.google.com/)
* [Add Firebase to the Android project](https://firebase.google.com/docs/android/setup)
* [Set the FCM client app in Android](https://firebase.google.com/docs/cloud-messaging/android/client)
* [Send upstream messages](https://firebase.google.com/docs/cloud-messaging/android/upstream)
* [Receive downstream messages](https://firebase.google.com/docs/cloud-messaging/android/receive)
* [Volley library for Android](https://developer.android.com/training/volley/simple.html)


##Technologies
 * Android
 * Firebase
 * PHP
 * MySQL


##Considerations
 * Localhost for android: 10.0.2.2
 * Localhost for mysql
 * IDE: Android Studio


##Related projects
 * [Implementation of an XMPP Connection Server for FCM](https://github.com/carlosCharz/fcmxmppserver)
 * [XMPP Connection Server for FCM using the latest version of the Smack library (4.1.8)](https://github.com/carlosCharz/fcmxmppserverv2)
 * [Implementation of a base exponential back-off strategy class](https://github.com/carlosCharz/ExponentialBackOff)


##About me
I am Carlos Becerra - MSc. Softwware & Systems. You can contact me via:

* [Google+](https://plus.google.com/+CarlosBecerraRodr%C3%ADguez)
* [Twitter](https://twitter.com/CarlosBecerraRo)


_**Any improvement or comment about the project is always welcome! As well as others shared their code publicly I want to share mine! Thanks!**_

##License
```javas
Copyright 2016 Carlos Becerra

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
