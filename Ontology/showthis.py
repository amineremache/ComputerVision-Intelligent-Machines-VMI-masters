from google import search
import wikipedia
import os
import codecs
import sys

foo = open("original.txt", 'r')

file = codecs.open("example.txt", "ab+", "utf-8")

string = foo.read()
file.write(string)