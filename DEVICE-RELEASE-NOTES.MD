5.1 (10-Aug-2017)

User story # 201 [ R&R information to be loaded by schedule and cached in DAL. Access via DAL Product APIs 
]

User story # 275

[ 
Create new API to implement below logic recommend a plan which is closest data to the given plan in features but within given credit limit.
3 Services extended getBundleList getCompatiblePlansList getCompatibleBundleDetails 
] 
User story # 314 
[ NEW cachePricesPaym API - calculate prices for lead device and lead plans after MP have been applied, 
input from pricing API, the result has to be pushed back to Solr. ]

----------------------------------------Sprint 5.1 Ends---------------------------------------------
5.2 (24-Aug-2017)

*Bug Fixes: *

Defect ID # 2450 
[
KeepDeviceChangePlan- Wrong error message displayed 
] 
Defect ID # 2924
[
API output diaplayed Invalid DeviceID
]
5.2 (24-Aug-2017)

User story # 1909 
[ PAYM_Tech_fetching_prices_Intermediate_tables: Feature description:
Update APIs to fetch pricing from the intermediate tables to display in the listing and details page.
API extension getdeviceList 
]

User story # 509
[
Digital_Device_Details_Ratings_And_Reviews_Panel: To view ratings and reviews for the device I have selected.
] 
User story # 2259
[ 
Device Listing Ratings Display: To view ratings and reviews for the list of device I have selected. 
]

----------------------------------------Sprint 5.2 Ends---------------------------------------------
5.3(07-Sept-2017)

*Bug Fixes: *

Defect ID # 4117
 [ 
  No of records found is not equal to no. of device in response. 
 ]
5.3 (07-Sept-2017)

User story # 3316
 [ 
Enhance CacheDeviceList: Enhance the current CacheDeviceList api to calculate the prices for all the device variants in a product group 
2) Find the bundle with minimum recurring price in the product group and send it to solr 
3) Add new column(s) in the intermediate pricing table
 [ lead plan id = Yes / No] , [ Min monthly cost]

] 
User story # 1909 
[ 
PAYM_Tech_fetching_prices_Intermediate_tables: Feature description: 
Update APIs to fetch pricing from the intermediate tables to display in the listing and details page. 
] 
User story # 509 
[ 
Digital_Device_Details_Ratings_And_Reviews_Panel: To view ratings and reviews for the device I have selected. 
] 
User story # 2259 
[ 
Device Listing Ratings Display: To view ratings and reviews for the list of device I have selected.
]

Prerequisites for CacheDeviceTile:

    After Loading the Canonical and MEF files into SOLR and COHERENCE.
    Deploy the Price Service.
    Deploy the Bundle Service.
    Deploy the Utility Private Scheduler Service
    Deploy the Device Service.
    Execute the DB scripts for Following tables(Please find the scripts in git(DigitalDb/Product/SQLS/5.3.0)) => DEVICE_LIST_PRE_CALC_DATA (DevicePreCalcData.sql) => DEVICE_LIST_PRE_CALC_MEDIA (DevicePreCalcMedia.sql) => DEVICE_OFFERAPPLIED_PRICE_DATA (DeviceOfferAppliedData.sql)
    Execute the DB scripts to Grant the Permissions to the daluser . Please find the Scripts in DigitalDb/Product/SQLS/5.3.0/GrantPermissionsScripts.sql
    Execute Exception Message Scripts from DigitalDb/daluser/release5.3/Devicelast10.sql Run the Below APIs :

CalculateForBundleAndHardware Price URL without Offer Code: https://internal-digitalx-ist-alb-mas-1955976139.eu-west-1.elb.amazonaws.com/price/calculateForBundleAndHardware payload: { "offerCode": null, "bundleAndHardwareList": [{ "bundleId": "110105", "hardwareId": "088274" } ] }

