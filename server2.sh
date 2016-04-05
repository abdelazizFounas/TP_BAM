#!/bin/bash
java -Djava.security.policy="server.policy" -Djava.security.manager -DLEVEL=ALL -jar MobilagentServer.jar Repository/Configurations/hostel.server1.xml server1
