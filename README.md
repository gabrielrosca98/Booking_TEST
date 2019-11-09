# Booking_Test
Booking test submission. Used IntelliJ IDEA 2019.2.4 (Ultimate Edition) for this project. 
# Part 1
## Run this command together with the program arguments:
`/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=51101:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 @/private/var/folders/1z/qryb9njn3nj6frsbttkk6vgw0000gn/T/idea_arg_file398373038 Booking.Main`

## Program arguments:
### Part 1 - Search for all taxis with no passenger limit
pickupLat pickupLon dropoffLat dropoffLon 

Example:
`/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=51082:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 @/private/var/folders/1z/qryb9njn3nj6frsbttkk6vgw0000gn/T/idea_arg_file478399105 Booking.Main 3.410632 -2.157533 3.410632 -2.157533`

### Part 1 - Search for all taxis with passenger limit
pickupLat pickupLon dropoffLat dropoffLon 

Example:
`/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=51113:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 @/private/var/folders/1z/qryb9njn3nj6frsbttkk6vgw0000gn/T/idea_arg_file867992081 Booking.Main 3.410632 -2.157533 3.410632 -2.157533 5`

### Part 1 - Search for a specific taxi provider taxis with passenger limit
pickupLat pickupLon dropoffLat dropoffLon numberPassengers providerID

Example:
`/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=51101:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 @/private/var/folders/1z/qryb9njn3nj6frsbttkk6vgw0000gn/T/idea_arg_file398373038 Booking.Main 3.410632 -2.157533 3.410632 -2.157533 3 dave`

# Part 2 - Search using the API
## Run 
`/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=51125:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 @/private/var/folders/1z/qryb9njn3nj6frsbttkk6vgw0000gn/T/idea_arg_file527926030 Booking.Application`

## Parameters
pickupt/dropoff/numberpassengers/providerid

Examples:


`http://localhost:8080/price?pickup=3.410632,-2.157533&dropoff=3.410632,-2.157533&numberpassengers=2`


`http://localhost:8080/price?pickup=3.410632,-2.157533&dropoff=3.410632,-2.157533&numberpassengers=2&provider_id=dave`




