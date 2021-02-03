import os
import sys

#this gets the query from php
query2 = sys.argv[1]

print("Pyhton test run:\n")
Query = "how bout now: "
destination_file = open("example.txt", "a")
destination_file.write(Query)
destination_file.write(query2)
