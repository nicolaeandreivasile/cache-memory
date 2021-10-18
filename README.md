# Cache Memory Simulator

## Description
This is a simulator of 3 types of Cache memory:
- **FIFO** - first in, first out
- **LRU** - least recently used
- **LFU** - least frequently used

####
Each type of memory can store one or more subscriptions. There are 3 types of subscriptions:
- **FREE** - unlimited requests
- **BASIC** - limited number of requests
- **PREMIUM** - limited number of requests.

####
If a subscription uses all the premium requests, it becomes basic, and the same will happen from basic to free.

## Operations
- ADD <name_subscription> <basic_requests> [premium_requests]
- GET <name_subscription>

###
ADD operation adds the subscription in main memory. If it exists there, it is replaced in main memory, and it is erased from cache, if it exists in cache too. GET operation uses a subscription from cache, or it puts subscriptions in cache and use it if it doesn't already exists there. It returns a number (0 - subscription exists in cache, 1 - subscription doesn't exist in cache, 2 - susbscription doesn't exist in main memory) and a type of subscription used (free, basic, premium). 


## Usage
The program will run as following:
1. compile and run from command prompt using given Makefile:
	- **make**: compiles .java files;
	- **java -cp classes cachememory.Main INPUT_PATH=enter_input_path OUTPUT_PATH=enter_output_path**: executes the program;
	- **make clean**: removes .class files;

## Input
The input file contains:
- type of cache memory used (first line)
- maximum space of given cache memory (second line)
- number of operations (third line)
- operations (the ohter lines)

## Output
The output is redirected to the output file.
