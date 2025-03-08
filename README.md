# Traffic Simulation Algorithm

## Overview

The traffic simulation system models the flow of traffic at an intersection with four roads (north, south, east, west). The algorithm dynamically adjusts traffic lights based on the average waiting times of vehicles. The goal is to optimize the traffic flow by giving green lights to the roads with the highest average delay, ensuring smoother passage for vehicles.

## How It Works

### 1. **Calculating Average Delay**
- For each road, the system calculates the average waiting time of all vehicles.
- The average waiting time is determined by the difference between the current simulation time and the time a vehicle first enters the queue.
- The road with the highest average waiting time will get the green light in the current step.

### 2. **Changing Traffic Lights**
- Initially, all traffic lights are set to red for all roads.
- The road with the highest average delay (calculated in the previous step) will be given the green light.
- All other roads will have their traffic lights set to red.
- This dynamic approach helps prioritize traffic where vehicles have been waiting the longest.

### 3. **Processing Vehicles**
- For the road with the green light, vehicles are processed.
- Vehicles are removed from the queue and pass through the intersection.
- The vehicle IDs of all cars that pass during this step are added to a list called `leftVehicles`.

### 4. **Recording Results**
- After each simulation step, the system records the list of vehicles that passed through the intersection in that step.
- This list of vehicle IDs is stored in the `leftVehicles` field for that step.


## To make a simulation
```bash
localhost:8080/api/traffic/processCommands?filePath=${filepath}/commands.json
```

## JSON Input Format

```json
{
    "commands": [
        {
            "type": "addVehicle",
            "vehicleId": "vehicle1",
            "startRoad": "south",
            "endRoad": "north"
        },
        {
            "type": "addVehicle",
            "vehicleId": "vehicle2",
            "startRoad": "north",
            "endRoad": "south"
        },
        {
          "type": "step"
        },
        {
          "type": "step"
        },
        {
            "type": "addVehicle",
            "vehicleId": "vehicle3",
            "startRoad": "west",
            "endRoad": "south"
        },
        {
            "type": "addVehicle",
            "vehicleId": "vehicle4",
            "startRoad": "west",
            "endRoad": "south"
        },
        {
          "type": "step"
        },
        {
          "type": "step"
        }
    ]
}
```
## JSON Output Format



```json
{
  "stepStatuses": [
    {
      "leftVehicles": [
        "vehicle1",
        "vehicle2"
      ]
    },
    {
      "leftVehicles": []
    },
    {
      "leftVehicles": [
        "vehicle3"
      ]
    },
    {
      "leftVehicles": [
        "vehicle4"
      ]
    }
  ]
}
'''
