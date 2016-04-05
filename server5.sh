#!/bin/bash
java -Djava.security.policy="server.policy" -Djava.security.manager -DLEVEL=ALL -jar MobilagentServer.jar Repository/Configurations/hostel.server4.xml server4
