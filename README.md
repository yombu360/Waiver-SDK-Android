# Waiver-SDK-Android

1. Add the jitpack.io maven repository to project level Gradle file

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the gradle implementation for the library in application level Gradle file

```implementation 'com.github.yombu360:Waiver-SDK-Android:0.1.4-alpha'```

3. The main class YombuWaiver can be used to handle all the task related to waiver part. In order to use this library the app needs to be initialized first using the following method:
```
YombuWaiver.getInstance().initialize(
    RESELLER_ID,
    API_KEY,
    MERCHANT_ID,
    LOCATION_ID,
    YombuInitializationCallback
);
```
You can get RESELLER_ID, API_KEY, MERCHANT_ID, LOCATION_ID by contacting Yombu at support@yombu.com. The initialization callback is an interface available in com.yombu.waiverlibrary.callbacks.YombuInitializationCallback which has two methods (i) `onInitializationSuccess()` -> Called if initialization of application succeeds and (ii) `onInitializationFailure(String message)` -> Called if initialization of the application fails with error message as a parameter.

4. Once the device is initialized, the library can be used to pass the required data for the processing of waiver as well as to load and display waiver screen.

5. The required data including first_name, last_name, and more can be passed for processing by using YombuWaiver's instance. The data can be set by using Builder's design pattern like below:
```
YombuWaiver.getInstance()
    .addString(YombuWaiver.Key.FIRST_NAME, "Yombu")
    .addString(YombuWaiver.Key.LAST_NAME, "Team")
    .addString(YombuWaiver.Key.PHONE, "1234567890")
    .addString(YombuWaiver.Key.EMAIL, "support@yombu.com");
```

There are different methods available for setting and clearing different types of data in the YombuWaiver class like below:
- `addString(KEY: String, VALUE: String)` => Sets the `VALUE` of type String that will be sent to the server using the key `KEY`.
- `addDate(KEY: String, VALUE: Date)` => Sets the `VALUE` of type Date that will be sent to the server using the key `KEY`.
- `addGender(KEY: String, VALUE: YombuWaiver.Gender)` => Sets the `VALUE` of type YombuWaiver.Gender that will be sent to the server using the key `KEY`.
- `addList(KEY: String, VALUE: List<String>)` => Sets the `VALUE` of type List<String> that will be sent to the server using the key `KEY`.
- `addMinorsList(MINORS: List<YombuWaiver.Minor>)` => Adds the `MINORS` of type List<YombuWaiver.Minor> that will be sent to the server.
- `addMinor(MINOR: YombuWaiver.Minor)` => Adds the `MINOR` of type YombuWaiver.Minor that will be sent to the server.
- `clearMinors()` => Clears all the minors that has been added by using `addMinorsList(MINORS: List<YombuWaiver.Minor>)` or `addMinor(MINOR: YombuWaiver.Minor)` methods.
- `clearAllData()` => Clears all the data that has been added by using any of the methods mentioned above.

YombuWaiver.Minor is a class designed to hold all the information related to a minor. The signature constructor of the YombuWaiver.Minor class is:
`Minor(FIRST_NAME: String, LAST_NAME: String, BIRTHDAY: Date)`
First name, last name and birthday are the required parameters for creating any minor's instance. To add any other information related to minor following signature methods from the YombuWaiver.Minor class can be used:
- `addString(KEY: String, VALUE: String)` => Sets the `VALUE` of type String that will be sent to the server using the key `KEY` for current minor.
- `addDate(KEY: String, VALUE: Date)` => Sets the `VALUE` of type Date that will be sent to the server using the key `KEY` for current minor.
- `addGender(KEY: String, VALUE: YombuWaiver.Gender)` => Sets the `VALUE` of type YombuWaiver.Gender that will be sent to the server using the key `KEY` for current minor.
- `addList(KEY: String, VALUE: List<String>)` => Sets the `VALUE` of type List<String> that will be sent to the server using the key `KEY` for current minor.
- `clearMinorData()` => Clears all the data for the current minor that has been added by using any of the methods mentioned above from the YombuWaiver.Minor class.
As first name, last name and birthday are set at the time of creating an instance of YombuWaiver.Minor. There are some methods to update them as below:
- `setFirstName(FIRST_NAME: String)` => Updates the `FIRST_NAME` of current minor.
- `setLastName(LAST_NAME: String)` => Updates the `LAST_NAME` of current minor.
- `setBirthday(BIRTHDAY: Date)` => Updates the `BIRTHDAY` of current minor.

There are also some of the standard keys for setting common data that needs to sent to the server. They are inside YombuWaiver.Key class as below:
- `MINDBODY_ID` => Used to set Mindbody ID of the customer.
- `FIRST_NAME` => Used to set First Name of the customer.
- `LAST_NAME` => Used to set Last Name of the customer.
- `PHONE` => Used to set Phone of the customer.
- `EMAIL` => Used to set Email of the customer.
- `BIRTHDAY` => Used to set Birthday of the customer.
- `GENDER` => Used to set Gender of the customer.
- `ADDRESS` => Used to set Address of the customer.
- `CITY` => Used to set City of the customer.
- `STATE` => Used to set State of the customer.
- `ZIP` => Used to set Postal Code/Zip Code/Region Code of the customer.
- `COUNTRY` => Used to set Country of the customer. The proper way to set country is using 2 letters shortcode of the country. For example, `US` for `United States of America` and `CA` for Canada
- `GUARDIAN_FIRST_NAME` => Used to set Guardian's First Name of the customer. It is required if the `FIRST_NAME` and `LAST_NAME` belongs to the child.
- `GUARDIAN_LAST_NAME` => Used to set Guardian's Last Name of the customer. It is required if the `FIRST_NAME` and `LAST_NAME` belongs to the child.
- `EMERGENCY_CONTACT_FIRST_NAME` => Used to set Emergency Contact First Name for the customer.
- `EMERGENCY_CONTACT_LAST_NAME` => Used to set Emergency Contact Last Name for the customer.
- `EMERGENCY_CONTACT_PHONE` => Used to set Emergency Contact Phone for the customer.
- `REFERRAL` => Used to set Referral for the customer.

### NOTE: First Name and Last Name must be provided for signing the waiver successfully.

6. To display the waiver following method can be called which presents the waiver screen to the customer for signing it and processing.
```
YombuWaiver.getInstance().displayWaiver(YombuWaiverProcessingCallback);
```
Waiver processing callback is an interface available in com.yombu.waiverlibrary.callbacks.YombuWaiverProcessingCallback which has two methods (i) `onWaiverSuccess(String documentId)` -> Called if the processing of waiver is successful with a documentId as a parameter and (ii) `onWaiverFailure(String message)` -> Called if waiver processing fails with an error message as a parameter.
