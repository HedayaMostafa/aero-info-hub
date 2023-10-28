# Flight Information Service(Aero Info Hub)

This Java Spring application provides two main functionalities for querying flight and airport information:

1. **Flight Information**
   - Given a Flight Number and Date, this service provides the following details:
     - Cargo Weight for the requested flight.
     - Baggage Weight for the requested flight.
     - Total Weight for the requested flight.

2. **Airport Information**
   - Given an IATA Airport Code and Date, this service provides the following details:
     - Number of flights departing from this airport.
     - Number of flights arriving to this airport.
     - Total number (pieces) of baggage arriving at this airport.
     - Total number (pieces) of baggage departing from this airport.

## Getting Started

### Prerequisites
- Java Development Kit (JDK)
- Spring Boot
- JUnit and Mockito for testing


API Endpoints
Get Flight Information
Endpoint: /flight-info
HTTP Method: GET
Parameters:
flightNumber (String): The flight number.
date (String): The date in the format "YYYY-MM-DD".
Get Airport Information
Endpoint: /airport-info
HTTP Method: GET
Parameters:
iataCode (String): The IATA airport code.
date (String): The date in the format "YYYY-MM-DD".


