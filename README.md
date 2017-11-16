# EliteBoyServer
[![Build Status](https://travis-ci.org/pr0ves/EliteBoyServer.svg?branch=develop)](https://travis-ci.org/pr0ves/EliteBoyServer)

## Usage
Clone this repo and run `gradlew bootRun` from console.

## Using the REST Api
### Authentication

The server uses a simple authentication to use the api.

You need to follow these steps to get authenticated:
* Send a json with POST to `/api/auth` with:
  - if it's the first time authenticating  ```{"name" = CMDRName}```
  - if already authenticated in past ```{"name" : CMDRName, "pass": password }```
  
* This returns:
  - if it's the first time, a json with: ```{"message" : "Success", "restPassword": password , "token": token}```
  - if already authenticated, a json with: ```{"message" : "Success", "token": token}```
  - an error message if `CMDRName` is not in the possible commander list; this list is made while the server creates the db reading the journals.

* Once obtained the token, this must be added to every request made in the `X-EBOY-ACCESS-TOKEN` header 

The token must be requested every time a session is started.

### REST API Endpoints
* Commander endpoints:
  - POST `/api/auth`
  - GET `/api/cmdr/getEntries` => Returns 500 journal entries from the db depending from `page`.
  Parameters: 
    + `page`: Int, default value 0
  - WebSocket `/api/cmdr/listenForEntries` => This send the entries as they are read from the journal through a web socket. 
  Whoever connects here, also needs to subscribe to `/api/stomp/entries` to receive the entries.
  
* EDCompanionApi endpoints:
  - POST `/api/cmdr/edapi/login` => Allows to login to the Companion api. 
  A json is needed in the request body with: ```{"email " : $your_email, "password": $your_password}```
  - GET `/api/cmdr/edapi/confirm` => Needed for confirmation.
  Parameters:
    + `code`: String, required
  - GET `/api/cmdr/edapi/getProfile` => Returns the profile retrieved with the api.
  - GET `/api/cmdr/edapi/getMarket` => Returns the market retrieved with the api.
  - GET `/api/cmdr/edapi/getShipyard` => Returns the shipyard retrieved with the api.
  
* EDSMApi endpoints:
  - GET `/api/cmdr/edsm/setApiKey` => Sets the EDSM api key and commander name.
  Parameters:
    + `cmdr`: String, required
    + `apiKey`: String, required
  - GET `/api/cmdr/edsm/getSystem` => Retrieves full information about a system. The name must be exactly the same.
  Parameters:
    + `system`: String
  - GET `/api/cmdr/edsm/findSystemsByName` => Retrieves basic information of systems that starts with `name`.
  Parameters:
    + `name`: String
  - GET `/api/cmdr/edsm/findSystemInSphere` => Retrieves basic information of systems in sphere. One between `system` or the coordinates must be provided.
  Parameters:
    + `system`: String
    + `x`: Double
    + `y`: Double
    + `z`: Double
    + `radius`: Int
  - GET `/api/cmdr/edsm/findSystemsInCube` => Retrieves basic information of systems in cube. One between `system` or the coordinates must be provided.
  Parameters:
    + `system`: String
    + `x`: Double
    + `y`: Double
    + `z`: Double
    + `size`: Int
