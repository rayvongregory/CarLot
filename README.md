# Car Lot

An app I've been building while on GenSpark Bench. This application allows users to buy and sell used cars.

## Guest Permissions

1. Allowed to search and view cars that are being sold by other users
2. Allowed to access the **/login** and **/register** pages

## Guest Restrictions

1. Not allowed to view seller contact information
2. Not allowed to see the asking price any car
3. Not allowed to sell your car on the app
4. Not allowed to interact with any other users
5. Not allowed to access the **/list**, **/dms**, **/account**, or **/requests** pages

## Registered User Permissions

1. Allowed to search and view cars that are being sold by other users
2. Allowed to see seller contact information
3. Allowed to interact with other registered users \*
4. Allowed to view and edit your account and contact information
5. Allowed to request to have your car listed on the app.
6. Allowed to view edit history for any of your car listings
7. Allowed to revert back to a previous listing
8. Allowed to access the **/list** and **/account** pages

\* If a user feels threatened or harassed, they are allowed to report the offending user. Conversation will be sent to administrator for evaluation.

## Registered User Restrictions

1. Not allowed to list your car directly on the app \*\*
2. Not allowed to access the **/login** and **/register** pages

\*\* Instead, you must wait for an administrator to approve your listing before it is viewable to others. If your listing is not approved, the listing will be returned to you to edit or delete. Once your car has been listed, you may delete it without needing approval from an adminstrator, but all edits made to public listings must be approved by an administrator.

## Administration Permissions

1. Allowed to search and view cars that are being sold by other users
2. Allowed to approve or deny requests to list cars or edit existing listings \*\*\*
3. Allowed to switch between **_user mode_** and **_admin mode_** to be instantly granted user permissions or admin permissions

\*\*\* If you choose to deny a request, you can provide the user with feedback on how they can improve their listing. If a user's list request or edit request is deemed inappropriate, administrators are allowed to suspend that user's account for up to 30 days.

### Authentication

When a user logs in, two JWTs are generated - a **_refresh token_** and an **_access token_**. The refresh token is stored on the backend and is set to expire in 7 days. The access token is sent to the frontend to be stored in the browser and expires in 15 minutes. The access token contains data used to authenticate the user and grant/deny access to pages based on their **role** (guest, user, or admin).

Each time the application user navigates to a new page, their authentication token is sent to the backend to be authenticated. If a user tries navigating to a page they are not allowed to access, they will either by redirected to the **/login** page or to the root page. If the access token has been tampered with in any way, the user will be immediately logged out of the application.