Run the CachceDeviceTile API Manually: POST: https://internal-digitalx-ist-alb-mas-1955976139.eu-west-1.elb.amazonaws.com/devices/catalogue/cacheDeviceTile/?filter[groupType]=DEVICE_PAYM After that Create Job for cacheDeviceTile Every 6 Hours Using Below URL: CreateJob: POST: https://internal-digitalx-ist-alb-mas-1955976139.eu-west-1.elb.amazonaws.com/scheduler/private/utility/scheduler/job payload: {
"jobGroup":"REST", "jobDescription":"CacheDeviceTile_JOB", "triggerDescription":"GEN_CACHEDEVICETILE_EVERY6HOURS", "cronExpression":"0 0 0/6 * * ?", "jobData": { "url":"https://internal-digitalx-ist-alb-mas-1955976139.eu-west-1.elb.amazonaws.com/devices/catalogue/cacheDeviceTile/?filter[groupType]=DEVICE_PAYM", "body":"na", "methodType":"POST"

            }

}

NOTE:"cronExpression":0 35 5/6 * * ? every 6 hours 35 is current minutes 5/6 means exactly 6 hours.

Delete Job: DELETE: https://internal-digitalx-dev-alb-mas-734979285.eu-west-1.elb.amazonaws.com/scheduler/private/utility/scheduler/job/REST:JOB_1153 JOB_1153->JOB_ID

----------------------------------------Sprint 5.3 Ends---------------------------------------------
5.4(21-sept-2017)

User story # 3334

[
 Journey context aware apis for PayM handsets. Instead of passing journey Id We are sending journey type and offercode 
]

User story # 4380

[ 
Enhance CacheDeviceList to get prices related to offer codes. This user story we need to indexing offer related price to solr. 
]

User story # 4385

[ 
Populate journey aware price details from Solr for Paym Handsets. we need to take offer realted price from SOLR and integrate that price to solr 
]

User story # 4798

[ 
Performance Improvement for PayM handsets apis. We are working on several services for performance improvement.
 Like getDeviceList,get deviceByMakeAndModel etc 
]

----------------------------------------Sprint 5.4 Ends---------------------------------------------
6.1(05-Oct-2017)

User story # 4385

[ 
Populate journey aware price details from Solr for Paym Handsets. we need to take offer realted price from SOLR and integrate that price to solr
]

User story # 4380

[ 
Enhance CacheDeviceList to get prices related to offer codes. This user story we need to indexing offer related price to solr. 
]

User story # 455

[ 
Digital Device Insurance. This Userstory for return insurance list for a particular device. 
]

User story # 1104

[ 
Digital Device Insurance. This Userstory for return insurance list for a particular device. 
]

User story # 4798

[
 Performance Improvement for PayM handsets apis. We are working on several services for performance improvement.
 Like getDeviceList,get deviceByMakeAndModel etc 
]

User story # 452

[ 
Digital Device extras. Based on journey type and sellable ret and displayable ret we are returning result. 
]

*Bug Fixes: *

Defect ID # 5886 
[ 
DeviceTile API for Device Listing Page is not displayed the Phones on Priority Order and not displaying the based on Pagination
]

Defect ID # 6234 
[ 
Duplicate data is Populating in intermediate table. 
]

Prerequisites for CacheDeviceTile:

    After Loading the Canonical and MEF files into SOLR and COHERENCE.
    Deploy the Price Service.
    Deploy the Bundle Service.
    Deploy the Utility Private Scheduler Service
    Deploy the Device Service.
    Execute the DB scripts to Grant the Permissions to the daluser . Please find the Scripts in DigitalDb/Product/SQLS/5.3.0/GrantPermissionsScripts.sql
    Execute Exception Message Scripts from DigitalDb/daluser/release6.1/ExceptionMessages.sql Run the Below APIs :

CalculateForBundleAndHardware Price URL without Offer Code: https://internal-digitalx-ist-alb-mas-1955976139.eu-west-1.elb.amazonaws.com/price/calculateForBundleAndHardware payload: { "offerCode": null, "bundleAndHardwareList": [{ "bundleId": "110105", "hardwareId": "088274" } ] }

