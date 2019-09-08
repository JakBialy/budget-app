# budget-app

Amateur budget-app, this is a back-end part of application, front-end application code is available at https://github.com/saraems/Budget with deployed front-end application on https://saraems.github.io/Budget/#/.

Current features:
 * user registration
 * login (with passing basic user details for front-end client)
 * logout with success/failer handlers
 * CSV file reader for bank expenses from .csv bank sheets (currentyl for 2 polish banks: Mbank and Eurobank) being able to return parsed data in form of JSON to front-end (or any) client
 
 CRUD ready:
 * Create: service able to receive expenses data, parse them and save them into DB for current user
 * Read: two options of reading data, one which is reading all for given user, second which is giving response with data for given time interval
 * Update: updating data for given list of operations for user
 * Delete: removing given list of operations id for a user, or (dangerous one!) removal of all operations for a user
 
 More specific helpfull functionalities going on!

Back-end application is deployed on heroku.
