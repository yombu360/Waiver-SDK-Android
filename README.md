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

```implementation 'com.github.yombu:waiverlibrary:0.1.1-alpha'```

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

5. The required data including name, phone, email, address, and more can be passed for processing by using YombuWaiver's instance. The data can be set by using Builder's design pattern like below:
```
YombuWaiver.getInstance()
    .setName("Yombu", "Team")
    .setPhone("1234567890")
    .setEmail("support@yombu.com");
```

There are different methods available for setting and clearing data in the YombuWaiver class like below:
- `setName(FIRST_NAME: String, LAST_NAME: String)` => Sets first name and last name of the customer.
- `setPhone(PHONE: String)` => Sets phone of the customer.
- `setEmail(EMAIL: String)` => Sets email of the customer.
- `setDateOfBirth(DATE_OF_BIRTH: Date)` => Sets date of birth of the customer.
- `setGender(GENDER: YombuWaiver.Gender)` => Sets gender of the customer.
- `setAddress(ADDRESS: String, CITY: String, STATE: String, POSTAL_CODE: String, COUNTRY_SHORT_CODE: String)` => Sets the address of the customer including address, city, state, zip code, and country. The country can be provided in 2 digits shorthand code like the US for the United States of America and CA for Canada.
- `setGuardianName(GUARDIAN_FIRST_NAME: String, GUARDIAN_LAST_NAME: String)` => Sets the guardian name of the customer. This is required if the customer is below 18 years of age. So, the waivers can be signed by the guardian.
- `setEmergencyContact(EMERGENCY_CONTACT_FIRST_NAME: String, EMERGENCY_CONTACT_LAST_NAME: String, EMERGENCY_CONTACT_PHONE: String)` => Sets the information about emergency contact for the customer which includes first name, last name and phone.
- `addMinor(MINOR_FIRST_NAME: String, MINOR_LAST_NAME: String, MINOR_DATE_OF_BIRTH: Date)` => Adds the children of the customer who is signing the waiver form. 
- `clearMinors()` => Clears all the minor's information that has been added.
- `clearAllData()` => Clears out all the data if set before including name, phone, email, etc.

### NOTE: First Name, Last Name, and Phone must be provided for signing the waiver successfully.

6. To display the waiver following method can be called which presents the waiver screen to the customer for signing it and processing.
```
YombuWaiver.getInstance().displayWaiver(YombuWaiverProcessingCallback);
```
Waiver processing callback is an interface available in com.yombu.waiverlibrary.callbacks.YombuWaiverProcessingCallback which has two methods (i) `onWaiverSuccess()` -> Called if the processing of waiver is successful and (ii) `onWaiverFailure(String message)` -> Called if waiver processing fails with an error message as a parameter.