Run the CachceDeviceTile API Manually: POST: https://internal-digitalx-ist-alb-mas-1955976139.eu-west-1.elb.amazonaws.com/devices/catalogue/cacheDeviceTile/?filter[groupType]=DEVICE_PAYM After that Create Job for cacheDeviceTile Every 6 Hours Using Below URL: CreateJob: POST: https://internal-digitalx-ist-alb-mas-1955976139.eu-west-1.elb.amazonaws.com/scheduler/private/utility/scheduler/job payload: {
"jobGroup":"REST", "jobDescription":"CacheDeviceTile_JOB", "triggerDescription":"GEN_CACHEDEVICETILE_EVERY6HOURS", "cronExpression":"0 0 0/6 * * ?", "jobData": { "url":"https://internal-digitalx-ist-alb-mas-1955976139.eu-west-1.elb.amazonaws.com/devices/catalogue/cacheDeviceTile/?filter[groupType]=DEVICE_PAYM", "body":"na", "methodType":"POST"

            }

}

NOTE:"cronExpression":0 35 5/6 * * ? every 6 hours 35 is current minutes 5/6 means exactly 6 hours.

Delete Job: DELETE: https://internal-digitalx-dev-alb-mas-734979285.eu-west-1.elb.amazonaws.com/scheduler/private/utility/scheduler/job/REST:JOB_1153 JOB_1153->JOB_ID

----------------------------------------Sprint 6.1 Ends---------------------------------------------
6.2(19-Oct-2017)

User story # 6840

[ 
Currently we check only Start date and End date of promotions and not the timestamp 
]

User story # 7125

[ 
Pricing api will be updated to use the Package type now , so we need to integrate with pricing api from browse side for PAYM 
]

User story # 6833

[ 
Device Listing + Get Bundle List(SIMO) code refactoring story. 
]

User story # 6786

[ 
Need to revisit design to improve the performance of GetDeviceDetails service Current Response = 700-800 MS for since user.
]

*Bug Fixes: *

Defect ID # 8058 
[ 
Regression Defect-groupName,groupType attributes are coming as null in JSON response for GetDeviceTileById MS 
]

Defect ID # 7526 
[
 PriceInfo for getDeviceTile is null on passing JourneyType and offerCode parameters 
]

Defect ID # 7597 
[ 
Regression Defect -sort parameter is giving invalid response inDevice List API
]

Defect ID # 7812 
[
 Regression Defect -Device Name for all the device in Device List API is same 
]

Defect ID # 8163
[
 Getcompatibleinsurance not working with Journey Type - conditionalaccept
]

-------------------------------------------Sprint 6.2 Ends----------------------

6.3(02-Nov-2017)
-----------------

User story # 6717

[ 
Make the getcompatibleaccessories api journeyaware 
]
URL for above US:
https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/accessory/queries/byDeviceId/?deviceId={deviceId}&journeyType={journeyType}

User story # 7877

[ 
Image URLs should be picked up from MEF for insurance products 
]
URL for above US:
https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/insurance/queries/byDeviceId/?deviceId={deviceId}

User story # 8806

[ 
Update APIs to not block User Journeys on error. 
]
URL for above US:
https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/deviceTile/?make=apple&productClass=HANDSET&sort=Priority&pageSize=9&pageNumber=0&groupType=DEVICE_PAYM&mustHaveFeatures=Great camera&journeyType={journeyType}&offerCode={offerCode}

https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/deviceTile/queries/byMakeModel/?make=apple&model=iPhone-7&groupType=DEVICE_PAYM&journeyType={journeyType}&offerCode={offerCode}

https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/device/{deviceId}?journeyType={journeyType}&offerCode={offerCode} 

User story # 8361
[
Journey context aware secondline promotions 
]
URL for above US:
https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/deviceTile/?make=apple&productClass=HANDSET&sort=Priority&pageSize=9&pageNumber=0&groupType=DEVICE_PAYM&mustHaveFeatures=Great camera&journeyType={journeyType}&offerCode={offerCode}

