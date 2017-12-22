# Danta - WebServices Project

Danta is the agnostic multi-platform templating engine. enables developers and IT teams to use technologies they already know, expediting the creation and leveraging of reusable technical assets.

Danta - WebServices Project provides access to Danta via pure web services.

## Prerequisites

 * [Danta - Parent Project](https://github.com/DantaFramework/Parent)
 * [Danta - API Project](https://github.com/DantaFramework/API)
 * [Danta - Core Project](https://github.com/DantaFramework/Core)
 * Java 8
 * Karaf 4.0.9 or later

## Documentation

### Installation

   * Clone the following repositories into the same folder (i.e. C:\workspace\danta or /User/{username}/workspace/danta)
   then run the maven build command (refer to **Compile** section of README.md, for each repository) in the following order
     * [Parent](https://github.com/DantaFramework/Parent)
     * [API](https://github.com/DantaFramework/API)
     * [Core](https://github.com/DantaFramework/Core)
     * [WebServices](https://github.com/DantaFramework/WebServices)

## License

Read [License](LICENSE) for more licensing information.

## Compile

    mvn clean install

## Deploy to Karaf

    mvn clean install -Pdeploy-karaf

## Credit

Special thanks to Jose Alvarez, who named Danta for the powerful ancient Mayan pyramid, La Danta.
La Danta is the largest pyramid in El Mirador—the biggest Mayan city found in Petén, Guatemala.