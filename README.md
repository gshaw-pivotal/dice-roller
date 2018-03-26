# DiceRoller #

A simple spring-boot Java application that can be used to generate dice rolls. Uses REST to receive requests for rolls and supports receiving either a single die type (eg. a 6 sided die, known as a d6) or multiple die types within a single request.

## Getting Started ###

```
    git clone https://github.com/gshaw-pivotal/dice-roller.git
```

## Sending Requests

The following is the expected format to send a request for one or more rolls of a single sie type:

```json
{
	"dieType":6,
	"rollCount":10
}
```

The following is the expected format to send a request for one or more rolls of multiple die types:

```json
{
	"dies":
	[{
	    "dieType":6,
	    "rollCount":10
	},
	{
	    "dieType":4,
	    "rollCount":10
	}]
}
```

- `dies` is the key for the list that contains the dies you wish to roll.
- `dieType` is an integer that indicates how many sides the die has.
- `rollCount` is an integer that indicates how many times you wish for the die to be rolled.

## Receiving Responses

The application will respond back a response structured in the same manner as the request it received expect it will contain extra details, including a list of the roll results and statistics about the rolls made. An example of a response follows:

```json
{
    "dieType": 6,
    "rollCount": 10,
    "rollResults": [
        6,
        4,
        6,
        5,
        3,
        3,
        1,
        3,
        3,
        6
    ],
    "dieRollStats": {
        "sum": 40,
        "max": 6,
        "min": 1,
        "mean": 4,
        "median": 3.5,
        "rollValueOccurrence": {
            "1": 1,
            "2": 0,
            "3": 4,
            "4": 1,
            "5": 1,
            "6": 3
        }
    }
}
```

- `rollResults` is a list that contains the results for the number of rolls requested.
- `dieRollStats` is the key for the object that holds various statistics about the roll results.
- `rollValueOccurrence` is the key for the object that holds the frequency statistics of all the possible values that could have been rolled.

## Notes ##

- A manifest file is present to support deployments to cloudfoundry.