https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/deviceTile/queries/byMakeModel/?make=apple&model=iPhone-7&groupType=DEVICE_PAYM&journeyType={journeyType}&offerCode={offerCode}

https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/device/{deviceId}?journeyType={journeyType}&offerCode={offerCode} 

User story # 9116
[
 GetDevice- Journey aware context
]

URL for above US:
https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/deviceTile/queries/byMakeModel/?make=apple&model=iPhone-7&groupType=DEVICE_PAYM&journeyType={journeyType}&offerCode={offerCode}

https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/device/{deviceId}?journeyType={journeyType}&offerCode={offerCode} 

https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/deviceTile/queries/byDeviceVariant/?deviceId={deviceId}&journeyType={journeyType}

*Bug Fixes: *
Defect ID # 10411 
[ 
Get CompatibleAccessories not journey aware :: (deviceID - 090853)
]
Defect ID # 10460 
[ 
SIT2-US 8806 - getDeviceTile API returning Error message for Invalid Journey 
]
Defect ID # 10482 
[ 
DeviceDetails-Invalid JourneyType should throw error instead of success response with Null values.
]
Defect ID # 10480 
[ 
DeviceTileByDeviceVarient-Invalid JourneyType should throw error instead of success response with Null values.
]

-------------------------------------------Sprint 6.3 Ends---------------------------------------------------
6.4(16-Nov-2017)
-----------------
User story # 11027
[ 
Compatible Insurance check for sales expiry 
]
URL for above US:

https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/insurance/queries/byDeviceId/?deviceId=093353&journeyType={JourneyType}

User story # 10443
[ 
Make getCompatibleAccessories offer code aware
]
URL for above US:
https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/accessory/queries/byDeviceId/?deviceId=093353&journeyType={journeyType}&offerCode={offerCode}

User story # 10433
[ 
Change apis to address COH changes on Product Group
]
URL for above US:
https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/accessory/queries/byDeviceId/?deviceId=093353&journeyType={journeyType}&offerCode={offerCode}

https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/insurance/queries/byDeviceId/?deviceId=093353&journeyType={journeyType}

User story # 7396
[ 
GetDeviceTile to expose MediaLinks 
]

URL for above US:

https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/deviceTile/?productClass=HANDSET&sort=Priority&groupType=DEVICE_PAYM 

6.5(30-Nov-2017)
-----------------
User story # 12564
[ 
GetDeviceTile to expose MediaLinks - non pricing promotions
]

URL for above US:

https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/deviceTile/?productClass=HANDSET&sort=Priority&pageSize=9&pageNumber=0&groupType=DEVICE_PAYM

Defect ID # 12468
[ 
Labels of hardware specific promotions- ExtraDiscount & AccessoryDiscount are not visible in solr after hitting cacheDeviceTile and in GetDeviceList response.
]

URL for above Defect:

https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/deviceTile/?productClass=HANDSET&sort=Priority&pageSize=9&pageNumber=0&groupType=DEVICE_PAYM

6.6(14-Dec-2017)
-----------------
User story # 11285
[ 
Compatible Accessories check for salesexpiry
]
https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/accessory/queries/byDeviceId/?deviceId=093353&journeyType=Upgrade&offerCode=W_HH_PAYM_01

User story # 12903
[ 
make getDeviceList journey aware
]
https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/deviceTile/?productClass=HANDSET&sort=Priority&pageSize=100&pageNumber=0&groupType=DEVICE_PAYM&journeyType={journeyType}&offerCode={offerCode}

User story # 14321
[ 
CacheDevice/DeviceList  to implement solr changes for journeytype 
]
https://internal-digitalx-sit2-alb-mas-1938696969.eu-west-1.elb.amazonaws.com/devices/catalogue/deviceTile/cacheDeviceTile/?filter[groupType]=DEVICE_PAYM
