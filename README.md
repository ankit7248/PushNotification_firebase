<h1> FCM Architectural Overview </h1>
<h2> FCM relies on the following set of components that build, transport, and receive messages: </h2>

<p>1. Tooling to compose or build message requests. The Notifications composer provides a GUI-based option for creating notification requests. For full automation and support for all message types, you must build message requests in a trusted server environment that supports the Firebase Admin SDK or the FCM server protocols. This environment could be Cloud Functions for Firebase, App Engine, or your own app server. </p>
<p>The FCM backend, which (among other functions) accepts message requests, performs fanout of messages via topics, and generates message metadata such as the message ID. </p>
<p>A platform-level transport layer, which routes the message to the targeted device, handles message delivery, and applies platform-specific configuration where appropriate. This transport layer includes:
</p>
<p>
 -> Android transport layer (ATL) for Android devices with Google Play services </p>
<p> -> Apple Push Notification service (APNs) for Apple devices </p>
<p>-> Web push protocol for web apps </p> 

<p> The FCM SDK on the user’s device, where the notification is displayed or the message is handled according to the app’s foreground/background state and any relevant application logic. </p>

<h2> Lifecycle flow </h2>
<p> <b>
Register devices to receive messages from FCM.</b>  An instance of a client app registers to receive messages, obtaining a registration token that uniquely identifies the app instance.</p>
<p> 
<b>Send and receive downstream messages. </b>  </p>
    <p> Send a message. The app server sends messages to the client app:</p>
    
   
 <p>1.The message is composed, either in the Notifications composer or a trusted environment, and a message request is sent to the FCM backend. </p>
    
 <p>2.When the device is online, the message is sent via the platform-specific transport layer to the device. </p>
    
 <p>3.The FCM backend receives the message request, generates a message ID and other metadata, and sends it to the platform specific transport layer. </p>
    
 <p>4.On the device, the client app receives the message or notification. </p>
