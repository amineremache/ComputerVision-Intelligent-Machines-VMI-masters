from google import search
import wikipedia
import os
import codecs
import sys

foo = open("original.txt", 'r')
original = foo.read().split(" ")
f = open("expansion.txt", 'r')
res = "No results found!"

try:
    if os.stat("expansion.txt").st_size != 0:
       word = f.read().split(") or (")
       res = "something"
    else:
       res = "No results found!"
except OSError:
    res = "Expansion file missing!"


mylist = []

for w in word:
      # remove some minor errors
      w = w.replace(")", "")
      w = w.replace("(", "")
      w = w.replace(" and", "")
      mylist.append(w)



#--------------------------------------------------------------------------
mylist = list(set(mylist))
print(mylist)
result = []
final = []
perm = False
for w in mylist:
        try:
            for url in search(w+' wikipedia', stop=1):
                 if "wikipedia" in url:
                     result.append(url)

            final.append(result[0])
            result[:] = []




        except:
                pass



# ny = wikipedia.summary("Conical pendulum", sentences=5, redirect=True, auto_suggest=False)
# print(ny)
#
#file.write(article)


final = list(set(final))
result = list(set(result))

print(result)

for r in final:
     print(r)
     query = r
     print(query[30:])
     encoded = query[30:]
     print(wikipedia.summary(encoded, sentences=2))



